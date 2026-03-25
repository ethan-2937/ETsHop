package com.etshop.service.impl;

import com.etshop.entity.constants.Constants;
import com.etshop.entity.enums.PageSize;
import com.etshop.entity.po.SysCategory;
import com.etshop.entity.query.SimplePage;
import com.etshop.entity.query.SysCategoryQuery;
import com.etshop.entity.vo.PaginationResultVO;
import com.etshop.mappers.SysCategoryMapper;
import com.etshop.service.SysCategoryService;
import com.etshop.utils.StringTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 *  业务接口实现
 */
@Service("sysCategoryService")
public class SysCategoryServiceImpl implements SysCategoryService {

	@Resource
	private SysCategoryMapper<SysCategory, SysCategoryQuery> sysCategoryMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<SysCategory> findListByParam(SysCategoryQuery param) {
		List<SysCategory> categoryList = this.sysCategoryMapper.selectList(param);
		if (param.getConvert2Tree()!=false && param.getConvert2Tree()) {
			categoryList = this.convert2Tree(categoryList, Constants.ZERO);
		}
		return categoryList;
	}

	/**
	 * 转换为树结构
	 */
	private List<SysCategory> convert2Tree(List<SysCategory> list,String pCategoryId){
		List<SysCategory> treeList = new ArrayList<>();
		for (SysCategory category : list) {
			if (category.getCategoryId()!=null&& category.getpCategoryId().equals(pCategoryId)) {
				category.setChildren(convert2Tree(list, category.getCategoryId()));
				treeList.add(category);
			}
		}
		return treeList;
	}

		/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(SysCategoryQuery param) {
		return this.sysCategoryMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<SysCategory> findListByPage(SysCategoryQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<SysCategory> list = this.findListByParam(param);
		PaginationResultVO<SysCategory> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(SysCategory bean) {
		return this.sysCategoryMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<SysCategory> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.sysCategoryMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<SysCategory> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.sysCategoryMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(SysCategory bean, SysCategoryQuery param) {
		StringTools.checkParam(param);
		return this.sysCategoryMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(SysCategoryQuery param) {
		StringTools.checkParam(param);
		return this.sysCategoryMapper.deleteByParam(param);
	}

	/**
	 * 根据CategoryId获取对象
	 */
	@Override
	public SysCategory getSysCategoryByCategoryId(String categoryId) {
		return this.sysCategoryMapper.selectByCategoryId(categoryId);
	}

	/**
	 * 根据CategoryId修改
	 */
	@Override
	public Integer updateSysCategoryByCategoryId(SysCategory bean, String categoryId) {
		return this.sysCategoryMapper.updateByCategoryId(bean, categoryId);
	}

	/**
	 * 根据CategoryId删除
	 */
	@Override
	public Integer deleteSysCategoryByCategoryId(String categoryId) {
		return this.sysCategoryMapper.deleteByCategoryId(categoryId);
	}

	@Override
	public void saveCategory(SysCategory bean) {
		if (bean.getCategoryId() == null) {
			bean.setCategoryId(StringTools.getRandomNumber(Constants.LENGTH_5));
			Integer maxSort = this.sysCategoryMapper.selectMaxSort(bean.getpCategoryId());
			bean.setSort(maxSort+1);
			this.sysCategoryMapper.insert(bean);
		}else {
			this.sysCategoryMapper.updateByCategoryId(bean, bean.getCategoryId());
		}
	}
	//TODO 分类存入缓存

	@Override
	public void delCategory(String categoryId) {
		SysCategoryQuery sysCategoryQuery = new SysCategoryQuery();
		sysCategoryQuery.setCategoryOrPCategory(categoryId);
		this.sysCategoryMapper.deleteByCategoryId(categoryId);
		//TODO 删除缓存
	}

	@Override
	public void changeCategorySort(String categoryIds) {
		String[] categoryIdsArray = categoryIds.split(",");
		List<SysCategory> categoryList = new ArrayList<>();
		Integer sort = 1;
		for (String categoryId : categoryIdsArray) {
			SysCategory sysCategory = new SysCategory();
				sysCategory.setCategoryId(categoryId);
				sysCategory.setSort(sort++);
				categoryList.add(sysCategory);
		}
		this.sysCategoryMapper.updateSortBatch(categoryList);
	}
}
