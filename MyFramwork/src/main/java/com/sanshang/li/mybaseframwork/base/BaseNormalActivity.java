package com.sanshang.li.mybaseframwork.base;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.sanshang.li.mybaseframwork.handler.UIHandler;

/**
 * Created by li on 2018/6/15.
 * WeChat 18571658038
 * author LiWei
 */

public class BaseNormalActivity extends AppCompatActivity {

    protected final String mActivityName = this.getClass().getSimpleName();

    protected static UIHandler mHandler = new UIHandler(Looper.getMainLooper());

    private ImageView ivTitleBack;
    private TextView tvTitleName;
    private ImageView ivTitleMenu;

    /**
     * 是否显示状态栏
     */
    private  boolean showState = true;
    /**
     * 是否显示标题栏
     */
    private  boolean showTitle = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!showState){

            getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                    WindowManager.LayoutParams. FLAG_FULLSCREEN);
        }
    }
}
