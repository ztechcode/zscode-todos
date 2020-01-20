package org.zafritech.zscode.todos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.zafritech.zscode.todos.data.models.Note;
import org.zafritech.zscode.todos.data.repositories.NoteRepository;
import org.zafritech.zscode.todos.services.NotesService;

@RestController
@RequestMapping("/notes")
public class NoteController {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private NotesService notesService;

	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ResponseEntity<List<Note>> fetchAllNotes(@RequestHeader (name="Authorization") String bearerToken) {
		
		List<Note> notes = notesService.fetchAllNotes(bearerToken);
		
		return new ResponseEntity<>(notes, HttpStatus.OK);
	}	

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Note> getTaskById(@PathVariable(value = "id") Long id) {
		
		Note note = noteRepository.findById(id).orElse(null); 

		if (note == null) {
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found.");
		}
		
		return new ResponseEntity<>(note, HttpStatus.OK);
    }

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ResponseEntity<Note> createTask(@RequestHeader (name="Authorization") String bearerToken, 
			                               @RequestParam("note") String text) { 
		
		Note note = notesService.createNote(bearerToken, text);
		
		return new ResponseEntity<>(note, HttpStatus.OK);
	}

}
