package com.etshop.mappers;

import com.etshop.entity.po.SysCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  数据库操作接口
 */
public interface SysCategoryMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 根据CategoryId更新
	 */
	 Integer updateByCategoryId(@Param("bean") T t,@Param("categoryId") String categoryId);


	/**
	 * 根据CategoryId删除
	 */
	 Integer deleteByCategoryId(@Param("categoryId") String categoryId);


	/**
	 * 根据CategoryId获取对象
	 */
	 T selectByCategoryId(@Param("categoryId") String categoryId);

	/**
	 * 根据父CategoryId查询最大排序
	 */
	 Integer selectMaxSort(@Param("pCategoryId") String pCategoryId);

	 /**
	 * 批量更新排序
	 */
	 Integer updateSortBatch(@Param("categoryList") List<SysCategory> sysCategoryList);
}
