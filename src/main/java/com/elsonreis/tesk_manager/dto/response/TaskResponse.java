package com.elsonreis.tesk_manager.dto.response;

import lombok.Data;

@Data
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private String status;
    private Long boardId;
}
