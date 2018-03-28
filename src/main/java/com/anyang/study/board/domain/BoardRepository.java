package com.anyang.study.board.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByTitle(String searchKeyword);

    List<Board> findAllByTitleSort(String searchKeyword, String sort);

}
