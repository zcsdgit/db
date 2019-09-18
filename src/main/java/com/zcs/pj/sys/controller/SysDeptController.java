package com.zcs.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcs.pj.common.vo.JsonResult;
import com.zcs.pj.sys.entity.SysDept;
import com.zcs.pj.sys.service.SysDeptService;


@Controller
@RequestMapping("/dept/")
public class SysDeptController {
	
	@Autowired
	private SysDeptService sysDeptService;
	//部门页面
	@RequestMapping("doDeptListUI")
	public String doDeptListUI(){
		return "sys/dept_list";
	}
	@RequestMapping("doDeptEditUI")
	public String doDeptEditUI(){
		return "sys/dept_edit";
	}
	//更新部门数据
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysDept entity){
		sysDeptService.updateObject(entity);
	    return new JsonResult("update ok");
	}
	//添加部门
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysDept entity){
		sysDeptService.saveObject(entity);
		return new JsonResult("save ok");
	}
	//呈现部门树
	@RequestMapping("doFindZTreeNodes")
	@ResponseBody
	public JsonResult doFindZTreeNodes(){
		return new JsonResult(
		sysDeptService.findZTreeNodes());
	}
	//删除部门
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id){
		sysDeptService.deleteObject(id);
		return new JsonResult("delete ok");
	}
	//呈现部门数据
	@RequestMapping("doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects(){
		return new JsonResult(sysDeptService.findObjects());
	}	
	
	
}
