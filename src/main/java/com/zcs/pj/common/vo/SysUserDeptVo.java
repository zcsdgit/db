package com.zcs.pj.common.vo;

import java.io.Serializable;
import java.util.Date;

import com.zcs.pj.sys.entity.SysDept;

import lombok.Data;
@Data
public class SysUserDeptVo implements Serializable {

	/**
	 * 	定义值对象封装查询时获取的用户与部门相关信息
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String username;
	private String password;//md5
	private String salt;
	private String email;
	private String mobile;
	private Integer valid=1;
	private SysDept sysDept; //private Integer deptId;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;


}
