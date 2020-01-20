package org.zafritech.zscode.todos.services.impl;

import java.security.SignatureException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.zscode.todos.data.models.Note;
import org.zafritech.zscode.todos.data.repositories.NoteRepository;
import org.zafritech.zscode.todos.security.Identity;
import org.zafritech.zscode.todos.services.NotesService;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private Identity identity;

	@Autowired
	private NoteRepository noteRepository;



	@Override
	public Note findNoteById(String token, Long id) {
		
		try {
			
			String apiKey = identity.getApiKey(token);
			Note note = noteRepository.findById(id).orElse(null);
			
			if (note != null && apiKey.equals(note.getOwner())) {
				
				return note;
			}
			
		} catch (SignatureException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public Note createNote(String token, String text) {
        
		try {
			
			String apiKey = identity.getApiKey(token);
			Note note = new Note(apiKey, text);
			
			return  noteRepository.save(note);
			
		} catch (SignatureException e) {
			
			e.printStackTrace();
		}
				
		return null;
	}

	@Override
	public List<Note> fetchAllNotes(String token) {
		
		try {
			
			String apiKey = identity.getApiKey(token);
			List<Note> notes = noteRepository.findByOwnerOrderByTimestampDesc(apiKey);
			
			return notes;
			
		} catch (SignatureException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Note updateNote(String token, Note newNote) {

		try {
			
			String apiKey = identity.getApiKey(token);
			Note note = noteRepository.findById(newNote.getId()).orElse(null);
			
			if (note != null && apiKey.equals(note.getOwner())) {
				
				return noteRepository.save(newNote);
			}
			
		} catch (SignatureException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void deleteNote(String token, Long id) {

		try {
			
			String apiKey = identity.getApiKey(token);
			Note note = noteRepository.findById(id).orElse(null);
			
			if (note != null && apiKey.equals(note.getOwner())) {
				
				noteRepository.delete(note);
			}
			
		} catch (SignatureException e) {
			
			e.printStackTrace();
		}
	}

}
