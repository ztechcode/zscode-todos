package org.zafritech.zscode.todos.data.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.zscode.todos.data.models.Repeat;
import org.zafritech.zscode.todos.data.models.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

	@Override
	List<Task> findAll();
	
	List<Task> findByRepeat(Repeat repeat);
	
//	List<Task> findAllByCategoryOrderByDueDesc(String filter);
	List<Task> findAllByCategory(String filter);
	
//	List<Task> findAllByDateEqualAndByCategoryOrderByDueDesc(LocalDate date, String filter);
	
//	List<Task> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndByCategoryOrderByDueDesc(Date end, Date start, String filter);
	
}
