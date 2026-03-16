package com.elsonreis.tesk_manager.dto.request;

import lombok.Data;

@Data
public class TaskRequest {

    private String title;
    private String description;
    private String status;
    private Long boardId;
}
