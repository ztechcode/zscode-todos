package org.zafritech.zscode.todos.data.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.zscode.todos.data.models.Category;
import org.zafritech.zscode.todos.data.models.Schedule;
import org.zafritech.zscode.todos.data.models.Task;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
	
	Schedule findFirstByDeadlineAndTask(Date date, Task task);

	List<Schedule> findAllByOrderByDeadlineAsc();
	
	List<Schedule> findByTaskCategoryNameOrderByDeadlineAsc(String name);
	
	List<Schedule> findByTask(Task task);
	
	List<Schedule> findByDeadlineLessThanEqualAndDoneOrderByDeadlineAsc(Date date, boolean done);
	
	List<Schedule> findByDeadlineLessThanEqualAndTaskCategoryAndDoneOrderByDeadlineAsc(Date date, Category category, boolean done);
	
	List<Schedule> findByDeadlineBetweenAndDoneOrderByDeadlineAsc(Date start, Date end, boolean done);
	
	List<Schedule> findByDeadlineBetweenOrderByDeadlineAsc(Date start, Date end);

	List<Schedule> findByDeadlineBetweenAndTaskCategoryOrderByDeadlineAsc(Date start, Date end, Category category);
	
	List<Schedule> findByDeadlineBetweenAndTaskCategoryAndDoneOrderByDeadlineAsc(Date start, Date end, Category category, boolean done);
}
