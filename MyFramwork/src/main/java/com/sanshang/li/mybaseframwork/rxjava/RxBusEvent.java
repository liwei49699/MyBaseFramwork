package com.sanshang.li.mybaseframwork.rxjava;

/**
 * Created by li on 2018/6/20.
 * WeChat 18571658038
 * author LiWei
 */

public class RxBusEvent {

    private String message;

    public RxBusEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RxBusEvent{" +
                "message='" + message + '\'' +
                '}';
    }
}
