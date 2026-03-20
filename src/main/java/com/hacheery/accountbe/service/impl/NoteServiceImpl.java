package com.hacheery.accountbe.service.impl;

import com.hacheery.accountbe.entity.Note;
import com.hacheery.accountbe.repository.NoteRepository;
import com.hacheery.accountbe.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
    }

    @Override
    public Note createNote(Note note, String currentUsername) {
        if (currentUsername != null) {
            note.setCreatedBy(currentUsername);
        } else {
            note.setCreatedBy("System");
        }
        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(Long id, Note updatedNote, String currentUsername) {
        Note existingNote = getNoteById(id);
        existingNote.setTitle(updatedNote.getTitle());
        existingNote.setContent(updatedNote.getContent());
        existingNote.setProjectName(updatedNote.getProjectName());
        existingNote.setType(updatedNote.getType());
        existingNote.setPriority(updatedNote.getPriority());
        existingNote.setStatus(updatedNote.getStatus());
        existingNote.setUpdatedBy(currentUsername != null ? currentUsername : "System");
        return noteRepository.save(existingNote);
    }

    @Override
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
}
