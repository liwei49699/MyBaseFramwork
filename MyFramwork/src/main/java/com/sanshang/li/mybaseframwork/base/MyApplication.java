package com.sanshang.li.mybaseframwork.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;

import com.sanshang.li.mybaseframwork.util.LogUtils;
import com.squareup.leakcanary.LeakCanary;

import java.util.Stack;

import cafe.adriel.androidaudioconverter.AndroidAudioConverter;
import cafe.adriel.androidaudioconverter.callback.ILoadCallback;

/**
 * Created by li on 2018/5/22.
 * WeChat 18571658038
 * author LiWei
 */

public class MyApplication extends Application {

    /**
     * 操作Activity的栈
     */
    private static Stack<Activity> sActivityStack = new Stack<>();
    private static Activity sCurrentActivity;
    public static MyApplication appInstance;

    public static MyApplication getAppInstance(){

        return appInstance;
    }

    public static Activity getCurrentActivity() {
        return sCurrentActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;

        // 监听生命周期
        registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        } else {

            LeakCanary.install(this);
        }

        AndroidAudioConverter.load(this, new ILoadCallback() {
            @Override
            public void onSuccess() {
                // Great!
                
                Log.d("--TAG--", "MyApplication onSuccess()" + "success");
            }
            @Override
            public void onFailure(Exception error) {
                // FFmpeg is not supported by device
                error.printStackTrace();
            }
        });
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        LogUtils.d(" ()");
        Log.d("--TAG--", "MyApplication onTerminate()" + "程序终止");
    }

    class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks{

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            addToActivityStack(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

            sCurrentActivity = activity;
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

            activityRemoveStack(activity);
        }
    }

    /**
     * 将Activity加入Stack
     * @param activity
     */
    public static void addToActivityStack(Activity activity){

        sActivityStack.add(activity);
    }

    /**
     * Activity移除Stack
     * @param activity
     */
    public static void activityRemoveStack(Activity activity){

        sActivityStack.remove(activity);
    }
    /**
     * 结束Activity并移除Stack
     * @param activity
     */
    public static void finishActivityRemoveStack(Activity activity){

        if(!activity.isFinishing()) {

            sActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 关闭所有的Activity
     */
    public static void finishAllActivity(){

        for (Activity Activity : sActivityStack) {
            if(!Activity.isFinishing()){
                Activity.finish();
            }
        }
        sActivityStack.clear();
    }

    /**
     * 退出应用
     */
    public static void exitApp(){

        for (Activity Activity : sActivityStack) {
            if(!Activity.isFinishing()){
                Activity.finish();
            }
        }
        sActivityStack.clear();
        //杀死进程    android.os.Process;
        Process.killProcess(Process.myPid());
        System.exit(1);
    }
}
