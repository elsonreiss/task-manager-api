package com.elsonreis.tesk_manager.controller;

import com.elsonreis.tesk_manager.dto.request.TaskRequest;
import com.elsonreis.tesk_manager.dto.response.TaskResponse;
import com.elsonreis.tesk_manager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin
public class TaskController {

    final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        TaskResponse createdTask = service.createTask(taskRequest);
        return ResponseEntity.status(201).body(createdTask);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> findAll() {
        List<TaskResponse> tasks = service.findAll();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> findById(@PathVariable Long id) {
        TaskResponse task = service.findById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<TaskResponse>> findByBoardId(@PathVariable Long boardId) {
        List<TaskResponse> tasks = service.findByBoardId(boardId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponse>> findByStatus(String status) {
        List<TaskResponse> tasks = service.findByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody TaskRequest taskRequest) {
        TaskResponse updatedTask = service.updateTask(id, taskRequest);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
