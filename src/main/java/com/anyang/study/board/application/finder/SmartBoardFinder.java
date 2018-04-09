package com.anyang.study.board.application.finder;

import com.anyang.study.board.domain.Board;
import com.anyang.study.board.domain.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SmartBoardFinder implements BoardFinder {

	@Autowired
	private BoardRepository boardRepository;

	@Override
	public Page<Board> getBoardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Override
	public Board getBoard(Long id) {
		return boardRepository.findById(id)
			.orElseGet(() -> Board.builder().id(id).build());
	}
}
