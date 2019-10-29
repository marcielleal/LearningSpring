package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Task;
import com.example.repository.TaskRepository;

@Service
@Transactional(readOnly = true)
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;

	public List<Task> findAll() {
		return taskRepository.findAll();
	}
	
	public Optional<Task> findOne(Integer id) {
		return taskRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Task save(Task entity) {
		return taskRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Task entity) {
		taskRepository.delete(entity);
	}
}
