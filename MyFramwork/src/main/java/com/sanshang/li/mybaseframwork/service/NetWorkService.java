package com.sanshang.li.mybaseframwork.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sanshang.li.mybaseframwork.receiver.NetworkBroadcastReceiver;

/**
 * Created by li on 2018/5/24.
 * WeChat 18571658038
 * author LiWei
 */

public class NetWorkService extends Service {

    private NetworkBroadcastReceiver networkReceiver;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        networkReceiver = new NetworkBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(networkReceiver);
    }
}
