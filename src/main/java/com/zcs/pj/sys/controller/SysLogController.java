package com.zcs.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcs.pj.common.vo.JsonResult;
import com.zcs.pj.common.vo.PageObject;
import com.zcs.pj.sys.entity.SysLog;
import com.zcs.pj.sys.service.SysLogService;


@Controller
public class SysLogController {
	@Autowired
	private SysLogService service;
	@RequestMapping("hello")
	@ResponseBody
	public String doDeleteLog(Integer id) {
		int row = service.deleteById(id);
		System.out.println(row);
		return "delete ok";
	}
	@RequestMapping("doIndexUI")
	public String UI() {
		return "starter";
	}
	//呈现日志页面
	@RequestMapping("log/doLogListUI")
	public String doLogListUI() {
		return "sys/log_list";
	}
	//载入分页条
	@RequestMapping("doPageUI")
	public String doPageUI() {
		return "common/page";
	}
	@RequestMapping("doLoginUI")
	public String doLoginUI(){
			return "login";
	}

	@RequestMapping("log/doFindPageObjects")
	@ResponseBody
	public JsonResult xx(String username,Integer pageCurrent,Integer pageSize){
		if(pageSize==null)
			pageSize=3;
		PageObject<SysLog> pageObject = service.findPageObjects(username, pageCurrent, pageSize);
		return new JsonResult(pageObject);
	}
	
	@RequestMapping("log/doDeleteObjects")
	@ResponseBody
	public JsonResult doDeleteObjects(Integer... ids) {
		service.deleteObjects(ids);
		return new JsonResult("delete ok");
	}
}
