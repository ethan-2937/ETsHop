package com.etshop.entity.query;

import java.math.BigDecimal;


/**
 * 订单信息参数
 */
public class OrderInfoQuery extends BaseParam {


	/**
	 * 订单ID
	 */
	private String orderId;

	private String orderIdFuzzy;

	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 用户ID
	 */
	private String userId;

	private String userIdFuzzy;

	/**
	 * 订单创建时间
	 */
	private String orderTime;

	private String orderTimeStart;

	private String orderTimeEnd;

	/**
	 * -1已删除 0:待付款 1:已付款,待发货  2:已发货  3:已完成 4:已取消 5:已关闭 6:已退款 7:部分退款
	 */
	private Integer orderStatus;

	/**
	 * 支付通道
	 */
	private String payChannel;

	private String payChannelFuzzy;

	/**
	 * 支付场景
	 */
	private String payScene;

	private String paySceneFuzzy;

	/**
	 * 支付订单号
	 */
	private String payOrderId;

	private String payOrderIdFuzzy;

	/**
	 * 通道ID
	 */
	private String channelOrderId;

	private String channelOrderIdFuzzy;

	/**
	 * 评价状态 0:未评价  1:已评价  2:已追评
	 */
	private Integer commentStatus;


	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return this.orderId;
	}

	public void setOrderIdFuzzy(String orderIdFuzzy){
		this.orderIdFuzzy = orderIdFuzzy;
	}

	public String getOrderIdFuzzy(){
		return this.orderIdFuzzy;
	}

	public void setAmount(BigDecimal amount){
		this.amount = amount;
	}

	public BigDecimal getAmount(){
		return this.amount;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setUserIdFuzzy(String userIdFuzzy){
		this.userIdFuzzy = userIdFuzzy;
	}

	public String getUserIdFuzzy(){
		return this.userIdFuzzy;
	}

	public void setOrderTime(String orderTime){
		this.orderTime = orderTime;
	}

	public String getOrderTime(){
		return this.orderTime;
	}

	public void setOrderTimeStart(String orderTimeStart){
		this.orderTimeStart = orderTimeStart;
	}

	public String getOrderTimeStart(){
		return this.orderTimeStart;
	}
	public void setOrderTimeEnd(String orderTimeEnd){
		this.orderTimeEnd = orderTimeEnd;
	}

	public String getOrderTimeEnd(){
		return this.orderTimeEnd;
	}

	public void setOrderStatus(Integer orderStatus){
		this.orderStatus = orderStatus;
	}

	public Integer getOrderStatus(){
		return this.orderStatus;
	}

	public void setPayChannel(String payChannel){
		this.payChannel = payChannel;
	}

	public String getPayChannel(){
		return this.payChannel;
	}

	public void setPayChannelFuzzy(String payChannelFuzzy){
		this.payChannelFuzzy = payChannelFuzzy;
	}

	public String getPayChannelFuzzy(){
		return this.payChannelFuzzy;
	}

	public void setPayScene(String payScene){
		this.payScene = payScene;
	}

	public String getPayScene(){
		return this.payScene;
	}

	public void setPaySceneFuzzy(String paySceneFuzzy){
		this.paySceneFuzzy = paySceneFuzzy;
	}

	public String getPaySceneFuzzy(){
		return this.paySceneFuzzy;
	}

	public void setPayOrderId(String payOrderId){
		this.payOrderId = payOrderId;
	}

	public String getPayOrderId(){
		return this.payOrderId;
	}

	public void setPayOrderIdFuzzy(String payOrderIdFuzzy){
		this.payOrderIdFuzzy = payOrderIdFuzzy;
	}

	public String getPayOrderIdFuzzy(){
		return this.payOrderIdFuzzy;
	}

	public void setChannelOrderId(String channelOrderId){
		this.channelOrderId = channelOrderId;
	}

	public String getChannelOrderId(){
		return this.channelOrderId;
	}

	public void setChannelOrderIdFuzzy(String channelOrderIdFuzzy){
		this.channelOrderIdFuzzy = channelOrderIdFuzzy;
	}

	public String getChannelOrderIdFuzzy(){
		return this.channelOrderIdFuzzy;
	}

	public void setCommentStatus(Integer commentStatus){
		this.commentStatus = commentStatus;
	}

	public Integer getCommentStatus(){
		return this.commentStatus;
	}

}
