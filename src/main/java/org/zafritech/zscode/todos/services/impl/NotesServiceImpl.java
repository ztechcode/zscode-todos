package org.zafritech.zscode.todos.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.zscode.commons.security.Identity;
import org.zafritech.zscode.todos.data.daos.JsonNoteDao;
import org.zafritech.zscode.todos.data.models.Note;
import org.zafritech.zscode.todos.data.repositories.NoteRepository;
import org.zafritech.zscode.todos.services.NotesService;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private Identity identity;

	@Autowired
	private NoteRepository noteRepository;

	@Override
	public List<Note> fetchAllNotes(String token) {
		
		List<Note> notes = new ArrayList<>();
		String apiKey = identity.getApiKey(token);
		
		if (apiKey != null) {
		
			notes = noteRepository.findByOwnerOrderByTimestampDesc(apiKey);
		}
		
		return notes;
	}

	@Override
	public Note findNoteById(String token, Long id) {
		
		if (userAuthorised(token, id)) {
			
			return noteRepository.findById(id).orElse(null);
			
		} else {
			
			return null;
		}
	}
	
	@Override
	public Note createNote(String token, JsonNoteDao dao) {

		String apiKey = identity.getApiKey(token);
		
		if (apiKey != null) {
			
			Note note = new Note(apiKey, dao.getText());
			return  noteRepository.save(note);
			
		} else {
			
			return null;
		}
	}

	@Override
	public Note updateNote(String token, JsonNoteDao dao) {

		if (userAuthorised(token, dao.getId())) {
			
			Note note = noteRepository.findById(dao.getId()).orElse(null);			
			note.setText(dao.getText());
			
			return noteRepository.save(note);
			
		}
		
		return null;
	}

	@Override
	public void deleteNote(String token, Long id) {
		
		if (userAuthorised(token, id)) {
			
			Note note = noteRepository.findById(id).orElse(null);
			
			if (note != null) {
			
				noteRepository.delete(note);
			}
		}
	}

	private boolean userAuthorised(String token, Long id) {

		String apiKey = identity.getApiKey(token);
		Note note = noteRepository.findById(id).orElse(null);
		
		if (note != null && apiKey.equals(note.getOwner())) {
				
			return true;
		}

		return false;
	}
	
}
