package com.anyang.study.board.application;

import com.anyang.study.board.domain.Board;

public interface ModifyService {
    Board insertBoard(Board board);

    void deleteBoard(Board board);

}
