package com.anyang.study.board.domain.repository;

import com.anyang.study.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
