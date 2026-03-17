package com.elsonreis.tesk_manager.dto.request;

import lombok.Data;

@Data
public class CommentRequest {

    private String message;
    private Long taskId;
}
