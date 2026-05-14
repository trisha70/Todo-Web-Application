package com.main.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entity.Task;
import com.main.entity.User;
import com.main.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository trepo;

    /* Save Task */
    public Task saveTask(Task task) {
        return trepo.save(task);
    }

    /* Get all tasks of user */
    public List<Task> getUserTasks(User user) {
        return trepo.findByUserAndDeletedFalseOrderByTaskDateAsc(user);
    }

    /* Mark task completed */
    public void markComplete(Long id) {

        Task task = trepo.findById(id).get();

        task.setCompleted(true);
        task.setCompletedDate(LocalDate.now());
        trepo.save(task);
    }

    /* Soft delete task */
    public void deleteTask(Long id) {

        Task task = trepo.findById(id).get();

        task.setDeleted(true);

        trepo.save(task);
    }

    /* Restore deleted task */
    public void restoreTask(Long id) {

        Task task = trepo.findById(id).get();

        task.setDeleted(false);

        trepo.save(task);
    }

    /* Get task by id */
    public Task getTaskById(Long id) {

        return trepo.findById(id).get();
    }

    /* Update task */
    public void updateTask(Task task) {

        Task existingTask = trepo.findById(task.getId()).get();

        existingTask.setTitle(task.getTitle());
        existingTask.setPriority(task.getPriority());
        existingTask.setTaskDate(task.getTaskDate());
        existingTask.setStartTime(task.getStartTime());
        existingTask.setEndTime(task.getEndTime());

        trepo.save(existingTask);
    }

    /* Completed tasks */
    public List<Task> getCompletedTasks(User user) {

        return trepo.findByUserAndCompletedTrueAndDeletedFalse(user);
    }

    /* Deleted tasks */
    public List<Task> getDeletedTasks(User user) {

        return trepo.findByUserAndDeletedTrue(user);
    }

    /* Due tasks */
    public List<Task> getDueTasks(User user) {

        return trepo.findByUserAndTaskDateBeforeAndCompletedFalseAndDeletedFalse(
                user, LocalDate.now());
    }

    /* Search tasks */
    public List<Task> searchTasks(User user, String keyword) {

        return trepo.findByUserAndTitleContainingIgnoreCaseAndDeletedFalse(
                user, keyword);
    }
    /* Filter by priority */

    public List<Task> getTasksByPriority(User user,String priority){

        return trepo.findByUserAndPriorityAndDeletedFalse(user,priority);

    }

    /* Sort by priority */

    public List<Task> getTasksSortedByPriority(User user){

        return trepo.findTasksSortedByPriority(user);

    }
}