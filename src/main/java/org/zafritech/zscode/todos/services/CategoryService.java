package org.zafritech.zscode.todos.services;

import org.springframework.stereotype.Service;
import org.zafritech.zscode.todos.data.models.Category;
import org.zafritech.zscode.todos.data.models.Project;

@Service
public interface CategoryService {
	
	public Category createCategory(String name);
	
	public Category updateCategory(String name, Long id);
	
	public Project createProject(String name);
	
	public Project updateProject(String name, Long id);
}
