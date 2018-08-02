package com.sanshang.li.mybaseframwork.custom;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewConfiguration;

import com.sanshang.li.mybaseframwork.R;

public class CircleProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress);

        Configuration configuration = getResources().getConfiguration();

        //获取国家码
        int mcc = configuration.mcc;

        //获取网络信号
        int mnc = configuration.mnc;

        //获取屏幕方向
        int orientation = configuration.orientation;

        Log.d("--TAG--", "CircleProgressActivity onCreate()：获取国家码：" + mcc);
        Log.d("--TAG--", "CircleProgressActivity onCreate()获取网络信号：" + mnc);
        Log.d("--TAG--", "CircleProgressActivity onCreate()获取屏幕方向：" + orientation);

        ViewConfiguration viewConfiguration = ViewConfiguration.get(this);

        //系统能够识别的最小滑动距离
        int slop = viewConfiguration.getScaledTouchSlop();
        //获取Fling滑翔的最小速度
        int minVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        //获取Fling滑翔的最大速度
        int maxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        //是否有物理按键
        boolean hasMenuKey = viewConfiguration.hasPermanentMenuKey();


        //双击间隔时间.在该时间内是双击，否则是单击
        int doubleTapTimeout=ViewConfiguration.getDoubleTapTimeout();
        //按住状态转变为长按状态需要的时间
        int longPressTimeout=ViewConfiguration.getLongPressTimeout();
        //重复按键的时间
        int keyRepeatTimeout=ViewConfiguration.getKeyRepeatTimeout();
    }
}
