package com.etshop.mappers;

import org.apache.ibatis.annotations.Param;

public interface SysProductPropertyMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 根据PropertyId更新
	 */
	 Integer updateByPropertyId(@Param("bean") T t, @Param("propertyId") String propertyId);


	/**
	 * 根据PropertyId删除
	 */
	 Integer deleteByPropertyId(@Param("propertyId") String propertyId);


	/**
	 * 根据PropertyId获取对象
	 */
	 T selectByPropertyId(@Param("propertyId") String propertyId);

	/**
	 * 根据父CategoryId查询最大排序
	 */
	Integer selectMaxSort(@Param("CategoryId") String categoryId);


}
