package org.zafritech.zscode.todos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HomeController {

	@Autowired
	private Environment env;

	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
	public String home() {

		return "Hello from Todos Service running at port: " + env.getProperty("local.server.port");
	}

}
