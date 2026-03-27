package com.elsonreis.tesk_manager.service;

import com.elsonreis.tesk_manager.dto.request.TaskRequest;
import com.elsonreis.tesk_manager.dto.response.TaskResponse;
import com.elsonreis.tesk_manager.entity.Board;
import com.elsonreis.tesk_manager.entity.Task;
import com.elsonreis.tesk_manager.enums.TaskStatus;
import com.elsonreis.tesk_manager.mapper.TaskMapper;
import com.elsonreis.tesk_manager.repository.BoardRepository;
import com.elsonreis.tesk_manager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    final TaskRepository taskrepository;
    final BoardRepository boardrepository;

    public TaskService(TaskRepository taskrepository, BoardRepository boardrepository) {
        this.taskrepository = taskrepository;
        this.boardrepository = boardrepository;
    }

    public TaskResponse createTask(TaskRequest taskRequest, String email) {

        Board board = boardrepository.findById(taskRequest.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board não encontrado com id: " + taskRequest.getBoardId()));

        if (!board.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Usuário não tem permissão para criar tarefa neste board!");
        }

        Task task = TaskMapper.toEntity(taskRequest);
        task.setBoard(board);

        Task savedTask = taskrepository.save(task);

        return TaskMapper.toDto(savedTask);
    }

    public List<TaskResponse> findAll() {

        return taskrepository.findAll()
                .stream()
                .map(TaskMapper::toDto)
                .toList();
    }

    public TaskResponse findById(Long id) {

        Task task = taskrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrado com id: " + id));

        return TaskMapper.toDto(task);
    }

    public List<TaskResponse> findByBoardId(Long boardId) {

        Board board = boardrepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board não encontrado com id: " + boardId));

        return taskrepository.findByBoardId(boardId)
                .stream()
                .map(TaskMapper::toDto)
                .toList();
    }

     public List<TaskResponse> findByStatus(String status) {

         TaskStatus taskStatus = TaskStatus.fromValue(status);

        return taskrepository.findByStatus(status)
                .stream()
                .map(TaskMapper::toDto)
                .toList();
    }

    public TaskResponse updateTask(Long id, TaskRequest taskRequest, String email) {

        Task task = taskrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrado com id: " + id));

        if (!task.getBoard().getUser().getEmail().equals(email)) {
            throw new RuntimeException("Usuário não tem permissão para eidtar essa esta tesk!");
        }

        Board board = boardrepository.findById(taskRequest.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board não encontrado com id: " + taskRequest.getBoardId()));

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(TaskStatus.valueOf(taskRequest.getStatus()));
        task.setBoard(board);

        Task updatedTask = taskrepository.save(task);

        return TaskMapper.toDto(updatedTask);
    }

    public void deleteById(Long id, String email) {

        Task task = taskrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrado com id: " + id));

        if (!task.getBoard().getUser().getEmail().equals(email)) {
            throw new RuntimeException("Usuário não tem permissão para deletar essa tesk!");
        }

        taskrepository.deleteById(id);
    }
}
