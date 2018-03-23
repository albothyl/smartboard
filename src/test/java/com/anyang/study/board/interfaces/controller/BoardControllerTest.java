package com.anyang.study.board.interfaces.controller;

import com.anyang.study.board.application.BoardService;
import com.anyang.study.board.domain.Board;
import com.anyang.study.board.interfaces.dto.BoardDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class BoardControllerTest {

    @Mock
    private BoardService boardService;

    @InjectMocks
    private BoardController boardController;

    private MockMvc mockMvc;

    private BoardDto boardDto;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
    }

    @Before
    public void initBoardDto() {
        long id = 1;
        boardDto = BoardDto.builder()
                .id(id)
                .title("제목")
                .content("내용")
                .writer("작성자").build();
    }

    @Test
    public void createTest() throws Exception {
        mockMvc.perform(get("/board/boardUpdate")).andExpect(status().isOk());
    }

    @Test
    public void modifyTest() throws Exception {
        mockMvc.perform(get("/board/boardUpdate/1")).andExpect(status().isOk());
        verify(boardService).getBoard(1);
        verifyNoMoreInteractions(boardService);
    }

    @Test
    public void updateTest() throws Exception {
        Board willUpdateBoard = new Board();
        mockMvc.perform(post("/board/boardUpdate").requestAttr("boardDto", boardDto)).andExpect(status().isOk());
        verify(boardService).getBoard(-1);
        willUpdateBoard.setTitle(boardDto.getTitle());
        willUpdateBoard.setContent(boardDto.getContent());
        willUpdateBoard.setWriter(boardDto.getWriter());
        verify(boardService).insertBoard(willUpdateBoard);
        verifyNoMoreInteractions(boardService);

    }
}
