package com.anyang.study.board.interfaces.controller;

import com.anyang.study.board.application.BoardService;
import com.anyang.study.board.domain.Board;
import com.anyang.study.board.interfaces.dto.BoardDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @RequestMapping("/board")
    public ModelAndView hello() {
        log.info("come in initial controller");

        ModelAndView mav = new ModelAndView("index");
        mav.addObject("Title", "Hello111");
        mav.addObject("Content", "Hello every one222");

        return mav;
    }

    //새글작성
    @RequestMapping(value = "/board/boardUpdate")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("boardUpdate");

        return mav;
    }

    //이전글 수정
    @RequestMapping(value = "/board/boardUpdate/{id}")
    public ModelAndView modify(@PathVariable(value = "id") long bid) {
        ModelAndView mav = new ModelAndView("boardUpdate");

        Board gotBoard = boardService.getBoard(bid);

        BoardDto gotBoardDto = new BoardDto();
        gotBoardDto.setId(gotBoard.getId());
        gotBoardDto.setTitle(gotBoard.getTitle());
        gotBoardDto.setContent(gotBoard.getContent());
        gotBoardDto.setWriter(gotBoard.getWriter());
        gotBoardDto.setModifiedAt(gotBoard.getModifiedAt());
        gotBoardDto.setCreatedAt(gotBoard.getCreatedAt());

        mav.addObject("board", gotBoardDto);

        return mav;
    }

    //게시글등록 후 상세페이지로
    @RequestMapping(value = "/board/boardUpdate", method = POST)
    public String update(BoardDto board) {

        Board requestedBoard = new Board();
        requestedBoard.setId(board.getId());
        requestedBoard.setTitle(board.getTitle());
        requestedBoard.setWriter(board.getWriter());
        requestedBoard.setContent(board.getContent());

        Board createdBoard = boardService.insertBoard(requestedBoard);
        long createdId = createdBoard.getId();
        return "redirect:/board/boardDetail/" + createdId;

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
