package com.itkenor.util;

import java.math.BigDecimal;

/**
 * BigDecimalUtil
 * 用于货币的计算
 */
public class BigDecimalUtil {

    //无参数的构造器
    private BigDecimalUtil(){

    }
    /**
     * 防止丢失精度的加法运算
     * @param v1
     * @param v2
     * @return add
     */
    public static BigDecimal add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    /**
     * 防止丢失精度的减法运算
     * @param v1
     * @param v2
     * @return subtract
     */
    public static BigDecimal sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }

    /**
     * 防止丢失精度的乘法运算
     * @param v1
     * @param v2
     * @return multiply
     */
    public static BigDecimal mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }
    /**
     * 防止丢失精度的除法运算
     * @param v1
     * @param v2
     * @return b1.divide(b2,2,BigDecimal.ROUND_HALF_UP)
     */
    public static BigDecimal div(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,2,BigDecimal.ROUND_HALF_UP);//四舍五入,保留2位小数

        //除不尽的情况
    }





}
