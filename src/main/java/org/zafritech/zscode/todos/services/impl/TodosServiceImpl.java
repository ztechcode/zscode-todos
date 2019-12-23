package org.zafritech.zscode.todos.services.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.zscode.todos.data.models.Category;
import org.zafritech.zscode.todos.data.models.Project;
import org.zafritech.zscode.todos.data.models.Tag;
import org.zafritech.zscode.todos.data.models.Task;
import org.zafritech.zscode.todos.data.repositories.CategoryRepository;
import org.zafritech.zscode.todos.data.repositories.ProjectRepository;
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
	private CategoryRepository categoryRepository;

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private TagRepository tagRepository;

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
	public Task updateTaskRepeatType(String repeat, Long id) {

		Task task = taskRepository.findById(id).orElse(null);
		
		if (task != null) {
			
			task.setRepeatType(RepeatType.valueOf(repeat));
			task = taskRepository.save(task);
		}
		
		return task;
	}
}
