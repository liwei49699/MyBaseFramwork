package com.sanshang.li.mybaseframwork.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sanshang.li.mybaseframwork.util.ToastUtils;

/**
 * Created by li on 2018/5/24.
 * WeChat 18571658038
 * author LiWei
 */

public class NetworkBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {

            // 处理网络问题
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            if(networkInfo != null) {

                boolean connected = networkInfo.isConnected();
                if(connected) {

                    String netMessage;
                    int type = networkInfo.getType();
                    switch (type) {
                        case ConnectivityManager.TYPE_WIFI :

                            netMessage = "WIFI";
                            break;
                        case ConnectivityManager.TYPE_MOBILE :

                            netMessage = "手机网络";
                            break;

                        default:

                            netMessage = "未知网络信息";
                            break;
                    }

                    ToastUtils.tMessage("当前使用的是：" + netMessage);

                } else {
                    ToastUtils.tMessage("网络已断开!!!");
                }
            } else {
                ToastUtils.tMessage("无相关网络信息!!!");
            }
        }
    }
}
