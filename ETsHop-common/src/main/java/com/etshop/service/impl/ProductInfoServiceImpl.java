package com.etshop.service.impl;

import com.etshop.entity.constants.Constants;
import com.etshop.entity.dto.ProductSaveDto;
import com.etshop.entity.enums.PageSize;
import com.etshop.entity.enums.ProductStatusEnum;
import com.etshop.entity.enums.ResponseCodeEnum;
import com.etshop.entity.po.ProductInfo;
import com.etshop.entity.po.ProductPropertyValue;
import com.etshop.entity.po.ProductSku;
import com.etshop.entity.po.SysCategory;
import com.etshop.entity.query.*;
import com.etshop.entity.vo.*;
import com.etshop.exception.BusinessException;
import com.etshop.mappers.ProductInfoMapper;
import com.etshop.mappers.ProductPropertyValueMapper;
import com.etshop.mappers.ProductSkuMapper;
import com.etshop.service.ProductInfoService;
import com.etshop.service.SysCategoryService;
import com.etshop.utils.CopyTools;
import com.etshop.utils.StringTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 商品信息 业务接口实现
 */
@Service("productInfoService")
public class ProductInfoServiceImpl implements ProductInfoService {

	@Resource
	private ProductInfoMapper<ProductInfo, ProductInfoQuery> productInfoMapper;

	@Resource
	private ProductSkuMapper<ProductSku, ProductSkuQuery> productSkuMapper;

	@Resource
	private ProductPropertyValueMapper<ProductPropertyValue, ProductPropertyValueQuery> productPropertyValueMapper;

