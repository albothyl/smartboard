package com.anyang.study.board.interfaces.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String modifiedAt;
    private String createdAt;
}
