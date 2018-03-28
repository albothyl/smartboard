package com.anyang.study.board.application;

import com.anyang.study.board.domain.Board;
import com.anyang.study.board.domain.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModifyServiceImpl implements ModifyService {
    @Autowired
    BoardRepository boardRepository;

    @Override
    public Board insertBoard(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public void deleteBoard(Board board) {
        boardRepository.delete(board);
    }

}
