package com.zcs.pj.sys.service;

import java.util.List;
import java.util.Map;

import com.zcs.pj.common.vo.Node;
import com.zcs.pj.sys.entity.SysMenu;

public interface SysMenuService {
	//呈现菜单数据
	List<Map<String,Object>> finObjects();
	
	//删除菜单
	int deleteObject(Integer id);
	
	//查询上级菜单信息
	List<Node> findZtreeMenuNodes();
	
	//添加菜单
	int insertObject(SysMenu entity);
	
	//修改菜单
	int updateObject(SysMenu entity);
}
