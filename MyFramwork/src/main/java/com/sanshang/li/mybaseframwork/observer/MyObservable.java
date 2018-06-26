package com.sanshang.li.mybaseframwork.observer;

/**
 * Created by li on 2018/6/21.
 * WeChat 18571658038
 * author LiWei
 */

public interface MyObservable {

    /**
     * 注册观察者
     * @param o
     */
    void registerObserver(MyObserver o);

    /**
     * 移除观察者
     * @param o
     */
    void removeObserver(MyObserver o);

    /**
     * 通知观察者
     */
    void notifyObserver();
}
