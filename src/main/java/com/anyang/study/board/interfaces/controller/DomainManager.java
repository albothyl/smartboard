package com.anyang.study.board.interfaces.controller;

import com.anyang.study.board.application.FindService;
import com.anyang.study.board.domain.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public class DomainManager {
    @Autowired
    FindService findService;

    private static DomainManager instance;

    public static DomainManager getInstance() {
        if(instance == null) {
            instance = new DomainManager();
        }
        return instance;
    }

    Board getBoard(@RequestParam Map<String, String> param) {
        Optional<String> updateId = Optional.ofNullable(param.get("id"));
        Board board = null;
        if (updateId.isPresent()) {

            Board updateBoard = findService.getBoard(Long.parseLong(param.get("id")));
            updateBoard = updateBoard.builder()
                    .id(Long.parseLong(param.get("id")))
                    .title(param.get("title"))
                    .content(param.get("content"))
                    .writer(param.get("writer"))
                    .createdAt(updateBoard.getCreatedAt())
                    .modifiedAt(LocalDateTime.now())
                    .build();
            board = updateBoard;
        } else {
            Board insertBoard = new Board();
            insertBoard = insertBoard.builder()
                    .title(param.get("title"))
                    .content(param.get("content"))
                    .writer(param.get("writer"))
                    .build();

            board = insertBoard;
        }
        return board;
    }
}