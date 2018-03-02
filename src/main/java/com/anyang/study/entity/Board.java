package com.anyang.study.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bno;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="uno")
    private User user;
    private String title;
    private String contents;
    @Column(name = "view_cnt")
    private long viewCnt;
    @Column(name = "reg_date")
    @CreatedDate
    private LocalDate regDate;
    @Column(name = "modify_date")
    @LastModifiedDate
    private LocalDate modifyDate;


}
