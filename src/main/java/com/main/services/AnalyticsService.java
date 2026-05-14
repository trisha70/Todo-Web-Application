package com.main.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entity.User;
import com.main.repository.TaskRepository;

@Service
public class AnalyticsService {

    @Autowired
    private TaskRepository trepo;

    /* =========================
       BASIC COUNTS
       ========================= */

    public long getTotalTasks(User user){
        return trepo.countByUserAndDeletedFalse(user);
    }

    public long getCompletedTasks(User user){
        return trepo.countByUserAndCompletedTrueAndDeletedFalse(user);
    }

    public long getPendingTasks(User user){
        return trepo.countByUserAndCompletedFalseAndDeletedFalse(user);
    }

    /* =========================
       COMPLETION RATE
       ========================= */

    public int getCompletionRate(User user){

        long total = getTotalTasks(user);
        long completed = getCompletedTasks(user);

        if(total == 0){
            return 0;
        }

        return (int)((completed * 100) / total);
    }

    /* =========================
       PRIORITY ANALYTICS
       ========================= */

    public long getHighPriority(User user){
        return trepo.countByUserAndPriorityAndDeletedFalse(user, "HIGH");
    }

    public long getMediumPriority(User user){
        return trepo.countByUserAndPriorityAndDeletedFalse(user, "MEDIUM");
    }

    public long getLowPriority(User user){
        return trepo.countByUserAndPriorityAndDeletedFalse(user, "LOW");
    }

    /* =========================
       DAILY ANALYTICS
       ========================= */

    public long getTodayCompleted(User user){
        return trepo.countByUserAndCompletedDateAndDeletedFalse(user, LocalDate.now());
    }

    /* =========================
       WEEKLY ANALYTICS
       ========================= */

    public long getWeeklyCompleted(User user){
        return trepo.countByUserAndCompletedDateAfterAndDeletedFalse(
                user, LocalDate.now().minusDays(7));
    }
}