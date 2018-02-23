package com.anyang.study.cgr.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;

@RunWith(SpringJUnit4ClassRunner.class)
public class CategoryDAOTest {
    @Autowired
    private DataSource ds;

    @Test
    public void testConnection() throws Exception{
        try (Connection conn = ds.getConnection()) {
            System.out.println(conn);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}