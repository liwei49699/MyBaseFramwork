package com.sanshang.li.mybaseframwork.observer;

import android.util.Log;

/**
 * Created by li on 2018/6/21.
 * WeChat 18571658038
 * author LiWei
 */

public class Phone implements MyObserver {

    private String mType;

    public Phone(String type) {
        mType = type;
    }

    @Override
    public void update(String message) {

        Log.d("--TAG--", "Phone update()" + mType + "准备好" + message);
    }
}
