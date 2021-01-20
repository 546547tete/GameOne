package com.example.gameone;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * 字符串工具类
 */

public class StrUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 字符串 千位符
     *
     * @param num
     * @return
     */
    public static String numTothousand(String num) {
        String numStr = "";
        if (isEmpty(num)) {
            return numStr;
        }
        NumberFormat nf = NumberFormat.getInstance();
        try {
            DecimalFormat df = new DecimalFormat("#,###");
            numStr = df.format(nf.parse(num));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numStr;
    }

    /**
     * 字符串 千位符  保留两位小数点后两位
     *
     * @param num
     * @return
     */
    public static String numTothousand00(String num) {
        String numStr = "";
        if (isEmpty(num)) {
            return numStr;
        }
        NumberFormat nf = NumberFormat.getInstance();
        try {
            DecimalFormat df = new DecimalFormat("#,##0.00");
            numStr = df.format(nf.parse(num));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numStr;
    }

}
