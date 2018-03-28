package com.anyang.study.board.interfaces.controller;

import com.anyang.study.board.application.BoardService;
import com.anyang.study.board.domain.Board;
import com.anyang.study.board.interfaces.dto.BoardDto;
import com.anyang.study.board.interfaces.exception.NullBoardException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @RequestMapping(value = "/board/sortType={sortType}&searchType={searchType}&searchKeyword={searchKeyword}")
    public ModelAndView boardSortList(@PathVariable(value = "sortType") String sort,
                                      @PathVariable(value = "searchType") String searchType,
                                      @PathVariable(value = "searchKeyword") String searchKeyword) {
        ModelAndView mav = new ModelAndView("boardList");
        if (sort.isEmpty()) {
            sort = "id";
        }
        List<Board> gotBoardList = boardService.getBoardAll(new Sort(Sort.Direction.DESC, sort), searchType, searchKeyword);

        ArrayList<BoardDto> gotBoardDtoList = new ArrayList<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        for (int i = 0; i < gotBoardList.size(); i++) {
            Board board = gotBoardList.get(i);
            BoardDto boardDto = BoardDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .writer(board.getWriter())
                    .createdAt(board.getCreatedAt().format(dateTimeFormatter))
                    .modifiedAt(board.getModifiedAt().format(dateTimeFormatter))
                    .build();
            gotBoardDtoList.add(boardDto);
        }
        mav.addObject("list", gotBoardDtoList);

        mav.addObject("sorttype", sort);
        mav.addObject("strsearchtype", searchType);
        mav.addObject("searchkeyword", searchKeyword);

        return mav;
    }

    @RequestMapping(value = "/boards", method = GET)
    public ModelAndView boardList() {
        ModelAndView mav = new ModelAndView("boardList");
        List<Board> gotBoardList = boardService.getBoardAll(null, "", "");

        ArrayList<BoardDto> gotBoardDtoList = new ArrayList<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        for (int i = 0; i < gotBoardList.size(); i++) {
            Board board = gotBoardList.get(i);
            BoardDto boardDto = BoardDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .writer(board.getWriter())
                    .createdAt(board.getCreatedAt().format(dateTimeFormatter))
                    .modifiedAt(board.getModifiedAt().format(dateTimeFormatter))
                    .build();
            gotBoardDtoList.add(boardDto);
        }
        mav.addObject("list", gotBoardDtoList);

        return mav;
    }

    @RequestMapping(value = "/board/update", method = GET)
    public ModelAndView updateFormGet() {
        ModelAndView mav = new ModelAndView("boardUpdate");
        return mav;
    }

    @RequestMapping(value = "/board/update", method = POST)
    public ModelAndView updateFormPost(@RequestParam(value = "id") Long boardId) {
        ModelAndView mav = null;
        try {
            Board gotBoard = boardService.getBoard(boardId);

            BoardDto gotBoardDto = BoardDto.builder()
                    .id(gotBoard.getId())
                    .title(gotBoard.getTitle())
                    .content(gotBoard.getContent())
                    .writer(gotBoard.getWriter()).build();
            mav = new ModelAndView("boardUpdate");
            mav.addObject("board", gotBoardDto);
        } catch (NullBoardException e) {
            mav = new ModelAndView("nullBoard");
        } finally {
            return mav;
        }
    }

    @RequestMapping(value = "/board", method = POST)
    public String create(BoardDto board) {

        Board willCreateBoard = Board.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .build();

        Board createdBoard = boardService.insertBoard(willCreateBoard);
        Long createdId = createdBoard.getId();
        return "redirect:/board/" + createdId;
    }

    @RequestMapping(value = "/board/{id}", method = POST)
    public String update(BoardDto board) {
        Board willUpdateBoard;
        if (board.getId() == null) {
            willUpdateBoard = Board.builder()
                    .title(board.getTitle())
                    .content(board.getContent())
                    .writer(board.getWriter())
                    .build();
        } else {
            willUpdateBoard = boardService.getBoard(board.getId());
            willUpdateBoard = Board.builder()
                    .title(board.getTitle())
                    .content(board.getContent())
                    .writer(board.getWriter())
                    .build();
        }

        Board updatedBoard = boardService.insertBoard(willUpdateBoard);
        Long updatedId = updatedBoard.getId();
        return "redirect:/board/" + updatedId;
    }

    @RequestMapping(value = "/board/{id}", method = GET)
    public ModelAndView boardDetail(@PathVariable(value = "id") Long boardId) {
        ModelAndView mav = null;
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

            Board board = boardService.getBoard(boardId);
            BoardDto boardDto = BoardDto.builder()
                    .writer(board.getWriter())
                    .content(board.getContent().replaceAll(System.getProperty("line.separator"), "<br>"))
                    .title(board.getTitle())
                    .id(board.getId())
                    .createdAt(board.getCreatedAt().format(dateTimeFormatter))
                    .modifiedAt(board.getModifiedAt().format(dateTimeFormatter))
                    .build();
            mav = new ModelAndView("boardDetail");
            mav.addObject("board", boardDto);
        } catch (NullBoardException e) {
            mav = new ModelAndView("nullBoard");
        } finally {
            return mav;
        }
    }

    @RequestMapping(value = "/board/{id}", method = DELETE)
    public String delete(@PathVariable(value = "id") Long boardId, RedirectAttributes rttr) {
        boardService.deleteBoard(boardService.getBoard(boardId));

        return "redirect:/boards";
    }

}
