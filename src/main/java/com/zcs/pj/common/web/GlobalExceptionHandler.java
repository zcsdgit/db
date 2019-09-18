package com.zcs.pj.common.web;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcs.pj.common.vo.JsonResult;

import lombok.extern.slf4j.Slf4j;

/**此注解修饰的类为一个全局异常处理类
	当控制层出现异常时：
	1.首先检测当前controller是否有异常处理方法
	2.其次检测全局异常处理类是否有异常处理方法
	注意：此类的路径要在启动类内，因为它检查controller的包结构 扫描启动类所在包或子包下的类
	
*/
@ControllerAdvice	
@Slf4j
public class GlobalExceptionHandler {
	/**
	 * 
	 * @param @ExceptionHandler 描述的方法为一个异常方法
	 * @return
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public JsonResult doHandleRuntimeException(RuntimeException e) {
		e.printStackTrace();	//给程序员看的
		log.error(e.getMessage());
		return new JsonResult(e);	//给客户端的
	}
	@ExceptionHandler(ShiroException.class)
	@ResponseBody
	public JsonResult doHandleShiroException(
			ShiroException e) {
		JsonResult r=new JsonResult();
		r.setState(0);
		if(e instanceof UnknownAccountException) {
			r.setMessage("账户不存在");
		}else if(e instanceof LockedAccountException) {
			r.setMessage("账户已被禁用");
		}else if(e instanceof IncorrectCredentialsException) {
			r.setMessage("密码不正确");
		}else if(e instanceof AuthorizationException) {
			r.setMessage("没有此操作权限");
		}else {
			r.setMessage("系统维护中");
		}
		e.printStackTrace();
		return r;
	}

}
