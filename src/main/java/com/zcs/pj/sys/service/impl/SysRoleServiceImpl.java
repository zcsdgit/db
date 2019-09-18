package com.zcs.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zcs.pj.common.exception.ServiceException;
import com.zcs.pj.common.utils.PageQueryUtils;
import com.zcs.pj.common.vo.CheckBox;
import com.zcs.pj.common.vo.PageObject;
import com.zcs.pj.common.vo.SysRoleMenuVo;
import com.zcs.pj.sys.dao.SysRoleDao;
import com.zcs.pj.sys.dao.SysRoleMenuDao;
import com.zcs.pj.sys.dao.SysUserRoleDao;
import com.zcs.pj.sys.entity.SysRole;
import com.zcs.pj.sys.service.SysRoleService;
@Service
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent, Integer pageSize) {
		
		//验证数据合法性
		//校验当前页是否正确
		PageQueryUtils.checkPageCurrent(pageCurrent);
		
		int rowCount = sysRoleDao.getRowCount(name);
		//校验总行数
		PageQueryUtils.checkRowCount(rowCount);
		int startIndex=PageQueryUtils.getStartIndex(pageCurrent, pageSize);
		//查询当前页要呈现记录
		List<SysRole> records = sysRoleDao.findPageObjects(name, startIndex, pageSize);
		//封装数据--将记录、总页数、当前页等等封装
		PageObject<SysRole> result = PageQueryUtils.getPageQuery(name, pageCurrent, pageSize, rowCount, records);
		return result;
	}
	//删除角色
	@Override
	public int deleteObject(Integer id) {
		//验证参数的合法性
		if(id==null||id<1) {
			throw new IllegalArgumentException("id不正确");
		}
		//执行删除(表之间如果没有物理关系，删除顺序可以随意，如果有，要先删除关系数据)
		int rows = sysRoleDao.deleteObject(id);
		if(rows==0)
			throw new ServiceException("数据不存在");
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		sysUserRoleDao.deleteObjectsByRoleId(id);
		return rows;
	}
	//保存角色相关信息
	@Override
	public int saveObject(SysRole entity, Integer[] menuIds) {

		//验证参数合法性
		if(entity==null)
			throw new IllegalArgumentException("保存数据不能为空");
		if(StringUtils.isEmpty(entity.getName()))
	    	throw new ServiceException("角色名不能为空");
		if(menuIds==null||menuIds.length==0)
			throw new IllegalArgumentException("必须为角色分配菜单");
		//保存数据
		int rows = sysRoleDao.insertObject(entity);
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		//返回结果		
		return rows;
	}
	
	//修改页面的数据的呈现
	@Override
	public SysRoleMenuVo findObjectById(Integer id) {

		//合法性验证
		if(id==null||id<0)
			throw new IllegalArgumentException("id不合法");
		//执行查询
		SysRoleMenuVo result = sysRoleDao.findObjectById(id);
		//验证结果并返回
		if(result==null)
			throw new ServiceException("此纪录不存在");
		return result;
	}
	
	//修改页面数据
	@Override
	public int updateObject(SysRole entity, Integer[] menuIds) {
		//合法性验证
		if(entity==null)
			throw new ServiceException("更新的对象不能为空");
		if(entity.getId()==null)
	    	throw new ServiceException("id的值不能为空");
	    	
	    	if(StringUtils.isEmpty(entity.getName()))
	    	throw new ServiceException("角色名不能为空");
	    	if(menuIds==null||menuIds.length==0)
	    	throw new ServiceException("必须为角色指定一个权限");
	    	//更新数据
	    	int rows = sysRoleDao.updateObject(entity);
	    	if(rows==0)
	    	    throw new ServiceException("对象可能已经不存在");
	    	//对于角色修改，因为存在修改角色、菜单关系的可能，所以先删除二者原本的关系数据，再重新插入新的关系数据
	    	sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
	    	sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
	    	//返回结果
		return rows;
	}
	
	//查询角色、id
		@Override
		public List<CheckBox> findObjects() {
			List<CheckBox> result = sysRoleDao.findObjects();
			return result;
		}

}
