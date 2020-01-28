package org.zafritech.zscode.todos.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.zscode.todos.data.models.Category;
import org.zafritech.zscode.todos.data.models.Project;
import org.zafritech.zscode.todos.data.models.StateRegistry;
import org.zafritech.zscode.todos.data.models.Tag;
import org.zafritech.zscode.todos.data.models.Task;
import org.zafritech.zscode.todos.data.repositories.CategoryRepository;
import org.zafritech.zscode.todos.data.repositories.ProjectRepository;
import org.zafritech.zscode.todos.data.repositories.StateRegistryRepository;
import org.zafritech.zscode.todos.data.repositories.TagRepository;
import org.zafritech.zscode.todos.data.repositories.TaskRepository;
import org.zafritech.zscode.todos.enums.Priority;
import org.zafritech.zscode.todos.services.DataLoaderService;
import org.zafritech.zscode.todos.services.StateRegistryService;

@Service
public class DataLoaderServiceImpl implements DataLoaderService {

	@Autowired
    private CategoryRepository categoryRepository;
	
	@Autowired
    private ProjectRepository projectRepository;

	@Autowired
    private TagRepository tagRepository;
	
	@Autowired
    private TaskRepository taskRepository;
	
    @Autowired
    private StateRegistryRepository stateRepository;
    
    @Autowired
    private StateRegistryService stateService;

    private static final Logger logger = LoggerFactory.getLogger(DataLoaderServiceImpl.class);
    
	@Override
	public boolean isInitialised(String dataKey) {
		 
        StateRegistry task = stateRepository.findByStateKeyAndActive(dataKey, true);
        
        return task != null;
	}

	@Override
	public void initialiseTasks(String dataKey) {

		// Categories Initialisation
		Category category = categoryRepository.save(new Category("Uncategorised"));
		logger.info("Initialised category: " + category.getName()); 
		
		category = categoryRepository.save(new Category("Family"));
		logger.info("Initialised category: " + category.getName());

		category = categoryRepository.save(new Category("Work"));
		logger.info("Initialised category: " + category.getName());

		category = categoryRepository.save(new Category("Financial"));
		logger.info("Initialised category: " + category.getName());

		category = categoryRepository.save(new Category("Professional"));
		logger.info("Initialised category: " + category.getName());

		category = categoryRepository.save(new Category("Social"));
		logger.info("Initialised category: " + category.getName());
		
		category = categoryRepository.save(new Category("Hobbies"));
		logger.info("Initialised category: " + category.getName());

		category = categoryRepository.save(new Category("Charitable"));
		logger.info("Initialised category: " + category.getName());
		
		// Projects Intialisation
		Project project = projectRepository.save(new Project("ZTechCode Software"));
		logger.info("Initialised project: " + project.getName()); 
		
		project = projectRepository.save(new Project("Project Dabasir"));
		logger.info("Initialised project: " + project.getName()); 

		// Tags Intialisation
		Tag tag = tagRepository.save(new Tag("ZSCODE"));
		logger.info("Initialised tag: " + tag.getName()); 
		
		tag = tagRepository.save(new Tag("DABASIR"));
		logger.info("Initialised tag: " + tag.getName()); 

		tag = tagRepository.save(new Tag("INCOSE"));
		logger.info("Initialised tag: " + tag.getName()); 

		tag = tagRepository.save(new Tag("Spring"));
		logger.info("Initialised tag: " + tag.getName()); 
		
		tag = tagRepository.save(new Tag("Android"));
		logger.info("Initialised tag: " + tag.getName()); 

		// Task Intialisation
		Task task = new Task("Go ahead with the development of ZSCODE");
		task.setPriority(Priority.HIGH);
		Set<Tag> tags = new HashSet<Tag>();
		tags.add(tagRepository.findFirstByName("ZSCODE"));
		tags.add(tagRepository.findFirstByName("DABASIR"));
		task.setTags(tags);
		task = taskRepository.save(task);
		logger.info("Initialised tag: " + task.getDetails()); 
		
        stateService.activateState(dataKey);
	}
}
