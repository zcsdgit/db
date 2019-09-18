package com.zcs.pj.sys.service;


import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zcs.pj.common.vo.PageObject;
import com.zcs.pj.common.vo.SysUserDeptVo;
import com.zcs.pj.sys.entity.SysUser;

public interface SysUserService {
	//分页
	PageObject<SysUserDeptVo> findPageObjects(@Param("username")String username,@Param("pageCurrent")Integer pageCurrent);
	
	//禁用
	int validById(Integer id,Integer valid,String modifiedUser);
	
	int saveObject(SysUser entity,Integer[] roleIds);
	
	//修改页面数据呈现
	Map<String,Object> findObjectById(Integer userId);
	
	//更新用户数据
	public int updateObject(SysUser entity,Integer[] roleIds);
	
	//修改密码
	int updatePassword(String password,String newPassword,String cfgPassword);
}
