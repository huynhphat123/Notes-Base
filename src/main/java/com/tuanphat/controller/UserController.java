package com.tuanphat.controller;

import com.tuanphat.entity.Notes;
import com.tuanphat.entity.User;
import com.tuanphat.repository.UserRepository;
import com.tuanphat.service.NotesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotesService notesService;

    @ModelAttribute
    public User getUser(Principal principal, Model model) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);
        return user;
    }

    @GetMapping("/addNotes")
    public String addNotes() {
        return "add_notes";
    }

    @GetMapping("/viewNotes")
    public String viewNotes(Model model,Principal principal) {
        User user = getUser(principal, model);
        List<Notes> notes = notesService.getNotesByUser(user);
        model.addAttribute("notesList", notes);
        return "view_notes";
    }

    @GetMapping("/editNotes/{id}")
    public String editNotes(@PathVariable int id, Model model) {
        Notes notes = notesService.getNoteById(id);
        model.addAttribute("n", notes);
        return "edit_notes";
    }

    @PostMapping("/saveNotes")
    public String saveNotes(@ModelAttribute Notes notes, HttpSession session,Principal principal,Model model) {
        notes.setDate(LocalDate.now());
        notes.setUser(getUser(principal,model));

        Notes saveNotes = notesService.saveNotes(notes);
        if (saveNotes != null) {
            session.setAttribute("msg", "Ghi chú thành công");
        } else {
            session.setAttribute("msg", "Có lỗi xảy ra, vui lòng thử lại!");
        }
        return "redirect:/user/addNotes";
    }

    @PostMapping("/updateNotes")
    public String updateNotes(@ModelAttribute Notes notes, HttpSession session,Principal principal,Model model) {
        notes.setDate(LocalDate.now());
        notes.setUser(getUser(principal,model));

        Notes saveNotes = notesService.saveNotes(notes);
        if (saveNotes != null) {
            session.setAttribute("msg", "Ghi chú cập nhật thành công");
        } else {
            session.setAttribute("msg", "Có lỗi xảy ra, vui lòng thử lại!");
        }
        return "redirect:/user/viewNotes";
    }

    @GetMapping("/deleteNotes/{id}")
    public String deleteNotes(@PathVariable int id,HttpSession session) {
        boolean deleteId = notesService.deleteNotes(id);
        if (deleteId) {
            session.setAttribute("msg", "xóa ghi chú thành công");
        } else {
            session.setAttribute("msg", "Có lỗi xảy ra, vui lòng thử lại!");
        }
        return "redirect:/user/viewNotes";
    }
}
