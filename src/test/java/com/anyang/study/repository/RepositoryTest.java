package com.anyang.study.repository;

import com.anyang.study.configuration.domain.DomainContextConfig;
import com.anyang.study.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainContextConfig.class)
@Rollback(false)
public class RepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;

    private User user1 = new User();
    private User user2 = new User();
    private User user3 = new User();

    @Test
    public void UserSaveTest() {
        user1.builder().id("id1").name("name1").password("passwd1").build();
        System.out.println(user1.toString());
    }
}
