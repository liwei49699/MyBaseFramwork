package com.sanshang.li.mybaseframwork.systemfloat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.systemfloat.util.BackSelectorUtils;
import com.sanshang.li.mybaseframwork.systemfloat.util.DensityUtils;
import com.sanshang.li.mybaseframwork.systemfloat.util.FloatBallManager;
import com.sanshang.li.mybaseframwork.systemfloat.util.FloatPermissionUtils;
import com.sanshang.li.mybaseframwork.systemfloat.view.ball.FloatBallCfg;
import com.sanshang.li.mybaseframwork.systemfloat.view.menu.FloatMenuCfg;
import com.sanshang.li.mybaseframwork.systemfloat.view.menu.MenuItem;
import com.sanshang.li.mybaseframwork.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FloatMenuActivity extends AppCompatActivity {

    private Context mContext;
    private FloatBallManager mFloatballManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_menu);
        ButterKnife.bind(this);

        mContext = this;

        showFloatWindow();
    }

    @OnClick({R.id.btn_apply_permission, R.id.btn_show, R.id.btn_hide})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_apply_permission:

                //申请权限
                applyPermission();
                break;
            case R.id.btn_show:

                mFloatballManager.show();
                break;
            case R.id.btn_hide:

                mFloatballManager.hide();
                break;
        }
    }

    private void showFloatWindow() {

        int ballSize = DensityUtils.dip2px(this, 45);
        Drawable ballIcon = BackSelectorUtils.getdrawble("ic_floatball", this);
        FloatBallCfg ballCfg = new FloatBallCfg(ballSize, ballIcon, FloatBallCfg.Gravity.LEFT_BOTTOM, -100);
        // 设置悬浮球不半隐藏
        ballCfg.setHideHalfLater(true);

        // 初始化悬浮菜单配置，有菜单item的大小和菜单item的个数
        int menuSize = DensityUtils.dip2px(this, 180);
        int menuItemSize = DensityUtils.dip2px(this, 40);
        FloatMenuCfg menuCfg = new FloatMenuCfg(menuSize, menuItemSize);
        // 生成floatBallManager
        mFloatballManager = new FloatBallManager(getApplicationContext(), ballCfg, menuCfg);

        addFloatMenuItem();
    }

    private void addFloatMenuItem() {

        MenuItem personItem = new MenuItem(BackSelectorUtils.getdrawble("ic_weixin", this)) {
            @Override
            public void action() {

                ToastUtils.tMessage("打开微信");
            }
        };
        MenuItem walletItem = new MenuItem(BackSelectorUtils.getdrawble("ic_weibo", this)) {
            @Override
            public void action() {

                ToastUtils.tMessage("打开微博");
            }
        };
        MenuItem settingItem = new MenuItem(BackSelectorUtils.getdrawble("ic_email", this)) {
            @Override
            public void action() {

                ToastUtils.tMessage("打开邮箱");
//                mFloatballManager.closeMenu();
            }
        };
        mFloatballManager.addMenuItem(personItem)
//                .addMenuItem(walletItem)
//                .addMenuItem(personItem)
//                .addMenuItem(walletItem)
//                .addMenuItem(settingItem)
                .buildMenu();
    }

    private void applyPermission() {

        boolean b = FloatPermissionUtils.checkPermission(mContext);
        if(b) {
            //已经授权
            ToastUtils.tMessage("已经授权过");
        } else {

            //申请
            FloatPermissionUtils.applyPermission(mContext);
        }
    }
}
