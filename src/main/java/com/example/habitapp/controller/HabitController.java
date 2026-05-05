package com.example.habitapp.controller;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.habitapp.entity.Habit;
import com.example.habitapp.repository.Habitrepository;
import com.example.habitapp.dto.CharacterData;




@Controller
public class HabitController {
    @Autowired
    Habitrepository habitrepository;


    //習慣全件表示
    @GetMapping("/habits")
    public String showHabitList(Model model) {
        model.addAttribute("habits", habitrepository.findAll());
        int day = LocalDate.now().getDayOfMonth();
        
        String[] messages = {
            "新しい月のスタート！一歩踏み出そう！",
            "昨日の自分より少し前へ！",
            "いい流れ来てる！",
            "小さな積み重ねが未来を変える",
            "焦らずコツコツいこう",
            "今日もやった自分えらい",
            "続けてるだけで強い",
            "習慣は裏切らない",
            "ほんの少しでもOK",
            "できた自分をちゃんと認めよう",

            "継続が力になる",
            "今日の1回が明日を変える",
            "無理しないで続けよう",
            "淡々と、それが最強",
            "やる気なくても1分だけやってみよう",
            "昨日より0.1成長",
            "今日もナイスチャレンジ！",
            "完璧じゃなくていい",
            "やめなければ負けじゃない",
            "今日も積み上げ成功",

            "少しずつでも前進",
            "未来の自分が喜んでる",
            "今やってることはちゃんと意味がある",
            "習慣が人生を作る",
            "ここまで続けてるのすごい",
            "今日も一歩クリア！",
            "いい感じ、その調子",
            "止まらなければOK",
            "続けてる自分を誇れ",
            "今日もよくやった！"
        };
        Map<Integer, CharacterData> characterMap = new HashMap<>();

        for (int i = 1; i <= messages.length; i++) {
            characterMap.put(i,
                new CharacterData(
                    "/image/" + i + ".png",
                    messages[i - 1]
                )
            );
        }
        CharacterData today = characterMap.get(day);
        if (today == null) {
            today = new CharacterData("/images/1.png", "今日もコツコツ！");
        }
        model.addAttribute("characterImage", today.getImage());
        model.addAttribute("message", today.getMessage());
        return "habits";
    }

    //習慣化セーブ
    @PostMapping("/habits")
    public String addHabit(@RequestParam String title,@RequestParam String description) {
        Habit habit = new Habit(title, description);
        habitrepository.save(habit);
        return "redirect:/habits";
    }
    //削除
    @GetMapping("/habits/delete")
    public String deleteHabit(@RequestParam Long id) {
        habitrepository.deleteById(id);
        return "redirect:/habits";
    }
    //編集
    @GetMapping("/habits/edit")
    public String editHabit(@RequestParam Long id, Model model) {
        Habit habit = habitrepository.findById(id).orElse(null);
        model.addAttribute("habit", habit);
        return "habit-edit";
    }

    @PostMapping("/habits/update")
    public String updateHabit(@RequestParam Long id,@RequestParam String title,@RequestParam String description) {
        // ① 既存データを取得
        Habit habit = habitrepository.findById(id).orElse(null);

        // ② 値を変更
        habit.setTitle(title);
        habit.setDescription(description);

        // ③ 保存（更新）
        habitrepository.save(habit);
        return "redirect:/habits";
    }
}
