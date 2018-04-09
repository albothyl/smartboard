package com.anyang.study.board.interfaces.controller.board

import com.anyang.study.board.domain.Board
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static com.anyang.study.board.interfaces.controller.board.BoardConverter.DATE_TIME_FORMATTER

class BoardConverterTest extends Specification {

	def "BoardEntity가 BoardDto로 변환되는지 확인한다."() {
		given:
		def board = EnhancedRandom.random(Board.class)

		when:
		def boardDto = BoardConverter.toDto.apply(board)

		then:
		boardDto.id == board.id
		boardDto.title == board.title
		boardDto.content == board.content
		boardDto.writer == board.writer
		boardDto.modifiedAt == board.modifiedAt.format(DATE_TIME_FORMATTER)
		boardDto.createdAt == board.createdAt.format(DATE_TIME_FORMATTER)
	}

	def "BoardDto가 BoardEntity로 변환되는지 확인한다."() {
		given:
		def boardDto = EnhancedRandom.random(BoardDto.class)

		when:
		def board = BoardConverter.toEntity.apply(boardDto)

		then:
		board.id == boardDto.id
		board.title == boardDto.title
		board.content == boardDto.content
		board.writer == boardDto.writer
	}
}
