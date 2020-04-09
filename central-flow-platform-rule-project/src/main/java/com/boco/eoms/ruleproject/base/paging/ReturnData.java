package com.boco.eoms.ruleproject.base.paging;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ssh
 * @description:
 * @date 2020/1/1416:25
 */
public class ReturnData <T>{
    //数据集合
    private List<T> rows = new ArrayList<T>();
    //数据总条数
    private long total;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
