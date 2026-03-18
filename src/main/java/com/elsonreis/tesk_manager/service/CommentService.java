package com.elsonreis.tesk_manager.service;

import com.elsonreis.tesk_manager.dto.request.CommentRequest;
import com.elsonreis.tesk_manager.dto.response.CommentResponse;
import com.elsonreis.tesk_manager.entity.Comment;
import com.elsonreis.tesk_manager.entity.Task;
import com.elsonreis.tesk_manager.mapper.CommentMapper;
import com.elsonreis.tesk_manager.repository.CommentRepository;
import com.elsonreis.tesk_manager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    final CommentRepository commentRepository;
    final TaskRepository taskRepository;

    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
    }

    public CommentResponse createComment(CommentRequest commentRequest) {

        Task task = taskRepository.findById(commentRequest.getTaskId())
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrado com id: " + commentRequest.getTaskId()));

        Comment comment = CommentMapper.toEntity(commentRequest);
        comment.setTask(task);

        Comment savedComment = commentRepository.save(comment);

        return CommentMapper.toDto(savedComment);
    }



    public List<CommentResponse> findAll() {

        List<Comment> commentRepository = this.commentRepository.findAll();

        if (commentRepository.isEmpty()) {
            throw new RuntimeException("Nenhum comentário encontrado");
        }

        return commentRepository
                .stream()
                .map(CommentMapper::toDto)
                .toList();
    }

    public CommentResponse findById(Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado com id: " + id));

        return CommentMapper.toDto(comment);
    }

    public List<CommentResponse> findByTaskId(Long taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrado com id: " + taskId));

        List<Comment> comments = commentRepository.findByTaskId(taskId);

        if (comments.isEmpty()) {
            throw new RuntimeException("Nenhum comentário encontrado para a tarefa com id: " + taskId);
        }

        return comments
                .stream()
                .map(CommentMapper::toDto)
                .toList();
    }

     public List<CommentResponse> findByUserId(Long userId) {

        List<Comment> comments = commentRepository.findByUserId(userId);

        if (comments.isEmpty()) {
            throw new RuntimeException("Nenhum comentário encontrado para o usuário com id: " + userId);
        }

        return comments
                .stream()
                .map(CommentMapper::toDto)
                .toList();
    }

    public CommentResponse updateComment(Long id, CommentRequest commentRequest) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado com id: " + id));

        Task task = taskRepository.findById(commentRequest.getTaskId())
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrado com id: " + commentRequest.getTaskId()));

        comment.setMessage(commentRequest.getMessage());
        comment.setTask(task);

        Comment updatedComment = commentRepository.save(comment);

        return CommentMapper.toDto(updatedComment);
    }

     public void deleteComment(Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado com id: " + id));

        commentRepository.delete(comment);
    }
}

