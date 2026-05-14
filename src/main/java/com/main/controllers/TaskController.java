package com.main.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.main.entity.Task;
import com.main.entity.User;
import com.main.repository.UserRepository;
import com.main.services.TaskService;

@Controller
public class TaskController {

    @Autowired
    private TaskService tser;

    @Autowired
    private UserRepository urepo;

    /* Open Add Task Page */
    @GetMapping("/addTask")
    public String openAddTaskPage(Model model) {

        model.addAttribute("task", new Task());

        return "addTask";
    }

    /* Save Task */
    @PostMapping("/saveTask")
    public String saveTask(@ModelAttribute Task task, Principal principal) {

        String email = principal.getName();

        User user = urepo.findByEmail(email).get();

        task.setUser(user);
        task.setCompleted(false);
        task.setDeleted(false);

        tser.saveTask(task);

        return "redirect:/yourTasks";
    }

    /* Show Tasks */
    @GetMapping("/yourTasks")
    public String showTasks(Model model, Principal principal) {

        User user = urepo.findByEmail(principal.getName()).get();

        List<Task> tasks = tser.getUserTasks(user);

        model.addAttribute("tasks", tasks);

        return "tasks";
    }

    /* Mark Complete */
    @GetMapping("/task/complete/{id}")
    public String markComplete(@PathVariable Long id) {

        tser.markComplete(id);

        return "redirect:/yourTasks";
    }

    /* Delete Task */
    @GetMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable Long id) {

        tser.deleteTask(id);

        return "redirect:/yourTasks";
    }

    /* Edit Task Page */
    @GetMapping("/task/edit/{id}")
    public String editTaskPage(@PathVariable Long id, Model model) {

        Task task = tser.getTaskById(id);

        model.addAttribute("task", task);

        return "editTask";
    }

    /* Update Task */
    @PostMapping("/task/update")
    public String updateTask(@ModelAttribute Task task) {

        tser.updateTask(task);

        return "redirect:/yourTasks";
    }

    /* Completed Tasks */
    @GetMapping("/completedTasks")
    public String completedTasks(Model model, Principal principal) {

        User user = urepo.findByEmail(principal.getName()).get();

        model.addAttribute("tasks", tser.getCompletedTasks(user));

        return "tasks";
    }

    /* Deleted Tasks */
    @GetMapping("/deletedTasks")
    public String deletedTasks(Model model, Principal principal) {

        User user = urepo.findByEmail(principal.getName()).get();

        model.addAttribute("tasks", tser.getDeletedTasks(user));

        return "tasks";
    }
    /* Restore Task */
    @GetMapping("/task/restore/{id}")
    public String restoreTask(@PathVariable Long id) {

        tser.restoreTask(id);

        return "redirect:/deletedTasks";
    }

    /* Due Tasks */
    @GetMapping("/dueTasks")
    public String dueTasks(Model model, Principal principal) {

        User user = urepo.findByEmail(principal.getName()).get();

        model.addAttribute("tasks", tser.getDueTasks(user));

        return "tasks";
    }
    /* Search Tasks */
    @GetMapping("/task/search")
    public String searchTasks(@RequestParam String keyword,
                              Principal principal,
                              Model model) {

        User user = urepo.findByEmail(principal.getName()).get();

        model.addAttribute("tasks", tser.searchTasks(user, keyword));

        return "tasks";
    }
    /* Tasks by Priority */

    @GetMapping("/tasks/priority/{level}")
    public String tasksByPriority(@PathVariable String level,
                                  Model model,
                                  Principal principal){

        User user = urepo.findByEmail(principal.getName()).get();

        model.addAttribute("tasks", tser.getTasksByPriority(user, level));

        return "tasks";
    }
    @GetMapping("/tasks/sort/priority")
    public String sortByPriority(Model model,Principal principal){

        User user = urepo.findByEmail(principal.getName()).get();

        model.addAttribute("tasks", tser.getTasksSortedByPriority(user));

        return "tasks";
    }
}