package com.anyang.study.board.interfaces.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String modifiedAt;
    private String createdAt;
}
