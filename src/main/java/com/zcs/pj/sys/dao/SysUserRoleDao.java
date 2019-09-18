package com.zcs.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserRoleDao {
	//删除角色用户关系数据
	@Delete("delete from sys_user_roles where role_id=#{roleId}")
	int deleteObjectsByRoleId(Integer roleId);
	/**
	 * 负责将用户与角色的关系数据写入到数据库
	 * @param userId 用户id
	 * @param rolesId 多个角色id
	 */
	int insertObjects(@Param("userId")Integer userId,@Param("roleIds")Integer[] roleIds);
	
	
	//根据用户id查询角色id
	List<Integer> findRoleIdsByUserId(Integer userId);
	
	//定义删除方法,先删除关系数据再添加
	int deleteObjectsByUserId(Integer userId);
	
}
