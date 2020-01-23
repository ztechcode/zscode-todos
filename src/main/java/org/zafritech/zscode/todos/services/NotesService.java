package org.zafritech.zscode.todos.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zafritech.zscode.todos.data.daos.JsonNoteDao;
import org.zafritech.zscode.todos.data.models.Note;

@Service
public interface NotesService {

	public List<Note> fetchAllNotes(String token);
	
	public Note findNoteById(String token, Long id);
	
	public Note createNote(String token, JsonNoteDao dao);

	public Note updateNote(String token, JsonNoteDao dao);
		
	public void deleteNote(String token, Long id);

}
