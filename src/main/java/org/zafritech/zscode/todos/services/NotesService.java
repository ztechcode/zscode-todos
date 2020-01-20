package org.zafritech.zscode.todos.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zafritech.zscode.todos.data.models.Note;

@Service
public interface NotesService {

	public Note findNoteById(String token, Long id);
	
	public Note createNote(String token, String text);

	public Note updateNote(String token, Note newNote);
		
	public List<Note> fetchAllNotes(String token);
	
	public void deleteNote(String token, Long id);
	
}
