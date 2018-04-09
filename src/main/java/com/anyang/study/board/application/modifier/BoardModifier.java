package com.anyang.study.board.application.modifier;

import com.anyang.study.board.domain.Board;

public interface BoardModifier {
	Board insertBoard(Board board);
	Board modify(Board board);
	void deleteBoard(Long id);
}
