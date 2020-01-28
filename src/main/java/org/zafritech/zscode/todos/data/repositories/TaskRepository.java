package org.zafritech.zscode.todos.data.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.zscode.todos.data.models.Repeat;
import org.zafritech.zscode.todos.data.models.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

	@Override
	List<Task> findAll();
	
	List<Task> findAllByOrderByDueDesc();
	
	List<Task> findByRepeat(Repeat repeat);
}
