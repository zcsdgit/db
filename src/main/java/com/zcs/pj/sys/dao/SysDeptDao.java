package com.zcs.pj.sys.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.zcs.pj.common.vo.Node;
import com.zcs.pj.sys.entity.SysDept;
@Mapper
public interface SysDeptDao {
	int updateObject(SysDept entity);
	int insertObject(SysDept entity);
	//部门树
	List<Node> findZTreeNodes();
	List<Map<String,Object>> findObjects();
	int getChildCount(Integer id);
	int deleteObject(Integer id);
}
