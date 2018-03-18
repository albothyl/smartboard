package com.anyang.study.board.application;

import com.anyang.study.board.domain.Board;
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

//요거 단위테스트 안됩니다...
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(initializers = ConfigurationApplicationContextInitializer.class, classes = {DomainContextConfig.class})
public class BoardServiceTest {
    @Autowired
    private BoardService boardService;

    @Test
    @Ignore
    public void insert() {
        Board board = new Board();
        board.setTitle("제목");
        board.setWriter("작성자");
        board.setContent("내용");

        Board insertedBoard = boardService.insertBoard(board);

        assertThat(insertedBoard.getId(), is(board.getId()));
    }




}
