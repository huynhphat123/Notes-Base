package com.tuanphat.service;

import com.tuanphat.entity.Notes;
import com.tuanphat.entity.User;
import com.tuanphat.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesServiceImpl implements NotesService{

    @Autowired
    private NotesRepository notesRepository;

    @Override
    public Notes saveNotes(Notes notes) {
        return notesRepository.save(notes);
    }

    @Override
    public Notes getNoteById(int id) {
        return notesRepository.findById(id).get();
    }

    @Override
    public Page<Notes> getNotesByUser(User user, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum,5);
        return notesRepository.findByUser(user,pageable);
    }

    @Override
    public Notes updateNotes(Notes notes) {
        return notesRepository.save(notes);

    }

    @Override
    public boolean deleteNotes(int id) {
        Notes notes = notesRepository.findById(id).get();
        if(notes != null)
        {
            notesRepository.delete(notes);
            return true;
        }
        return false;
    }
}
