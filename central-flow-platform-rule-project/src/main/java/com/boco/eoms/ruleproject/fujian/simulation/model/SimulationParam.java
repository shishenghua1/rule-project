package com.boco.eoms.ruleproject.fujian.simulation.model;

import com.boco.eoms.ruleproject.base.util.StaticMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 仿真数据获取参数对象
 * @author ssh
 */
@ApiModel(value=" 仿真数据获取参数对象")
public class SimulationParam {
    @ApiModelProperty(value="地市",example="福州市")
    private String city;
    @ApiModelProperty(value="网络工单一级分类",example="无线网")
    private String networkSheetOne;
    @ApiModelProperty(value="网络工单二级分类",example="无线网-LTE")
    private String networkSheetTwo;
    @ApiModelProperty(value="地市中文名",example="福州市")
    private String cityName;
    @ApiModelProperty(value="网络工单一级分类中文名",example="无线网")
    private String networkSheetOneName;
    @ApiModelProperty(value="网络工单二级分类中文名",example="无线网-LTE")
    private String networkSheetTwoName;
    @JsonFormat(pattern = StaticMethod.DATE_FORMAT)
    @ApiModelProperty(value="派单开始时间",dataType="Date",example="2019-06-05 10:25:30")
    private Date sheetStartTime;
    @JsonFormat(pattern = StaticMethod.DATE_FORMAT)
    @ApiModelProperty(value="派单结束时间",dataType="Date",example="2019-06-05 10:25:30")
    private Date sheetEndTime;
    @ApiModelProperty(value="网关id数组")
    private String[] gateWayIds;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNetworkSheetOne() {
        return networkSheetOne;
    }

    public void setNetworkSheetOne(String networkSheetOne) {
        this.networkSheetOne = networkSheetOne;
    }

    public String getNetworkSheetTwo() {
        return networkSheetTwo;
    }

    public void setNetworkSheetTwo(String networkSheetTwo) {
        this.networkSheetTwo = networkSheetTwo;
    }

    public Date getSheetStartTime() {
        return sheetStartTime;
    }

    public void setSheetStartTime(Date sheetStartTime) {
        this.sheetStartTime = sheetStartTime;
    }

    public Date getSheetEndTime() {
        return sheetEndTime;
    }

    public void setSheetEndTime(Date sheetEndTime) {
        this.sheetEndTime = sheetEndTime;
    }

    public String[] getGateWayIds() {
        return gateWayIds;
    }

    public void setGateWayIds(String[] gateWayIds) {
        this.gateWayIds = gateWayIds;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getNetworkSheetOneName() {
        return networkSheetOneName;
    }

    public void setNetworkSheetOneName(String networkSheetOneName) {
        this.networkSheetOneName = networkSheetOneName;
    }

    public String getNetworkSheetTwoName() {
        return networkSheetTwoName;
    }

    public void setNetworkSheetTwoName(String networkSheetTwoName) {
        this.networkSheetTwoName = networkSheetTwoName;
    }
}
