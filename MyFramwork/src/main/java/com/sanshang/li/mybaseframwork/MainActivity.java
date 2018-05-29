package com.sanshang.li.mybaseframwork;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.sanshang.li.mybaseframwork.base.BaseActivity;
import com.sanshang.li.mybaseframwork.service.NetWorkService;
import com.sanshang.li.mybaseframwork.translucent.TranslucentActivity;
import com.sanshang.li.mybaseframwork.util.DeviceUtil;
import com.sanshang.li.mybaseframwork.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.tv_content)
    TextView mTvContent;
    private Intent networkIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        hideState();
        hideTitle();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setTitleName("主页");

        //监听网络变化
        networkIntent = new Intent(this, NetWorkService.class);
        startService(networkIntent);

        int width = DeviceUtil.getWidth(this);
        int height = DeviceUtil.getHeight(this);
        int stateHeight = DeviceUtil.getStateHeight(this);

        LogUtils.d("MainActivity onCreate()" + width);
        LogUtils.d("MainActivity onCreate()" + height);
        LogUtils.d("MainActivity onCreate()" + stateHeight);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(networkIntent);
    }

    @OnClick(R.id.tv_content)
    public void onViewClicked() {

        Message obtain = Message.obtain();

        obtain.arg1 = 10;
        obtain.arg2 = 100;
        mHandler.sendMessage(obtain);
    }

    @Override
    protected void handlerMsg(Message msg) {

        Toast.makeText(MainActivity.this, msg.arg1 + "==" + msg.arg2, Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, TranslucentActivity.class));
    }
}
