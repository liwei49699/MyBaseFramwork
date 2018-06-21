package com.sanshang.li.mybaseframwork.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by li on 2018/6/20.
 * WeChat 18571658038
 * author LiWei
 */

public class StringUtils {

    /**
     * 是否为空
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s.trim());
    }

    /**
     * 判断是否为中文
     * @param str
     * @return
     */
    public static boolean isChinese(String str){

        String ruleChina = "^[\u4e00-\u9fa5]*$";
        Pattern p= Pattern.compile(ruleChina);
        Matcher m=p.matcher(str);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断是否为字母
     * @param firstData
     * @return
     */
    public static boolean isEnglish(String firstData){

        char c = firstData.charAt(0);
        if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){

            return   true;
        } else {

            return   false;
        }
    }
}
