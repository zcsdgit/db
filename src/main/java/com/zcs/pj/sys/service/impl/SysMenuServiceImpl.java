package com.zcs.pj.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zcs.pj.common.annotation.RequiredCache;
import com.zcs.pj.common.exception.ServiceException;
import com.zcs.pj.common.vo.Node;
import com.zcs.pj.sys.dao.SysMenuDao;
import com.zcs.pj.sys.dao.SysRoleMenuDao;
import com.zcs.pj.sys.entity.SysMenu;
import com.zcs.pj.sys.service.SysMenuService;
@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	//呈现菜单数据
	@Override
	@RequiredCache	
	public List<Map<String, Object>> finObjects() {
		List<Map<String,Object>> list=sysMenuDao.finObjects();
		if(list==null||list.size()==0)
			throw new ServiceException("没有对应菜单");
		return list;
	}
	//基于id删除菜单
	@Override
	public int deleteObject(Integer id) {
		//验证数据的合法性
		if(id==null||id<=0) {
			throw new IllegalArgumentException("请先选择");
		}
		//基于id进行子菜单查询
		int count = sysMenuDao.getChildCount(id);
		if(count>0)
			throw new ServiceException("请先删除子菜单");
		//删除菜单元素
		int rows = sysMenuDao.deleteObject(id);
		if(rows==0)
			throw new ServiceException("此菜单不存在");
		//删除菜单角色关系数据
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		return rows;
	}
	//查询菜单树
	@Override
	public List<Node> findZtreeMenuNodes() {
		//验证数据合法性。。
		//查询数据
		List<Node> list = sysMenuDao.findZtreeMenuNodes();
		return list;
	}
	
	//添加菜单
	@Override
	public int insertObject(SysMenu entity) {
		//合法验证
		if(entity==null) {
			throw new IllegalArgumentException("保存对象不能为空");
		}
		if(StringUtils.isEmpty(entity.getName())) {
			throw new ServiceException("菜单名不能为空");
		}
		int rows;
		try {
		rows=sysMenuDao.insertObject(entity);
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		//返回数据
		return rows;
	}
	
	//修改菜单
	@Override
	public int updateObject(SysMenu entity) {
		if(entity==null) {
			throw new IllegalArgumentException("保存对象不能为空");
		}
		if(StringUtils.isEmpty(entity.getName())) {
			throw new ServiceException("菜单名不能为空");
		}
		int row=sysMenuDao.updateObject(entity);
		if(row==0)
			throw new ServiceException("记录不存在");
		return row;
	}
	
	

}
