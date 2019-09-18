package com.zcs.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.zcs.pj.common.vo.CheckBox;
import com.zcs.pj.common.vo.SysRoleMenuVo;
import com.zcs.pj.sys.entity.SysRole;
		
@Mapper
public interface SysRoleDao {
	/**
	 * 分页查询角色信息
	 * @param startIndex 上一页的结束位置
	 * @param pageSize 每页要查询的记录数
	 * @return
	 */
	List<SysRole> findPageObjects(@Param("name") String name,@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize);
	
	//查询记录数
	int getRowCount(@Param("name")String name);
	
	//删除角色
	@Delete("delete from sys_roles where id=#{id}")
	int deleteObject(Integer id);
	
	//插入角色信息
	int insertObject(SysRole entity);
	//基于角色id获取角色以及对应菜单信息
	SysRoleMenuVo findObjectById(Integer id);
	//修改数据
	int updateObject(SysRole entity);
	//查询角色id、角色名
		@Select("select id,name from sys_roles")
		List<CheckBox> findObjects();
}
