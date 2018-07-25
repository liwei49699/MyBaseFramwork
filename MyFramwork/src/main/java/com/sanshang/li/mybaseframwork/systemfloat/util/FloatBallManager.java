package com.sanshang.li.mybaseframwork.systemfloat.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;


import com.sanshang.li.mybaseframwork.systemfloat.view.ball.FloatBall;
import com.sanshang.li.mybaseframwork.systemfloat.view.ball.FloatBallCfg;
import com.sanshang.li.mybaseframwork.systemfloat.view.ball.StatusBar;
import com.sanshang.li.mybaseframwork.systemfloat.view.menu.FloatMenu;
import com.sanshang.li.mybaseframwork.systemfloat.view.menu.FloatMenuCfg;
import com.sanshang.li.mybaseframwork.systemfloat.view.menu.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 悬浮球管理器
 * Created by li on 2018/7/17.
 * WeChat 18571658038
 * author LiWei
 */

public class FloatBallManager {

    public int mScreenWidth, mScreenHeight;
    private IFloatBallPermission mPermission;
    private OnFloatBallClickListener mFloatballClickListener;
    private WindowManager mWindowManager;
    private Context mContext;
    private FloatBall floatBall;
    private FloatMenu floatMenu;
    private StatusBar statusBarView;
    public int floatballX, floatballY;
    private boolean isShowing = false;
    private List<MenuItem> menuItems = new ArrayList<>();

    public FloatBallManager(Context application, FloatBallCfg ballCfg) {
        this(application, ballCfg, null);
    }

    public FloatBallManager(Context application, FloatBallCfg ballCfg, FloatMenuCfg menuCfg) {
        mContext = application.getApplicationContext();
        FloatBallUtils.inSingleActivity = false;
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        computeScreenSize();
        floatBall = new FloatBall(mContext, this, ballCfg);
        floatMenu = new FloatMenu(mContext, this, menuCfg);
        statusBarView = new StatusBar(mContext, this);
    }

    public void buildMenu() {
        inflateMenuItem();
    }

    /**
     * 添加一个菜单条目
     *
     * @param item
     */
    public FloatBallManager addMenuItem(MenuItem item) {
        menuItems.add(item);
        return this;
    }

    public int getMenuItemSize() {
        return menuItems != null ? menuItems.size() : 0;
    }

    /**
     * 设置菜单
     *
     * @param items
     */
    public FloatBallManager setMenu(List<MenuItem> items) {
        menuItems = items;
        return this;
    }

    private void inflateMenuItem() {
        floatMenu.removeAllItemViews();
        for (MenuItem item : menuItems) {
            floatMenu.addItem(item);
        }
    }

    public int getBallSize() {
        return floatBall.getSize();
    }

    public void computeScreenSize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point point = new Point();
            mWindowManager.getDefaultDisplay().getSize(point);
            mScreenWidth = point.x;
            mScreenHeight = point.y;
        } else {
            mScreenWidth = mWindowManager.getDefaultDisplay().getWidth();
            mScreenHeight = mWindowManager.getDefaultDisplay().getHeight();
        }
    }

    public int getStatusBarHeight() {
        return statusBarView.getStatusBarHeight();
    }

    public void onStatusBarHeightChange() {
        floatBall.onLayoutChange();
    }

    public void show() {

        if (isShowing) return;
        isShowing = true;
        floatBall.setVisibility(View.VISIBLE);
        statusBarView.attachToWindow(mWindowManager);
        floatBall.attachToWindow(mWindowManager);
        floatMenu.detachFromWindow(mWindowManager);
    }

    public void closeMenu() {
        floatMenu.closeMenu();
    }

    public void reset() {
        floatBall.setVisibility(View.VISIBLE);
        floatBall.postSleepRunnable();
        floatMenu.detachFromWindow(mWindowManager);
    }

    public void onFloatBallClick() {

        if (menuItems != null && menuItems.size() > 0) {
            floatMenu.attachToWindow(mWindowManager);
        } else {
            if (mFloatballClickListener != null) {
                mFloatballClickListener.onFloatBallClick();
            }
        }
    }

    public void hide() {
        if (!isShowing) return;
        isShowing = false;
        floatBall.detachFromWindow(mWindowManager);
        floatMenu.detachFromWindow(mWindowManager);
        statusBarView.detachFromWindow(mWindowManager);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        computeScreenSize();
        reset();
    }

    public void setPermission(IFloatBallPermission iPermission) {
        this.mPermission = iPermission;
    }

    public void setOnFloatBallClickListener(OnFloatBallClickListener listener) {
        mFloatballClickListener = listener;
    }

    public interface OnFloatBallClickListener {
        void onFloatBallClick();
    }

    public interface IFloatBallPermission {
        /**
         * request the permission of floatball,just use {@link #requestFloatBallPermission(Activity)},
         * or use your custom method.
         *
         * @return return true if requested the permission
         * @see #requestFloatBallPermission(Activity)
         */
        boolean onRequestFloatBallPermission();

        /**
         * detect whether allow  using floatball here or not.
         *
         * @return
         */
        boolean hasFloatBallPermission(Context context);

        /**
         * request floatball permission
         */
        void requestFloatBallPermission(Activity activity);
    }
}
