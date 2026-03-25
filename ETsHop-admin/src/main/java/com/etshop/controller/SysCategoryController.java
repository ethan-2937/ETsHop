package com.etshop.controller;

import com.etshop.entity.po.SysCategory;
import com.etshop.entity.query.SysCategoryQuery;
import com.etshop.entity.vo.ResponseVO;
import com.etshop.service.SysCategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  Controller
 */
@RestController("sysCategoryController")
@RequestMapping("/sysCategory")
public class SysCategoryController extends ABaseController{

	@Resource
	private SysCategoryService sysCategoryService;
	/**
	 * 查询分类树
	 */
	@RequestMapping("/loadCategory")
	public ResponseVO loadCategory(){
		SysCategoryQuery query = new SysCategoryQuery();
		query.setConvert2Tree(true);
		query.setOrderBy("s.sort asc");
		return getSuccessResponseVO(sysCategoryService.findListByParam(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/saveCategory")
	public ResponseVO saveCategory(SysCategory bean) {
		sysCategoryService.saveCategory(bean);
		return getSuccessResponseVO(null);
	}


	/**
	 * 根据CategoryId删除
	 */
	@RequestMapping("/delCategory")
	public ResponseVO delCategory(String categoryId) {
		sysCategoryService.delCategory(categoryId);
		return getSuccessResponseVO(null);
	}

	@RequestMapping("/changeCategorySort")
	public ResponseVO changeCategorySort(String categoryIds) {
		sysCategoryService.changeCategorySort(categoryIds);
		return getSuccessResponseVO(null);
	}
}