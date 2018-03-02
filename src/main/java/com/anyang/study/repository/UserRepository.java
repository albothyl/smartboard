package com.anyang.study.repository;

import com.anyang.study.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Board, Long> {
}
