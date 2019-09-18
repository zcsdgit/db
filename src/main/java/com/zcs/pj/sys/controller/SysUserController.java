package com.zcs.pj.sys.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcs.pj.common.vo.JsonResult;
import com.zcs.pj.common.vo.PageObject;
import com.zcs.pj.common.vo.SysUserDeptVo;
import com.zcs.pj.sys.entity.SysUser;
import com.zcs.pj.sys.service.SysUserService;

@Controller
@RequestMapping("/user/")
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("doUserListUI")
	public String doUserListUI() {
		return "sys/user_list";
	}
	//分页数据
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username,Integer pageCurrent) {
		PageObject<SysUserDeptVo> result = sysUserService.findPageObjects(username, pageCurrent);
		return new JsonResult(result);
	}
	
	//禁用
	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(Integer id,Integer valid) {
		sysUserService.validById(id, valid, "admin");
		return new JsonResult("更新成功");
	}
	//跳转到添加页面
	@RequestMapping("doUserEditUI")
	public String doUserEditUI() {
		return "sys/user_edit";
	}
	
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysUser entity,Integer[] roleIds) {
		sysUserService.saveObject(entity, roleIds);
		return new JsonResult("添加成功");
	}
	//修改页面数据呈现
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {//注意参数名一定要和提交的参数名一致，否则接收不到参数
		Map<String, Object> map = sysUserService.findObjectById(id);
		return new JsonResult(map);
	}
	//更新修改后的数据
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysUser entity,Integer[] roleIds){
		sysUserService.updateObject(entity, roleIds);
		return new JsonResult("update ok");
	}
	
	//认证
	 @RequestMapping("doLogin")
	   @ResponseBody
	public JsonResult doLogin(String username,String password) {
		//封装用户信息
		UsernamePasswordToken token=new UsernamePasswordToken(username, password);
		//传递token对象给SecurityManager
		Subject subject = SecurityUtils.getSubject();
		//对用户信息进行身份认证
		subject.login(token);
		//分析:
		   //1)token会传给shiro的SecurityManager
		   //2)SecurityManager将token传递给认证管理器
		   //3)认证管理器会将token传递给realm
		return new JsonResult("login ok");
	}
	
	 //修改密码
	 @RequestMapping("doUpdatePassword")
	 @ResponseBody
	 public JsonResult doUpdatePassword(String pwd,String newPwd,String cfgPwd) {
		 sysUserService.updatePassword(pwd, newPwd, cfgPwd);
		 return new JsonResult("update ok");
	 }
	 
	 @RequestMapping("doPwdEditUI")
	 public String doPwdEditUI() {
		 return "sys/pwd_edit";
	 }
}
