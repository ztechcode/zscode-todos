package org.zafritech.zscode.todos.services.impl;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.zscode.todos.data.daos.TaskDao;
import org.zafritech.zscode.todos.data.daos.TasksRequestDateDao;
import org.zafritech.zscode.todos.data.daos.TasksRequestRangeDao;
import org.zafritech.zscode.todos.data.models.Category;
import org.zafritech.zscode.todos.data.models.Repeat;
import org.zafritech.zscode.todos.data.models.Schedule;
import org.zafritech.zscode.todos.data.models.Task;
import org.zafritech.zscode.todos.data.models.TaskLog;
import org.zafritech.zscode.todos.data.models.TaskNote;
import org.zafritech.zscode.todos.data.repositories.CategoryRepository;
import org.zafritech.zscode.todos.data.repositories.RepeatRepository;
import org.zafritech.zscode.todos.data.repositories.ScheduleRepository;
import org.zafritech.zscode.todos.data.repositories.TaskLogRepository;
import org.zafritech.zscode.todos.data.repositories.TaskNoteRepository;
import org.zafritech.zscode.todos.data.repositories.TaskRepository;
import org.zafritech.zscode.todos.enums.Priority;
import org.zafritech.zscode.todos.enums.RepeatType;
import org.zafritech.zscode.todos.enums.Tags;
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

	@Autowired
    private TaskNoteRepository taskNoteRepository;
	
    private static final Logger logger = LoggerFactory.getLogger(DataLoaderServiceImpl.class);

	private static final Integer daysLookAhead = 428;
	
	@Override
	@Transactional
	public Task createTask(TaskDao dao) {
		
		Repeat repeat = null;
		ZonedDateTime deadline = ZonedDateTime.parse(dao.getDeadline());
		
		if (dao.getRepeat() != null) {
    		
    		ZonedDateTime start = timeUtils.parseZonedDateTime(dao.getRepeat().getStart()); 
    		ZonedDateTime last = dao.getRepeat().getLast() == null ? start : timeUtils.parseZonedDateTime(dao.getRepeat().getLast()); 
    		RepeatType type = RepeatType.valueOf(dao.getRepeat().getType());
    		Integer count = dao.getRepeat().getCount();
    		
    		repeat = new Repeat(type, count, Date.from(start.toInstant())); 
    		repeat.setLast(Date.from(last.toInstant()));
    		repeat.setDays(dao.getRepeat().getDays()); 
    		
    		repeat = repeatRepository.save(repeat);
		}

		Task task = new Task(dao.getDetails(), timeUtils.ZonedDateTimeToDate(deadline));
    	task.setPriority(Priority.valueOf(dao.getPriority())); 
    	task.setDeadline(timeUtils.ZonedDateTimeToDate(deadline));
    	task.setRepeat(repeat);
    	
		task = taskRepository.save(task);
		
		Schedule schedule = new Schedule(task, timeUtils.ZonedDateTimeToDate(deadline));
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
	public void deleteTask(Long id) {

		Task task = taskRepository.findById(id).orElse(null);
		
		if (task != null) {
		
			List<Schedule> schedules = scheduleRepository.findByTask(task);
			
			for (Schedule schedule : schedules) {
				
				scheduleRepository.delete(schedule);
			}
			
			taskRepository.delete(task); 
		}
	}
		
	@Override
	public List<Task> filterTaskByCategory(String filter) {
		
		if (filter.equals("all")) {
		
			return taskRepository.findAll();
			
		} else {
			
			return taskRepository.findAllByCategory(filter);
		}
	}
	
	@Override
	public List<Schedule> findTaskOnDate(TasksRequestDateDao dao) {

		List<Schedule> schedules = new ArrayList<>();
		
		ZonedDateTime zonedDate = timeUtils.parseZonedDateTime(dao.getDate()); 
		Date start = Date.from(zonedDate.with(ChronoField.HOUR_OF_DAY, 0).toInstant());
		Date end = Date.from(zonedDate.with(ChronoField.HOUR_OF_DAY, 23).toInstant());
		
		if (dao.getFilter() == null || dao.getFilter().equalsIgnoreCase("All")) {
		
			schedules = scheduleRepository.findByDeadlineBetweenOrderByDeadlineAsc(start, end);
		
		} else {
			
			Category category = categoryRepository.findFirstByNameIgnoreCase(dao.getFilter());
			schedules = scheduleRepository.findByDeadlineBetweenAndTaskCategoryOrderByDeadlineAsc(start, end, category);
		}
		
		return schedules;
	}

	@Override
	public List<Schedule> findTaskUpToDate(TasksRequestDateDao dao) {

		List<Schedule> schedules = new ArrayList<>();
		
		ZonedDateTime zonedDate = timeUtils.parseZonedDateTime(dao.getDate()); 
		Date endOfDay = Date.from(zonedDate.with(ChronoField.HOUR_OF_DAY, 23).toInstant());
		
		if (dao.getFilter() == null || dao.getFilter().equalsIgnoreCase("All")) {
		
			schedules = scheduleRepository.findByDeadlineLessThanEqualAndDoneOrderByDeadlineAsc(endOfDay, false);
		
		} else {
			
			Category category = categoryRepository.findFirstByNameIgnoreCase(dao.getFilter());
			schedules = scheduleRepository.findByDeadlineLessThanEqualAndTaskCategoryAndDoneOrderByDeadlineAsc(endOfDay, category, false);
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
		
			schedules = scheduleRepository.findByDeadlineBetweenAndDoneOrderByDeadlineAsc(start, end, false);
		
		} else {
			
			Category category = categoryRepository.findFirstByNameIgnoreCase(dao.getFilter());
			schedules = scheduleRepository.findByDeadlineBetweenAndTaskCategoryAndDoneOrderByDeadlineAsc(start, end, category, false);
		}
		
		return schedules;
	}
	
	@Override
	public List<Schedule> findAllTaskByDateRange(TasksRequestRangeDao dao) {
		
		List<Schedule> schedules = new ArrayList<>();
		
		ZonedDateTime zonedStartDate = timeUtils.parseZonedDateTime(dao.getStartDate()); 
		ZonedDateTime zonedEndDate = timeUtils.parseZonedDateTime(dao.getEndDate()); 
		Date start = Date.from(zonedStartDate.with(ChronoField.HOUR_OF_DAY, 0).toInstant());
		Date end = Date.from(zonedEndDate.with(ChronoField.HOUR_OF_DAY, 23).toInstant());
		
		if (dao.getFilter() == null || dao.getFilter().equalsIgnoreCase("All")) {
		
			schedules = scheduleRepository.findByDeadlineBetweenOrderByDeadlineAsc(start, end);
		
		} else {
			
			Category category = categoryRepository.findFirstByNameIgnoreCase(dao.getFilter());
			schedules = scheduleRepository.findByDeadlineBetweenAndTaskCategoryOrderByDeadlineAsc(start, end, category);
		}
		
		return schedules;
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
			
			Schedule schedule = new Schedule(task, task.getDeadline());
    		scheduleRepository.save(schedule);
		}
	}

	@Override
	public void scheduleFutureRepeatTasks() {

		List<Task> tasks = taskRepository.findAll();
		
		for (Task task: tasks) {
			
			if (task.getRepeat() != null) {
			
				Integer schedules = scheduleRepeatTask(task.getId(), daysLookAhead);
				
				logger.info("Scheduled " + schedules + " tasks.");
			}
		}
	}
	
	@Override
	@Transactional
	public Integer scheduleRepeatTask(Long id, Integer days) {

		Integer count = 0;
		
		LocalDateTime todayDate = LocalDateTime.now();  
		LocalDateTime lastScheduleDate = todayDate.plusDays(days);  
		
		Task task = taskRepository.findById(id).orElse(null);
		List<String> weekDays = new ArrayList<>();
		
		if (task.getRepeat() != null) {
			
			Repeat repeat = repeatRepository.findById(id).orElse(null);
			
			LocalDateTime startDate = timeUtils.DateToLocalDateTime(repeat.getStart());
			LocalDateTime nextDate = startDate;
			weekDays = repeat.getDays();
			
			if (repeat.getLast() != null) {
				
				nextDate = timeUtils.DateToLocalDateTime(repeat.getLast());
			}
			
			if(nextDate != null) {
				
				while (nextDate.compareTo(lastScheduleDate) < 0) {
					
					if (nextDate.compareTo(todayDate) > 0) {
					
						Schedule schedule = scheduleRepository.findFirstByDeadlineAndTask(timeUtils.LocalDateTimeToDate(nextDate), task);
						
						if (schedule == null) {
							
			        		schedule = new Schedule(task, timeUtils.LocalDateTimeToDate(nextDate));
			        		scheduleRepository.save(schedule);
			        		
			        		count++;
			        		
			        		logger.info(nextDate + ": Scheduled repeat task - " + task.getDetails());
						
						} else {
							
							// Skip! Already scheduled for this date
						}
						
					} else {
						
						// Skip! This date is in the past.
					}
					
					nextDate = timeUtils.nextDate(nextDate, repeat.getType(), repeat.getCount(), weekDays);
				}
				
				repeat.setLast(timeUtils.LocalDateTimeToDate(nextDate));
				repeatRepository.save(repeat);		
			}
		}
		
		return count;
	}

	@Override
	@Transactional
	public String[] getTags() {

    	String[] stringTags = Stream.of(Tags.values()).map(Tags::name).toArray(String[]::new);

    	return stringTags;
	}

	@Override
	@Transactional
	public Task setCategory(Long taskId, Long categoryId) {

		Task task = taskRepository.findById(taskId).orElse(null);
		Category category = categoryRepository.findById(categoryId).orElse(null);
		
		if ((task != null) && (category != null)) {
			
			task.setCategory(category); 
		}
		
		return task;
	}

	@Override
	@Transactional
	public Task addTag(Long id, String tag) {
		
		List<String> tags = new ArrayList<>();
		Task task = taskRepository.findById(id).orElse(null);
		tags.addAll(task.getTags());
		
		if (task != null) {
		
			tags.add(tag);
			task.setTags(tags);
		}
		
		return taskRepository.save(task); 
	}

	@Override
	@Transactional
	public Task addNote(Long id, String note) {
		
		
		Task task = taskRepository.findById(id).orElse(null);
		
		if (task != null) {
			
			List<TaskNote> notes = task.getNotes();
			TaskNote taskNote = taskNoteRepository.save(new TaskNote(task, note));
			notes.add(taskNote);
			task.setNotes(notes); 
		}
		
		return taskRepository.save(task);
	}
}
