package com.hacheery.accountbe.service;

import com.hacheery.accountbe.entity.Note;
import java.util.List;

public interface NoteService {
    List<Note> getAllNotes();
    Note getNoteById(Long id);
    Note createNote(Note note, String currentUsername);
    Note updateNote(Long id, Note note, String currentUsername);
    void deleteNote(Long id);
}
