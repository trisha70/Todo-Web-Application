package com.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entity.User;
import com.main.repository.TaskRepository;

@Service
public class ProgressServices {

    @Autowired
    private TaskRepository trepo;

    public long getTotalTasks(User user){
        return trepo.countByUserAndDeletedFalse(user);
    }

    public long getCompletedTasks(User user){
        return trepo.countByUserAndCompletedTrueAndDeletedFalse(user);
    }

    public long getPendingTasks(User user){
        return trepo.countByUserAndCompletedFalseAndDeletedFalse(user);
    }

    public int getProgressPercentage(User user){

        long total = getTotalTasks(user);

        long completed = getCompletedTasks(user);

        if(total == 0){
            return 0;
        }

        return (int)((completed * 100) / total);
    }
}