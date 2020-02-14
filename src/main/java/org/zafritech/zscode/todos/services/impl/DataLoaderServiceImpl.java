package org.zafritech.zscode.todos.services.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.zscode.todos.data.daos.CategoryDao;
import org.zafritech.zscode.todos.data.daos.TagDao;
import org.zafritech.zscode.todos.data.daos.TaskDao;
import org.zafritech.zscode.todos.data.daos.TaskNoteDao;
import org.zafritech.zscode.todos.data.models.Category;
import org.zafritech.zscode.todos.data.models.StateRegistry;
import org.zafritech.zscode.todos.data.models.Task;
import org.zafritech.zscode.todos.data.repositories.CategoryRepository;
import org.zafritech.zscode.todos.data.repositories.StateRegistryRepository;
import org.zafritech.zscode.todos.services.DataLoaderService;
import org.zafritech.zscode.todos.services.StateRegistryService;
import org.zafritech.zscode.todos.services.TodosService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DataLoaderServiceImpl implements DataLoaderService {

	@Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StateRegistryRepository stateRepository;
    
    @Autowired
    private StateRegistryService stateRegistryService;

	@Autowired
	private TodosService todosService;
	
    private static final Logger logger = LoggerFactory.getLogger(DataLoaderServiceImpl.class);

	@Override
	public boolean isInitialised(String dataKey) {
		 
        StateRegistry task = stateRepository.findByStateKeyAndActive(dataKey, true);
        
        return task != null;
	}

	@Override
	public void initialiseCategories(String dataFile, String dataKey) {

		ObjectMapper mapper = new ObjectMapper();
		
		try {
            
            List<CategoryDao> objects = Arrays.asList(mapper.readValue(new File(dataFile), CategoryDao[].class));
            
            for (CategoryDao dao : objects) {
            	
            	Category category = categoryRepository.save(new Category(dao.getName()));
            	logger.info("Initialised category: " + category.getName());
            }
          	
    		stateRegistryService.activateState(dataKey);
    			            
		} catch (IOException ex) {
		    
			logger.error(ex.getMessage());
		}
	}
	
	@Override
	public void initialiseTasks(String dataFile, String dataKey) throws ParseException {

		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
            List<TaskDao> objects = Arrays.asList(mapper.readValue(new File(dataFile), TaskDao[].class));
            
            for (TaskDao dao : objects) {
            	
            	Task task = todosService.createTask(dao);
            	
            	if (!dao.getTags().isEmpty()) {

        			List<TagDao> tagDaos = dao.getTags();
        			
        			for (TagDao tags : tagDaos) {
        				
        				todosService.addTag(task.getId(), tags.getName());
        			}
            	}
            	
            	if (!dao.getNotes().isEmpty()) {
            		
            		List<TaskNoteDao> notes = dao.getNotes();
            		
            		for (TaskNoteDao note : notes) {
            			
            			todosService.addNote(task.getId(), note.getNote());
            		}
            	}
            	
            	if (dao.getCategory() != null) {
            	
            		Long categoryId = categoryRepository.findFirstByNameIgnoreCase(dao.getCategory()).getId();
            		todosService.setCategory(task.getId(), categoryId);
            	}
            	
        		logger.info("Initialised task: " + task.getDetails());  
            }
            
            todosService.scheduleNonRepeatTasks();
            todosService.scheduleFutureRepeatTasks();

    		stateRegistryService.activateState(dataKey);
    		
		} catch (IOException ex) {
		    
			logger.error(ex.getMessage());
		}
	
	}

}
