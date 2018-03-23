package com.anyang.study.board.domain;

import com.anyang.study.configuration.domain.ConfigurationApplicationContextInitializer;
import com.anyang.study.configuration.domain.DomainContextConfig;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigurationApplicationContextInitializer.class, classes = {DomainContextConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    private static final int TOTAL_COUNT = 10;

    //초기 설정 디비 전체 삭제후 10건 추가
    @Before
    public void setup() {
        boardRepository.deleteAll();
        boardRepository.flush();

        for (int i = 0; i < TOTAL_COUNT; i++) {
            Board board = new Board();
            board.setTitle("insert test " + i);
            board.setContent("insert content " + i);
            board.setWriter("insert writer " + i);
            boardRepository.save(board);
        }
    }

    //전체 리스트 조회
    @Test
    public void testBoardAllList() {
        List<Board> list = boardRepository.findAll();
        assertThat(list.size(), is(TOTAL_COUNT));
    }

    //특정 아이디 존재 여부
    @Test
    public void testBoardModifyId() {
        List<Board> list = boardRepository.findAll();
        Long id = 0L;
        for (Board board : list) {
            id = board.getId();
        }
        assertNotNull(id);
    }

    //마지막 리스트 삭제후 비교
    @Test
    public void testBoardOneDelete() {
        List<Board> list = boardRepository.findAll();
        Long beforeId = 0L;
        for (Board board : list) {
            beforeId = board.getId();
        }
        boardRepository.deleteById(beforeId);

        list = boardRepository.findAll();
        Long afterId = 0L;
        for (Board board : list) {
            afterId = board.getId();
        }

        assertThat(afterId+1L, is(beforeId));
    }
}