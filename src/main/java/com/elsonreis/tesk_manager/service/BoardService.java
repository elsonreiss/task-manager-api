package com.elsonreis.tesk_manager.service;

import com.elsonreis.tesk_manager.dto.request.BoardRequest;
import com.elsonreis.tesk_manager.dto.response.BoardResponse;
import com.elsonreis.tesk_manager.entity.Board;
import com.elsonreis.tesk_manager.entity.User;
import com.elsonreis.tesk_manager.mapper.BoardMapper;
import com.elsonreis.tesk_manager.repository.BoardRepository;
import com.elsonreis.tesk_manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    final BoardRepository boardRepository;
    final UserRepository userRepository;

    public BoardService(BoardRepository repository, UserRepository userRepository) {
        this.boardRepository = repository;
        this.userRepository = userRepository;
    }

    public BoardResponse createBoard(BoardRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        Board board = new Board();
        board.setName(request.getName());
        board.setUser(user);

        Board savedBoard = boardRepository.save(board);

        return BoardMapper.toDto(savedBoard);
    }

    public List<BoardResponse> findAll() {

        List<Board> boards = boardRepository.findAll();

        return boards.stream()
                .map(BoardMapper::toDto)
                .toList();
    }

    public BoardResponse findById(Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quadro não encontrado!"));

        return BoardMapper.toDto(board);
    }

    public List<BoardResponse> findByUserId(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Usuário não encontrado!");
        }

        return boardRepository.findByUserId(userId)
                .stream()
                .map(BoardMapper::toDto)
                .toList();
    }

    public BoardResponse updateBoard(Long id, BoardRequest request) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quadro não encontrado!"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        board.setName(request.getName());
        board.setUser(user);

        Board updatedBoard = boardRepository.save(board);

        return BoardMapper.toDto(updatedBoard);
    }

    public void deleteBoard(Long id) {

        if (boardRepository.existsById(id)) {
            boardRepository.deleteById(id);
        } else {
            throw new RuntimeException("Quadro não encontrado!");
        }
    }
}
