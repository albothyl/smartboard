package com.anyang.study.board.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByOrderByIdDesc();

    List<Board> findAllByOrderByIdAsc();

    List<Board> findByTitleLikeOrderByIdDesc(String searchKeyword);

    List<Board> findByContentLikeOrderByIdDesc(String searchKeyword);

    List<Board> findByWriterLikeOrderByIdDesc(String searchKeyword);

    List<Board> findByTitleLikeOrderByIdAsc(String searchKeyword);

    List<Board> findByContentLikeOrderByIdAsc(String searchKeyword);

    List<Board> findByWriterLikeOrderByIdAsc(String searchKeyword);
}
