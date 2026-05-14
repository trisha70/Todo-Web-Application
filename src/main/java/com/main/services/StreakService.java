package com.main.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entity.Task;
import com.main.entity.User;
import com.main.repository.TaskRepository;

@Service
	public class StreakService {

	    @Autowired
	    private TaskRepository trepo;

	    /* CURRENT STREAK */

	    public int getCurrentStreak(User user){

	        List<Task> tasks = trepo.findByUserAndCompletedTrue(user);

	        Set<LocalDate> dates = tasks.stream()
	                .map(Task::getCompletedDate)
	                .filter(Objects::nonNull)
	                .collect(Collectors.toSet());

	        int streak = 0;
	        LocalDate today = LocalDate.now();

	        while(dates.contains(today.minusDays(streak))){
	            streak++;
	        }

	        return streak;
	    }

	    /* LONGEST STREAK */

	    public int getLongestStreak(User user){

	        List<Task> tasks = trepo.findByUserAndCompletedTrue(user);

	        List<LocalDate> dates = tasks.stream()
	                .map(Task::getCompletedDate)
	                .filter(Objects::nonNull)
	                .distinct()
	                .sorted()
	                .collect(Collectors.toList());

	        int maxStreak = 0;
	        int current = 1;

	        for(int i = 1; i < dates.size(); i++){

	            if(dates.get(i).equals(dates.get(i-1).plusDays(1))){
	                current++;
	            } else {
	                maxStreak = Math.max(maxStreak, current);
	                current = 1;
	            }
	        }

	        return Math.max(maxStreak, current);
	    }
	}


