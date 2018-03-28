package com.anyang.study.board.application;

import com.anyang.study.board.domain.Board;
import com.anyang.study.board.interfaces.dto.BoardDto;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommonUtil {

    public ArrayList<BoardDto> voToDto(List<Board> boards) {
        ArrayList<BoardDto> gotBoardDtoList = new ArrayList<>();
        for (Board board : boards) {
            BoardDto dto = voToDto(board);
            gotBoardDtoList.add(dto);
        }
        return gotBoardDtoList;
    }

    public BoardDto voToDto(Board board) {
        BoardDto gotBoardDto = BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .modifiedAt(board.getModifiedAt())
                .createdAt(board.getCreatedAt()).build();
        return gotBoardDto;
    }
}
