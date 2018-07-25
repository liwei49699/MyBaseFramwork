package com.sanshang.li.mybaseframwork.floatwindow.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

import com.sanshang.li.mybaseframwork.floatwindow.view.FloatWindowHintView;
import com.sanshang.li.mybaseframwork.floatwindow.view.FloatWindowView;

import java.lang.reflect.Field;

/**
 * Created by li on 2018/7/9.
 * WeChat 18571658038
 * author LiWei
 */

public class FloatWindowManager {

    private static final String TAG = "--TAG--";

    private static volatile FloatWindowManager instance;

    private boolean isWindowDismiss = true;
    private WindowManager windowManager = null;
    private WindowManager.LayoutParams mParams = null;
    private FloatWindowView floatView = null;

    public static FloatWindowManager getInstance() {
        if (instance == null) {
            synchronized (FloatWindowManager.class) {
                if (instance == null) {
                    instance = new FloatWindowManager();
                }
            }
        }
        return instance;
    }

    public void applyOrShowFloatWindow(Context context) {

            showWindow(context);
    }

    private void showWindow(Context context) {
        if (!isWindowDismiss) {
            Log.e(TAG, "view is already added here");
            return;
        }

        isWindowDismiss = false;
        if (windowManager == null) {
            windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        }

        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();

        if(mParams == null) {

            mParams = new WindowManager.LayoutParams();
            mParams.packageName = context.getPackageName();
            mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            int mType;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                mType = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }
            mParams.type = mType;
            mParams.format = PixelFormat.RGBA_8888;
            mParams.gravity = Gravity.LEFT | Gravity.TOP;

            //起始位置
            mParams.x = screenWidth;
            mParams.y = screenHeight / 2;
        }

        floatView = new FloatWindowView(context);
        floatView.setParams(mParams);
        floatView.setIsShowing(true);
        windowManager.addView(floatView, mParams);
    }

    public void dismissWindow() {
        if (isWindowDismiss) {
            Log.e(TAG, "window can not be dismiss cause it has not been added");
            return;
        }

        isWindowDismiss = true;
        floatView.setIsShowing(false);
        if (windowManager != null && floatView != null) {
            windowManager.removeViewImmediate(floatView);
        }
    }

    private  FloatWindowHintView hintView;
    private  WindowManager.LayoutParams hintParams;

    /**
     * 创建一个大悬浮窗。位置为屏幕正中间。
     */
    public void createHintWindow(Context context) {

        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();

        if (hintView == null) {
            hintView = new FloatWindowHintView(context);
        }

        if (hintParams == null) {
            hintParams = new WindowManager.LayoutParams();
            hintParams.x = screenWidth / 2
                    - hintView.viewWidth / 2;
            hintParams.y = screenHeight / 2
                    - hintView.viewHeight / 2;
            hintParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            hintParams.format = PixelFormat.RGBA_8888;
            hintParams.gravity = Gravity.LEFT | Gravity.TOP;
            hintParams.width = hintView.viewWidth;
            hintParams.height = hintView.viewHeight;
        }

        windowManager.addView(hintView, hintParams);

        hintWindowShow = true;

        dismissWindow();
    }

    private boolean hintWindowShow = false;

    public void dismissHintWindow() {

        if (!hintWindowShow) {
            Log.e(TAG, "window can not be dismiss cause it has not been added");
            return;
        }

        hintWindowShow = false;
        if (windowManager != null && hintView != null) {

            hintView.setIsShowing(false);
            windowManager.removeViewImmediate(hintView);
        }
    }

    public static void commonROMPermissionApplyInternal(Context context) throws NoSuchFieldException, IllegalAccessException {

        Class clazz = Settings.class;
        Field field = clazz.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION");

        Intent intent = new Intent(field.get(null).toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }
}
