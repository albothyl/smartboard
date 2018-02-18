package com.anyang.study.domain.mapping;

import com.anyang.study.domain.repository.Board;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardDaoImpl {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public void write(Board board) {
		sqlSessionTemplate.insert("Board.write", board);
	}

	public Board read(Long id) {
		return sqlSessionTemplate.selectOne("Board.read", id);
	}
}
