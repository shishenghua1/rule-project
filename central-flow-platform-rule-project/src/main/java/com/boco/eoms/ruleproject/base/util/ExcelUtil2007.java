package com.boco.eoms.ruleproject.base.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;

import java.math.BigInteger;
import java.text.DateFormat;
import java.util.List;
import java.util.regex.Pattern;

/**


* @author ssh
* 类说明：excel 07版工具类

*/
public class ExcelUtil2007 {
	 /***
     * 设置表头
     * @param workbook
     * @param sheet
     * @param titleList 表头集合
     */
    public static void setTitle(XSSFWorkbook workbook, XSSFSheet sheet, List<String> titleList){
        XSSFRow titleRow = sheet.createRow(0);
        if(titleList!=null&&titleList.size()>0) {
        	//设置为居中加粗
            XSSFCellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            //设置列宽和标题，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
            XSSFCell cell;
        	for(int i=0;i<titleList.size();i++) {
        		sheet.setColumnWidth(i, titleList.get(i).getBytes().length*256);
        		cell = titleRow.createCell(i);
                cell.setCellValue(titleList.get(i));
                cell.setCellStyle(style);
        	}
        }
    }

    /**
     * excel读取除字符串类型外的数据设置为空的处理
	 * @param cell
	 * @return
             */
    @SuppressWarnings("deprecation")
    public static String getCellValue(Cell cell) {
        String strCell = "";
        if (cell != null) {
            Object o = null;
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING://字符串类型
                    o = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC://数字类型
                    o = getValueOfNumericCell(cell);
                    break;
                case Cell.CELL_TYPE_BOOLEAN://boolean类型
                    o = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    o = "";
                    break;
                default:
                    o = cell.getRichStringCellValue().getString();
                    break;
            }
            strCell = String.valueOf(o);
        }
        return strCell.trim();
    }

    /**
     * 数字类型
     * @param cell
     * @return
     */
    private static Object getValueOfNumericCell(Cell cell) {
        Boolean isDate = HSSFDateUtil.isCellDateFormatted(cell);
        Double d = cell.getNumericCellValue();
        Object o = null;
        if (isDate) {
            o = DateFormat.getDateInstance().format(cell.getDateCellValue());
        } else {
            o = getRealStringValueOfDouble(d);
        }
        return o;
    }

    /**
     * 获取数字内容
     * @param d
     * @return
     */
    private static String getRealStringValueOfDouble(Double d) {
        String doubleStr = d.toString();
        boolean b = doubleStr.contains("E");
        int indexOfPoint = doubleStr.indexOf('.');
        if (b) {
            int indexOfE = doubleStr.indexOf('E');
            // 小数部分
            BigInteger xs = new BigInteger(doubleStr.substring(indexOfPoint + BigInteger.ONE.intValue(), indexOfE));
            // 指数
            int pow = Integer.valueOf(doubleStr.substring(indexOfE + BigInteger.ONE.intValue()));
            int xsLen = xs.toByteArray().length;
            int scale = xsLen - pow > 0 ? xsLen - pow : 0;
            doubleStr = String.format("%." + scale + "f", d);
        } else {
            java.util.regex.Pattern p = Pattern.compile(".0$");
            java.util.regex.Matcher m = p.matcher(doubleStr);
            if (m.find()) {
                doubleStr = doubleStr.replace(".0", "");
            }
        }
        return doubleStr;
    }

}

