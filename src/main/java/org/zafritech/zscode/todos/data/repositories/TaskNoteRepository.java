package org.zafritech.zscode.todos.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.zscode.todos.data.models.TaskNote;

public interface TaskNoteRepository extends CrudRepository<TaskNote, Long> {

}
