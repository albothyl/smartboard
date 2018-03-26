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

    //목록 가져오기 - 정렬, 키워드 검색
    @RequestMapping(value = "/board/sortType={sortType}&searchType={searchType}&searchKeyword={searchKeyword}")
    public ModelAndView boardSortList(@PathVariable(value = "sortType") String sort,
                                      @PathVariable(value = "searchType") String searchtype,
                                      @PathVariable(value = "searchKeyword") String searchkeyword) {
        ModelAndView mav = new ModelAndView("boardList");
        //List<Board> gotBoardList = boardService.getBoardAll(new Sort("writer"));
        if (sort.isEmpty()) {
            sort = "id";
        }
        List<Board> gotBoardList = boardService.getBoardAll(new Sort(Sort.Direction.DESC, sort), searchtype, searchkeyword);

        ArrayList<BoardDto> gotBoardDtoList = new ArrayList<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        for (int i = 0; i < gotBoardList.size(); i++) {
            Board board = gotBoardList.get(i);
            BoardDto dto = new BoardDto();
            dto.setId(board.getId());
            dto.setTitle(board.getTitle());
            dto.setContent(board.getContent());
            dto.setWriter(board.getWriter());
            dto.setCreatedAt(board.getCreatedAt().format(dateTimeFormatter));
            dto.setModifiedAt(board.getModifiedAt().format(dateTimeFormatter));
            gotBoardDtoList.add(dto);
        }
        mav.addObject("list", gotBoardDtoList);

        mav.addObject("sorttype", sort);
        mav.addObject("strsearchtype", searchtype);
        mav.addObject("searchkeyword", searchkeyword);

        return mav;
    }

    //목록 가져오기
    @RequestMapping(value = "/boards", method = GET)
    public ModelAndView boardList() {
        ModelAndView mav = new ModelAndView("boardList");
        List<Board> gotBoardList = boardService.getBoardAll(null, "", "");

        ArrayList<BoardDto> gotBoardDtoList = new ArrayList<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        for (int i = 0; i < gotBoardList.size(); i++) {
            Board board = gotBoardList.get(i);
            BoardDto dto = new BoardDto();
            dto.setId(board.getId());
            dto.setTitle(board.getTitle());
            dto.setContent(board.getContent());
            dto.setWriter(board.getWriter());
            dto.setCreatedAt(board.getCreatedAt().format(dateTimeFormatter));
            dto.setModifiedAt(board.getModifiedAt().format(dateTimeFormatter));
            gotBoardDtoList.add(dto);
        }
        mav.addObject("list", gotBoardDtoList);

        return mav;
    }

    //게스글등록 폼
    @RequestMapping(value = "/board/update", method = GET)
    public ModelAndView updateFormGet() {
        ModelAndView mav = new ModelAndView("boardUpdate");
        return mav;
    }

    @RequestMapping(value = "/board/update", method = POST)
    public ModelAndView updateFormPost(@RequestParam(value = "id") long bid) {
        ModelAndView mav = null;
        try {
            Board gotBoard = boardService.getBoard(bid);

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

    //게시글등록
    @RequestMapping(value = "/board", method = POST)
    public String create(BoardDto board) {
        Board willCreateBoard = new Board();

        willCreateBoard.setTitle(board.getTitle());
        willCreateBoard.setContent(board.getContent());
        willCreateBoard.setWriter(board.getWriter());

        Board createdBoard = boardService.insertBoard(willCreateBoard);
        long createdId = createdBoard.getId();
        return "redirect:/board/" + createdId;
    }

    //게시글수정
    @RequestMapping(value = "/board/{id}", method = POST)
    public String update(BoardDto board) {
        Board willUpdateBoard;
        if (board.getId() == null) {
            willUpdateBoard = new Board();
        } else {
            willUpdateBoard = boardService.getBoard(board.getId());
        }
        willUpdateBoard.setTitle(board.getTitle());
        willUpdateBoard.setContent(board.getContent());
        willUpdateBoard.setWriter(board.getWriter());

        Board updatedBoard = boardService.insertBoard(willUpdateBoard);
        long updatedId = updatedBoard.getId();
        return "redirect:/board/" + updatedId;
    }

    //디테일
    @RequestMapping(value = "/board/{id}", method = GET)
    public ModelAndView boardDetail(@PathVariable(value = "id") long bid) {
        ModelAndView mav = null;
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

            Board board = boardService.readBoard(bid);
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

    //삭제
    @RequestMapping(value = "/board/{id}", method = DELETE)
    public String delete(@PathVariable(value = "id") long bid, RedirectAttributes rttr) {
        boardService.deleteBoard(boardService.getBoard(bid));

        return "redirect:/boards";
    }

}
