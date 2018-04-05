package com.anyang.study.board.application;

import com.anyang.study.board.domain.Board;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FindService {

    List<Board> getPageBoard(String searchType, String searchKewyord, Pageable pageable);

    Board getBoard(Long id);

}

