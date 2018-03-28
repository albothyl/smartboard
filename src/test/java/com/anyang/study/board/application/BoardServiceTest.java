package com.anyang.study.board.application;

import com.anyang.study.board.domain.Board;
import com.anyang.study.board.interfaces.exception.NullBoardException;
import com.anyang.study.configuration.domain.ConfigurationApplicationContextInitializer;
import com.anyang.study.configuration.domain.DomainContextConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(initializers = ConfigurationApplicationContextInitializer.class, classes = {DomainContextConfig.class})
public class BoardServiceTest {
    @Autowired
    private BoardService boardService;

    @Test
    @Ignore
    public void insert() {
        Board board = Board.builder()
                .title("제목")
                .writer("작성자")
                .content("내용")
                .build();

        Board insertedBoard = boardService.insertBoard(board);

        assertThat(insertedBoard.getId(), is(board.getId()));
    }

    @Test
    public void get() {
        Board board = Board.builder()
                .title("제목")
                .writer("작성자")
                .content("내용")
                .build();

        Board insertedBoard = boardService.insertBoard(board);

        Board gotBoard = boardService.getBoard(insertedBoard.getId());
        assertThat(gotBoard.getId(), is(insertedBoard.getId()));
    }

    @Test(expected = NullBoardException.class)
    public void nullBoardEx() {
        long bid = -100;
        Board gotBoard = boardService.getBoard(bid);
    }
}
