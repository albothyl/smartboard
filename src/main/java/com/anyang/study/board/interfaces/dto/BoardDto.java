package com.anyang.study.board.interfaces.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Builder
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private LocalDate createdAt2;
}
