package com.boco.eoms.ruleproject.fujian.rulegateway.model;

import java.util.Date;

/**

* 创建时间：2019年6月26日 下午3:10:22

* 项目名称：central-flow-platform-rule

* @author ssh
* 类说明：
*/
public class CwmRuleGroupRel {
	//主键id
	private String id;
	//父节点id
	private String parentNodeId;
	//节点类型
	private String nodeType;
	//排序
	private int orderBy;
	//创建时间
	private Date createTime;
	//关联规则id
	private String ruleIdRel;
	//关联规则信息
	private String ruleInfoRel;

	public CwmRuleGroupRel() {
		super();
	}
	public CwmRuleGroupRel(int orderBy, String ruleInfoRel) {
		super();
		this.orderBy = orderBy;
		this.ruleInfoRel = ruleInfoRel;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the parentNodeId
	 */
	public String getParentNodeId() {
		return parentNodeId;
	}
	/**
	 * @param parentNodeId the parentNodeId to set
	 */
	public void setParentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	/**
	 * @return the nodeType
	 */
	public String getNodeType() {
		return nodeType;
	}
	/**
	 * @param nodeType the nodeType to set
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	/**
	 * @return the orderBy
	 */
	public int getOrderBy() {
		return orderBy;
	}
	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the ruleIdRel
	 */
	public String getRuleIdRel() {
		return ruleIdRel;
	}
	/**
	 * @param ruleIdRel the ruleIdRel to set
	 */
	public void setRuleIdRel(String ruleIdRel) {
		this.ruleIdRel = ruleIdRel;
	}
	/**
	 * @return the ruleInfoRel
	 */
	public String getRuleInfoRel() {
		return ruleInfoRel;
	}
	/**
	 * @param ruleInfoRel the ruleInfoRel to set
	 */
	public void setRuleInfoRel(String ruleInfoRel) {
		this.ruleInfoRel = ruleInfoRel;
	}

	
	
}

