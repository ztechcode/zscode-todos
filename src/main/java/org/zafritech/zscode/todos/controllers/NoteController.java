package org.zafritech.zscode.todos.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.zafritech.zscode.commons.security.Identity;
import org.zafritech.zscode.todos.data.daos.JsonNoteDao;
import org.zafritech.zscode.todos.data.models.Note;
import org.zafritech.zscode.todos.services.NotesService;

@RestController
@RequestMapping("/notes")
public class NoteController {

	@Autowired
	private Identity identity;

	@Autowired
	private NotesService notesService;

	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ResponseEntity<List<Note>> fetchAllNotes(@RequestHeader (name="Authorization") String bearerToken) {
		
		List<Note> notes = notesService.fetchAllNotes(bearerToken);
		
		return new ResponseEntity<>(notes, HttpStatus.OK);
	}	

	@RequestMapping(value = "/apikey", method = RequestMethod.GET)
	public ResponseEntity<String> getApiKey(@RequestHeader (name="Authorization") String bearerToken) {
		
		String apiKey = identity.getApiKey(bearerToken);
		
		return new ResponseEntity<>(apiKey, HttpStatus.OK);		
	}

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Note> getNoteById(@RequestHeader (name="Authorization") String bearerToken, 
            								@PathVariable(value = "id") Long id) {
		
		Note note = notesService.findNoteById(bearerToken, id) ;

		if (note == null) {
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found.");
		}
		
		return new ResponseEntity<>(note, HttpStatus.OK);
    }

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ResponseEntity<Note> createNote(@RequestHeader (name="Authorization") String bearerToken, 
										   @Valid @RequestBody JsonNoteDao dao) { 
		
		Note note = notesService.createNote(bearerToken, dao);
		
		return new ResponseEntity<>(note, HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<Note> updateNote(@RequestHeader (name="Authorization") String bearerToken, 
										   @Valid @RequestBody JsonNoteDao dao) { 

		Note newNote = notesService.updateNote(bearerToken, dao);
		
		return new ResponseEntity<>(newNote, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteNote(@RequestHeader (name="Authorization") String bearerToken, 
										@PathVariable(value = "id") Long id) { 
		
		notesService.deleteNote(bearerToken, id);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}