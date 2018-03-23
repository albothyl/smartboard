package com.anyang.study.board.application;

import com.anyang.study.board.domain.Board;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BoardService {
    //게시판 목록을 가져온다.
    List<Board> getBoardAll(Sort sort, String strSearchtype, String strSearchkeyword);

    //게시판 정보를 데이터베이스에서 읽어와 화면에 출력한다.
    Board getBoard(long id);

    //게시판 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장한다.
    Board insertBoard(Board board);

    //선택한 게시판 정보를 삭제한다.
    void deleteBoard(Board board);

}
