package com.zcs.pj.common.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
	/**
	 * 	通过此对象封装角色及角色对应的菜单id
	 * @author UID
	 *
	 */
@Data
public class SysRoleMenuVo implements Serializable {

	private static final long serialVersionUID = 1L;

	//角色id
	private Integer id;
	/*角色名称*/
	private String name;
	/*角色备注*/
	private String note;
	/*角色对应的菜单id*/
	private List<Integer> menuIds;
}
