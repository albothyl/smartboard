package com.anyang.study.board.interfaces.controller;

import com.anyang.study.board.application.BoardService;
import com.anyang.study.board.domain.Board;
import com.anyang.study.board.interfaces.dto.BoardDto;
import com.anyang.study.board.interfaces.exception.NullBoardException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    //목록 가져오기
    @RequestMapping(value = "/board/boardList")
    public ModelAndView boardList() {
        ModelAndView mav = new ModelAndView("boardList");
        List<Board> gotBoardList = boardService.getBoardAll();

        ArrayList<BoardDto> gotBoardDtoList = new ArrayList<>();
        for (int i = 0; i < gotBoardList.size(); i++) {
            Board board = gotBoardList.get(i);
            BoardDto dto = new BoardDto();
            dto.setId(board.getId());
            dto.setTitle(board.getTitle());
            dto.setContent(board.getContent());
            dto.setWriter(board.getWriter());
            dto.setCreatedAt(board.getCreatedAt());
            dto.setCreatedAt2(LocalDate.from(board.getCreatedAt()));

            dto.setModifiedAt(board.getModifiedAt());
            gotBoardDtoList.add(dto);
        }
        mav.addObject("list", gotBoardDtoList);

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
        try {
            Board gotBoard = boardService.getBoard(bid);

            BoardDto gotBoardDto = BoardDto.builder()
                    .id(gotBoard.getId())
                    .title(gotBoard.getTitle())
                    .content(gotBoard.getContent())
                    .writer(gotBoard.getWriter())
                    .modifiedAt(gotBoard.getModifiedAt())
                    .createdAt(gotBoard.getCreatedAt()).build();

            mav.addObject("board", gotBoardDto);

            return mav;
        } catch (NullBoardException e) {
            ModelAndView nullBoard = new ModelAndView("nullBoard");
            return nullBoard;
        }
    }

    //게시글등록 후 상세페이지로
    @RequestMapping(value = "/board/boardUpdate", method = POST)
    public String update(BoardDto board) {

        Board willUpdateBoard = boardService.getBoard(board.getId());

        willUpdateBoard.setTitle(board.getTitle());
        willUpdateBoard.setContent(board.getContent());
        willUpdateBoard.setWriter(board.getWriter());

        Board updatedBoard = boardService.insertBoard(willUpdateBoard);
        long updatedId = updatedBoard.getId();
        return "redirect:/board/boardDetail/" + updatedId;

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


    @RequestMapping(value = "/board/boardDetail/{id}")
    public ModelAndView boardDetail(@PathVariable(value = "id") long bid) {
        ModelAndView mav = new ModelAndView("boardDetail");

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
}
