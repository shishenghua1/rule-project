package com.boco.eoms.ruleproject.base.util;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 * Title: StaticMethod.java
 * </p>
 *
 * <p>
 * Description:项目公共方法类
 * </p>
 *
 * @author renjiangtao
 * @version 1.0
 * @date 2018年5月25日
 */
public class StaticMethod {
    private static Pattern p = Pattern.compile("[A-Z]");

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static Logger logger = LoggerFactory.getLogger(StaticMethod.class);
    /**
     * classpath标识
     */
    public final static String CLASSPATH_FLAG = "classpath:";

    public static String dateToString(Date d) {
        return dateToString(d, DATE_FORMAT);
    }

    // 日期转为字符串，自定义格式
    public static String dateToString(Date d, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(d);
    }

    // 字符串转为日期，默认yyyy-MM-dd
    public static Date stringToDate(String str) {
        if (str != null && !"".equals(str)) {
            return stringToDate(str, DATE_FORMAT, "yyyy-MM-dd");
        } else {
            return null;
        }
    }

    // 字符串转为日期，自定义格式
    public static Date stringToDate(String str, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date d = sdf.parse(str);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static Date stringToDate(String str, String pattern, String defaultPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        SimpleDateFormat sdf1 = new SimpleDateFormat(defaultPattern);
        try {
            Date d = sdf.parse(str);
            return d;
        } catch (ParseException e) {
            try {
                Date d = sdf1.parse(str);
                return d;
            } catch (ParseException ex) {
                ex.printStackTrace();
                return null;
            }

        }
    }

    /**
     * 字符转换函数
     *
     * @param s 需要转换的对象
     * @return str:如果字符串为null,返回为空,否则返回该字符串
     */
    public static String nullObject2String(Object s) {
        String str = "";
        try {
            str = s.toString();
        } catch (Exception e) {
            str = "";
        }
        return str;
    }

    /**
     * 将一个对象转换为整形
     *
     * @param s
     * @return
     */
    public static int nullObject2int(Object s) {
        String str = "";
        int i = 0;
        try {
            str = s.toString();
            i = Integer.parseInt(str);
        } catch (Exception e) {
            i = 0;
        }
        return i;
    }

    /**
     * 将对象转换为整形
     *
     * @param s
     * @param in
     * @return
     */
    public static int nullObject2int(Object s, int in) {
        String str = "";
        int i = in;
        try {
            str = s.toString();
            i = Integer.parseInt(str);
        } catch (Exception e) {
            i = in;
        }
        return i;
    }

    /**
     * 对象转换为long型
     *
     * @param s
     * @return
     */
    public static long nullObject2Long(Object s) {
        long i = 0;

        String str = "";
        try {
            str = s.toString();
            i = Long.parseLong(str);
        } catch (Exception e) {
            i = 0;
        }

        return i;
    }

    /**
     * 将对象转换为long,如果无法转换则返回temp
     *
     * @param s
     * @param temp
     * @return
     */
    public static long nullObject2Long(Object s, long temp) {
        long i = temp;

        String str = "";
        try {
            str = s.toString();
            i = Long.parseLong(str);
        } catch (Exception e) {
            i = temp;
        }

        return i;
    }

    /**
     * 将对象转换为float,如果无法转换则返回temp
     *
     * @param s
     * @param temp
     * @return
     */
    public static float nullObject2Float(Object s, float temp) {
        float i = temp;
        String str = "";
        try {
            str = s.toString();
            i = Float.parseFloat(str);
        } catch (Exception e) {
            i = temp;
        }
        return i;
    }

    /**
     * 时间转换方法：根据输入的格式(String _dtFormat)得到当前时间格式得到当前的系统时间 Add By ChengJiWu
     *
     * @param _dtFormat
     * @return
     */
    public static String getCurrentDateTime(String _dtFormat) {
        String currentdatetime = "";
        try {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat dtFormat = new SimpleDateFormat(_dtFormat);
            currentdatetime = dtFormat.format(date);
        } catch (Exception e) {
            logger.info("时间格式不正确");

        }
        return currentdatetime;
    }

    /**
     * 时间转换方法：得到默认的时间格式为("yyyy-MM-dd HH:mm:ss")的当前时间
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return getCurrentDateTime(DATE_FORMAT);
    }

    /**
     * @param string
     * @return
     * @see 字符处理方法：将首字符转换为大写
     */
    public static String firstToUpperCase(String string) {
        String post = string.substring(1, string.length());
        String first = ("" + string.charAt(0)).toUpperCase();
        return first + post;
    }

    /**
     * @param param
     * @return
     * @see 字符处理方法：字符串大写字母转下划线
     */
    public static String upperCharToUnderLine(String param) {
        if (param == null || param.equals("")) {
            return "";
        }
        StringBuilder builder = new StringBuilder(param);
        Matcher mc = p.matcher(param);
        int i = 0;
        while (mc.find()) {
            builder.replace(mc.start() + i, mc.end() + i, "_" + mc.group().toLowerCase());
            i++;
        }

        if ('_' == builder.charAt(0)) {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    /**
     * 读java包时返回的路径
     *
     * @param filePath 文件路径
     * @return
     * @throws FileNotFoundException
     */
    public static String getFilePathForUrl(String filePath) throws FileNotFoundException {
        URL url = getFileUrl(filePath);
        return url.getFile();
    }

    /**
     * 获取filePath的url
     *
     * @param filePath
     * @return
     * @throws FileNotFoundException 创建url失败将抛出MalformedURLException
     */
    public static URL getFileUrl(String filePath) throws FileNotFoundException {
        URL url = null;
        if (filePath != null) {
            if (filePath.length() > CLASSPATH_FLAG.length()) {
                if (CLASSPATH_FLAG.equals(filePath.substring(0, CLASSPATH_FLAG.length()))) {
                    try {
                        url = StaticMethod.class.getClassLoader().getResource(getPathButClasspath(filePath));
                        URL url1 = StaticMethod.class.getResource(getPathButClasspath(filePath));
                        URL url2 = Thread.currentThread().getContextClassLoader().getResource(getPathButClasspath(filePath));
                    } catch (Exception e) {
                        throw new FileNotFoundException(filePath + "is not found.");
                    }
                }
            }
        }
        return url;
    }

    /**
     * 去掉classpath
     *
     * @param path
     * @return
     */
    private static String getPathButClasspath(String path) {
        return path.substring(CLASSPATH_FLAG.length());
    }

    /**
     * 取文本左边
     *
     * @param str      文本
     * @param beginStr 开始文本
     * @return
     */
    public static String getStrLeft(String str, String beginStr) {
        int beginNum = str.indexOf(beginStr);
        if (beginNum == -1) {
            return "";
        } else {
            return str.substring(0, beginNum);
        }
    }

    /**
     * 取文本左边
     *
     * @param str 文本
     * @param num 距离后面有多位
     * @return
     */
    public static String getStrLeft(String str, int num) {
        if (str.length() == 0) {
            return "";
        } else {
            return str.substring(0, str.length() - num);
        }
    }

    /**
     * 字符串排序
     *
     * @param flagId
     * @param splitFlag
     * @return
     */
    public static String sortFlagId(String flagId, String splitFlag) {
        flagId = flagId.replace("null", "0");
        String[] array = flagId.split(splitFlag);
        return Arrays.stream(array).sorted().collect(Collectors.joining(splitFlag));
    }

    /**
     * 根据map得value获取第一个key
     * @param map
     * @param value
     * @return
     */
    public static String getKey(Map map, Object value){
        for(Object key: map.keySet()){
            if((map.get(key).toString().trim()).equals(value.toString().trim())){
                return key.toString();
            }
        }
        return "";
    }

    /**
     * 判断2个集合内容是否完全相同
     * @param list1 list1不重复
     * @param list2
     * @return
     */
    public static boolean isEqual(List list1, List list2){
        boolean isEqual = false;
        if(list1!=null&&list2!=null&&list1.size()>0&&list2.size()>0){
            if(list1.containsAll(list2)&&list2.containsAll(list1)&&list1.size()==list2.size()){
                isEqual =  true;
            }
        }
        return isEqual;
    }
}
