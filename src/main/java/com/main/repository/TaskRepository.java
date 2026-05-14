package com.main.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.main.entity.Task;
import com.main.entity.User;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

    List<Task> findByUserAndDeletedFalseOrderByTaskDateAsc(User user);

    List<Task> findByUserAndCompletedTrueAndDeletedFalse(User user);

    List<Task> findByUserAndDeletedTrue(User user);

    List<Task> findByUserAndTaskDateBeforeAndCompletedFalseAndDeletedFalse(User user, LocalDate date);

    List<Task> findByUserAndTitleContainingIgnoreCaseAndDeletedFalse(User user,String keyword);

    /* Priority filter */

    List<Task> findByUserAndPriorityAndDeletedFalse(User user,String priority);

    /* Priority sorting (correct order HIGH → MEDIUM → LOW) */

    @Query("""
           SELECT t FROM Task t
           WHERE t.user = :user AND t.deleted = false
           ORDER BY 
           CASE
               WHEN t.priority = 'HIGH' THEN 1
               WHEN t.priority = 'MEDIUM' THEN 2
               WHEN t.priority = 'LOW' THEN 3
           END
           """)
    List<Task> findTasksSortedByPriority(@Param("user") User user);
    
    long countByUserAndDeletedFalse(User user);

    long countByUserAndCompletedTrueAndDeletedFalse(User user);

    long countByUserAndCompletedFalseAndDeletedFalse(User user);
    List<Task> findByUserAndCompletedTrue(User user);
    /* Analytics - Priority count */

    long countByUserAndPriorityAndDeletedFalse(User user, String priority);

    /* Analytics - Date based */

    long countByUserAndCompletedDateAndDeletedFalse(User user, LocalDate date);

    long countByUserAndCompletedDateAfterAndDeletedFalse(User user, LocalDate date);

}