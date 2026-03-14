package com.elsonreis.tesk_manager.mapper;

import com.elsonreis.tesk_manager.dto.response.BoardResponse;
import com.elsonreis.tesk_manager.entity.Board;

public class BoardMapper {

    public static BoardResponse toDto(Board board) {

        BoardResponse dto = new BoardResponse();

        dto.setId(board.getId());
        dto.setName(board.getName());
        dto.setUserId(board.getUser().getId());

        return dto;
    }
}
