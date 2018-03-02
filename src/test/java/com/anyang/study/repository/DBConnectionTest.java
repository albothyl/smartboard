package com.anyang.study.repository;

import com.anyang.study.configuration.domain.DomainContextConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainContextConfig.class)
public class DBConnectionTest {
    @Autowired
    private DataSource ds;

    @Test
    public void testConnection() throws Exception {
        try (Connection con = ds.getConnection()) {
            System.out.println("Connection 연결");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
