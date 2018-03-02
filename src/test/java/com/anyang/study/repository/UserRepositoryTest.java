package com.anyang.study.repository;

import com.anyang.study.configuration.domain.DomainContextConfig;
import com.anyang.study.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainContextConfig.class)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user1 = new User();
    private User user2 = new User();
    private User user3 = new User();

    @Before
    public void initUser(){
        user1 = user1.builder().id("id1").name("name1").password("passwd1").build();
        user2 = user2.builder().id("id2").name("name2").password("passwd2").build();
        user3 = user3.builder().id("id3").name("name3").password("passwd3").build();
    }

    @Test
    public void UserSaveTest() {
        System.out.println(user1.toString());
        System.out.println(user2.toString());
        System.out.println(user3.toString());
    }
}
