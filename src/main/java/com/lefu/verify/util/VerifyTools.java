package com.lefu.verify.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liufu on 16/1/15.
 */
public class VerifyTools {


    public static Pattern pattern;
    private static Matcher matcher;
    /***
     * 身份证15位
     */
    private static final String ID_REGEX_15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    /***
     * 身份证15位
     */
    private static final String ID_REGEX_18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$";
    /**
     * 邮箱
     */
    private static final String EMAIL_REGEX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /***
     * 验证是否是电话号
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhone(String phoneNumber) {
        if (phoneNumber.length() == 11) {
            return true;
        }
        return false;
        /*
         * pattern = Pattern.compile(PHONE_REGEX); matcher =
		 * pattern.matcher(phoneNumber); return matcher.find();
		 */
    }

    /***
     * 验证是否是身份证15位
     *
     * @param number
     * @return
     */
    public static boolean isIDCard15(String number) {
        pattern = Pattern.compile(ID_REGEX_15);
        matcher = pattern.matcher(number);
        return matcher.find();
    }

    /***
     * 验证是否是身份证18位
     *
     * @param number
     * @return
     */
    public static boolean isIDCard18(String number) {
        int[] w = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
                2, 1};
        String[] a = new String[]{"1", "0", "X", "9", "8", "7", "6", "5",
                "4", "3", "2"};
        pattern = Pattern.compile(ID_REGEX_18);
        matcher = pattern.matcher(number);
        boolean test = matcher.find();
        int t = 0;
        if (test) {
            for (int i = 0; i < 17; i++) {
                t += Integer.parseInt(number.charAt(i) + "") * w[i];
            }
            String s = a[t % 11];
            if (!s.equals(number.substring(17))) {
                test = false;
            }
        }
        return test;
    }

    /***
     * 验证是否是身份证
     *
     * @param number
     * @return
     */
    public static boolean isIDCard(String number) {
        return isIDCard15(number) || isIDCard18(number);
    }

    /***
     * 验证是否是身份证
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        pattern = Pattern.compile(EMAIL_REGEX);
        matcher = pattern.matcher(email);
        return matcher.find();
    }

    /***
     * 验证是否符正则
     *
     * @return
     */
    public static boolean isMatch(String regExp, String value) {
        pattern = Pattern.compile(regExp);
        matcher = pattern.matcher(value);
        return matcher.find();
    }

    /**
     * 是否是正确的密码规则
     */
    public static boolean isRealPwd(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            return false;
        }
        if (pwd.length() < 11) {
            return false;
        }
        Pattern pattern1 = Pattern.compile("^[0-9]*$");
        Matcher matcher1 = pattern1.matcher(pwd);
        if (matcher1.matches()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[a-zA-z]*$");
        Matcher matcher = pattern.matcher(pwd);
        if (matcher.matches()) {
            return false;
        }
        return true;
    }
}
