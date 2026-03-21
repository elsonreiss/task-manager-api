package com.elsonreis.tesk_manager.repository;

import com.elsonreis.tesk_manager.entity.Board;
import com.elsonreis.tesk_manager.entity.Comment;
import com.elsonreis.tesk_manager.entity.Task;
import com.elsonreis.tesk_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByUser(User user);

    List<Board> findByUserId(Long userId);
}
