package com.etshop.entity.po;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductSku implements Serializable {


	/**
	 * 商品ID
	 */
	private String productId;

	/**
	 * 属性值id组hash
	 */
	private String propertyValueIdHash;

	/**
	 * 属性值id组
	 */
	private String propertyValueIds;

	/**
	 * 价格
	 */
	private BigDecimal price;

	/**
	 * 库存
	 */
	private Integer stock;

	/**
	 * 排序
	 */
	private Integer sort;


	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return this.productId;
	}

	public void setPropertyValueIdHash(String propertyValueIdHash){
		this.propertyValueIdHash = propertyValueIdHash;
	}

	public String getPropertyValueIdHash(){
		return this.propertyValueIdHash;
	}

	public void setPropertyValueIds(String propertyValueIds){
		this.propertyValueIds = propertyValueIds;
	}

	public String getPropertyValueIds(){
		return this.propertyValueIds;
	}

	public void setPrice(BigDecimal price){
		this.price = price;
	}

	public BigDecimal getPrice(){
		return this.price;
	}

	public void setStock(Integer stock){
		this.stock = stock;
	}

	public Integer getStock(){
		return this.stock;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return this.sort;
	}

	@Override
	public String toString (){
		return "商品ID:"+(productId == null ? "空" : productId)+"，属性值id组hash:"+(propertyValueIdHash == null ? "空" : propertyValueIdHash)+"，属性值id组:"+(propertyValueIds == null ? "空" : propertyValueIds)+"，价格:"+(price == null ? "空" : price)+"，库存:"+(stock == null ? "空" : stock)+"，排序:"+(sort == null ? "空" : sort);
	}
}
