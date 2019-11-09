package com.example.restcontroller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Task;
import com.example.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskRestController {
	@Autowired
	private TaskService taskService;

	@GetMapping("/{id}")
	public ResponseEntity<?> show(@PathVariable("id") Integer id) {
		return new ResponseEntity<>(taskService.findOne(id).get(), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> showAll() {
		return new ResponseEntity<>(taskService.findAll(), HttpStatus.OK);
	}

//	@Secured("ROLE_ADMIN")
	@PostMapping
	public ResponseEntity<?> add(@RequestBody Task task) {
		return new ResponseEntity<>(taskService.save(task), HttpStatus.CREATED);
	}

//	@Secured("ROLE_ADMIN")
	@PutMapping
	public ResponseEntity<?> update( @RequestBody Task task) {
		return new ResponseEntity<>(taskService.save(task), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Integer id) {
		taskService.delete(taskService.findOne(id).get());
	}

	@ExceptionHandler
	public ResponseEntity<?> noSuchHandler(NoSuchElementException e) {
		return ResponseEntity.notFound().build();
	}
}
