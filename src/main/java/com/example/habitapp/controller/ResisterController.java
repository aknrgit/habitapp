package com.example.habitapp.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.habitapp.entity.User;
import com.example.habitapp.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ResisterController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String loginId,
            @RequestParam String password) {

        String friendCode =
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8);

        User user = new User(loginId, password, friendCode);

        userRepository.save(user);

        return "redirect:/today";
    }
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
   @PostMapping("/login")
    public String login(@RequestParam String loginId,@RequestParam String password,HttpSession session) {
        User user = userRepository.findByLoginId(loginId);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loginUser", user);
            return "redirect:/today";
        }

        return "redirect:/login";
}
}
