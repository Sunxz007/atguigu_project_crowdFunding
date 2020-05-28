package com.sun.test;

import com.sun.crowd.entity.Admin;
import com.sun.crowd.entity.Role;
import com.sun.crowd.mapper.AdminMapper;
import com.sun.crowd.mapper.RoleMapper;
import com.sun.crowd.service.api.AdminService;
import com.sun.crowd.service.api.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;


//在类上标记必要的注解，spring整合junit
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Test
    public void testTx(){
        Admin admin = new Admin(null, "adminTest", "123456", "tx测试", "test.@sun.com", new Date().getTime()+"");
        adminService.saveAdmin(admin);
    }

    @Test
    public void testInsert() {
        Admin admin = new Admin(null, "adminTest", "123456", "连接测试", "test.@sun.com", new Date().getTime()+"");
        int i = adminMapper.insertSelective(admin);
        System.out.println(i);
    }

    @Test
    public void test() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testLog(){
        //获取logger类，传入的class对象就是当前打印日志的类
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);

        //根据不同的日志级别打印日志
        logger.debug("Im Debug Level");
        logger.debug("Im Debug Level");
        logger.debug("Im Debug Level");

        logger.info("Im Info Level");
        logger.info("Im Info Level");
        logger.info("Im Info Level");

        logger.warn("Im Warn Level");
        logger.warn("Im Warn Level");
        logger.warn("Im Warn Level");

        logger.error("Im Error Level");
        logger.error("Im Error Level");
        logger.error("Im Error Level");
    }

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testRole(){
        for (int i = 0; i < 200; i++) {
            roleMapper.insert(new Role(i, "role" + i));
        }
    }
}
