package org.zafritech.zscode.todos.data.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.zafritech.zscode.todos.data.models.Note;

public interface NoteRepository extends PagingAndSortingRepository<Note, Long> {

	List<Note> findAllByOrderByTimestampDesc();
	
	List<Note> findByOwnerOrderByTimestampDesc(String apiKey);
}
