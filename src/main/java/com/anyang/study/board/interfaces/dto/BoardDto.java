package com.anyang.study.board.interfaces.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private String createdAt2;

}
