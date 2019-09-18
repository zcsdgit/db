package com.zcs.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zcs.pj.sys.entity.SysLog;

@Mapper	//系统底层会自动为@Mapper修饰的接口创建实现类对象
public interface SysLogDao {
	@Delete("delete from sys_logs where id=#{id}")
	int deleteById(Integer id);
	/**按条件查询总记录数
	 * @param username 查询条件*/
	int getRowCount(@Param("username")String username);
	/**基于条件执行分页查询，获取当前页记录
	 * @param username 查询条件
	 * @param startIndex 起始位置
	 * @param pageSize 每页记录数*/
	List<SysLog> findPageObjects(@Param("username")String username,@Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);
	//删除多个记录
	int deleteObjects(@Param("ids") Integer[] ids);
	//添加日志
	int insertObject(SysLog entity);
}
