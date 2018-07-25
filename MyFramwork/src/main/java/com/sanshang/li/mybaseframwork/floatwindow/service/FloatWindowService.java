package com.sanshang.li.mybaseframwork.floatwindow.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.sanshang.li.mybaseframwork.floatwindow.util.FloatWindowManager;

public class FloatWindowService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

        openFloatWindow();

        Log.d("--TAG--", "FloatWindowService onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int i = super.onStartCommand(intent, flags, startId);
        Log.d("--TAG--", "FloatWindowService onStartCommand()" + i);

        //防止重新启动
        return START_NOT_STICKY;
    }

    private void openFloatWindow() {

        FloatWindowManager.getInstance().applyOrShowFloatWindow(FloatWindowService.this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        closeFloatWindow();

        Log.d("--TAG--", "FloatWindowService onDestroy()" + "执行关闭服务");
    }

    private void closeFloatWindow() {

        FloatWindowManager.getInstance().dismissWindow();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        Log.d("--TAG--", "FloatWindowService onTaskRemoved()" + "执行移除栈");

        closeFloatWindow();
    }
}
