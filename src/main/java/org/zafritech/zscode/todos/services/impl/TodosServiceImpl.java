package org.zafritech.zscode.todos.services.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.zscode.todos.ScheduledTasks;
import org.zafritech.zscode.todos.data.models.Category;
import org.zafritech.zscode.todos.data.models.Project;
import org.zafritech.zscode.todos.data.models.Repeat;
import org.zafritech.zscode.todos.data.models.Tag;
import org.zafritech.zscode.todos.data.models.Task;
import org.zafritech.zscode.todos.data.repositories.CategoryRepository;
import org.zafritech.zscode.todos.data.repositories.ProjectRepository;
import org.zafritech.zscode.todos.data.repositories.RepeatRepository;
import org.zafritech.zscode.todos.data.repositories.TagRepository;
import org.zafritech.zscode.todos.data.repositories.TaskRepository;
import org.zafritech.zscode.todos.enums.Priority;
import org.zafritech.zscode.todos.enums.RepeatType;
import org.zafritech.zscode.todos.services.TodosService;

@Service
public class TodosServiceImpl implements TodosService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private RepeatRepository repeatRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private TagRepository tagRepository;

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public Category createCategory(String name) {

		Category category = new Category(name);
		category = categoryRepository.save(category); 
		
		return category; 
	}

	@Override
	public Category updateCategory(String name, Long id) {

		Category category = categoryRepository.findById(id).orElse(null);
		
		if (category != null) {
			
			category.setName(name);
			category = categoryRepository.save(category);
		}
		
		return category;
	}

	@Override
	public Project createProject(String name) {

		Project project = new Project(name);
		project = projectRepository.save(project); 
		
		return project;
	}

	@Override
	public Project updateProject(String name, Long id) {

		Project project = projectRepository.findById(id).orElse(null);
		
		if (project != null) {
			
			project.setName(name);
			project = projectRepository.save(project);
		}
		
		return project;
	}

	@Override
	public Tag createTag(String name) {

		Tag tag = new Tag(name);
		tag = tagRepository.save(tag); 
		
		return tag;
	}

	@Override
	public Tag updateTag(String name, Long id) {

		Tag tag = tagRepository.findById(id).orElse(null);
		
		if (tag != null) {
			
			tag.setName(name);
			tag = tagRepository.save(tag);
		}
		
		return tag;
	}
	
	@Override
	public Task createTask(String details) {
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, 7); // Default task due date in 7 days
        
        Date due = cal.getTime();
        
		Task task = new Task(details);
		task.setCategory(categoryRepository.findFirstByName("Uncategorised"));
		task.setDue(due); 
		
		task = taskRepository.save(task);
		
		return task;
	}

	@Override
	public Task completeTask(Long id) {
		
		Task task = taskRepository.findById(id).orElse(null);
		
		if (task != null) {
			
			task.setComplete(true);
			task = taskRepository.save(task);
		}
		
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
	public Task updateTaskPriority(String priority, Long id) {

		Task task = taskRepository.findById(id).orElse(null);
		
		if (task != null) {
			
			task.setPriority(Priority.valueOf(priority));
			task = taskRepository.save(task);
		}
		
		return task;
	}

	@Override
	public Task updateTaskRepeatType(Long id) {

		Task task = taskRepository.findById(id).orElse(null);
		
		if (task.getRepeat() != null) {
			
			Repeat repeat = repeatRepository.findById(id).orElse(null);
			
			// Get interval in hours
			Date nextDate = getNextDate(repeat.getCurrent(), repeat.getType(), repeat.getCount());
			
			if (nextDate != null) {
					
				Task next = new Task(task.getDetails(), nextDate);
				next.setParent(task.getParent());
				next.setCategory(task.getCategory());
				next.setPriority(task.getPriority());
				next.setProject(task.getProject());
				next.setTags(task.getTags());
				next.setRepeat(task.getRepeat());
				taskRepository.save(next);
				
				repeat.setLast(repeat.getCurrent());
				repeat.setCurrent(nextDate); 
				repeatRepository.save(repeat);
				
				task.setRepeat(null);
				taskRepository.save(task); 
			}
		}
		
		return task;
	}

	@Override
	public Task scheduleTaskRepeat(Long id) {

		Task task = taskRepository.findById(id).orElse(null);
		
		if (task.getRepeat() != null) {
			
			Repeat repeat = repeatRepository.findById(id).orElse(null);
			
			// Get interval in hours
			Date nextDate = getNextDate(repeat.getCurrent(), repeat.getType(), repeat.getCount());
			
			if (nextDate != null) {
					
				Task next = new Task(task.getDetails(), nextDate);
				next.setParent(task.getParent());
				next.setCategory(task.getCategory());
				next.setPriority(task.getPriority());
				next.setProject(task.getProject());
				next.setTags(task.getTags());
				next.setRepeat(task.getRepeat());
				taskRepository.save(next);
				
				repeat.setLast(repeat.getCurrent());
				repeat.setCurrent(nextDate); 
				repeatRepository.save(repeat);
				
				task.setRepeat(null);
				taskRepository.save(task); 
			}
		}
		
		return task;
	}

	@Override
	public void scheduleAllRepeatTasks() {

		List<Task> tasks = taskRepository.findAll();
		
		for (Task task: tasks) {
			
			if (task.getRepeat() != null) {
			
				Task scheduled = scheduleTaskRepeat(task.getId());
			
				log.info(dateFormat.format(new Date()) + ": Scheduled repeat task - " + scheduled.getDetails());
				
			} else {
				
				log.info(dateFormat.format(new Date()) + ": Not a Repeat task - " + task.getDetails());
			}
		}
	}
	
	private Date getNextDate(Date current, RepeatType type, Integer count) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(current);
		
		if (type == RepeatType.DAYS) {
			
			calendar.add(Calendar.DATE, count);
			return calendar.getTime();
			
		} else if (type == RepeatType.WEEKS) {

			calendar.add(Calendar.DATE, count*7);
			return calendar.getTime();
				
		} else if (type == RepeatType.MONTHS) {

			calendar.add(Calendar.MONTH, count);
			return calendar.getTime();
				
		} else if (type == RepeatType.YEARS) {

			calendar.add(Calendar.YEAR, count);
			return calendar.getTime();
				
		}
		
		return null;
	}

}
