package com.example.habitapp.controller;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.habitapp.entity.DailyComment;
import com.example.habitapp.entity.DailySchedule;
import com.example.habitapp.entity.Habit;
import com.example.habitapp.entity.HabitRecord;
import com.example.habitapp.entity.SupportComment;
import com.example.habitapp.entity.User;
import com.example.habitapp.repository.DailyCommentRepository;
import com.example.habitapp.repository.DailyScheduleRepository;
import com.example.habitapp.repository.FriendRepository;
import com.example.habitapp.repository.HabitRecordRepository;
import com.example.habitapp.repository.Habitrepository;
import com.example.habitapp.repository.SupportCommentRepository;
import com.example.habitapp.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import com.example.habitapp.dto.CharacterData;





@Controller
public class HabitController {
    @Autowired
    Habitrepository habitrepository;

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    UserRepository userRepository;


    //習慣全件表示
    @GetMapping("/habits")
    public String showHabitList(Model model) {
        model.addAttribute("habits", habitrepository.findAll());
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
    @GetMapping("/today")
    public String showHabitListToday(Model model,HttpSession session) {

    List<Habit> habits = habitrepository.findAll();

    Set<Long> completedHabitIds = new HashSet<>();

    Map<Long, Integer> achievementRates = new HashMap<>();

    LocalDate now = LocalDate.now();
    LocalDate start = now.withDayOfMonth(1);
    LocalDate end = now.withDayOfMonth(now.lengthOfMonth());

    for (Habit habit : habits) {

        boolean done =
            habitRecordRepository.existsByHabitIdAndAchievedDate(
                habit.getId(),
                LocalDate.now()
            );

        if (done) {
            completedHabitIds.add(habit.getId());
        }

        int count =
            habitRecordRepository.countByHabitIdAndAchievedDateBetween(
                habit.getId(),
                start,
                end
            );

        int rate = count * 100 / now.lengthOfMonth();

        achievementRates.put(habit.getId(), rate);
    }

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
        characterMap.put(
            i,
            new CharacterData(
                "/image/" + i + ".png",
                messages[i - 1]
            )
        );
    }

    CharacterData today = characterMap.get(day);

    if (today == null) {
        today = new CharacterData(
            "/image/1.png",
            "今日もコツコツ！"
        );
    }
    
    User loginUser =
        (User) session.getAttribute("loginUser");

    if (loginUser != null) {
        model.addAttribute("loginUser", loginUser);
    }

    model.addAttribute("characterImage", today.getImage());
    model.addAttribute("message", today.getMessage());
    model.addAttribute("habits", habits);
    model.addAttribute("completedHabitIds", completedHabitIds);
    model.addAttribute("achievementRates", achievementRates);

    return "today";
}

    @Autowired
    HabitRecordRepository habitRecordRepository;
    @PostMapping("/habits/complete")
    public String completeHabit(@RequestParam Long habitId) {
        boolean alreadyDone = habitRecordRepository.existsByHabitIdAndAchievedDate(habitId,LocalDate.now());
        if (!alreadyDone) {
            HabitRecord record = new HabitRecord(habitId,LocalDate.now());
            habitRecordRepository.save(record);
        }
        
        return "redirect:/today";
    }
    @GetMapping("/habits/{id}")
    public String showHabitDetail(@PathVariable Long id, Model model) {
        Habit habit = habitrepository.findById(id).orElse(null);

        List<Integer> monthlyRates = new ArrayList<>();
        List<String> monthLabels = new ArrayList<>();
        int year = LocalDate.now().getYear();

        // 1月〜12月
        for (int month = 1; month <= 12; month++) {
            LocalDate monthStart = LocalDate.of(year, month, 1);
            LocalDate monthEnd = monthStart.withDayOfMonth(monthStart.lengthOfMonth());
            int count = habitRecordRepository.countByHabitIdAndAchievedDateBetween(
                    id,
                    monthStart,
                    monthEnd
                );

            int rate = count * 100 / monthStart.lengthOfMonth();
            monthlyRates.add(rate);
            monthLabels.add(month + "月");
        }

        model.addAttribute("habit", habit);
        model.addAttribute("monthlyRates", monthlyRates);
        model.addAttribute("monthLabels", monthLabels);

        return "habit-detail";
    }

