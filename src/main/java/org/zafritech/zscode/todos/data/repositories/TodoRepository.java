package org.zafritech.zscode.todos.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.zscode.todos.data.models.Todo;

public interface TodoRepository extends CrudRepository<Todo, Long> {

}
