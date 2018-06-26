package com.sanshang.li.mybaseframwork.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2018/6/21.
 * WeChat 18571658038
 * author LiWei
 */

public class News implements MyObservable {

    private List<MyObserver> mObservers;

    public News() {

        mObservers = new ArrayList<>();
    }

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

            observer.update("数据更新了");
        }
    }
}
