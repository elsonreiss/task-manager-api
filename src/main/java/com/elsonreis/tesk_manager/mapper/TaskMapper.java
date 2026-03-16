package com.elsonreis.tesk_manager.mapper;

import com.elsonreis.tesk_manager.dto.request.TaskRequest;
import com.elsonreis.tesk_manager.dto.response.TaskResponse;
import com.elsonreis.tesk_manager.entity.Task;
import com.elsonreis.tesk_manager.enums.TaskStatus;

public class TaskMapper {

    public static TaskResponse toDto(Task task) {

        TaskResponse dto = new TaskResponse();

        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus().name());
        dto.setBoardId(task.getBoard().getId());

        return dto;
    }

    public static Task toEntity(TaskRequest dto) {

        Task task = new Task();

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(TaskStatus.valueOf(dto.getStatus()));

        return task;
    }
}
