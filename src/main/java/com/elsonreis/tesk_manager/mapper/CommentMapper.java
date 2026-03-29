package com.elsonreis.tesk_manager.mapper;

import com.elsonreis.tesk_manager.dto.request.CommentRequest;
import com.elsonreis.tesk_manager.dto.response.CommentResponse;
import com.elsonreis.tesk_manager.entity.Comment;

public class CommentMapper {

    public static Comment toEntity(CommentRequest dto) {

        Comment comment = new Comment();

        comment.setMessage(dto.getMessage());

        return comment;
    }

    public static CommentResponse toDto(Comment comment) {

        CommentResponse dto = new CommentResponse();

        dto.setId(comment.getId());
        dto.setMessage(comment.getMessage());
        dto.setTaskId(comment.getTask().getId());
        dto.setUserId(comment.getUser().getId());
        dto.setCreatedAt(comment.getCreatedAt().toString());

        return dto;
    }
}
