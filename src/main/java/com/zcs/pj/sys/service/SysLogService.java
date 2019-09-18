package com.zcs.pj.sys.service;

import com.zcs.pj.common.vo.PageObject;
import com.zcs.pj.sys.entity.SysLog;

public interface SysLogService {
	int deleteById(Integer id);
	/**返回值尽量不用map，因为可读性不好，而且值的类型不可控*/
	PageObject<SysLog> findPageObjects(String username,Integer pageCurrent,Integer pageSize);
	int deleteObjects(Integer[] ids);
}
