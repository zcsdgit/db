package com.zcs.pj.common.vo;

import java.io.Serializable;

import lombok.Data;
	/**
	 * 	为业务层返回的数据添加状态信息
	 * 	1.正常数据
	 * 	2.异常数据
	 * @author UID
	 *
	 */
@Data	//自动添加getter、setter方法
//@NoArgsConstructor 自动为类添加无参构造
public class JsonResult implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**状态码*/
	private int state=1;//1表示SUCCESS,0表示ERROR
	/**状态信息*/
	private String message="ok";
	/**正确数据*/
	private Object data;
	
	public JsonResult() {}
	
	public JsonResult(String message){
		this.message=message;
	}
	/**有参构造，一般查询时调用，封装查询结果*/
	public JsonResult(Object data) {
		this.data=data;
	}
	/**出现异常时时调用*/
	public JsonResult(Throwable t){
		this.state=0;
		this.message=t.getMessage();
	}


}
