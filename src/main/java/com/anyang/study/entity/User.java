package com.anyang.study.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long uno;
    private String id;
    private String name;
    private String password;

}
