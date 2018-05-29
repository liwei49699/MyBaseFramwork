package com.sanshang.li.mybaseframwork.base;

import android.app.Activity;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.handler.IHandler;
import com.sanshang.li.mybaseframwork.handler.UIHandler;


/**
 * Created by li on 2018/5/22.
 * WeChat 18571658038
 * author LiWei
 */

public abstract class BaseActivity extends AppCompatActivity {

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

        // 这句很关键，注意是调用父类的方法
        super.setContentView(R.layout.activity_base);
    }

    private void initToolbar() {

        Toolbar toolbarTitle = findViewById(R.id.toolbar_title);

        if (toolbarTitle != null) {
            setSupportActionBar(toolbarTitle);
        }
        if (getSupportActionBar() != null) {
            // Enable the Up button
            ActionBar bar = getSupportActionBar();
            bar.setDisplayHomeAsUpEnabled(false);
            bar.setDisplayShowTitleEnabled(false);

        }

        ivTitleBack = findViewById(R.id.iv_title_back);
        tvTitleName = findViewById(R.id.tv_title_name);
        ivTitleMenu = findViewById(R.id.iv_title_menu);

        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        tvTitleName.setText("标题");

        ivTitleMenu.setVisibility(View.GONE);

        setHandler();

        if(!showTitle) {

            toolbarTitle.setVisibility(View.GONE);
        }
    }

    private void setHandler() {

        mHandler.setHandler(new IHandler() {

            public void handleMessage(Message msg) {

                handlerMsg(msg);//有消息就提交给子类实现的方法
            }
        });
    }

    /**
     * 设置是否显示状态栏
     */
    public void hideState( ) {

        showState = false;
    }
    /**
     * 设置是否显示标题栏
     */
    public void hideTitle( ) {

        showTitle = false;
    }


    //让子类处理消息
    protected void handlerMsg(Message msg){

    }

    protected  void setTitleName(String titleName){

        tvTitleName.setText(titleName);
    }

    protected void hideLeftBack(){

        ivTitleBack.setVisibility(View.GONE);
    }

    protected void setMenuIconClick(int iconId, View.OnClickListener listener){

        ivTitleMenu.setVisibility(View.VISIBLE);
        ivTitleMenu.setImageDrawable(getResources().getDrawable(iconId));
        ivTitleMenu.setOnClickListener(listener);
    }

    @Override
    public void setContentView(int layoutId) {

        setContentView(View.inflate(this, layoutId, null));

    }

    @Override
    public void setContentView(View view) {

        LinearLayout rootLayout = findViewById(R.id.ll_root);

        if (rootLayout == null) {
            return;
        }

        rootLayout.addView(view,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        initToolbar();
    }
}
