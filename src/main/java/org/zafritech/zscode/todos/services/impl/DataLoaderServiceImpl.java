package org.zafritech.zscode.todos.services.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.zscode.todos.data.daos.CategoryDao;
import org.zafritech.zscode.todos.data.daos.TagDao;
import org.zafritech.zscode.todos.data.daos.TaskDao;
import org.zafritech.zscode.todos.data.models.Category;
import org.zafritech.zscode.todos.data.models.Repeat;
import org.zafritech.zscode.todos.data.models.StateRegistry;
import org.zafritech.zscode.todos.data.models.Tag;
import org.zafritech.zscode.todos.data.models.Task;
import org.zafritech.zscode.todos.data.models.TaskTag;
import org.zafritech.zscode.todos.data.repositories.CategoryRepository;
import org.zafritech.zscode.todos.data.repositories.RepeatRepository;
import org.zafritech.zscode.todos.data.repositories.StateRegistryRepository;
import org.zafritech.zscode.todos.data.repositories.TagRepository;
import org.zafritech.zscode.todos.data.repositories.TaskRepository;
import org.zafritech.zscode.todos.enums.Priority;
import org.zafritech.zscode.todos.enums.RepeatType;
import org.zafritech.zscode.todos.services.DataLoaderService;
import org.zafritech.zscode.todos.services.StateRegistryService;
import org.zafritech.zscode.todos.services.TodosService;
import org.zafritech.zscode.todos.utils.TimeUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DataLoaderServiceImpl implements DataLoaderService {

	@Autowired
    private TimeUtils timeUtils;
	
	@Autowired
    private CategoryRepository categoryRepository;
	
	@Autowired
    private TagRepository tagRepository;
	
	@Autowired
    private TaskRepository taskRepository;
	
	@Autowired
    private RepeatRepository repeatRepository;
	
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
	public void initialiseTags(String dataFile, String dataKey) {

		ObjectMapper mapper = new ObjectMapper();
		
		try {
            
            List<TagDao> objects = Arrays.asList(mapper.readValue(new File(dataFile), TagDao[].class));
            
            for (TagDao dao : objects) {
            	
            	Tag tag = tagRepository.save(new Tag(dao.getName()));
        		logger.info("Initialised tag: " + tag.getName()); 
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
            	
            	ZonedDateTime target = timeUtils.parseZonedDateTime(dao.getDue());  
            	
            	Repeat repeat = null;
            	
            	if (dao.getRepeat() != null) {
            		
            		ZonedDateTime start = timeUtils.parseZonedDateTime(dao.getRepeat().getStart()); 
            		RepeatType type = RepeatType.valueOf(dao.getRepeat().getType());
            		Integer count = dao.getRepeat().getCount();
            		
            		repeat = new Repeat(type, count, Date.from(start.toInstant())); 
            		repeat = repeatRepository.save(repeat);
            		
            	}
            	
            	Task task = new Task(dao.getDetails());
            	task.setCategory(categoryRepository.findFirstByNameIgnoreCase(dao.getCategory()));
            	task.setPriority(Priority.valueOf(dao.getPriority())); 
            	task.setTarget(target.toLocalDate());
            	task.setRepeat(repeat);
        		task = taskRepository.save(task);

        		List<TaskTag> tags = new ArrayList<>();
        		tags.add(new TaskTag(task, tagRepository.findFirstByName("ZSCODE").getName()));
        		tags.add(new TaskTag(task, tagRepository.findFirstByName("DABASIR").getName()));
        		task.getTags().addAll(tags);
        		task = taskRepository.save(task);
            	
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
