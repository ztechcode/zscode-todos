package org.zafritech.zscode.todos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.zafritech.zscode.todos.data.models.Task;
import org.zafritech.zscode.todos.data.repositories.TaskRepository;
import org.zafritech.zscode.todos.services.TodosService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private TodosService todosService;

	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ResponseEntity<List<Task>> fetchAllUsers() {
		
		List<Task> tasks = taskRepository.findAllByOrderByDueDesc();
		
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}	

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Task> getTaskById(@PathVariable(value = "id") Long id) {
		
		Task task = taskRepository.findById(id).orElse(null); 

		if (task == null) {
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found.");
		}
		
		return new ResponseEntity<>(task, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/task/save", method = RequestMethod.POST)
	public ResponseEntity<Task> createTask(@RequestParam("details") String details) { 
		
		Task task = todosService.createTask(details);
		
		return new ResponseEntity<>(task, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/task/complete/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Task> completeTask(@PathVariable(value = "id") Long id) { 
		
		Task task = todosService.completeTask(id);
		
		return new ResponseEntity<>(task, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/task/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Task> updateTask(@RequestParam("details") String details,
										   @PathVariable(value = "id") Long id) { 
		
		Task task = todosService.updateTask(details, id);
		
		return new ResponseEntity<>(task, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/task/priority/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Task> updateTaskPriority(@RequestParam("priority") String priority,
										           @PathVariable(value = "id") Long id) { 
		
		Task task = todosService.updateTaskPriority(priority, id);
		
		return new ResponseEntity<>(task, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/task/repeat/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Task> updateTaskRepeatType(@RequestParam("repeat") String repeat,
										             @PathVariable(value = "id") Long id) { 
		
		Task task = todosService.updateTaskRepeatType(repeat, id);
		
		return new ResponseEntity<>(task, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/task/delete/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> deleteTask(@PathVariable(value = "id") Long id) { 
		
		Task task = taskRepository.findById(id).orElse(null);
		
		if (task != null) {
			
			taskRepository.delete(task); 
		}
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
