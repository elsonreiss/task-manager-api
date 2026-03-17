package com.elsonreis.tesk_manager.dto.response;

import lombok.Data;

@Data
public class CommentResponse {

    private Long id;
    private String message;
    private Long taskId;
}
