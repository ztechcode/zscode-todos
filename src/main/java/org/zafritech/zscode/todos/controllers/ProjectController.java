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
import org.zafritech.zscode.todos.data.models.Project;
import org.zafritech.zscode.todos.data.repositories.ProjectRepository;
import org.zafritech.zscode.todos.services.TodosService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TodosService todosService;

	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ResponseEntity<List<Project>> fetchAllProjects() {
		
		List<Project> projects = projectRepository.findAllByOrderByNameAsc();
		
		return new ResponseEntity<>(projects, HttpStatus.OK);
	}		

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Project> getProjectById(@PathVariable(value = "id") Long id) {
		
		Project project = projectRepository.findById(id).orElse(null); 

		if (project == null) {
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found.");
		}
		
		return new ResponseEntity<>(project, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/project/save", method = RequestMethod.POST)
	public ResponseEntity<Project> createProject(@RequestParam("name") String name) { 
		
		Project project = todosService.createProject(name);
		
		return new ResponseEntity<>(project, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/project/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Project> updateProject(@RequestParam("name") String name,
										   	     @PathVariable(value = "id") Long id) { 
		
		Project project = todosService.updateProject(name, id);
		
		return new ResponseEntity<>(project, HttpStatus.OK);
	}
	
	// TBD: Need to check if project is not in use
	@RequestMapping(value = "/project/delete/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> deleteProject(@PathVariable(value = "id") Long id) { 
		
		Project project = projectRepository.findById(id).orElse(null);
		
		if (project != null) {
			
			projectRepository.delete(project); 
		}
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
