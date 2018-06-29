package com.sanshang.li.mybaseframwork.jni;

/**
 * Created by li on 2018/6/29.
 * WeChat 18571658038
 * author LiWei
 */

public class JniUtils {

    static {

        System.loadLibrary("native-lib");
    }

    public JniUtils() {
    }

    public native String getString();

    public native String getName();
}