package com.example.habitapp.controller;
import java.util.ArrayList;
import java.util.List;

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




@Controller
public class HabitController {
   /*  private List<String> habits = new ArrayList<>();

    @RequestMapping(path = "/habits")
    public String habits(Model model) {
        model.addAttribute("habits", habits);
        return "habits";
    }
    @RequestMapping(path = "/habits/new")
    public String addhabits() {
        return "habit-form";
    }
    @RequestMapping(path = "/habits",method=RequestMethod.POST)
    public String receive_habit_parameter(String addhabits) {
    if (addhabits != null && !addhabits.isEmpty()) {
      habits.add(addhabits);
    }
        System.out.println(habits);
        return "redirect:/habits";
    }
    @RequestMapping(path = "/habits/delete",method=RequestMethod.GET)
    public String deleate_index(int index) {
        habits.remove(index);
        return "redirect:/habits";
    }
    @RequestMapping(path = "/habits/edit", method = RequestMethod.GET)
    public String edit(int index, Model model) {
        String task = habits.get(index);
        model.addAttribute("task", task);
        model.addAttribute("index", index);
        return "habit-edit";

    }
    @RequestMapping(path = "/habits/update", method = RequestMethod.POST)
        public String update(int index, String task) {
        habits.set(index, task);
        return "redirect:/habits";
    } */
    @Autowired
    Habitrepository habitrepository;


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
}
