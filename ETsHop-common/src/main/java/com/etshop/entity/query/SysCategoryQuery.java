package com.etshop.entity.query;



/**
 * 参数
 */
public class SysCategoryQuery extends BaseParam {


	/**
	 * 
	 */
	private String categoryId;

	private String categoryIdFuzzy;

	/**
	 * 
	 */
	private String categoryName;

	private String categoryNameFuzzy;

	/**
	 * 
	 */
	private String pCategoryId;

	private String pCategoryIdFuzzy;

	/**
	 * 
	 */
	private Integer sort;

	private String categoryOrPCategory;
	public void setCategoryOrPCategory(String categoryOrPCategory){
		this.categoryOrPCategory = categoryOrPCategory;
	}

	public String getCategoryOrPCategory(){
		return this.categoryOrPCategory;
	}


	/**
	 * 是否转换为树结构
	 */
	private boolean convert2Tree;
	public void setConvert2Tree(boolean convert2Tree){
		this.convert2Tree = convert2Tree;
	}

	public boolean getConvert2Tree(){
		return this.convert2Tree;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return this.categoryId;
	}

	public void setCategoryIdFuzzy(String categoryIdFuzzy){
		this.categoryIdFuzzy = categoryIdFuzzy;
	}

	public String getCategoryIdFuzzy(){
		return this.categoryIdFuzzy;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return this.categoryName;
	}

	public void setCategoryNameFuzzy(String categoryNameFuzzy){
		this.categoryNameFuzzy = categoryNameFuzzy;
	}

	public String getCategoryNameFuzzy(){
		return this.categoryNameFuzzy;
	}

	public void setpCategoryId(String pCategoryId){
		this.pCategoryId = pCategoryId;
	}

	public String getpCategoryId(){
		return this.pCategoryId;
	}

	public void setpCategoryIdFuzzy(String pCategoryIdFuzzy){
		this.pCategoryIdFuzzy = pCategoryIdFuzzy;
	}

	public String getpCategoryIdFuzzy(){
		return this.pCategoryIdFuzzy;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return this.sort;
	}

}
