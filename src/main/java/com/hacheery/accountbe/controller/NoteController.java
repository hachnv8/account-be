package com.hacheery.accountbe.controller;

import com.hacheery.accountbe.entity.Note;
import com.hacheery.accountbe.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNoteById(id));
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note, Principal principal) {
        String username = (principal != null) ? principal.getName() : "System";
        Note savedNote = noteService.createNote(note, username);
        return ResponseEntity.ok(savedNote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note note, Principal principal) {
        String username = (principal != null) ? principal.getName() : "System";
        Note updatedNote = noteService.updateNote(id, note, username);
        return ResponseEntity.ok(updatedNote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
