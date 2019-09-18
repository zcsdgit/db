package com.zcs.pj.common.utils;

import java.util.List;

import com.zcs.pj.common.exception.ServiceException;
import com.zcs.pj.common.vo.PageObject;

public class PageQueryUtils {
	//起始位置
	public static int getStartIndex(Integer pageCurrent,Integer pageSize) {
		return (pageCurrent-1)*pageSize;
	}
	//分页记录
	public static <T>PageObject<T> getPageQuery(String username, Integer pageCurrent,Integer pageSize,Integer rowCount,List<T> records) {
		
		
		//4.对查询结果进行计算和封装
		PageObject<T> po = new PageObject<>();
		po.setRowCount(rowCount);
		po.setRecords(records);
		po.setPageSize(pageSize);
		po.setPageCount((rowCount-1)/pageSize+1);//总页数，
		po.setPageCurrent(pageCurrent);
		//5。返回结果
		return po;
	}
	//校验当前页是否正确
	public static void checkPageCurrent(Integer pageCurrent) {
		//1.参数校验
		if(pageCurrent==null||pageCurrent<1) {
				throw new IllegalArgumentException("页码不正确");
			}
		
	}
	//校验总行数
	public static void checkRowCount(Integer rowCount) {
		//2.查询总记录数并进行校验
				if(rowCount==0) {
					throw new ServiceException("记录不存在");
				}
	}
}
