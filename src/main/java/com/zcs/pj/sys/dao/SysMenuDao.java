package com.zcs.pj.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.zcs.pj.common.vo.Node;
import com.zcs.pj.sys.entity.SysMenu;
@Mapper
//@CacheNamespace	//二级缓存
public interface SysMenuDao {
	//呈现菜单数据
	@Select("select c.*,p.name parentName from sys_menus c left join sys_menus p on c.parentId=p.id")
	List<Map<String, Object>> finObjects();
	//基于菜单id查询是否有子菜单
	@Select("select count(*) from sys_menus where parentId=#{id}")
	int getChildCount(Integer id);
	//根据菜单id删除菜单
	@Delete("delete from sys_menus where id=#{id}")
	int deleteObject(Integer id);
	//查询上级菜单信息
	@Select("select id,name,parentId from sys_menus")
	List<Node> findZtreeMenuNodes();
	//添加菜单
	@Insert("insert into sys_menus(name,url,type,sort,note,parentId,permission,createdTime,modifiedTime,createdUser,modifiedUser) values(#{name},#{url},#{type},#{sort},#{note},#{parentId},#{permission},now(),now(),#{createdUser},#{modifiedUser})")
	int insertObject(SysMenu entity);
	//更新菜单
	int updateObject(SysMenu entity);
	//中基于菜单id查找权限标识信息
	List<String> findPermissions(@Param("menuIds") Integer[] menuIds);

}
