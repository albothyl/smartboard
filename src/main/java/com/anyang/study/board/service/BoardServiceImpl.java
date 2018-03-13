package com.anyang.study.board.service;

import com.anyang.study.board.domain.Board;
import com.anyang.study.board.domain.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("BoardService")
public class BoardServiceImpl implements BoardService{
    @Autowired
    BoardRepository boardRepository;
    @Override
    public Board getBoard(Board board) {
        return null;
    }

    @Override
    public void insertBoard(Board board) {

    }

    @Override
    public void updateBoard(Board board) {

    }

    @Override
    public void deleteBoard(Board board) {
        boardRepository.delete(board);
    }
}
