package org.zafritech.zscode.todos.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.zscode.todos.data.models.TaskTag;

public interface TaskTagRepository extends CrudRepository<TaskTag, Long> {

}
