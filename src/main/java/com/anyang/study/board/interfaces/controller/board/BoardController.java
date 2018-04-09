package com.anyang.study.board.interfaces.controller.board;

import com.anyang.study.board.application.finder.BoardFinder;
import com.anyang.study.board.application.modifier.BoardModifier;
import com.anyang.study.board.domain.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.anyang.study.board.interfaces.controller.board.BoardConverter.toDto;
import static com.anyang.study.board.interfaces.controller.board.BoardConverter.toEntity;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class BoardController {

	private static final String REDIRECT_BOARDS = "redirect:/boards/";

	@Autowired
	private BoardFinder boardFinder;

	@Autowired
	private BoardModifier boardModifier;

	//리스트 보여주기
	@RequestMapping(value = "/boards", method = GET)
	public ModelAndView list(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<BoardDto> boardDtoPage = boardFinder.getBoardList(pageable).map(toDto);

		ModelAndView mav = new ModelAndView("boardList");
		mav.addObject("list", boardDtoPage);

		return mav;
	}

	//신규글 작성양식
	@RequestMapping(value = "/form", method = GET)
	public String createForm() {
		return "boardForm";
	}

	//게시글작성
	@RequestMapping(value = "/board", method = POST)
	public String create(BoardDto boardDto) {
		Board board = boardModifier.insertBoard(toEntity.apply(boardDto));

		return REDIRECT_BOARDS + board.getId();
	}

	//기존글 수정양식
	@RequestMapping(value = "/boards/{id}/editForm", method = GET)
	public ModelAndView modifyForm(@PathVariable(value = "id") Long boardId) {
		Board board = boardFinder.getBoard(boardId);

		ModelAndView mav = new ModelAndView("boardForm");
		mav.addObject("boardDto", toDto.apply(board));

		return mav;
	}

	//게시글수정
	@RequestMapping(value = "/boards/{id}", method = PATCH)
	public String modify(@PathVariable(value = "id") Long boardId, BoardDto boardDto) {
		Board source = toEntity.apply(boardDto);
		boardModifier.modify(source);

		return REDIRECT_BOARDS + boardId;
	}

	//게시글보기
	@RequestMapping(value = "/boards/{id}", method = GET)
	public ModelAndView detail(@PathVariable(value = "id") Long boardId) {
		Board board = boardFinder.getBoard(boardId);

		ModelAndView mav = new ModelAndView("boardDetail");
		mav.addObject("board", toDto.apply(board));

		return mav;
	}

	//게시글삭제
	@RequestMapping(value = "/boards/{id}", method = DELETE)
	public String delete(@PathVariable(value = "id") Long boardId) {
		boardModifier.deleteBoard(boardId);

		return REDIRECT_BOARDS;
	}
}
