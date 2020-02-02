package org.zafritech.zscode.todos.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.zscode.todos.data.models.TaskLog;

public interface TaskLogRepository extends CrudRepository<TaskLog, Long> {

}
