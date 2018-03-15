package com.anyang.study.board.service;

import com.anyang.study.board.domain.Board;

public interface BoardService {
    //게시판 정보를 데이터베이스에서 읽어와 화면에 출력한다.
    Board getBoard(Board board);

    //게시판 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장한다.
    Board insertBoard(Board board);

    //화면에 조회된 게시 정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영한다.
    void updateBoard(Board board);

    //선택한 게시판 정보를 삭제한다.
    void deleteBoard(Board board);

}
