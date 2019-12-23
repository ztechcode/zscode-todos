package org.zafritech.zscode.todos.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.zscode.todos.data.models.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {

}
