package com.zcs.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface SysRoleMenuDao {
	//根据菜单id删除角色菜单关系
	@Delete("delete from sys_role_menus where menu_id=#{menuId}")
	int deleteObjectsByMenuId(Integer menuId);
	//根据角色id删除角色菜单关系数据
	@Delete("delete from sys_role_menus where role_id=#{roleId}")
	int deleteObjectsByRoleId(Integer roleId);
	//插入角色菜单关系
	int insertObjects(@Param("roleId")Integer roleId,@Param("menuIds")Integer[] menuIds);
	//呈现修改页面的数据,因为在mapper中写了所以不用再写一次
	//@Select("select menu_id from sys_role_menus where role_Id=#{id}")
	//int findMenuIdsByRoleId(Integer id);
	//基于角色id查找菜单id信息
	List<Integer> findMenuIdsByRoleIds(@Param("roleIds")Integer[] roleIds);
	
}
