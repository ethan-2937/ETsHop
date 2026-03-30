package com.etshop.service.impl;

import com.etshop.entity.enums.PageSize;
import com.etshop.entity.po.ProductSku;
import com.etshop.entity.query.ProductSkuQuery;
import com.etshop.entity.query.SimplePage;
import com.etshop.entity.vo.PaginationResultVO;
import com.etshop.mappers.ProductSkuMapper;
import com.etshop.service.ProductSkuService;
import com.etshop.utils.StringTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *  业务接口实现
 */
@Service("productSkuService")
public class ProductSkuServiceImpl implements ProductSkuService {

	@Resource
	private ProductSkuMapper<ProductSku, ProductSkuQuery> productSkuMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<ProductSku> findListByParam(ProductSkuQuery param) {
		return this.productSkuMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(ProductSkuQuery param) {
		return this.productSkuMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<ProductSku> findListByPage(ProductSkuQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<ProductSku> list = this.findListByParam(param);
		PaginationResultVO<ProductSku> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(ProductSku bean) {
		return this.productSkuMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<ProductSku> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.productSkuMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<ProductSku> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.productSkuMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(ProductSku bean, ProductSkuQuery param) {
		StringTools.checkParam(param);
		return this.productSkuMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(ProductSkuQuery param) {
		StringTools.checkParam(param);
		return this.productSkuMapper.deleteByParam(param);
	}

	/**
	 * 根据ProductIdAndPropertyValueIdHash获取对象
	 */
	@Override
	public ProductSku getProductSkuByProductIdAndPropertyValueIdHash(String productId, String propertyValueIdHash) {
		return this.productSkuMapper.selectByProductIdAndPropertyValueIdHash(productId, propertyValueIdHash);
	}

	/**
	 * 根据ProductIdAndPropertyValueIdHash修改
	 */
	@Override
	public Integer updateProductSkuByProductIdAndPropertyValueIdHash(ProductSku bean, String productId, String propertyValueIdHash) {
		return this.productSkuMapper.updateByProductIdAndPropertyValueIdHash(bean, productId, propertyValueIdHash);
	}

	/**
	 * 根据ProductIdAndPropertyValueIdHash删除
	 */
	@Override
	public Integer deleteProductSkuByProductIdAndPropertyValueIdHash(String productId, String propertyValueIdHash) {
		return this.productSkuMapper.deleteByProductIdAndPropertyValueIdHash(productId, propertyValueIdHash);
	}
}