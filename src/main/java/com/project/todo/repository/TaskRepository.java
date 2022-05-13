package com.project.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.todo.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findAllByUserId(Long userId);
	List<Task> findAllByUserIdAndIsCompleted(Long userId, Boolean isCompleted);
	Task findOneById(Long taskId);
}
