package com.anyang.study.board.interfaces.controller;

import com.anyang.study.board.application.GetService;
import com.anyang.study.board.application.ModifyService;
import com.anyang.study.board.domain.Board;
import com.anyang.study.board.interfaces.dto.BoardDto;
import com.anyang.study.board.interfaces.util.DomainManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private GetService getService;
    @Autowired
    private ModifyService modifyService;

    //리스트 보여주기
    @RequestMapping(value = "/boards", method = GET)
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("boardList");
        List<Board> gotBoardList = getService.getBoardAll(null, "", "");

        ArrayList<BoardDto> boardDtoList = new ArrayList<>();

        for (int i = 0; i < gotBoardList.size(); i++) {
            Board board = gotBoardList.get(i);
            BoardDto boardDto = DomainManager.domainToDto(board, "yyyy.MM.dd");
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

        Board willCreateBoard = DomainManager.dtoToDomain(boardDto);

        Board createdBoard = modifyService.insertBoard(willCreateBoard);
        Long createdId = createdBoard.getId();
        return "redirect:/board/" + createdId;
    }

    //기존글 수정양식
    @RequestMapping(value = "/boards/{id}/editForm", method = GET)
    public ModelAndView modifyForm(@RequestParam(value = "id") Long boardId) {
        Board gotBoard = getService.getBoard(boardId);

        BoardDto boardDto = DomainManager.domainToDto(gotBoard, "yyyy-MM-dd hh:mm:ss");
        ModelAndView mav = new ModelAndView("boardForm");
        mav.addObject("board", boardDto);
        return mav;
    }

    //게시글수정
    @RequestMapping(value = "/boards/{id}", method = PATCH)
    public String modify(@PathVariable(value = "id") Long boardId,
                         BoardDto boardDto) {
        Board willUpdateBoard = getService.getBoard(boardId);

        willUpdateBoard.setTitle(boardDto.getTitle());
        willUpdateBoard.setContent(boardDto.getContent());
        willUpdateBoard.setWriter(boardDto.getWriter());

        Board modifiedBoard = modifyService.insertBoard(willUpdateBoard);
        Long modifiedId = modifiedBoard.getId();
        return "redirect:/board/" + modifiedId;
    }

    //게시글보기
    @RequestMapping(value = "/boards/{id}", method = GET)
    public ModelAndView detail(@PathVariable(value = "id") Long boardId) {

        Board gotBoard = getService.getBoard(boardId);
        BoardDto boardDto = DomainManager.domainToDto(gotBoard, "yyyy-MM-dd hh:mm:ss");
        ModelAndView mav = new ModelAndView("boardDetail");
        mav.addObject("board", boardDto);
        return mav;
    }

    //게시글삭제
    @RequestMapping(value = "/boards/{id}", method = DELETE)
    public String delete(@PathVariable(value = "id") Long boardId) {
        modifyService.deleteBoard(getService.getBoard(boardId));

        return "redirect:/boards";
    }


/*    @RequestMapping(value = "/board/sortType={sortType}&searchType={searchType}&searchKeyword={searchKeyword}")
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
    }*/

}
