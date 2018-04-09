package com.anyang.study.board.interfaces.controller.board;

import com.anyang.study.board.domain.Board;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
class BoardConverter {

	private static final String DATE_TIME_PATTERN = "yyyy-MM-dd hh:mm:ss";
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

	static Function<Board, BoardDto> toDto = board -> BoardDto.builder()
		.id(board.getId())
		.title(board.getTitle())
		.content(board.getContent())
		.writer(board.getWriter())
		.modifiedAt(board.getModifiedAt().format(DATE_TIME_FORMATTER))
		.createdAt(board.getCreatedAt().format(DATE_TIME_FORMATTER))
		.build();

	static Function<BoardDto, Board> toEntity = boardDto -> Board.builder()
		.id(boardDto.getId())
		.title(boardDto.getTitle())
		.content(boardDto.getContent())
		.writer(boardDto.getWriter())
		.build();
}
