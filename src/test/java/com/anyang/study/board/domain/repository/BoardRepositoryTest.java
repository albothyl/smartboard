package com.anyang.study.board.domain.repository;

import com.anyang.study.board.domain.Board;
import com.anyang.study.board.domain.BoardRepository;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigurationApplicationContextInitializer.class, classes = {DomainContextConfig.class})
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    private static final int TOTAL_COUNT = 10;

    @Before
    public void setup() {
        boardRepository.deleteAll();
        boardRepository.flush();

        for (int i = 0; i < TOTAL_COUNT; i++) {
            Board board = Board.builder()
                    .title("insert test " + i)
                    .content("insert content " + i)
                    .writer("insert writer " + i)
                    .build();

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
        Long id = 0L;
        for (Board board : list) {
            id = board.getId();
        }
        assertNotNull(id);
    }
    
    @Test
    public void delete() {
        List<Board> list = boardRepository.findAll();
        Long beforeId = 0L;
        for (Board board : list) {
            beforeId = board.getId();
        }
        boardRepository.deleteById(beforeId);

        list = boardRepository.findAll();
        Long afterId = 0L;
        for (Board board : list) {
            afterId = board.getId();
        }

        assertThat(afterId+1L, is(beforeId));
    }
}