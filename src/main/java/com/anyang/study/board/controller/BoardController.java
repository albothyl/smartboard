package com.anyang.study.board.controller;

import com.anyang.study.board.domain.Board;
import com.anyang.study.board.service.BoardServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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

    @RequestMapping(value = "/board/create", method = GET)
    public ModelAndView createForm(){
        log.info("board create");
        ModelAndView mav = new ModelAndView("createform");

        return mav;
    }
    @RequestMapping(value = "/board/create", method = POST)
    public ModelAndView create(
            @RequestParam("title") String title,
            @RequestParam("writer") String writer,
            @RequestParam("content") String content){

        Board board = new Board();
        board.setTitle(title);
        board.setWriter(writer);
        board.setContent(content);

        Board createdBoard = boardService.insertBoard(board);
        log.info("board created");
        log.info(title);

        ModelAndView mav = new ModelAndView("createcomplete");

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
