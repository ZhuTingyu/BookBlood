package com.base.util.utility;

import android.text.TextUtils;

import com.base.http.R;
import com.base.util.Lists;
import com.base.util.Utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zhu TingYu on 2017/11/20.
 */

public class StringUtil {

    /**
     * 字符串是否为空
     * @param string
     * @return
     */

    public static boolean isStringValid(String string) {
        return string != null && !string.isEmpty() && string.length() > 0;
    }
    /**
     * 手机号码是否有效
     * @param phoneNumber
     * @return
     */
    public static boolean phoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.length() == 11) {
            try {

                Long phone = Long.valueOf(phoneNumber);
                if (phone >= 0) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return isValid;
    }

    /**
     * 通用正则表达式调用方法
     *
     * @param Content    需要检查的字符串
     * @param PatternStr 正则表达式
     * @return
     */
    public static boolean compile(String Content, String PatternStr) {
        Pattern p = Pattern.compile(PatternStr);
        Matcher m = p.matcher(Content);
        return m.matches();
    }

    public static boolean isPasswordValid(String password){
        boolean isValid = false;

        if(isStringValid(password)){
            if(password.length() >= 6){
                isValid = true;
            }
        }

        return isValid;
    }

    public static String emptyString() {
        return "";
    }

    public static String blankString() {
        return "    ";
    }
}
