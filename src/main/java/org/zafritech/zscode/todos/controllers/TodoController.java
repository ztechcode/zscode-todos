package org.zafritech.zscode.todos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zafritech.zscode.todos.services.TodosService;

@RestController
@RequestMapping("/api")
public class TodoController {

	@Autowired
	private Environment env;

	@Autowired
	private TodosService todosService;

	@RequestMapping({"/", ""})
	public String home() {

		return "Hello from Todos Service running at port: " + env.getProperty("local.server.port");
	}
	
}
