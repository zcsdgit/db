package com.zcs.pj.common.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class Node implements Serializable{
	/**
	 * 用来封装数据库查询出来的菜单信息
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Integer parentId;
}
