package com.anyang.study.board.interfaces.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.anyang.study.board.application.FindService;
import com.anyang.study.board.application.TransactionService;
import com.anyang.study.board.domain.Board;
import com.anyang.study.board.interfaces.dto.BoardDto;
import com.anyang.study.board.interfaces.util.DomainHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    private FindService findService;
    @Autowired
    private TransactionService transactionService;

    private static final String REDIRECT_BOARDS = "redirect:/boards/";
    private static final String DATE_PATTERN = "yyyy.mm.dd";
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd hh:mm:ss";

    //리스트 보여주기
    @RequestMapping(value = "/boards", method = GET)
    public ModelAndView list(@RequestParam(value = "sortType", required = false, defaultValue = "desc") String sortType,
                             @RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
                             @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword
    ) {
        ModelAndView mav = new ModelAndView("boardList");
        List<Board> gotBoardList = findService.getBoardAll(sortType, searchType, searchKeyword);

        ArrayList<BoardDto> boardDtoList = new ArrayList<>();

        for (int i = 0; i < gotBoardList.size(); i++) {
            Board board = gotBoardList.get(i);
            BoardDto boardDto = DomainHelper.domainToDto(board, DATE_PATTERN);
            boardDtoList.add(boardDto);
        }
        mav.addObject("list", boardDtoList);

        return mav;
    }

    //신규글 작성양식
    @RequestMapping(value = "/boardsForm", method = GET)
    public ModelAndView createForm() {
        ModelAndView mav = new ModelAndView("boardForm");
        return mav;
    }

    //게시글작성
    @RequestMapping(value = "/boards", method = POST)
    public String create(BoardDto boardDto) {

        Board willCreateBoard = DomainHelper.dtoToDomain(boardDto);

        Board createdBoard = transactionService.insertBoard(willCreateBoard);
        Long createdId = createdBoard.getId();
        return REDIRECT_BOARDS + createdId;
    }

    //기존글 수정양식
    @RequestMapping(value = "/boards/{id}/editForm", method = GET)
    public ModelAndView modifyForm(@PathVariable(value = "id") Long boardId) {
        Board gotBoard = findService.getBoard(boardId);

        BoardDto boardDto = DomainHelper.domainToDto(gotBoard, DATE_TIME_PATTERN);
        ModelAndView mav = new ModelAndView("boardForm");
        mav.addObject("boardDto", boardDto);
        return mav;
    }

    //게시글수정
    @RequestMapping(value = "/boards/{id}", method = PATCH)
    public String modify(@PathVariable(value = "id") Long boardId,
                         BoardDto boardDto) {
        Board baseBoard = findService.getBoard(boardId);
        Board willUpdateBoard = DomainHelper.dtoToDomain(boardDto);
        willUpdateBoard.setBoard(baseBoard.getId(), baseBoard.getCreatedAt(), baseBoard.getModifiedAt());

        Board modifiedBoard = transactionService.insertBoard(willUpdateBoard);
        Long modifiedId = modifiedBoard.getId();
        return REDIRECT_BOARDS + modifiedId;
    }

    //게시글보기
    @RequestMapping(value = "/boards/{id}", method = GET)
    public ModelAndView detail(@PathVariable(value = "id") Long boardId) {

        Board gotBoard = findService.getBoard(boardId);
        BoardDto boardDto = DomainHelper.domainToDto(gotBoard, "yyyy-MM-dd hh:mm:ss");
        ModelAndView mav = new ModelAndView("boardDetail");
        mav.addObject("board", boardDto);
        return mav;
    }

    //게시글삭제
    @RequestMapping(value = "/boards/{id}", method = DELETE)
    public String delete(@PathVariable(value = "id") Long boardId) {
        transactionService.deleteBoard(findService.getBoard(boardId));

        return REDIRECT_BOARDS;
    }

}
