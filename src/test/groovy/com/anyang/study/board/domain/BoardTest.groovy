package com.anyang.study.board.domain

import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

class BoardTest extends Specification {

	def "BoardEntity가 입력받은 title, content로 변경된다."() {
		given:
		def board = EnhancedRandom.random(Board.class)
		def newTitle = "newTitle"
		def newContent = "newContent"

		when:
		board.modify(newTitle, newContent)

		then:
		board.title == newTitle
		board.content == newContent
	}
}
