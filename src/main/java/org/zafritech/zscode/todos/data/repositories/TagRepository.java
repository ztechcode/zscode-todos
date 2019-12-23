package org.zafritech.zscode.todos.data.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.zscode.todos.data.models.Tag;

public interface TagRepository extends CrudRepository<Tag, Long> {

	Tag findFirstByName(String name);
	
	List<Tag> findAllByOrderByNameAsc();
}
