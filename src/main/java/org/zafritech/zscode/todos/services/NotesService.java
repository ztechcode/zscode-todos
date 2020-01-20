package org.zafritech.zscode.todos.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zafritech.zscode.todos.data.models.Note;

@Service
public interface NotesService {

	public Note createNote(String token, String text);
	
	public List<Note> fetchAllNotes(String token);
	
}
