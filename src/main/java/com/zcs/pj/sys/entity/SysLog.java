package com.zcs.pj.sys.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;
/*
 *	借助此对象封装用户的行为日志信息
 *	pojo普通的java对象
 *	po (持久化对象) 
 *	vo (值对象)
 *	...
 */
@Data	//可以省略setter、getter方法
@ToString	//自动生成tostring方法
public class SysLog implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**用户名*/
	private String username;
	/**用户操作*/
	private String operation;
	/**请求方法*/
	private String method;
	/**请求参数*/
	private String params;
	/**执行时长(毫秒)*/
	private Long time;
	/**IP地址*/
	private String ip;
	/**创建时间*/
	private Date createdTime;
	
	
	
}
