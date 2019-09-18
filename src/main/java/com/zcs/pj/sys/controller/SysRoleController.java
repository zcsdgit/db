package com.zcs.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcs.pj.common.vo.JsonResult;
import com.zcs.pj.common.vo.PageObject;
import com.zcs.pj.sys.entity.SysRole;
import com.zcs.pj.sys.service.SysRoleService;

@Controller
@RequestMapping("/role/")
public class SysRoleController {
	@Autowired
	private SysRoleService sysRoleService;
	
	@RequestMapping("doRoleListUI")
	public String doRoleListUI() {
		return "sys/role_list";
	}
	
	@RequestMapping("doPageUI")
	public String doPageUI() {
		return "common/page";
	}
	
	//角色分页
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String name,Integer pageCurrent,Integer pageSize) {
		if(pageSize==null)
			pageSize=3;
		PageObject<SysRole> result = sysRoleService.findPageObjects(name, pageCurrent, pageSize);
		return new JsonResult(result);
	}
	
	//删除角色
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id) {
		sysRoleService.deleteObject(id);
		return new JsonResult("删除成功");
	}
	
	//添加角色
	@RequestMapping("doRoleEditUI")
	public String doRoleEditUI() {
		
		return "sys/role_edit";
	}
	
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysRole entity,Integer[] menuIds) {
		sysRoleService.saveObject(entity, menuIds);
		return new JsonResult("添加成功");
	}
	
	//呈现修改页面的数据
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		return new JsonResult(sysRoleService.findObjectById(id));
	}
	//更新数据
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysRole entity,Integer[] menuIds) {
		sysRoleService.updateObject(entity, menuIds);
		return new JsonResult("更新成功");
	}
	
	//查询角色id、及角色名
		@RequestMapping("doFindRoles")
		@ResponseBody
		public JsonResult doFindObjects() {
			return new JsonResult(sysRoleService.findObjects());
		}
}
