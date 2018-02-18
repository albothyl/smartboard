package com.anyang.study.domain.repository;

import com.anyang.study.configuration.domain.ConfigurationApplicationContextInitializer;
import com.anyang.study.configuration.domain.DomainContextConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(initializers = ConfigurationApplicationContextInitializer.class, classes = { DomainContextConfig.class })
@Transactional
public class BoardRepositoryTest {

	@Autowired
	private BoardRepository boardRepository;

	@Test
	public void CRUD_Test() {
		Board board = new Board();
		board.setTitle("title");
		board.setContent("content");
		board.setWriter("writer");

		final Board savedBoard = boardRepository.save(board);

		assertThat(savedBoard.getTitle(), is(board.getTitle()));
		assertThat(savedBoard.getContent(), is(board.getContent()));
		assertThat(savedBoard.getWriter(), is(board.getWriter()));
	}
}