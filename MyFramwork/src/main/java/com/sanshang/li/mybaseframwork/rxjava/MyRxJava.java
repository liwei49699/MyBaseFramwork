package com.sanshang.li.mybaseframwork.rxjava;

/**
 * Created by li on 2018/6/15.
 * WeChat 18571658038
 * author LiWei
 */

public class MyRxJava {

    private Integer mInteger;
    private String code;

    public MyRxJava(Integer integer, String code) {
        mInteger = integer;
        this.code = code;
    }

    public Integer getInteger() {
        return mInteger;
    }

    public void setInteger(Integer integer) {
        mInteger = integer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "MyRxJava{" +
                "mInteger=" + mInteger +
                ", code='" + code + '\'' +
                '}';
    }
}
