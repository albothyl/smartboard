package com.anyang.study.board.application.modifier;

import com.anyang.study.board.application.finder.BoardFinder;
import com.anyang.study.board.domain.Board;
import com.anyang.study.board.domain.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SmartBoardModifier implements BoardModifier {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private BoardFinder boardFinder;

	@Override
	public Board insertBoard(Board board) {
		return boardRepository.save(board);
	}

	@Override
	public Board modify(Board source) {
		Board board = boardFinder.getBoard(source.getId());
		board.modify(source.getTitle(), source.getContent());

		return boardRepository.save(board);
	}

	@Override
	public void deleteBoard(Long id) {
		boardRepository.deleteById(id);
	}
}
