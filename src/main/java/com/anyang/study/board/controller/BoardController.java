package com.anyang.study.board.controller;

import com.anyang.study.board.domain.Board;
import com.anyang.study.board.service.BoardServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    private BoardServiceImpl boardService;

    @RequestMapping("/board")
    public ModelAndView hello() {
        log.info("come in initial controller");

        ModelAndView mav = new ModelAndView("index");
        mav.addObject("Title", "Hello111");
        mav.addObject("Content", "Hello every one222");

        return mav;
    }

    @RequestMapping("/board/delete")
    public ModelAndView delete() {
        log.info("board delete");
        Board board = new Board();
        Long id;
        id = 12L;

        board.setId(id);
        boardService.deleteBoard(board);
        ModelAndView mav = new ModelAndView("board");
        mav.addObject("Title", "보드게시판");
        mav.addObject("Content", "게시판 삭제되었습니다.");

        return mav;
    }

}
