package com.sanshang.li.mybaseframwork.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.view.MyCircleBackProgressView;
import com.sanshang.li.mybaseframwork.view.MyHorizontalProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCustomViewActivity extends AppCompatActivity {

    @BindView(R.id.mhp_dialog)
    MyHorizontalProgressView mMhpDialog;
    @BindView(R.id.mcbp_dialog)
    MyCircleBackProgressView mMcbpDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_custom_view);
        ButterKnife.bind(this);

        mMhpDialog.setProgressWithAnimation(100).setProgressListener(new MyHorizontalProgressView.ProgressListener() {
            @Override
            public void currentProgressListener(float currentProgress) {

                Log.d("--TAG--", "MyCustomViewActivity currentProgressListener()" + currentProgress);
            }
        });
        mMhpDialog.startProgressAnimation();

        mMcbpDialog.setProgress(100).setProgressListener(new MyCircleBackProgressView.ProgressListener() {
            @Override
            public void currentProgressListener(float currentProgress) {

                Log.e("--TAG--", "currentProgressListener: " + currentProgress);
            }
        });

    }

    @OnClick(R.id.btn_start)
    public void onViewClicked() {

        mMhpDialog.setProgressWithAnimation(100);
        mMcbpDialog.setProgress(100);
    }
}
