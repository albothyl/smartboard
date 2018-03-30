package com.anyang.study.board.interfaces.controller;

import com.anyang.study.board.domain.Board;
import com.anyang.study.board.interfaces.dto.BoardDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DtoManager {
    private DtoManager() {
    }

    private static DtoManager instance;

    public static DtoManager getInstance() {
        if(instance == null) {
            instance = new DtoManager();
        }
        return instance;
    }


    List<BoardDto> getBoardDtos(List<Board> gotBoardList) {
        List<BoardDto> gotBoardDtoList = new ArrayList<BoardDto>();

        for (Board board : gotBoardList) {
            BoardDto gotBoardDto = BoardDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .writer(board.getWriter())
                    .modifiedAt(board.getModifiedAt())
                    .createdAt(board.getCreatedAt())
                    .createdAt2(LocalDate.from(board.getCreatedAt()))
                    .build();
            gotBoardDtoList.add(gotBoardDto);
        }
        return gotBoardDtoList;
    }

    BoardDto getBoardDto(Board gotBoard) {
        return BoardDto.builder()
                .id(gotBoard.getId())
                .title(gotBoard.getTitle())
                .content(gotBoard.getContent())
                .writer(gotBoard.getWriter())
                .modifiedAt(gotBoard.getModifiedAt())
                .createdAt(gotBoard.getCreatedAt()).build();
    }
}