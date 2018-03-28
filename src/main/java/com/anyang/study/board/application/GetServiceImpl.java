package com.anyang.study.board.application;

import com.anyang.study.board.domain.Board;
import com.anyang.study.board.domain.BoardRepository;
import com.anyang.study.board.interfaces.exception.NullBoardException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class GetServiceImpl implements GetService {
    @Autowired
    BoardRepository boardRepository;

    @Override
    public List<Board> getBoardAll(Sort sort, String searchType, String searchKeyword) {
        List<Board> arrayList;

        if (sort == null) {
            if (searchKeyword.isEmpty()) {
                arrayList = boardRepository.findAll();
            } else {
                arrayList = boardRepository.findAllByTitle(searchKeyword);
            }
        } else {
            if (searchKeyword.isEmpty()) {
                arrayList = boardRepository.findAll(sort);
            } else {
                arrayList = boardRepository.findAllByTitleSort(searchKeyword, sort.toString());
            }
        }
        return arrayList;
    }

    @Override
    public Board getBoard(Long id) {
        try {
            Optional<Board> gotBoard = boardRepository.findById(id);
            Board foundBoard = gotBoard.get();
            return foundBoard;
        } catch (NoSuchElementException e) {
            throw new NullBoardException();
        }
    }
}
