package org.zafritech.zscode.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.zafritech.zscode.todos.services.DataLoaderService;

@Component
@ComponentScan("org.zafritech")
public class TodosStartCLR implements CommandLineRunner {

	@Autowired
	private DataLoaderService dataLoader;
	
	@Override
	public void run(String... args) throws Exception {

		if (!dataLoader.isInitialised("TODOS_SYSTEM_USER_INIT")) {

			dataLoader.registerSystemUser("TODOS_SYSTEM_USER_INIT"); 
		}

		// Allow 5 seconds delay
		Thread.sleep(5000);
		
		if (!dataLoader.isInitialised("TODOS_CATEGORIES_INIT")) { 
			
			dataLoader.initialiseCategories("TODOS_CATEGORIES_INIT"); 
		}

		if (!dataLoader.isInitialised("TODOS_TASKS_INIT")) { 
			
			Thread.sleep(5000);
			dataLoader.initialiseTasks("TODOS_TASKS_INIT"); 
		}
	}
}
