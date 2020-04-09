package com.boco.eoms.ruleproject.base.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author ssh
 * @description:json返回格式工具类
 * @date 2019/9/416:25
 */
public class ReturnJsonUtil {

    /**
     * json成功的返回信息
     *
     * @param message
     * @return
     */
    public static JSONObject returnSuccessInfo(String message) {
        JSONObject successInfo = new JSONObject();
        successInfo.put("result", "success");
        successInfo.put("message", message);
        return successInfo;
    }

    /**
     * json失败的返回信息
     *
     * @param message
     * @return
     */
    public static JSONObject returnFailInfo(String message) {
        JSONObject failInfo = new JSONObject();
        failInfo.put("result", "fail");
        failInfo.put("message", message);
        return failInfo;
    }

    /**
     * 成功信息内容
     *
     * @param successInfo
     * @param message
     * @return
     */
    public static JSONObject returnSuccessInfo(JSONObject successInfo, String message) {
        successInfo.put("result", "success");
        successInfo.put("message", message);
        return successInfo;
    }

    /**
     * 失败信息内容
     *
     * @param failInfo
     * @param message
     * @return
     */
    public static JSONObject returnFailInfo(JSONObject failInfo, String message) {
        failInfo.put("result", "fail");
        failInfo.put("message", message);
        return failInfo;
    }

    /**
     * 成功信息内容
     *
     * @param successInfo
     * @param message
     * @return
     */
    public static JSONObject returnSuccessInfo(JSONArray successInfo, String message) {
        JSONObject obj = new JSONObject(true);
        obj.put("data",successInfo);
        obj.put("result", "success");
        obj.put("message", message);
        return obj;
    }

    /**
     * 失败信息内容
     *
     * @param failInfo
     * @param message
     * @return
     */
    public static JSONObject returnFailInfo(JSONArray failInfo, String message) {
        JSONObject obj = new JSONObject(true);
        obj.put("data",failInfo);
        obj.put("result", "fail");
        obj.put("message", message);
        return obj;
    }

    /**
     * 成功信息内容
     *
     * @param successInfo
     * @param message
     * @return
     */
    public static JSONObject returnSuccessData(JSONObject successInfo, String message) {
        JSONObject obj = new JSONObject(true);
        obj.put("data",successInfo);
        obj.put("result", "success");
        obj.put("message", message);
        return obj;
    }

}
