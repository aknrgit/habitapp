package com.example.habitapp.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.habitapp.entity.Friend;
import com.example.habitapp.entity.SupportComment;
import com.example.habitapp.entity.User;
import com.example.habitapp.repository.FriendRepository;
import com.example.habitapp.repository.SupportCommentRepository;
import com.example.habitapp.repository.UserRepository;

@Controller
public class FriendController {

    private final SupportCommentRepository supportCommentRepository;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public FriendController(SupportCommentRepository supportCommentRepository,
                            FriendRepository friendRepository,
                            UserRepository userRepository) {
        this.supportCommentRepository = supportCommentRepository;
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }



    @PostMapping("/friend/add")
    public String addFriend(@RequestParam String friendCode) {

        Long loginUserId = 1L;

        System.out.println("入力された友達コード = " + friendCode);

        User friendUser = userRepository.findByFriendCode(friendCode);

        if (friendUser == null) {
            System.out.println("友達コードが見つかりません");
            return "redirect:/friend";
        }

        System.out.println("見つかった友達ID = " + friendUser.getId());

        Friend friend = new Friend(loginUserId, friendUser.getId());

        friendRepository.save(friend);

        System.out.println("友達を保存しました");

        return "redirect:/friend";
    }

    @GetMapping("/friend")
    public String friend(Model model) {

        Long loginUserId = 1L;

        List<Friend> friendLinks =
                friendRepository.findByOwnerUserId(loginUserId);

        System.out.println("friendLinks.size() = " + friendLinks.size());

        List<User> friends = new ArrayList<>();

        for (Friend friend : friendLinks) {
            User user = userRepository.findById(friend.getFriendUserId()).orElse(null);

            if (user != null) {
                friends.add(user);
            }
        }

        System.out.println("friends.size() = " + friends.size());

        model.addAttribute("friends", friends);

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

    @GetMapping("/friend/timeline/{id}")
    public String showFriendTimeline(
            @PathVariable Long id,
            Model model) {
        System.out.println("友達タイムラインID = " + id);

        return "friend-timeline";
    }
}