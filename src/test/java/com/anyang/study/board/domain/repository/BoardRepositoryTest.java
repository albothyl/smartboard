package com.anyang.study.board.domain.repository;

import com.anyang.study.board.domain.Board;
import com.anyang.study.board.domain.BoardRepository;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigurationApplicationContextInitializer.class, classes = {DomainContextConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    private static final int TOTAL_COUNT = 10;
    private static boolean isInitialized = false;
    //초기 설정 디비 전체 삭제후 10건 추가
    @Before
    public void setup() {
<<<<<<< HEAD:src/test/java/com/anyang/study/board/domain/repository/BoardRepositoryTest.java
        boardRepository.deleteAll();
        boardRepository.flush();

        for (int i = 0; i < TOTAL_COUNT; i++) {
            Board board = Board.builder()
                    .title("insert test " + i)
                    .content("insert content " + i)
                    .writer("insert writer " + i)
                    .build();

            boardRepository.save(board);
=======
        if(!isInitialized) {
            boardRepository.deleteAll();
            boardRepository.flush();
            for (int i = 0; i < TOTAL_COUNT; i++) {
                Board board = new Board();
                boardRepository.save(board.builder()
                        .title("title " + i)
                        .content("content " + i)
                        .writer("writer " + i)
                        .build());
            }
>>>>>>> feature/crud_sdh:src/test/java/com/anyang/study/board/domain/BoardRepositoryTest.java
        }
        isInitialized = true;
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

        assertThat(afterId + 1L, is(beforeId));
    }
}