package com.sanshang.li.mybaseframwork.memory.thread;

import android.os.SystemClock;
import android.util.Log;

/**
 * Created by Administrator on 2018\7\21 0021.
 */

public class MyOtherThread extends Thread {

    @Override
    public void run() {

        for (int i = 0; i < 100; i++) {

            SystemClock.sleep(1000L);
            Log.d("--TAG--", "MyOtherThread run()===" + i);
        }
    }
}
