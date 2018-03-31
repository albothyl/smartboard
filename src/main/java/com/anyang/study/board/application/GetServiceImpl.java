package com.anyang.study.board.application;

import com.anyang.study.board.domain.Board;
import com.anyang.study.board.domain.BoardRepository;
import com.anyang.study.board.interfaces.exception.NullBoardException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service("GetService")
public class GetServiceImpl implements GetService {
    @Autowired
    private BoardRepository boardRepository;

    @Override
    public List<Board> getBoardAll(String sort, String searchType, String searchKeyword) {
        return getBoardsBySearchOption(sort, searchType, searchKeyword);
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

    private List<Board> getBoardsBySearchOption(String sort, String searchType, String searchKeyword) {
        List<Board> arrayList;

        /* 1뎁스 : 정렬타입
         * 2뎁스 : 서치타입(타이틀, 저자 등)
         * 3뎁스 : 키워드
         */
        if (Objects.equals(sort, "desc")) {
            if (Objects.equals(searchType, "title")) {
                arrayList = boardRepository.findByTitleLikeOrderByIdDesc("%" + searchKeyword + "%");
            } else if (Objects.equals(searchType, "content")) {
                arrayList = boardRepository.findByContentLikeOrderByIdDesc("%" + searchKeyword + "%");
            } else if (Objects.equals(searchType, "writer")) {
                arrayList = boardRepository.findByWriterLikeOrderByIdDesc("%" + searchKeyword + "%");
            } else {
                arrayList = boardRepository.findAllByOrderByIdDesc();
            }
        } else {
            if (Objects.equals(searchType, "title")) {
                arrayList = boardRepository.findByTitleLikeOrderByIdAsc("%" + searchKeyword + "%");
            } else if (Objects.equals(searchType, "content")) {
                arrayList = boardRepository.findByContentLikeOrderByIdAsc("%" + searchKeyword + "%");
            } else if (Objects.equals(searchType, "writer")) {
                arrayList = boardRepository.findByWriterLikeOrderByIdAsc("%" + searchKeyword + "%");
            } else {
                arrayList = boardRepository.findAllByOrderByIdAsc();
            }
        }

        return arrayList;
    }
}
