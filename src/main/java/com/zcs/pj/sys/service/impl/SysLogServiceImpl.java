package com.zcs.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zcs.pj.common.exception.ServiceException;
import com.zcs.pj.common.utils.PageQueryUtils;
import com.zcs.pj.common.vo.PageObject;
import com.zcs.pj.sys.dao.SysLogDao;
import com.zcs.pj.sys.entity.SysLog;
import com.zcs.pj.sys.service.SysLogService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SysLogServiceImpl implements SysLogService {
	@Autowired
	private SysLogDao sysLogDao;
	@Override
	public int deleteById(Integer id) {
		log.info("动吧日志：");
		return sysLogDao.deleteById(id);
	}
	@Override
	public PageObject<SysLog> findPageObjects(String username, Integer pageCurrent,Integer pageSize) {
		
		//1.参数校验
//		if(pageCurrent==null||pageCurrent<1) {
//			throw new IllegalArgumentException("页码不正确");
//		}
		PageQueryUtils.checkPageCurrent(pageCurrent);
//		//2.查询总记录数并进行校验
		int rowCount = sysLogDao.getRowCount(username);
		PageQueryUtils.checkRowCount(rowCount);
//		if(rowCount==0) {
//			throw new ServiceException("记录不存在");
//		}
//		//3.查询当前页要呈现的记录
		int startIndex=PageQueryUtils.getStartIndex(pageCurrent, pageSize);//当前页起始位置
		List<SysLog> records = sysLogDao.findPageObjects(username, startIndex, pageSize);
//		//4.对查询结果进行计算和封装
//		PageObject<SysLog> po = new PageObject<>();
//		po.setRowCount(rowCount);
//		po.setRecords(records);
//		po.setPageSize(pageSize);
//		po.setPageCount((rowCount-1)/pageSize+1);//总页数，
//		po.setPageCurrent(pageCurrent);
//		//5。返回结果
		PageObject<SysLog> result = PageQueryUtils.getPageQuery(username, pageCurrent, pageSize, rowCount, records);
		
		return result;
	}
	//删除多个数据
	@Override
	public int deleteObjects(Integer[] ids) {
		//校验参数合法性
		if(ids==null||ids.length==0) {
			throw new IllegalArgumentException("至少选择一个");
		}
		//执行删除操作
		int rows ;
		try {
		 rows = sysLogDao.deleteObjects(ids);
		}catch(Throwable e) {
			e.printStackTrace();
			throw new ServiceException("系统故障....");
		}
		//4.对结果进行验证
				if(rows==0)
				throw new ServiceException("记录可能已经不存在");

		return rows;
	}

}
