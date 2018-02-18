package com.anyang.study.domain.dao;

import com.anyang.study.domain.repository.Board;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardDaoImpl {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public int write(Board board) {
		return sqlSessionTemplate.insert("Board.write", board);
	}

	public Board read(int id) {
		return sqlSessionTemplate.selectOne("Board.read", id);
	}
}
