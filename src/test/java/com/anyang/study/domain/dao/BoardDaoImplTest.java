package com.anyang.study.domain.dao;

import com.anyang.study.configuration.domain.ConfigurationApplicationContextInitializer;
import com.anyang.study.configuration.domain.DomainContextConfig;
import com.anyang.study.domain.repository.Board;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(initializers = ConfigurationApplicationContextInitializer.class, classes = { DomainContextConfig.class })
public class BoardDaoImplTest {

	@Autowired
	private BoardDaoImpl boardDao;

	@Test
	public void CR_Test() {
		Board board = new Board();
		board.setTitle("title");
		board.setContent("content");
		board.setWriter("writer");

		boardDao.write(board);

		final Board read = boardDao.read(board.getId());

		assertThat(read.getId(), is(board.getId()));
		assertThat(read.getTitle(), is(board.getTitle()));
		assertThat(read.getContent(), is(board.getContent()));
		assertThat(read.getWriter(), is(board.getWriter()));
	}
}