package com.anyang.study.board.application;

import com.anyang.study.board.domain.Board;
import com.anyang.study.board.domain.BoardRepository;
import com.anyang.study.board.interfaces.exception.NullBoardException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service("BoardService")
public class FindServiceImpl implements FindService {
    @Autowired
    private BoardRepository boardRepository;

    @Override
    public List<Board> getBoardAll(Sort sort, String searchType, String searchKeyword) {
        List<Board> boards;

        if (sort == null) {
            if (searchKeyword.isEmpty()) boards = boardRepository.findAll();
            else boards = boardRepository.findAllByTitle(searchKeyword);
        } else {
            if (searchKeyword.isEmpty()) boards = boardRepository.findAll(sort);
            else boards = boardRepository.findAllByTitleSort(searchKeyword, sort.toString());
        }
        return boards;
    }

    @Override
    public Board getBoard(Long boardId) {
        try {
            Optional<Board> board = boardRepository.findById(boardId);

            Board foundBoard = board.get();
            return foundBoard;
        } catch (NoSuchElementException e) {
            throw new NullBoardException();
        }
    }
}
