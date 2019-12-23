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
import org.zafritech.zscode.todos.data.models.Tag;
import org.zafritech.zscode.todos.data.repositories.TagRepository;
import org.zafritech.zscode.todos.services.TodosService;

@RestController
@RequestMapping("/tags")
public class TagController {

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private TodosService todosService;

	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ResponseEntity<List<Tag>> fetchAllTags() {
		
		List<Tag> tags = tagRepository.findAllByOrderByNameAsc();
		
		return new ResponseEntity<>(tags, HttpStatus.OK);
	}		

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Tag> getTagById(@PathVariable(value = "id") Long id) {
		
		Tag tag = tagRepository.findById(id).orElse(null); 

		if (tag == null) {
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found.");
		}
		
		return new ResponseEntity<>(tag, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/tag/save", method = RequestMethod.POST)
	public ResponseEntity<Tag> createTag(@RequestParam("name") String name) { 
		
		Tag tag = todosService.createTag(name);
		
		return new ResponseEntity<>(tag, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/tag/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Tag> updateProject(@RequestParam("name") String name,
										   	 @PathVariable(value = "id") Long id) { 
		
		Tag tag = todosService.updateTag(name, id);
		
		return new ResponseEntity<>(tag, HttpStatus.OK);
	}
	
	// TBD: Need to check if tag is not in use
	@RequestMapping(value = "/tag/delete/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> deleteTag(@PathVariable(value = "id") Long id) { 
		
		Tag tag = tagRepository.findById(id).orElse(null);
		
		if (tag != null) {
			
			tagRepository.delete(tag); 
		}
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
