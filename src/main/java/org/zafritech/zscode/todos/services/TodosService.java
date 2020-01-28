package org.zafritech.zscode.todos.services;

import org.springframework.stereotype.Service;
import org.zafritech.zscode.todos.data.models.Category;
import org.zafritech.zscode.todos.data.models.Project;
import org.zafritech.zscode.todos.data.models.Tag;
import org.zafritech.zscode.todos.data.models.Task;

@Service
public interface TodosService {

	public Category createCategory(String name);
	
	public Category updateCategory(String name, Long id);
	
	public Project createProject(String name);
	
	public Project updateProject(String name, Long id);

	public Tag createTag(String name);
	
	public Tag updateTag(String name, Long id);
	
	public Task createTask(String details);
	
	public Task completeTask(Long id);
	
	public Task updateTask(String details, Long id);
	
	public Task updateTaskPriority(String priority, Long id);
	
	public Task updateTaskRepeatType(Long id);
	
	public Task scheduleTaskRepeat(Long id);
	
	public void scheduleAllRepeatTasks();
}
