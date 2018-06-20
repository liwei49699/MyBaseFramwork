package com.sanshang.li.mybaseframwork.translucent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.rxjava.RxJavaActivity;
import com.sanshang.li.mybaseframwork.util.ToastUtils;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TranslucentActivity extends AppCompatActivity {

    @BindView(R.id.et_share)
    EditText mEtShare;

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        statusBarColor();
        statusBarIconColor();
        setContentView(R.layout.activity_translucent);
        ButterKnife.bind(this);

        mPreferences = getSharedPreferences("test", MODE_PRIVATE);

        mPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                ToastUtils.tMessage(key);

                startActivity(new Intent(TranslucentActivity.this, RxJavaActivity.class));
            }
        });
    }

    public void statusBarIconColor() {

        //状态栏中的文字颜色和图标颜色，需要android系统6.0以上，而且目前只有一种可以修改（一种是深色，一种是浅色即白色）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public void statusBarColor() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                //改为透明
                field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.btn_change)
    public void onViewClicked() {

        String s = mEtShare.getText().toString();
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putString("test",s);
        edit.apply();
    }
}
