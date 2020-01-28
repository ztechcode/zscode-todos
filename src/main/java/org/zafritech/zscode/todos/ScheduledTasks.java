package org.zafritech.zscode.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zafritech.zscode.todos.services.TodosService;

@Component
public class ScheduledTasks {

	@Autowired
    private TodosService todosService;
	
	@Scheduled(cron = "0 0,30 * * * *") // Every hour at minutes 00 and 30
	public void scheduleRecurringTasks() {
		
		todosService.scheduleAllRepeatTasks();
	}
}
