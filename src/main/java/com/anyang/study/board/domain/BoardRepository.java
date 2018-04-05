package com.anyang.study.board.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllBy(Pageable pageable);

    List<Board> findByTitleLike(String searchKeyword, Pageable pageable);

    List<Board> findByContentLike(String searchKeyword, Pageable pageable);

    List<Board> findByWriterLike(String searchKeyword, Pageable pageable);

}
