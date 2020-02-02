package org.zafritech.zscode.todos.services.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.zscode.todos.ScheduledTasks;
import org.zafritech.zscode.todos.data.daos.TasksRequestDateDao;
import org.zafritech.zscode.todos.data.daos.TasksRequestRangeDao;
import org.zafritech.zscode.todos.data.models.Category;
import org.zafritech.zscode.todos.data.models.Repeat;
import org.zafritech.zscode.todos.data.models.Schedule;
import org.zafritech.zscode.todos.data.models.Task;
import org.zafritech.zscode.todos.data.models.TaskLog;
import org.zafritech.zscode.todos.data.repositories.CategoryRepository;
import org.zafritech.zscode.todos.data.repositories.RepeatRepository;
import org.zafritech.zscode.todos.data.repositories.ScheduleRepository;
import org.zafritech.zscode.todos.data.repositories.TaskLogRepository;
import org.zafritech.zscode.todos.data.repositories.TaskRepository;
import org.zafritech.zscode.todos.enums.Priority;
import org.zafritech.zscode.todos.services.TodosService;
import org.zafritech.zscode.todos.utils.TimeUtils;

@Service
public class TodosServiceImpl implements TodosService {

	@Autowired
    private TimeUtils timeUtils;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private RepeatRepository repeatRepository;
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private TaskLogRepository taskLogRepository;

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	
	private static final Integer daysLookAhead = 428;
	
	@Override
	public List<Task> filterTaskByCategory(String filter) {
		
		if (filter.equals("all")) {
		
			return taskRepository.findAll();
			
		} else {
			
			return taskRepository.findAllByCategory(filter);
		}
	}
	
	@Override
	public List<Schedule> findTaskByDate(TasksRequestDateDao dao) {

		List<Schedule> schedules = new ArrayList<>();
		
		ZonedDateTime zonedDate = timeUtils.parseZonedDateTime(dao.getDate()); 
		Date start = Date.from(zonedDate.with(ChronoField.HOUR_OF_DAY, 0).toInstant());
		Date end = Date.from(zonedDate.with(ChronoField.HOUR_OF_DAY, 23).toInstant());
		
		if (dao.getFilter() == null || dao.getFilter().equalsIgnoreCase("All")) {
		
			schedules = scheduleRepository.findByTimeBetweenAndDoneOrderByTimeAsc(start, end, false);
		
		} else {
			
			Category category = categoryRepository.findFirstByNameIgnoreCase(dao.getFilter());
			schedules = scheduleRepository.findByTimeBetweenAndTaskCategoryAndDoneOrderByTimeAsc(start, end, category, false);
		}
		
		return schedules;
	}
	
	@Override
	public List<Schedule> findTaskByDateRange(TasksRequestRangeDao dao) {
		
		List<Schedule> schedules = new ArrayList<>();
		
		ZonedDateTime zonedStartDate = timeUtils.parseZonedDateTime(dao.getStartDate()); 
		ZonedDateTime zonedEndDate = timeUtils.parseZonedDateTime(dao.getEndDate()); 
		Date start = Date.from(zonedStartDate.with(ChronoField.HOUR_OF_DAY, 0).toInstant());
		Date end = Date.from(zonedEndDate.with(ChronoField.HOUR_OF_DAY, 23).toInstant());
		
		if (dao.getFilter() == null || dao.getFilter().equalsIgnoreCase("All")) {
		
			schedules = scheduleRepository.findByTimeBetweenAndDoneOrderByTimeAsc(start, end, false);
		
		} else {
			
			Category category = categoryRepository.findFirstByNameIgnoreCase(dao.getFilter());
			schedules = scheduleRepository.findByTimeBetweenAndTaskCategoryAndDoneOrderByTimeAsc(start, end, category, false);
		}
		
		return schedules;
	}
	
	@Override
	public Task createTask(String details) {

		Date due = new Timestamp(System.currentTimeMillis());
        
		Task task = new Task(details);
		task.setCategory(categoryRepository.findFirstByNameIgnoreCase("Uncategorised"));
		task = taskRepository.save(task);
		
		Schedule schedule = new Schedule(task, due);
		scheduleRepository.save(schedule);
		
		return task;
	}

	@Override
	public Task updateTask(String details, Long id) {

		Task task = taskRepository.findById(id).orElse(null);
		
		if (task != null) {
			
			task.setDetails(details);
			task = taskRepository.save(task);
		}
		
		return task;
	}

	@Override
	public Schedule completeScheduledTask(Long id) {
		
		Schedule schedule = scheduleRepository.findById(id).orElse(null);
		
		if (schedule != null) {
			
			schedule.setDone(true);
			schedule = scheduleRepository.save(schedule);
			
			TaskLog log = new TaskLog(schedule.getTask(), new Date());
			taskLogRepository.save(log);
		}
		
		return schedule;
	}

	@Override
	public Task updateTaskPriority(String priority, Long id) {

		Task task = taskRepository.findById(id).orElse(null);
		
		if (task != null) {
			
			task.setPriority(Priority.valueOf(priority));
			task = taskRepository.save(task);
		}
		
		return task;
	}

	@Override
	public void scheduleNonRepeatTasks() {
		
		List<Task> tasks = taskRepository.findByRepeat(null);
		
		for (Task task: tasks) {
			
			Schedule schedule = new Schedule(task, Date.from(task.getTarget().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
    		scheduleRepository.save(schedule);
		}
	}

	@Override
	public void scheduleFutureRepeatTasks() {

		List<Task> tasks = taskRepository.findAll();
		
		for (Task task: tasks) {
			
			if (task.getRepeat() != null) {
			
				Integer schedules = scheduleRepeatTask(task.getId(), daysLookAhead);
				
				log.info("Scheduled " + schedules + " tasks.");
			}
		}
	}
	
	@Override
	@Transactional
	public Integer scheduleRepeatTask(Long id, Integer days) {

		Integer count = 0;
		
		LocalDate todayDate = LocalDate.now();  
		LocalDate lastScheduleDate = todayDate.plusDays(days);  
		
		Task task = taskRepository.findById(id).orElse(null);
		
		if (task.getRepeat() != null) {
			
			Repeat repeat = repeatRepository.findById(id).orElse(null);
			LocalDateTime startDate = repeat.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			LocalDate nextDate = startDate.toLocalDate();
			
			if (repeat.getLastRepeat() != null) {
				
				nextDate = repeat.getLastRepeat();
			}
			
			while (nextDate.compareTo(lastScheduleDate) < 0) {
				
				if (nextDate.compareTo(todayDate) > 0) {
				
					Schedule schedule = scheduleRepository.findFirstByDateAndTask(nextDate, task);
					
					if (schedule == null) {

		        		schedule = new Schedule(task, Date.from(nextDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		        		scheduleRepository.save(schedule);
		        		
		        		count++;
		        		
						log.info(nextDate + ": Scheduled repeat task - " + task.getDetails());
					
					} else {
						
						// Skip! Already scheduled for this date
					}
					
				} else {
					
					// Skip! This date is in the past.
				}
				
				nextDate = timeUtils.nextDate(nextDate, repeat.getType(), repeat.getCount());
			}
			
			repeat.setLastRepeat(nextDate);
			repeatRepository.save(repeat);
		}
		
		return count;
	}
}
