package org.zafritech.zscode.todos.data.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.zscode.todos.data.models.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	Category findFirstByName(String name);
	
	List<Category> findAllByOrderByNameAsc();
}
