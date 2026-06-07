package com.example.habitapp.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.habitapp.entity.SupportComment;
import com.example.habitapp.repository.SupportCommentRepository;

@Controller
public class FriendController {

    private final SupportCommentRepository supportCommentRepository;

    public FriendController(SupportCommentRepository supportCommentRepository) {
        this.supportCommentRepository = supportCommentRepository;
    }

    @GetMapping("/friend")
    public String friend() {
        return "friend";
    }

  
    @PostMapping("/comments/send")
    public String sendComment(
        @RequestParam String name,
        @RequestParam String message,
        RedirectAttributes redirectAttributes) {
    SupportComment comment = new SupportComment(
            name,
            message,
            LocalDateTime.now()
    );

    supportCommentRepository.save(comment);

    redirectAttributes.addFlashAttribute(
            "success",
            "コメントを送信しました！"
    );

    return "redirect:/friend";
    }
}