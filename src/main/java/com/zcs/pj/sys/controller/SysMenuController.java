package com.zcs.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcs.pj.common.vo.JsonResult;
import com.zcs.pj.sys.entity.SysMenu;
import com.zcs.pj.sys.service.SysMenuService;

@Controller
public class SysMenuController {
	@Autowired
	private SysMenuService sysMenuService;
	//呈现菜单页面
	@RequestMapping("menu/doMenuUI")
	public String doMenuUI() {
		return "sys/menu_list";
	}
	//呈现菜单数据
	@RequestMapping("menu/doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects() {
		
		return new JsonResult(sysMenuService.finObjects());
	}
	//删除菜单
	@RequestMapping("menu/doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id) {
		//传递参数到service层
		sysMenuService.deleteObject(id);
		return new JsonResult("delete OK");
	}
	//跳转到菜单添加编辑页面
	@RequestMapping("menu/doMenuEditUI")
	public String doMenuEditUI() {
		return "sys/menu_edit";
	}
	//查询上级菜单信息
	@RequestMapping("menu/doFindZtreeMenuNodes")
	@ResponseBody
	public JsonResult doFindZtreeMenuNodes() {
		return new JsonResult(sysMenuService.findZtreeMenuNodes());
	}
	
	//添加菜单
	@RequestMapping("menu/doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysMenu entity) {
		sysMenuService.insertObject(entity);
		return new JsonResult("添加成功");
	}
	
	//修改菜单
	@RequestMapping("menu/doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysMenu entity) {
		sysMenuService.updateObject(entity);
		return new JsonResult("更新成功");
	}
	
}
