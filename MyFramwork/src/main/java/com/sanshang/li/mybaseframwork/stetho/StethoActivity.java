package com.sanshang.li.mybaseframwork.stetho;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.sanshang.li.mybaseframwork.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class StethoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stetho);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btn_debug_net, R.id.btn_debug_sp, R.id.btn_debug_db})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_debug_net:

                debugNet();
                break;
            case R.id.btn_debug_sp:

                debugSP();
                break;
            case R.id.btn_debug_db:

                SharedPreferences sp = getSharedPreferences("stetho", 0);
                boolean up = sp.getBoolean("up", false);
                String name = sp.getString("name","");

                Log.d("--TAG--", "StethoActivity onViewClicked()" + up + "===" + name);
                break;
        }
    }

    private void debugSP() {

        SharedPreferences sp = getSharedPreferences("stetho", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("name", "LiWei");
        edit.putBoolean("up", true);
        edit.commit();
    }

    private void debugNet() {

        OkHttpUtils.get()
                .url("https://api.douban.com/v2/movie/top250")
                .addParams("start","0")
                .addParams("count","10")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                System.out.println("请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.d("--TAG--" + Thread.currentThread().getName(), "StethoActivity onResponse()" + response);
                System.out.println("请求成功");
            }
        });

    }
}
