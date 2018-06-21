package com.sanshang.li.mybaseframwork.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by li on 2018/6/21.
 * WeChat 18571658038
 * author LiWei
 */

public class NumberUtils {

    //截取数字
    public static String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    // 截取非数字
    public static String splitNotNumber(String content) {
        Pattern pattern = Pattern.compile("\\D+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }
}
