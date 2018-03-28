package com.anyang.study.board.interfaces.util;

import com.anyang.study.board.domain.Board;
import com.anyang.study.board.interfaces.dto.BoardDto;

import java.time.format.DateTimeFormatter;

public class DomainManager {

    public static BoardDto domainToDto(Board board, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        BoardDto boardDto = BoardDto.builder()
                .modifiedAt(board.getModifiedAt().format(dateTimeFormatter))
                .createdAt(board.getCreatedAt().format(dateTimeFormatter))
                .writer(board.getWriter())
                .content(board.getContent())
                .title(board.getTitle())
                .id(board.getId())
                .build();
        return boardDto;
    }

    public static Board dtoToDomain(BoardDto boardDto) {
        Board board = Board.builder()
                .writer(boardDto.getWriter())
                .content(boardDto.getContent())
                .title(boardDto.getTitle())
                .id(boardDto.getId())
                .build();
        return board;
    }
}