    @GetMapping("/achievements/today")
    public String showTodayAchievements(Model model,HttpSession session) {
        List<HabitRecord> records = habitRecordRepository.findByAchievedDate(LocalDate.now());
        model.addAttribute("records", records);

        List<SupportComment> supportComments = supportCommentRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("supportComments", supportComments);

        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);
        return "today-achievements";
    }
    @Autowired
    DailyCommentRepository dailyCommentRepository;
    @PostMapping("/daily-comment")
    public String saveDailyComment(@RequestParam String comment) {
        DailyComment dailyComment = new DailyComment(comment,LocalDate.now());
        dailyCommentRepository.save(dailyComment);
        return "redirect:/achievements/today";
    }

    @GetMapping("/achievements")
    public String showAchievements(Model model) {
        List<HabitRecord> records = habitRecordRepository.findAllByOrderByAchievedDateDesc();
        System.out.println("records = " + records.size());
        List<DailyComment> comments = dailyCommentRepository.findAllByOrderByCreatedDateDesc();
        // 日付ごとの習慣一覧を保存するMap
        Map<LocalDate, List<String>> achievementMap = new LinkedHashMap<>();
        // 日付ごとの感想を保存するMap
        Map<LocalDate, String> commentMap = new LinkedHashMap<>();

         // 達成記録を1件ずつ取り出す
        for (HabitRecord record : records) {
            // 達成日を取得
            LocalDate date = record.getAchievedDate();
            // その日付のListが無ければ新しく作る
            // その後、習慣タイトルを追加
            achievementMap
                .computeIfAbsent(
                    date,
                    d -> new ArrayList<>()
                )
                .add(
                    record.getHabit().getTitle()
                );
        }
        // 感想を1件ずつ取り出す
        for (DailyComment comment : comments) {
            // 感想の日付を取得
            LocalDate date = comment.getCreatedDate();
            // 日付と感想をMapへ保存
            commentMap.put(date,comment.getComment());  
        }
        model.addAttribute("achievementMap",achievementMap);
        model.addAttribute("commentMap",commentMap);

        List<SupportComment> supportComments = supportCommentRepository.findAllByOrderByCreatedAtDesc();
        Map<LocalDate, List<SupportComment>> supportCommentMap = new LinkedHashMap<>();
        for (SupportComment comment : supportComments) {
            LocalDate date = comment.getCreatedAt().toLocalDate();
            supportCommentMap.computeIfAbsent(date, d -> new ArrayList<>()).add(comment);
        }
        model.addAttribute("supportCommentMap", supportCommentMap);
        return "achievements";
    }
    //１日のスケージュール組み立て
    @Autowired
    DailyScheduleRepository dailyScheduleRepository;
   @GetMapping("/daily-schedule")
    public String showDailySchedule(Model model) {
        List<Integer> hours = new ArrayList<>();

        for (int i = 5; i <= 22; i++) {
            hours.add(i);
        }

        List<DailySchedule> schedules =
            dailyScheduleRepository.findAll();

        // 平日
        Map<String, String> weekdayMap =
            new HashMap<>();

        // 休日
        Map<String, String> holidayMap =
            new HashMap<>();

        for (DailySchedule schedule : schedules) {

            if (schedule.getScheduleTime() != null) {

                String hour =
                    String.valueOf(
                        schedule.getScheduleTime().getHour()
                    );

                // 平日
                if ("WEEKDAY".equals(schedule.getScheduleType())) {

                    weekdayMap.put(
                        hour,
                        schedule.getContent()
                    );
                }

                // 休日
                if ("HOLIDAY".equals(schedule.getScheduleType())) {

                    holidayMap.put(
                        hour,
                        schedule.getContent()
                    );
                }
            }
        }

        System.out.println("weekdayMap = " + weekdayMap);
        System.out.println("holidayMap = " + holidayMap);

        model.addAttribute("hours", hours);

        model.addAttribute(
            "weekdayMap",
            weekdayMap
        );

        model.addAttribute(
            "holidayMap",
            holidayMap
        );

        return "daily-schedule";
    }

    @PostMapping("/daily-schedule")
    public String addDailySchedule(@RequestParam String scheduleTime,@RequestParam String content,@RequestParam String scheduleType) {
        DailySchedule schedule = new DailySchedule(LocalTime.parse(scheduleTime),content,scheduleType);
        dailyScheduleRepository.save(schedule);
        return "redirect:/daily-schedule";
    }

    @Autowired
    SupportCommentRepository supportCommentRepository;
    @PostMapping("/support-comment")
    public String saveSupportComment(
            @RequestParam String supporterName,
            @RequestParam String message) {

        SupportComment comment =
            new SupportComment(
                supporterName,
                message,
                LocalDateTime.now()
            );

        supportCommentRepository.save(comment);

        return "redirect:/achievements/today";
    }
    @GetMapping("/support")
    public String showSupportPage() {
        return "support";
    }



}
