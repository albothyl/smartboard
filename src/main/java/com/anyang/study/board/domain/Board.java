package com.anyang.study.board.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@Table(schema = "smartboard", name = "board")
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String writer;
    @LastModifiedDate
    private LocalDateTime modifiedAt;
    @CreatedDate
    private LocalDateTime createdAt;


    public void setBoard(Long id, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
