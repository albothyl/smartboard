package com.anyang.study.board.application;

import com.anyang.study.board.domain.Board;
import com.anyang.study.board.domain.BoardRepository;
import com.anyang.study.board.interfaces.exception.NullBoardException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


import java.util.Optional;

@Slf4j
@Service("BoardService")
public class BoardServiceImpl implements BoardService {
    @Autowired
    BoardRepository boardRepository;

    @Override
    public List<Board> getBoardAll() {
        List<Board> arrayList = new ArrayList<>();
        arrayList = boardRepository.findAll();
        return arrayList;
    }

    @Override
    public Board getBoard(long id) throws NoSuchElementException {
        try {
            Optional<Board> gotBoard = boardRepository.findById(id);
            Board foundBoard = gotBoard.get();
            return foundBoard;
        } catch (NoSuchElementException e) {
            throw new NullBoardException();
        }
    }

    @Override
    public Board insertBoard(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public void updateBoard(Board board) {

    }

    @Override
    public void deleteBoard(Board board) {
        boardRepository.delete(board);
    }
}
