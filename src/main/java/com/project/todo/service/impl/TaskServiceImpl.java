package com.project.todo.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.todo.model.Task;
import com.project.todo.model.User;
import com.project.todo.repository.TaskRepository;
import com.project.todo.service.SecurityService;
import com.project.todo.service.TaskService;
import com.project.todo.service.UserService;

@Service
public class TaskServiceImpl implements TaskService {

	private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public void addTask(String desc) {

		try {
			User user = userService.findByUsername(securityService.findLoggedInUsername());

			Task t = new Task();
			t.setUser(user);
			t.setDesc(desc);
			t.setCurrentDate(new Date());
			t.setLastUpdDt(new Date());
			t.setIsCompleted(false);
			taskRepository.save(t); 
		}
		catch(Exception ex) {
			logger.info("error saving task");
		}
	}

	@Override
	public void updateTask(Long id, String desc) {
		logger.info("updating task");

		try {
			Task task = taskRepository.findOneById(id);

			if(task != null) {
				task.setDesc(desc);
				task.setLastUpdDt(new Date());
				taskRepository.save(task);
			}
		}
		catch(Exception ex) {
			logger.error("error updating task");
		}
	}

	@Override
	public void removeTask(Long id) {

		try {
			Task task = taskRepository.findOneById(id);

			if(task != null) {
				taskRepository.delete(id);
			}
		}
		catch(Exception ex) {
			logger.error("error removing task");
		}
	}

	@Override
	public void toggleCompleted(Long id, Boolean isCompleted) {

		try {
			Task t = taskRepository.findOneById(id);

			if(t != null) {
				boolean completed = (isCompleted == null || isCompleted == Boolean.FALSE) ? false : true;
				t.setIsCompleted(completed);
				taskRepository.save(t);
			}
		}
		catch(Exception ex) {
			logger.error("error toggling completed");
		}
	}

	@Override
	public List<Task> getAllTasksForUser(Long id) {
		logger.debug("getting tasks for user id: " + id.toString());

		List<Task> tasks = null;

		try {
			tasks = taskRepository.findAllByUserId(id);
		}
		catch(Exception ex) {
			logger.error("error getting all tasks for user");
		}

		return tasks;
	}

	@Override
	public List<Task> getActiveTasksForUser(Long id) {
		logger.debug("getting tasks for user id: " + id.toString());

		List<Task> tasks = null;

		try {
			tasks = taskRepository.findAllByUserIdAndIsCompleted(id, false);
		}
		catch(Exception ex) {
			logger.error("error getting all tasks for user");
		}

		return tasks;
	}

	@Override
	public List<Task> getCompletedTasksForUser(Long id) {
		logger.debug("getting tasks for user id: " + id.toString());

		List<Task> tasks = null;

		try {
			tasks = taskRepository.findAllByUserIdAndIsCompleted(id, true);
		}
		catch(Exception ex) {
			logger.error("error getting all tasks for user");
		}

		return tasks;
	}

	@Override
	public void clearCompleted() {
		logger.debug("clearing completed tasks");

		try {
			User user = userService.findByUsername(securityService.findLoggedInUsername());

			List<Task> tasks = taskRepository.findAllByUserIdAndIsCompleted(user.getId(), true);

			for(Task task : tasks) {
				if(task.getIsCompleted()) {
					taskRepository.delete(task);
				}
			}
		}
		catch(Exception ex) {
			logger.error("error getting all tasks for user");
		}
	}
}
