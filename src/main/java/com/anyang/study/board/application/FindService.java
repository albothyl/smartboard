package com.anyang.study.board.application;

import com.anyang.study.board.domain.Board;

import java.util.List;

public interface FindService {
    List<Board> getBoardAll(String sort, String searchType, String searchKeyword);

    Board getBoard(Long id);

}

