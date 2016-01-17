package com.lefu.verify.util;

/**
 * @author liufu on 16/1/15.
 */
public class ErrorState {

    public static int SUCCESS = -1;
    /**
     * 非空错误
     */
    public static int NOT_NULL_ERROR = 1;

    /**
     * 值类型错误
     */
    public static int TYPE_ERROR = 2;

    /**
     * 正则匹配错误
     */
    public static int REG_EXP_ERROR = 3;

    /**
     * 长度错误
     */
    public static int LENGTH_ERROR = 4;
}
