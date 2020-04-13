package org.zafritech.zscode.todos.services.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.zscode.commons.data.daos.SystemLoginDao;
import org.zafritech.zscode.commons.data.models.StateRegistry;
import org.zafritech.zscode.commons.data.repositories.StateRegistryRepository;
import org.zafritech.zscode.commons.services.Authentication;
import org.zafritech.zscode.commons.services.FileService;
import org.zafritech.zscode.commons.services.impl.StateImpl;
import org.zafritech.zscode.todos.config.TodosConfigProperties;
import org.zafritech.zscode.todos.data.daos.CategoryDao;
import org.zafritech.zscode.todos.data.daos.TagDao;
import org.zafritech.zscode.todos.data.daos.TaskDao;
import org.zafritech.zscode.todos.data.daos.TaskNoteDao;
import org.zafritech.zscode.todos.data.models.Category;
import org.zafritech.zscode.todos.data.models.Task;
import org.zafritech.zscode.todos.data.repositories.CategoryRepository;
import org.zafritech.zscode.todos.services.DataLoaderService;
import org.zafritech.zscode.todos.services.TodosService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DataLoaderServiceImpl implements DataLoaderService {

	@Autowired
	private StateImpl commons;

	@Autowired
	private TodosConfigProperties props;

	@Autowired
	private Authentication identity;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private StateRegistryRepository stateRepository;

	@Autowired
	private TodosService todosService;

	private static final Logger logger = LoggerFactory.getLogger(DataLoaderServiceImpl.class);

	@Override
	public boolean isInitialised(String dataKey) {

		StateRegistry task = stateRepository.findByStateKeyAndActive(dataKey, true);

		return task != null;
	}

	@Override
	public void initialiseCategories(String dataKey) {

		String token = identity.login(new SystemLoginDao(props.getApp().getEmail(), props.getApp().getPassword()), props.getUrls().getAuthApi() + "login");
		String downloadDirectory = props.getPaths().getUploadDir();
		String categoriesFileUrl = props.getUrls().getResourcesApi() + "files/name/initializr-todos-categories";
		String categoriesFileName = downloadDirectory + "initializr-todos-categories.json";

		ObjectMapper mapper = new ObjectMapper();

		try {
			
			if (fileService.download(categoriesFileUrl, categoriesFileName, token)) {
	
				List<CategoryDao> objects = Arrays.asList(mapper.readValue(new File(categoriesFileName), CategoryDao[].class));
	
				for (CategoryDao dao : objects) {
	
					Category category = categoryRepository.save(new Category(dao.getName()));
					logger.info("Initialised category: " + category.getName());
				}
			}

		} catch (IOException ex) {

			logger.error(ex.getMessage());
		}

		commons.registerState(dataKey);
	}

	@Override
	public void initialiseTasks(String dataKey) {

		String token = identity.login(new SystemLoginDao(props.getApp().getEmail(), props.getApp().getPassword()), props.getUrls().getAuthApi() + "login");
		String downloadDirectory = props.getPaths().getUploadDir();
		String tasksFileUrl = props.getUrls().getResourcesApi() + "files/name/initializr-todos-tasks";
		String tasksFileName = downloadDirectory + "initializr-todos-tasks.json";

		ObjectMapper mapper = new ObjectMapper();

		try {
			
			if (fileService.download(tasksFileUrl, tasksFileName, token)) {
	
				List<TaskDao> objects = Arrays.asList(mapper.readValue(new File(tasksFileName), TaskDao[].class));
	
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
				
				new Thread() {
					
					public void run() {
							
						todosService.scheduleNonRepeatTasks();
						logger.info("Scheduled once off tasks... ");
						
						todosService.scheduleFutureRepeatTasks();
						logger.info("Scheduled repeat tasks... ");
					}
					
				}.start();

			    commons.registerState(dataKey);
			}

		} catch (IOException ex) {

			logger.error(ex.getMessage());
		}
	}
}
