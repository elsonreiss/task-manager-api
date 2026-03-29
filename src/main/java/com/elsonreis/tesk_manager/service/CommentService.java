package com.elsonreis.tesk_manager.service;

import com.elsonreis.tesk_manager.dto.request.CommentRequest;
import com.elsonreis.tesk_manager.dto.response.CommentResponse;
import com.elsonreis.tesk_manager.entity.Comment;
import com.elsonreis.tesk_manager.entity.Task;
import com.elsonreis.tesk_manager.entity.User;
import com.elsonreis.tesk_manager.exception.ForbiddenException;
import com.elsonreis.tesk_manager.exception.NotFoundException;
import com.elsonreis.tesk_manager.mapper.CommentMapper;
import com.elsonreis.tesk_manager.repository.CommentRepository;
import com.elsonreis.tesk_manager.repository.TaskRepository;
import com.elsonreis.tesk_manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    final CommentRepository commentRepository;
    final TaskRepository taskRepository;
    final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public CommentResponse createComment(CommentRequest commentRequest, String email) {

        Task task = taskRepository.findById(commentRequest.getTaskId())
                .orElseThrow(() -> new NotFoundException("Tarefa não encontrado com id: " + commentRequest.getTaskId()));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com email: " + email));

        Comment comment = CommentMapper.toEntity(commentRequest);
        comment.setTask(task);
        comment.setUser(user);

        return CommentMapper.toDto(commentRepository.save(comment));
    }



    public List<CommentResponse> findAll() {
        return commentRepository.findAll()
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
        if (!taskRepository.existsById(taskId)) {
            throw new RuntimeException("Tarefa não encontrado com id: " + taskId);
        }

        return commentRepository.findByTaskId(taskId)
                .stream()
                .map(CommentMapper::toDto)
                .toList();
    }

    public CommentResponse updateComment(Long id, CommentRequest commentRequest, String email) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado com id: " + id));

        if (!comment.getUser().getEmail().equals(email)) {
            throw new ForbiddenException("Usuário não autorizado para atualizar este comentário");
        }

        Task task = taskRepository.findById(commentRequest.getTaskId())
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrado com id: " + commentRequest.getTaskId()));

        comment.setMessage(commentRequest.getMessage());
        comment.setTask(task);

        Comment updatedComment = commentRepository.save(comment);

        return CommentMapper.toDto(updatedComment);
    }

     public void deleteComment(Long id, String email) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado com id: " + id));

        if (!comment.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Usuário não autorizado para deletar este comentário");
        }

        commentRepository.delete(comment);
    }
}

