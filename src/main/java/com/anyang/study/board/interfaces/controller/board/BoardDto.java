package com.anyang.study.board.interfaces.controller.board;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class BoardDto {
	private Long id;
	private String title;
	private String content;
	private String writer;
	private String modifiedAt;
	private String createdAt;
}


