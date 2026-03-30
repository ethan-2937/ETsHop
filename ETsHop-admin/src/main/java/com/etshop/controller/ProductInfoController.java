package com.etshop.controller;

import com.etshop.entity.dto.ProductSaveDto;
import com.etshop.entity.po.ProductInfo;
import com.etshop.entity.query.ProductInfoQuery;
import com.etshop.entity.vo.ResponseVO;
import com.etshop.service.ProductInfoService;
import com.etshop.valid.CreateGroup;
import com.etshop.valid.UpdateGroup;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品信息 Controller
 */
@RestController("productInfoController")
@RequestMapping("/productInfo")
public class ProductInfoController extends ABaseController{

	@Resource
	private ProductInfoService productInfoService;
	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadProduct")
	public ResponseVO loadDataList(String productNameFuzzy,Integer pageNo,String categoryIdOrPCategoryId,Integer commendType){
		ProductInfoQuery query = new ProductInfoQuery();
		query.setProductNameFuzzy(productNameFuzzy);
		query.setPageNo(pageNo);
		query.setCommendType(commendType);
		query.setCategoryIdOrPCategoryId(categoryIdOrPCategoryId);
		query.setOrderBy("p.create_time desc");
		return getSuccessResponseVO(productInfoService.findListByPage4ListVO(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/addProduct")
	public ResponseVO addProduct(@RequestBody @Validated(CreateGroup.class) ProductSaveDto productSaveDto) {
		productInfoService.saveProduct(productSaveDto);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据ProductId查询对象
	 */
	@RequestMapping("/getProductInfo")
	public ResponseVO getProductInfo(@NotEmpty String productId){
		return getSuccessResponseVO(productInfoService.getProductInfo(productId));
	}
	/**
	 * 更新
	 */
	@RequestMapping("/updateProduct")
	public ResponseVO updateProduct(@RequestBody @Validated(UpdateGroup.class) ProductSaveDto productSaveDto){
		productInfoService.saveProduct(productSaveDto);
		return getSuccessResponseVO(null);
	}
}