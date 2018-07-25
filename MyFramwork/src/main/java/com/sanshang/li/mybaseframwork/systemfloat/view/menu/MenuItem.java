package com.sanshang.li.mybaseframwork.systemfloat.view.menu;

import android.graphics.drawable.Drawable;

/**
 * 系统悬浮菜单项
 * Created by li on 2018/7/17.
 * WeChat 18571658038
 * author LiWei
 */

public abstract class MenuItem {
    /**
     * 菜单icon
     */
    public Drawable mDrawable;

    public MenuItem(Drawable drawable) {
        this.mDrawable = drawable;
    }

    /**
     * 点击次菜单执行的操作
     */
    public abstract void action();
}