	@Resource
	private SysCategoryService sysCategoryService;


	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<ProductInfo> findListByParam(ProductInfoQuery param) {
		return this.productInfoMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(ProductInfoQuery param) {
		return this.productInfoMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<ProductInfo> findListByPage(ProductInfoQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<ProductInfo> list = this.findListByParam(param);
		PaginationResultVO<ProductInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(ProductInfo bean) {
		return this.productInfoMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<ProductInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.productInfoMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<ProductInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.productInfoMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(ProductInfo bean, ProductInfoQuery param) {
		StringTools.checkParam(param);
		return this.productInfoMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(ProductInfoQuery param) {
		StringTools.checkParam(param);
		return this.productInfoMapper.deleteByParam(param);
	}

	/**
	 * 根据ProductId获取对象
	 */
	@Override
	public ProductInfo getProductInfoByProductId(String productId) {
		return this.productInfoMapper.selectByProductId(productId);
	}

	/**
	 * 根据ProductId修改
	 */
	@Override
	public Integer updateProductInfoByProductId(ProductInfo bean, String productId) {
		return this.productInfoMapper.updateByProductId(bean, productId);
	}

	/**
	 * 根据ProductId删除
	 */
	@Override
	public Integer deleteProductInfoByProductId(String productId) {
		return this.productInfoMapper.deleteByProductId(productId);
	}

    @Override
	@Transactional(rollbackFor = Exception.class)
    public void saveProduct(ProductSaveDto productSaveDto) {
		ProductInfo productInfo = productSaveDto.getProductInfo();
		List<ProductPropertyValue> productPropertyValueList = productSaveDto.getProductPropertyList();
		List<ProductSku> skuList = productSaveDto.getSkuList();

		Boolean isAdd = StringTools.isEmpty(productInfo.getProductId());
		if (isAdd) {
			productInfo.setProductId(StringTools.getRandomNumber(Constants.LENGTH_15));
		}
		productPropertyValueList.forEach(p -> p.setProductId(productInfo.getProductId()));
		skuList.forEach(p -> p.setProductId(productInfo.getProductId()));
		//不接受页面传入的状态
		productInfo.setStatus(null);
		productInfo.setCommendType(null);
		Optional<ProductSku> minPrice = skuList.stream().min(Comparator.comparing(ProductSku::getPrice));
		Optional<ProductSku> maxPrice = skuList.stream().max(Comparator.comparing(ProductSku::getPrice));

		productInfo.setMinPrice(minPrice.get().getPrice());
		productInfo.setMaxPrice(maxPrice.get().getPrice());
		//新增
		if (isAdd) {
			productInfo.setCreateTime(new Date());
			productInfo.setStatus(ProductStatusEnum.OFF_SALE.getStatus());
			productInfoMapper.insert(productInfo);
			productPropertyValueMapper.insertBatch(productPropertyValueList);
			productSkuMapper.insertBatch(skuList);
		} else {
			ProductPropertyValueQuery productPropertyValueQuery = new ProductPropertyValueQuery();
			productPropertyValueQuery.setProductId(productInfo.getProductId());
			List<ProductPropertyValue> dbProductPropertyValueList = productPropertyValueMapper.selectList(productPropertyValueQuery);
			Map<String, ProductPropertyValue> dbProductPropertyValueMap = dbProductPropertyValueList.stream().
					collect(Collectors.toMap(ProductPropertyValue::getPropertyValueId, item -> item));
			List<ProductPropertyValue> productPropertyValueAddList = new ArrayList<>();
			List<ProductPropertyValue> productPropertyValueUpdateList = new ArrayList<>();
			List<ProductPropertyValue> productPropertyValueDeleteList = new ArrayList<>();
			//参数有，数据库没有，新增，两个都有，修改
			for (ProductPropertyValue item : productPropertyValueList) {
				if (dbProductPropertyValueMap.get(item.getPropertyValueId()) == null) {
					productPropertyValueAddList.add(item);
				} else {
					productPropertyValueUpdateList.add(item);
				}
			}
			//参数没有，数据库有，删除
			Map<String, ProductPropertyValue> productPropertyValueMap = productPropertyValueList.stream()
					.collect(Collectors.toMap(ProductPropertyValue::getPropertyValueId, item -> item));
			for (ProductPropertyValue item : dbProductPropertyValueList) {
				if (productPropertyValueMap.get(item.getPropertyValueId()) == null) {
					productPropertyValueDeleteList.add(item);
				}
			}
			//获取sku中新增，修改，删除
			List<ProductSku> productSkuAddList = new ArrayList<>();
			List<ProductSku> productSkuUpdateList = new ArrayList<>();
			List<ProductSku> productSkuDeleteList = new ArrayList<>();

			ProductSkuQuery productSkuQuery = new ProductSkuQuery();
			productSkuQuery.setProductId(productInfo.getProductId());
			List<ProductSku> dbProductSkuList = productSkuMapper.selectList(productSkuQuery);
			Map<String, ProductSku> dbProductSkuMap = dbProductSkuList.stream().
					collect(Collectors.toMap(ProductSku::getPropertyValueIdHash, item -> item));
			for (ProductSku item : skuList) {
				if (dbProductSkuMap.get(item.getPropertyValueIdHash()) == null) {
					productSkuAddList.add(item);
				} else {
					productSkuUpdateList.add(item);
				}
			}
			//参数没有，数据库有，删除
			Map<String, ProductSku> productSkuMap = skuList.stream().
					collect(Collectors.toMap(ProductSku::getPropertyValueIdHash, item -> item));
			for (ProductSku item : dbProductSkuList) {
				if (productSkuMap.get(item.getPropertyValueIdHash()) == null) {
					productSkuDeleteList.add(item);
				}
			}
			//不能修改的内容
			productInfo.setCategoryId(null);
			productInfo.setpCategoryId(null);
			productInfo.setStatus(null);
			productInfoMapper.updateByProductId(productInfo, productInfo.getProductId());

			if (!productPropertyValueAddList.isEmpty()) {
				productPropertyValueMapper.insertBatch(productPropertyValueAddList);
			}
			if (!productPropertyValueUpdateList.isEmpty()) {
				productPropertyValueMapper.updateBatch(productInfo.getProductId(), productPropertyValueUpdateList);
			}
			if (!productPropertyValueDeleteList.isEmpty()) {
				productPropertyValueMapper.deleteBatch(productInfo.getProductId(), productPropertyValueDeleteList);
			}
			//sku
			if (!productSkuAddList.isEmpty()){
				productSkuMapper.insertBatch(productSkuAddList);
			}
			if (!productSkuUpdateList.isEmpty()) {
				productSkuMapper.updateBatch(productInfo.getProductId(), productSkuUpdateList);
			}
			if (!productSkuDeleteList.isEmpty()){
				productSkuMapper.deleteBatch(productInfo.getProductId(), productSkuDeleteList);
			}
		}

		//TODO 将数据库信息写入elastic search 将商品数据向量化存储
	}
	@Override
	public PaginationResultVO<ProductListVO> findListByPage4ListVO(ProductInfoQuery param) {
		PaginationResultVO<ProductInfo> paginationResultVO = findListByPage(param);
		if(paginationResultVO.getTotalCount()==0){
			return new PaginationResultVO<>(new ArrayList<>());
		}
		List<ProductInfo> productInfoList = paginationResultVO.getList();
		// 查询分类
		SysCategoryQuery categoryQuery = new SysCategoryQuery();
		categoryQuery.setConvert2Tree(false);
		List<SysCategory> categoryList = sysCategoryService.findListByParam(categoryQuery);
		Map<String, SysCategory> categoryMap = categoryList.stream().collect(Collectors.toMap(item -> item.getCategoryId(), item -> item));

		// 查询sku
		List<String> productIdList = productInfoList.stream().map(ProductInfo::getProductId).collect(Collectors.toList());
		ProductSkuQuery productSkuQuery = new ProductSkuQuery();
		productSkuQuery.setProductIdList(productIdList);

		List<ProductSku> productSkuList = productSkuMapper.selectList(productSkuQuery);
		Map<String, List<ProductSku>> skuMap = productSkuList.stream().collect(Collectors.groupingBy(ProductSku::getProductId));

		// 组装VO
		List<ProductListVO> productListVOList = productInfoList.stream().map(productInfo -> {
			ProductListVO productListVO = CopyTools.copy(productInfo, ProductListVO.class);
			productListVO.setCategoryName(categoryMap.get(productInfo.getpCategoryId()).getCategoryName()+"/"+categoryMap.get(productInfo.getCategoryId()).getCategoryName());
			List<ProductSku> skuList = skuMap.get(productInfo.getProductId());
			productListVO.setSkuCount(skuList.size());
			productListVO.setTotalStock(skuList.stream().mapToInt(ProductSku::getStock).sum());
			return productListVO;
		}).collect(Collectors.toList());
		return new PaginationResultVO<>(paginationResultVO.getTotalCount(),
				paginationResultVO.getPageSize(),paginationResultVO.getPageNo(),paginationResultVO.getPageTotal()
				,productListVOList);
		}

	@Override
	public ProductInfoDetailVO getProductInfo(String productId) {
		ProductInfo productInfo = productInfoMapper.selectByProductId(productId);
		if(productInfo==null){
			throw new BusinessException(ResponseCodeEnum.CODE_600);
	}
		ProductPropertyValueQuery productPropertyValueQuery = new ProductPropertyValueQuery();
		productPropertyValueQuery.setProductId(productId);
		productPropertyValueQuery.setOrderBy("property_sort ASC");
		List<ProductPropertyValue> productPropertyValueList = productPropertyValueMapper.selectList(productPropertyValueQuery);

		List<ProductPropertyVO> productPropertyVOS = new ArrayList<>();
		Map<String,ProductPropertyVO> tempMap = new HashMap<>();
		for (ProductPropertyValue productPropertyValue : productPropertyValueList) {
				ProductPropertyVO productPropertyVO = tempMap.get(productPropertyValue.getPropertyId());
				ProductPropertyValueVO productPropertyValueVO = new ProductPropertyValueVO(productPropertyValue.getPropertyValueId()
				,productPropertyValue.getPropertyCover()
				,productPropertyValue.getPropertyValue()
				,productPropertyValue.getPropertyRemark()
			);
			if(productPropertyVO==null){
			productPropertyVO = new ProductPropertyVO(productPropertyValue.getPropertyId()
						,productPropertyValue.getPropertyName()
						,productPropertyValue.getPropertySort()
						,productPropertyValue.getCoverType()
			);
				tempMap.put(productPropertyValue.getPropertyId(),productPropertyVO);
				List<ProductPropertyValueVO> productPropertyValueVOList = new ArrayList<>();
				productPropertyValueVOList.add(productPropertyValueVO);
				productPropertyVO.setPropertyValues(productPropertyValueVOList);
				productPropertyVOS.add(productPropertyVO);
			}else {
				productPropertyVO.getPropertyValues().add(productPropertyValueVO);
			}
		}

		ProductSkuQuery productSkuQuery = new ProductSkuQuery();
		productSkuQuery.setProductId(productId);
		productSkuQuery.setOrderBy("sort ASC");
		List<ProductSku> productSkuList = productSkuMapper.selectList(productSkuQuery);

		ProductInfoDetailVO productInfoDetailVO = new ProductInfoDetailVO();
		productInfoDetailVO.setProductInfo(productInfo);
		productInfoDetailVO.setProductPropertyList(productPropertyVOS);
		productInfoDetailVO.setSkuList(productSkuList);
		return productInfoDetailVO;
	}
}