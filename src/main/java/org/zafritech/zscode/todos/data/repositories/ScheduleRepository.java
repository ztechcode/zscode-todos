package org.zafritech.zscode.todos.data.repositories;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.zscode.todos.data.models.Category;
import org.zafritech.zscode.todos.data.models.Schedule;
import org.zafritech.zscode.todos.data.models.Task;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
	
	Schedule findFirstByDateAndTask(LocalDate date, Task task);

	List<Schedule> findAllByOrderByTimeAsc();
	
	List<Schedule> findByTaskCategoryNameOrderByTimeAsc(String name);
	
	List<Schedule> findByTimeBetweenAndDoneOrderByTimeAsc(Date start, Date end, boolean done);
	
	List<Schedule> findByTimeBetweenAndTaskCategoryAndDoneOrderByTimeAsc(Date start, Date end, Category category, boolean done);
}