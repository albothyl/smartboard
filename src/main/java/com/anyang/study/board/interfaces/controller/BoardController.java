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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    //목록 가져오기 - 정렬, 키워드 검색
    @RequestMapping(value = "/board/boardList/sortType={sortType}&searchType={searchType}&searchKeyword={searchKeyword}")
    public ModelAndView boardList(@PathVariable(value = "sortType") String sort,
                                  @PathVariable(value = "searchType") String searchtype,
                                  @PathVariable(value = "searchKeyword") String searchkeyword) {
        ModelAndView mav = new ModelAndView("boardList");
        //List<Board> gotBoardList = boardService.getBoardAll(new Sort("writer"));
        if(sort.isEmpty()) {
            sort = "id";
        }

        List<Board> gotBoardList = boardService.getBoardAll(new Sort(Sort.Direction.DESC, sort), searchtype, searchkeyword);

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

        mav.addObject("sorttype", sort);
        mav.addObject("strsearchtype", searchtype);
        mav.addObject("searchkeyword", searchkeyword);

        return mav;
    }

    //목록 가져오기
    @RequestMapping(value = "/board/boardList")
    public ModelAndView boardList() {
        ModelAndView mav = new ModelAndView("boardList");
        List<Board> gotBoardList = boardService.getBoardAll(null,"","");

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
        ModelAndView mav = null;
        try {
            Board gotBoard = boardService.getBoard(bid);

            BoardDto gotBoardDto = BoardDto.builder()
                    .id(gotBoard.getId())
                    .title(gotBoard.getTitle())
                    .content(gotBoard.getContent())
                    .writer(gotBoard.getWriter())
                    .modifiedAt(gotBoard.getModifiedAt())
                    .createdAt(gotBoard.getCreatedAt()).build();
            mav = new ModelAndView("boardUpdate");
            mav.addObject("board", gotBoardDto);
        } catch (NullBoardException e) {
            mav = new ModelAndView("nullBoard");
        } finally {
            return mav;
        }
    }

    //게시글등록 후 상세페이지로
    @RequestMapping(value = "/board/boardUpdate", method = POST)
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
        return "redirect:/board/boardDetail/" + updatedId;
    }

    @RequestMapping(value = "/board/boardDelete/{id}", method = POST)
    public String delete(@PathVariable(value = "id") long bid, RedirectAttributes rttr) {
        boardService.deleteBoard(boardService.getBoard(bid));

        rttr.addFlashAttribute("msg", "success");
        return "redirect:/board/boardList";
    }


    @RequestMapping(value = "/board/boardDetail/{id}")
    public ModelAndView boardDetail(@PathVariable(value = "id") long bid) {
        ModelAndView mav = new ModelAndView("boardDetail");

        Board board = boardService.getBoard(bid);

        board.setContent(board.getContent());
        mav.addObject("board", board);

        return mav;
    }
}
