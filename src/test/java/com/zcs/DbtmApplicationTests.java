package com.zcs;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zcs.pj.sys.dao.SysLogDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbtmApplicationTests {

	
	@Autowired
	private DataSource dataSource;
	@Test
	public void testDataSource() throws SQLException {
		//测试是否获得数据源
		System.out.println(dataSource);
		//测试是否成功连接
		Connection conn = dataSource.getConnection();
		System.out.println(conn);
	}
	
	@Autowired
	private SysLogDao sysLogDao;
	@Test
	public void testDao() {
		//测试dao层
		int row = sysLogDao.deleteById(19);
		System.out.println(row);
	}
	@Test
	public void testxx() {
		int rows = sysLogDao.getRowCount("admin");
		System.out.println(rows);
	}
}
