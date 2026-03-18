package com.elsonreis.tesk_manager.controller;

import com.elsonreis.tesk_manager.dto.request.CommentRequest;
import com.elsonreis.tesk_manager.dto.response.CommentResponse;
import com.elsonreis.tesk_manager.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest commentRequest) {
        CommentResponse createdComment = service.createComment(commentRequest);
        return ResponseEntity.status(201).body(createdComment);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> findAll() {
        List<CommentResponse> comments = service.findAll();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id) {
        CommentResponse comment = service.findById(id);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/task")
    public ResponseEntity<List<CommentResponse>> findByTaskId(@RequestParam Long taskId) {
        List<CommentResponse> comments = service.findByTaskId(taskId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/user")
    public ResponseEntity<List<CommentResponse>> findByUserId(@RequestParam Long userId) {
        List<CommentResponse> comments = service.findByUserId(userId);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest) {
        CommentResponse updatedComment = service.updateComment(id, commentRequest);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        service.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
