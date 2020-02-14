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
import org.zafritech.zscode.todos.data.models.Category;
import org.zafritech.zscode.todos.data.repositories.CategoryRepository;
import org.zafritech.zscode.todos.services.CategoryService;
import org.zafritech.zscode.todos.services.TodosService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private TodosService todosService;

	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ResponseEntity<List<Category>> fetchAllCategories() {
		
		List<Category> categories = categoryRepository.findAllByOrderByNameAsc();
		
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}	

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Category> getCategoryById(@PathVariable(value = "id") Long id) {
		
		Category category = categoryRepository.findById(id).orElse(null); 

		if (category == null) {
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found.");
		}
		
		return new ResponseEntity<>(category, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/category/save", method = RequestMethod.POST)
	public ResponseEntity<Category> createCategory(@RequestParam("name") String name) { 
		
		Category category = categoryService.createCategory(name);
		
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/category/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Category> updateCategory(@RequestParam("name") String name,
										   	   @PathVariable(value = "id") Long id) { 
		
		Category category = categoryService.updateCategory(name, id);
		
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	// TBD: Need to check if category is not in use
	@RequestMapping(value = "/category/delete/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long id) { 
		
		Category category = categoryRepository.findById(id).orElse(null);
		
		if (category != null) {
			
			categoryRepository.delete(category); 
		}
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "tags", method = RequestMethod.GET)
    public ResponseEntity<String[]> geTags() {
		
		String[] tags = todosService.getTags(); 
	
		return new ResponseEntity<>(tags, HttpStatus.OK);
    }
	
}
