package com.anyang.study.board.domain.repository;

import com.anyang.study.board.domain.Board;
import com.anyang.study.configuration.domain.ConfigurationApplicationContextInitializer;
import com.anyang.study.configuration.domain.DomainContextConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigurationApplicationContextInitializer.class, classes = { DomainContextConfig.class })
public class BoardRepositoryTest {
    @Autowired
    BoardRepository boardRepository;

    @Test
    public void insert() {
        Board board = new Board();
        board.setTitle("insert test");
        board.setContent("content");
        board.setWriter("dhshin");
        boardRepository.save(board);
        assertEquals(1, boardRepository.count());

    }

    @Test
    public void delete() {
        Board board = new Board();
        board.setTitle("delete test");
        board.setContent("content");
        board.setWriter("dhshin");
        boardRepository.save(board);
        assertEquals(1, boardRepository.count());
        boardRepository.delete(board);
        assertEquals(0, boardRepository.count());
    }
}