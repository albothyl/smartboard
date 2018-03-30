package com.anyang.study.board.interfaces.controller;

import com.anyang.study.board.application.FindService;
import com.anyang.study.board.application.TransactionService;
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

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
public class BoardController {

    private DomainManager domainManager = DomainManager.getInstance();
    private DtoManager dtoManager = DtoManager.getInstance();
    @Autowired
    FindService findService;
    @Autowired
    TransactionService transactionService;

    //목록 가져오기 - 정렬, 키워드 검색
    @RequestMapping(value = "/board/boardList/sortType={sortType}&searchType={searchType}&searchKeyword={searchKeyword}")
    public ModelAndView boardList(@PathVariable(value = "sortType") String sort,
                                  @PathVariable(value = "searchType") String searchType,
                                  @PathVariable(value = "searchKeyword") String searchKeyword) {
        ModelAndView mav = new ModelAndView("boardList");
        if (sort.isEmpty()) {
            sort = "id";
        }
        List<Board> boardList = findService.getBoardAll(new Sort(Sort.Direction.DESC, sort), searchType, searchKeyword);

        List<BoardDto> boardDtoList = dtoManager.getBoardDtos(boardList);

        mav.addObject("list", boardDtoList);

        mav.addObject("sortType", sort);
        mav.addObject("searchType", searchType);
        mav.addObject("searchKeyword", searchKeyword);

        return mav;
    }


    //목록 가져오기
    @RequestMapping(value = "/board/boardList")
    public ModelAndView boardList() {
        ModelAndView mav = new ModelAndView("boardList");
        List<Board> boardList = findService.getBoardAll(null, "", "");
        List<BoardDto> boardDtoList = dtoManager.getBoardDtos(boardList);
        mav.addObject("list", boardDtoList);

        return mav;
    }

    @RequestMapping(value = "/board/boardDetail/{id}")
    public ModelAndView boardDetail(@PathVariable(value = "id") Long boardId) {
        ModelAndView mav = new ModelAndView("boardDetail");

        Board board = findService.getBoard(boardId);
        BoardDto gotBoardDto = dtoManager.getBoardDto(board);

        mav.addObject("board", gotBoardDto);

        return mav;
    }


    @RequestMapping(value = "/board/boardUpdate/{id}")
    public ModelAndView modify(@PathVariable(value = "id") Long boardId) {
        ModelAndView mav = null;
        try {
            Board board = findService.getBoard(boardId);

            BoardDto boardDto = dtoManager.getBoardDto(board);
            mav = new ModelAndView("boardUpdate");
            mav.addObject("board", boardDto);
        } catch (NullBoardException e) {
            mav = new ModelAndView("nullBoard");
        } finally {
            return mav;
        }
    }

    //새글작성
    @RequestMapping(value = "/board/boardUpdate")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("boardUpdate");

        return mav;
    }

    @RequestMapping(value = "/board/boardUpdate", method = POST)
    public String update(@RequestParam Map<String, String> param) {
        Board board = domainManager.getBoard(param, findService);

        Board updatedBoard = transactionService.insertBoard(board);
        Long updatedId = updatedBoard.getId();
        return "redirect:/board/boardDetail/" + updatedId;
    }


    @RequestMapping(value = "/board/boardDelete/{id}")
    public String delete(@PathVariable(value = "id") Long boardId, RedirectAttributes returnMessage) {
        transactionService.deleteBoard(boardId);

        returnMessage.addFlashAttribute("msg", "success");
        return "redirect:/board/boardList";
    }

}
