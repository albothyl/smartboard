package com.anyang.study.board.application.finder;

import com.anyang.study.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardFinder {
	Page<Board> getBoardList(Pageable pageable);
	Board getBoard(Long id);
}

