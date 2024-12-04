package com.tuanphat.controller;

import com.tuanphat.entity.User;
import com.tuanphat.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, HttpSession session) {

        boolean f = userService.existEmailCheck(user.getEmail());

        if (f) {
            session.setAttribute("msg", "Email đã tồn tại");
        } else {
            User saveUser = userService.saveUser(user);
            if (saveUser != null) {
                session.setAttribute("msg", "Đăng ký thành công");
            } else {
                session.setAttribute("msg", "Có lỗi xảy ra, vui lòng thử lại!");
            }
        }

        return "redirect:/register";
    }

    @GetMapping("/signin")
    public String login() {
        return "login";
    }

}
