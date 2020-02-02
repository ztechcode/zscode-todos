package org.zafritech.zscode.todos.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.zafritech.zscode.todos.data.models.Category;
import org.zafritech.zscode.todos.data.models.Project;
import org.zafritech.zscode.todos.data.models.Tag;
import org.zafritech.zscode.todos.data.repositories.CategoryRepository;
import org.zafritech.zscode.todos.data.repositories.ProjectRepository;
import org.zafritech.zscode.todos.data.repositories.TagRepository;
import org.zafritech.zscode.todos.services.CategoryService;

public class CategoryServiceImpl implements CategoryService {

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
	

}
