package com.anyang.study.domain.repository;

import com.anyang.study.configuration.domain.ConfigurationApplicationContextInitializer;
import com.anyang.study.configuration.domain.DomainContextConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(initializers = ConfigurationApplicationContextInitializer.class, classes = { DomainContextConfig.class })
@Transactional
@Rollback(false)
public class BoardRepositoryTest {

	@Autowired
	private BoardRepository boardRepository;

	@Test
	public void CR_Test() {
		Board board = new Board();
		board.setTitle("title");
		board.setContent("content");
		board.setWriter("writer");

		final Board savedBoard = boardRepository.save(board);

		assertThat(board.getTitle(), is(savedBoard.getTitle()));
		assertThat(board.getContent(), is(savedBoard.getContent()));
		assertThat(board.getWriter(), is(savedBoard.getWriter()));

		final Optional<Board> byId = boardRepository.findById(savedBoard.getId());
		final Board read = byId.get();

		assertThat(board.getTitle(), is(read.getTitle()));
		assertThat(board.getContent(), is(read.getContent()));
		assertThat(board.getWriter(), is(read.getWriter()));
	}
}