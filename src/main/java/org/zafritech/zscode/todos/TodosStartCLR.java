package org.zafritech.zscode.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.zafritech.zscode.todos.data.init.DataLoaderService;

@Component
@ComponentScan("org.zafritech")
public class TodosStartCLR implements CommandLineRunner {

	@Autowired
	private DataLoaderService dataLoader;
	
	@Override
	public void run(String... args) throws Exception {

		if (!dataLoader.isInitialised("TODOS_CATEGORY_INIT")) { 
			
			dataLoader.initialiseTodos("TODOS_CATEGORY_INIT"); 
		}
	}
}
