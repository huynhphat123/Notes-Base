package com.tuanphat.repository;

import com.tuanphat.entity.Notes;
import com.tuanphat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

    public List<Notes> findByUser(User user);

}
