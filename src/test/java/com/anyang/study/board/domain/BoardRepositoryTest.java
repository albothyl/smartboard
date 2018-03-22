package com.anyang.study.board.domain;

import com.anyang.study.configuration.domain.ConfigurationApplicationContextInitializer;
import com.anyang.study.configuration.domain.DomainContextConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigurationApplicationContextInitializer.class, classes = {DomainContextConfig.class})
public class BoardRepositoryTest {
    @Autowired
    BoardRepository boardRepository;

    public static final int TOTAL_COUNT = 10;

    @Before
    public void setup() {
        boardRepository.deleteAll();
        boardRepository.flush();

        for (int i = 0; i < TOTAL_COUNT; i++) {
            Board board = new Board();
            board.setTitle("insert test " + i);
            board.setContent("insert content " + i);
            board.setWriter("insert writer " + i);
            boardRepository.save(board);
        }
    }

    @Test
    public void totalBoardListCount() {
        List<Board> list = boardRepository.findAll();
        assertThat(list.size(), is(TOTAL_COUNT));
    }

    @Test
    public void modifyBoardId() {
        List<Board> list = boardRepository.findAll();
        Long id = null;
        for (Board board : list) {
            id = board.getId();
        }
        assertNotNull(id);
    }
    
    @Test
    public void delete() {
        List<Board> list = boardRepository.findAll();
        Long id = null;
        Long beforeId = null;
        for (Board board : list) {
            beforeId = board.getId();
        }
        boardRepository.deleteById(beforeId);

        list = boardRepository.findAll();
        Long afterId = null;
        for (Board board : list) {
            afterId = board.getId();
        }

        assertThat(afterId+1, is(beforeId));
    }
}