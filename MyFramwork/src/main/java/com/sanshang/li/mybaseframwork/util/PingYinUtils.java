//package com.sanshang.li.mybaseframwork.util;
//
///**
// * Created by li on 2018/6/20.
// * WeChat 18571658038
// * author LiWei
// */
//
//public class PingYinUtils {
//
//    /**
//     * 将字符串中的中文转化为拼音,其他字符不变
//     * @param inputString
//     * @return
//     */
//    public static String getPingYin(String inputString) {
//
//        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
//        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        format.setVCharType(HanyuPinyinVCharType.WITH_V);
//
//        char[] input = inputString.trim().toCharArray();
//        String output = "";
//
//        try {
//            for (int i = 0; i < input.length; i++) {
//                if (Character.toString(input[i]).matches(
//                        "[\\u4E00-\\u9FA5]+")) {
//                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(
//                            input[i], format);
//                    output += temp[0];
//                } else
//                    output += Character.toString(input[i]);
//            }
//        } catch (BadHanyuPinyinOutputFormatCombination e) {
//            e.printStackTrace();
//        }
//        return output;
//    }
//
//    /**
//     * 汉字转换位汉语拼音首字母，英文字符不变
//     * @param chines
//     * @return 拼音
//     */
//    public static String converterToFirstSpell(String chines) {
//
//        String pinyinName = "";
//        char[] nameChar = chines.toCharArray();
//        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
//        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
//        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        for (int i = 0; i < nameChar.length; i++) {
//            if (nameChar[i] > 128) {
//                try {
//                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(
//                            nameChar[i], defaultFormat)[0].charAt(0);
//                } catch (BadHanyuPinyinOutputFormatCombination e) {
//                    e.printStackTrace();
//                }
//            } else {
//                pinyinName += nameChar[i];
//            }
//        }
//        return pinyinName;
//    }
//
//}
