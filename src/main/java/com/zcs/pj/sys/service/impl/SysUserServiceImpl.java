package com.zcs.pj.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zcs.pj.common.annotation.RequiredLog;
import com.zcs.pj.common.exception.ServiceException;
import com.zcs.pj.common.utils.PageQueryUtils;
import com.zcs.pj.common.vo.PageObject;
import com.zcs.pj.common.vo.SysUserDeptVo;
import com.zcs.pj.sys.dao.SysUserDao;
import com.zcs.pj.sys.dao.SysUserRoleDao;
import com.zcs.pj.sys.entity.SysUser;
import com.zcs.pj.sys.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;

	/* 查询用户分页数据 */
	@Override
	@RequiredLog("query user")
	public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {

		// 1.数据合法性验证
		PageQueryUtils.checkPageCurrent(pageCurrent);
		// 2.依据条件获取总记录数
		int rowCount = sysUserDao.getRowCount(username);
		PageQueryUtils.checkRowCount(rowCount);
		// 3.计算startIndex的值
		int pageSize = 3;
		int startIndex = PageQueryUtils.getStartIndex(pageCurrent, pageSize);
		// 4.依据条件获取当前页数据
		List<SysUserDeptVo> records = sysUserDao.findPageObjects(username, startIndex, pageSize);
		// 封装数据
		PageObject<SysUserDeptVo> result = PageQueryUtils.getPageQuery(username, pageCurrent, pageSize, rowCount,
				records);

		return result;
	}

	// 禁用
	/**
	 * 当方法上@RequiresPermissions注解时 此方法需要进行权限控制(授权以后才可以访问)
	 */
	@RequiresPermissions("sys:user:valid")
	@Override
	public int validById(Integer id, Integer valid, String modifiedUser) {

		// 1.合法性验证
		if (id == null || id <= 0)
			throw new ServiceException("参数不合法,id=" + id);
		if (valid != 1 && valid != 0)
			throw new ServiceException("参数不合法,valie=" + valid);
		if (StringUtils.isEmpty(modifiedUser))
			throw new ServiceException("修改用户不能为空");
		// 2.执行禁用或启用操作
		int rows = 0;
		try {
			rows = sysUserDao.validById(id, valid, modifiedUser);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("底层正在维护");
		}
		// 3.判定结果,并返回
		if (rows == 0)
			throw new ServiceException("此记录可能已经不存在");

		return rows;
	}

	// 添加数据
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {

		// 1.验证数据合法性
		if (entity == null)
			throw new ServiceException("保存对象不能为空");
		if (StringUtils.isEmpty(entity.getUsername()))
			throw new ServiceException("用户名不能为空");
		if (StringUtils.isEmpty(entity.getPassword()))
			throw new ServiceException("密码不能为空");
		if (roleIds == null || roleIds.length == 0)
			throw new ServiceException("至少要为用户分配角色");

		// 2.将数据写入数据库
		String salt = UUID.randomUUID().toString();
		entity.setSalt(salt);
		// 加密,使用此类需要添加shiro框架的依赖
		SimpleHash sHash = new SimpleHash("MD5", entity.getPassword(), salt, 1);
		entity.setPassword(sHash.toHex());// 转16进制在赋值
		int rows = sysUserDao.insertObejct(entity);
		// 返回结果
		return rows;
	}

	// 修改页面数据呈现
	@Override
	public Map<String, Object> findObjectById(Integer userId) {

		if (userId == null || userId <= 0)
			throw new ServiceException("参数数据不合法,userId=" + userId);
		// 2.业务查询
		SysUserDeptVo user = sysUserDao.findObjectById(userId);
		if (user == null)
			throw new ServiceException("此用户已经不存在");
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(userId);
		// 3.数据封装
		Map<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);

		return map;
	}

//更新用户数据
	@Override
	public int updateObject(SysUser entity, Integer[] roleIds) {
		// 1.参数有效性验证
		if (entity == null)
			throw new IllegalArgumentException("保存对象不能为空");
		if (StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		if (roleIds == null || roleIds.length == 0)
			throw new IllegalArgumentException("必须为其指定角色");
		// 其它验证自己实现，例如用户名已经存在，密码长度，...
		// 2.更新用户自身信息

		int rows = sysUserDao.updateObject(entity);
		sysUserRoleDao.deleteObjectsByUserId(entity.getId());
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		return rows;
	}

	/**
	 * 密码加密
	 * 
	 * @param password    原密码
	 * @param newPassword 新密码(还没加密)
	 * @param cfgPassword 确认密码
	 */
	@Override
	public int updatePassword(String password, String newPassword, String cfgPassword) {
		// 判定新密码与确认密码是否相同
		if (StringUtils.isEmpty(newPassword))
			throw new IllegalArgumentException("新密码不能为空");
		if (StringUtils.isEmpty(cfgPassword))
			throw new IllegalArgumentException("确认密码不能为空");
		if (!newPassword.equals(cfgPassword))
			throw new IllegalArgumentException("两次输入密码不想等");
		// 判断原密码是否正确
		if (StringUtils.isEmpty(password))
			throw new IllegalArgumentException("原密码不能为空");
		// 获取登陆用户
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		SimpleHash sh = new SimpleHash("MD5", password, user.getSalt(), 1);
		if (!user.getPassword().equals(sh.toHex()))
			throw new IllegalArgumentException("原密码不正确");
		// 3.对新密码进行加密
		String salt = UUID.randomUUID().toString();
		sh = new SimpleHash("MD5", newPassword, salt, 1);
		// 4.将新密码加密以后的结果更新到数据库
		int rows = sysUserDao.updatePassword(sh.toHex(), salt, user.getId());
		if (rows == 0)
			throw new ServiceException("修改失败");
		
		return rows;
	}

}
