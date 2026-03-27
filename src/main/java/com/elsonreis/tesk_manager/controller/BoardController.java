package com.elsonreis.tesk_manager.controller;

import com.elsonreis.tesk_manager.dto.request.BoardRequest;
import com.elsonreis.tesk_manager.dto.response.BoardResponse;
import com.elsonreis.tesk_manager.secutiry.AuthUtils;
import com.elsonreis.tesk_manager.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
@CrossOrigin
public class BoardController {

    final BoardService service;

    public BoardController(BoardService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@Valid @RequestBody BoardRequest request) {
        String email = AuthUtils.getEmailFromContext();
        BoardResponse createdBoard = service.createBoard(request, email);
        return ResponseEntity.status(201).body(createdBoard);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponse>> findAll() {

        List<BoardResponse> boards = service.findAll();
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> findById(@PathVariable Long id) {

        BoardResponse board = service.findById(id);
        return ResponseEntity.ok(board);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BoardResponse>> findByUserId(@PathVariable Long userId) {

        List<BoardResponse> boards = service.findByUserId(userId);
        return ResponseEntity.ok(boards);
    }

    @PutMapping("/{id}")
     public ResponseEntity<BoardResponse> updateBoard(@PathVariable Long id, @Valid @RequestBody BoardRequest request) {
        String email = AuthUtils.getEmailFromContext();
        BoardResponse updatedBoard = service.updateBoard(id, request, email);
        return ResponseEntity.ok(updatedBoard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id) {

        service.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}
