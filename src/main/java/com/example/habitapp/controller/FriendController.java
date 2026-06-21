package com.example.habitapp.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.habitapp.entity.DailyComment;
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
    private final com.example.habitapp.repository.DailyCommentRepository dailyCommentRepository;

    public FriendController(SupportCommentRepository supportCommentRepository,
                            FriendRepository friendRepository,
                            UserRepository userRepository,
                            com.example.habitapp.repository.DailyCommentRepository dailyCommentRepository) {
        this.supportCommentRepository = supportCommentRepository;
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
        this.dailyCommentRepository = dailyCommentRepository;
    }



    @PostMapping("/friend/add")
    public String addFriend(@RequestParam String friendCode, HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login";

        System.out.println("入力された友達コード = " + friendCode);

        User friendUser = userRepository.findByFriendCode(friendCode);

        if (friendUser == null) {
            System.out.println("友達コードが見つかりません");
            return "redirect:/friend";
        }

        System.out.println("見つかった友達ID = " + friendUser.getId());

        Friend friend = new Friend(loginUser.getId(), friendUser.getId());

        friendRepository.save(friend);

        System.out.println("友達を保存しました");

        return "redirect:/friend";
    }

    @GetMapping("/friend")
    public String friend(Model model, HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login";

        List<Friend> friendLinks =
                friendRepository.findByOwnerUserId(loginUser.getId());

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

  
    @GetMapping("/friend/timeline/{friendUserId}")
    public String showFriendTimeline(@PathVariable Long friendUserId, Model model) {

        User friendUser = userRepository.findById(friendUserId).orElseThrow();

        List<DailyComment> dailyComments =
                dailyCommentRepository.findByUserIdOrderByCreatedDateDesc(friendUserId);

        Map<Long, List<SupportComment>> commentMap = new HashMap<>();

        for (DailyComment dailyComment : dailyComments) {
            List<SupportComment> comments =
                    supportCommentRepository.findByDailyCommentIdOrderByCreatedAtAsc(dailyComment.getId());

            commentMap.put(dailyComment.getId(), comments);
        }

        model.addAttribute("friendUser", friendUser);
        model.addAttribute("dailyComments", dailyComments);
        model.addAttribute("commentMap", commentMap);

        return "friend-timeline";
    }

        @PostMapping("/comments/send")
        public String sendComment(@RequestParam Long dailyCommentId,
                    @RequestParam(required = false) String name,
                    @RequestParam String message,
                    HttpSession session) {

        DailyComment dailyComment =
            dailyCommentRepository.findById(dailyCommentId).orElseThrow();

        User loginUser = (User) session.getAttribute("loginUser");
        String supporterName = name;
        if ((supporterName == null || supporterName.isBlank()) && loginUser != null) {
            supporterName = loginUser.getLoginId();
        }

        SupportComment supportComment = new SupportComment(
            supporterName,
            message,
            LocalDateTime.now(),
            dailyCommentId
        );

        supportCommentRepository.save(supportComment);

        return "redirect:/friend/timeline/" + dailyComment.getUserId();
        }
}