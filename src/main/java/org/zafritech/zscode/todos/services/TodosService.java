package org.zafritech.zscode.todos.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zafritech.zscode.todos.data.daos.BasicTaskDao;
import org.zafritech.zscode.todos.data.daos.TasksRequestDateDao;
import org.zafritech.zscode.todos.data.daos.TasksRequestRangeDao;
import org.zafritech.zscode.todos.data.models.Schedule;
import org.zafritech.zscode.todos.data.models.Task;

@Service
public interface TodosService {

	public List<Task> filterTaskByCategory(String filter);
	
	public Task createTask(BasicTaskDao dao);
	
	public Task updateTask(String details, Long id);
	
	public void deleteTask(Long id);
	
	public Task updateTaskPriority(String priority, Long id);

	public Schedule completeScheduledTask(Long id);
	
	public List<Schedule> findTaskOnDate(TasksRequestDateDao dao);
	
	public List<Schedule> findTaskUpToDate(TasksRequestDateDao dao);
	
	public List<Schedule> findTaskByDateRange(TasksRequestRangeDao dao);
	
	public List<Schedule> findAllTaskByDateRange(TasksRequestRangeDao dao);

	public void scheduleNonRepeatTasks();
	
	public void scheduleFutureRepeatTasks();
	
	public Integer scheduleRepeatTask(Long id, Integer days);
}
