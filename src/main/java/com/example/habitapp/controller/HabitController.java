package com.example.habitapp.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class HabitController {
    private List<String> habits = new ArrayList<>();

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
    }
}
