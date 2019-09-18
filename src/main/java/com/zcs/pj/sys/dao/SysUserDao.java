package com.zcs.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zcs.pj.common.vo.SysUserDeptVo;
import com.zcs.pj.sys.entity.SysUser;

@Mapper
public interface SysUserDao {
	//总记录
	int getRowCount(@Param("username")String username);
	//当前页数据
	List<SysUserDeptVo> findPageObjects(@Param("username")String username,@Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);
	
	//禁用
	@Update("update sys_users set valid=#{valid},modifiedUser=#{modifiedUser},modifiedTime=now() where id=#{id}")
	int validById(@Param("id")Integer id,@Param("valid")Integer valid,@Param("modifiedUser")String modifiedUser);
	
	/*	1)	接收业务层数据(SysUser)，并将实体对象数据保存到数据库
		2)	接收用户和角色的关系数据,并将数据写入到中间关系表中
	 */
	//将用户数据插入数据库
	int insertObejct(SysUser entity);
	
	//用户修改页面呈现
	//根据id查询用户及部门
	SysUserDeptVo findObjectById(Integer id);
	
	//编辑页面数据更新
	int updateObject(SysUser entity);
	
	//认证
	@Select("select *from sys_users where username=#{username}")
	SysUser findUserByUserName(String username);
	
	//修改密码
	/**
	 * 基于用户id修改用户的密码
	 * @param password 新的密码
	 * @param salt 密码加密时使用的盐值
	 * @param id 用户id
	 * @return
	 */
	int updatePassword(@Param("password")String password,@Param("salt")String salt,@Param("id")Integer id);
	
}
