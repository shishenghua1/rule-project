package com.boco.eoms.ruleproject.base.util;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author ssh
 * @description:分页对象
 * @date 2020/1/1314:32
 */
public class PageUtil {
    //当前页
    @ApiModelProperty(value="当前页",example="1")
    private Integer curPage;

    //页的大小
    @ApiModelProperty(value="页的大小",example="1")
    private Integer pageSize;

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
