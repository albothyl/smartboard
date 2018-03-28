package com.anyang.study.board.application;

import com.anyang.study.board.domain.Board;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BoardService {
    List<Board> getBoardAll(Sort sort, String searchType, String searchKeyword);

    Board getBoard(Long id);

    Board insertBoard(Board board);

    void deleteBoard(Board board);

}
