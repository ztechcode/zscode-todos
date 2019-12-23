package org.zafritech.zscode.todos.data.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.zscode.todos.data.models.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {

	Project findFirstByName(String name);
	
	List<Project> findAllByOrderByNameAsc();
}
