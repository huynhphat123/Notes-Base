package com.tuanphat.service;

import com.tuanphat.entity.Notes;
import com.tuanphat.entity.User;

import java.util.List;

public interface NotesService {

    public Notes saveNotes(Notes notes);

    public Notes getNoteById(int id);

    public List<Notes> getNotesByUser(User user);

    public Notes updateNotes(Notes notes);

    public boolean deleteNotes(int id);
}
