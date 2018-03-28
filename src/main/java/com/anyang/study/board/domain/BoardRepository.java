package com.anyang.study.board.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select p from Board p where p.title = ?1 ")
    List<Board> findAllByTitle(String searchkeyword);

    @Query("select p from Board p where p.title = ?1 order by ?2 desc")
    List<Board> findAllByTitleSort(String searchkeyword, String sort);


}
