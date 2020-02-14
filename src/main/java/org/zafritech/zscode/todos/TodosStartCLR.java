package org.zafritech.zscode.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.zafritech.zscode.todos.config.TodosConfigProperties;
import org.zafritech.zscode.todos.services.DataLoaderService;

@Component
@ComponentScan("org.zafritech")
public class TodosStartCLR implements CommandLineRunner {

	@Autowired
	private TodosConfigProperties configProps;
	
	@Autowired
	private DataLoaderService dataLoader;
	
	@Override
	public void run(String... args) throws Exception {

		if (!dataLoader.isInitialised("TODOS_CATEGORIES_INIT")) { 
			
			dataLoader.initialiseCategories(configProps.getPaths().getDataDir() +  "init/categories.json", "TODOS_CATEGORIES_INIT"); 
		}

		if (!dataLoader.isInitialised("TODOS_TASKS_INIT")) { 
			
			dataLoader.initialiseTasks(configProps.getPaths().getDataDir() +  "init/tasks.json", "TODOS_TASKS_INIT"); 
		}
	}
}
