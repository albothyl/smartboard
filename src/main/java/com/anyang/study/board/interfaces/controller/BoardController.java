package com.anyang.study.board.interfaces.controller;

import com.anyang.study.board.application.BoardService;
import com.anyang.study.board.application.CommonUtil;
import com.anyang.study.board.domain.Board;
import com.anyang.study.board.interfaces.dto.BoardDto;
import com.anyang.study.board.interfaces.exception.NullBoardException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.ArrayList;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @RequestMapping(value = "/board/boardList", method = GET)
    public ModelAndView boardList() {
        ModelAndView mav = new ModelAndView("boardList");
        ArrayList<BoardDto> boardDtoList = boardService.getBoardList();

        mav.addObject("boardList", boardDtoList);

        return mav;
    }

    @RequestMapping(value = "/board/boardUpdate", method = GET)
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("boardUpdate");
        return mav;
    }

    @RequestMapping(value = "/board/boardUpdate/{id}")
    public ModelAndView modify(@PathVariable(value = "id") long id) {
        ModelAndView mav = null;
        try {
            Board gotBoard = boardService.getBoard(id);
            CommonUtil commonUtil = new CommonUtil();
            BoardDto boardDto = commonUtil.voToDto(gotBoard);
            mav = new ModelAndView("boardUpdate");
            mav.addObject("board", boardDto);
        } catch (NullBoardException e) {
            mav = new ModelAndView("nullBoard");
        } finally {
            return mav;
        }
    }

    //게시글등록 후 상세페이지로
    @RequestMapping(value = "/board/boardUpdate", method = POST)
    public String update(BoardDto board) {
        Board boardVo = new Board();
        if (board.getId() != null) {
            boardVo = boardService.getBoard(board.getId());
        }
        boardVo.setTitle(board.getTitle());
        boardVo.setContent(board.getContent());
        boardVo.setWriter(board.getWriter());

        Board updatedBoard = boardService.insertBoard(boardVo);
        long updatedId = updatedBoard.getId();
        return "redirect:/board/boardDetail/" + updatedId;
    }

    @RequestMapping(value = "/board/boardDelete/{id}", method = POST)
    public String delete(@PathVariable(value = "id") long bid, RedirectAttributes rttr) {
        boardService.deleteBoard(boardService.getBoard(bid));

        rttr.addFlashAttribute("msg", "success");
        return "redirect:/board/boardList";
    }


    @RequestMapping(value = "/board/boardDetail/{id}", method = GET)
    public ModelAndView boardDetail(@PathVariable(value = "id") long id) {
        ModelAndView mav = new ModelAndView("boardDetail");
        Board board = boardService.getBoard(id);

        CommonUtil commonUtil = new CommonUtil();
        BoardDto boardDetail = commonUtil.voToDto(board);
        mav.addObject("board", boardDetail);

        return mav;
    }
}
