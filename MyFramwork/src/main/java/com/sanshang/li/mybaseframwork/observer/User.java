package com.sanshang.li.mybaseframwork.observer;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2018/6/21.
 * WeChat 18571658038
 * author LiWei
 */

public class User implements MyObserver,MyObservable {

    private String mName;

    public User(String name) {
        mName = name;
    }

    @Override
    public void update(String message) {

        Log.d("--TAG--", "User update()" + mName + "收到了消息" + message);
        notifyObserver();
    }

    private List<MyObserver> mObservers = new ArrayList<>();

    @Override
    public void registerObserver(MyObserver o) {

        mObservers.add(o);
    }

    @Override
    public void removeObserver(MyObserver o) {

        mObservers.remove(o);
    }

    @Override
    public void notifyObserver() {

        for (MyObserver observer : mObservers) {

            observer.update("看新闻");
        }
    }
}
