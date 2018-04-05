package com.anyang.study.board.application;

import com.anyang.study.board.domain.Board;
import com.anyang.study.board.domain.BoardRepository;
import com.anyang.study.board.interfaces.exception.NullBoardException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FindServiceImpl implements FindService {

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public List<Board> getPageBoard(String searchType, String searchKewyord, Pageable pageable) {
        return getBoardsBySearchOption(searchType, searchKewyord, pageable);
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

    private List<Board> getBoardsBySearchOption(String searchType, String searchKeyword, Pageable pageable) {
        List<Board> arrayList;

        if (Objects.equals(searchType, "title")) {
            arrayList = boardRepository.findByTitleLike("%" + searchKeyword + "%", pageable);
        } else if (Objects.equals(searchType, "content")) {
            arrayList = boardRepository.findByContentLike("%" + searchKeyword + "%", pageable);
        } else if (Objects.equals(searchType, "writer")) {
            arrayList = boardRepository.findByWriterLike("%" + searchKeyword + "%", pageable);
        } else {
            arrayList = boardRepository.findAllBy(pageable);
        }

        return arrayList;
    }
}
