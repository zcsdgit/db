package com.zcs.pj.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zcs.pj.common.vo.CheckBox;
import com.zcs.pj.common.vo.PageObject;
import com.zcs.pj.common.vo.SysRoleMenuVo;
import com.zcs.pj.sys.entity.SysRole;

@Service
public interface SysRoleService {
	//分页
	PageObject<SysRole> findPageObjects(String name,Integer pageCurrent,Integer pageSize);
	//删除角色相关
	int deleteObject(Integer id);
	
	//保存角色相关信息
	int saveObject (SysRole entity,Integer[] menuIds);
	
	//呈现修改页面的数据
	SysRoleMenuVo findObjectById(Integer id);
	//修改页面数据
	int updateObject(SysRole entity,Integer[] menuIds);
	
	//查询角色id及角色名
		List<CheckBox> findObjects();
}
