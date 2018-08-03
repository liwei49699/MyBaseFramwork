package com.sanshang.li.mybaseframwork.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.sanshang.li.mybaseframwork.MainActivity;
import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.util.DeviceUtils;
import com.sanshang.li.mybaseframwork.util.LogUtils;
import com.squareup.leakcanary.LeakCanary;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.Stack;
import java.util.prefs.Preferences;

import cafe.adriel.androidaudioconverter.AndroidAudioConverter;
import cafe.adriel.androidaudioconverter.callback.ILoadCallback;
import okhttp3.OkHttpClient;

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

        initStetho();

        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(this, loginInfo(), options(this));

        // ... your codes
        if (NIMUtil.isMainProcess(this)) {
            // 注意：以下操作必须在主进程中进行
            // 1、UI相关初始化操作
            // 2、相关Service调用
        }
    }

    // 如果返回值为 null，则全部使用默认参数。
    private SDKOptions options(Context context) {

        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = MainActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.drawable.ic_photo_camera_white_24dp;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用采用默认路径作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
        String sdkPath = getAppCacheDir(context) + "/nim"; // 可以不设置，那么将采用默认路径
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
        // 2、通过Resources获取屏幕宽度
        DisplayMetrics dm = getResources().getDisplayMetrics();
        options.thumbnailSize = (int) (165.0 / 320.0 * dm.widthPixels);

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
//        options.userInfoProvider = new UserInfoProvider() {
//            @Override
//            public UserInfo getUserInfo(String account) {
//                return null;
//            }
//
//            @Override
//            public int getDefaultIconResId() {
//                return R.drawable.ic_gif;
//            }
//
//            @Override
//            public Bitmap getTeamIcon(String tid) {
//                return null;
//            }
//
//            @Override
//            public Bitmap getAvatarForMessageNotifier(String account) {
//                return null;
//            }
//
//            @Override
//            public String getDisplayNameForMessageNotifier(String account, String sessionId,
//                                                           SessionTypeEnum sessionType) {
//                return null;
//            }
//        };
        options.userInfoProvider = new UserInfoProvider(){

            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId, SessionTypeEnum sessionType) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(SessionTypeEnum sessionType, String sessionId) {
                return null;
            }
        };
        return options;
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {

        // 从本地读取上次登录成功时保存的用户登录信息
//        String account = Preferences.getUserAccount();
//        String token = Preferences.getUserToken();
//
//        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
//            DemoCache.setAccount(account.toLowerCase());
//            return new LoginInfo(account, token);
//        } else {
            return null;
//        }
    }

    /**
     * 配置 APP 保存图片/语音/文件/log等数据的目录
     * 这里示例用SD卡的应用扩展存储目录
     */
    static String getAppCacheDir(Context context) {
        String storageRootPath = null;
        try {
            // SD卡应用扩展存储区(APP卸载后，该目录下被清除，用户也可以在设置界面中手动清除)，请根据APP对数据缓存的重要性及生命周期来决定是否采用此缓存目录.
            // 该存储区在API 19以上不需要写权限，即可配置 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" android:maxSdkVersion="18"/>
            if (context.getExternalCacheDir() != null) {
                storageRootPath = context.getExternalCacheDir().getCanonicalPath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(storageRootPath)) {
            // SD卡应用公共存储区(APP卸载后，该目录不会被清除，下载安装APP后，缓存数据依然可以被加载。SDK默认使用此目录)，该存储区域需要写权限!
            storageRootPath = Environment.getExternalStorageDirectory() + "/" + context.getPackageName();
        }

        return storageRootPath;
    }

    private void initStetho() {

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());

        //注意:下面这段话是为OkHttpClient配置拦截器,只有用这个OkHttpClient请求数据才能被拦截
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())//添加Stetho的拦截器
                .build();

        //使用自定义的OkHttpClient
        OkHttpUtils.initClient(client);
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
