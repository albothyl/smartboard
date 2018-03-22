package com.anyang.study.board.interfaces.controller;

import com.anyang.study.board.interfaces.dto.BoardDto;
import com.anyang.study.configuration.domain.ConfigurationApplicationContextInitializer;
import com.anyang.study.configuration.domain.DomainContextConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(initializers = ConfigurationApplicationContextInitializer.class, classes = {DomainContextConfig.class})
public class BoardControllerTest {

    @Autowired
    private BoardController boardController;
    private BoardDto boardDto;

    @Before
    public void initBoardDto() {
        boardDto = BoardDto.builder()
                .title("제목")
                .content("내용")
                .writer("작성자").build();
    }

    @Test
    public void create() {
        ModelAndView mav = boardController.create();
        assertThat(mav.getViewName(), is("boardUpdate"));
    }

    @Test
    public void modify() {
        ModelAndView mav = boardController.modify()
    }
}
