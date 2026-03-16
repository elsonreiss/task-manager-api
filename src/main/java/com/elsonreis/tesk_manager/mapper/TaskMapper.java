package com.elsonreis.tesk_manager.mapper;

import com.elsonreis.tesk_manager.dto.response.TaskResponse;
import com.elsonreis.tesk_manager.entity.Task;

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
}
