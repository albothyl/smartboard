package com.anyang.study.board.interfaces.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Builder
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String modifiedAt;
    private String createdAt;
    private int viewCnt;
}
