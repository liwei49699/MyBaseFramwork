package com.sanshang.li.mybaseframwork.rxjava;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;
import com.qingmei2.rximagepicker.core.RxImagePicker;
import com.qingmei2.rximagepicker.entity.Result;
import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.base.BaseActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class MyOtherRxActivity extends BaseActivity {

    @BindView(R.id.btn_rx_image)
    Button mBtnRxImage;
    @BindView(R.id.iv_img)
    ImageView mIvImg;
    @BindView(R.id.btn_bind_view)
    Button mBtnBindView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_other_rx);
        ButterKnife.bind(this);

        mBtnRxImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onButtonClick();
            }
        });

        RxView.clicks(mBtnBindView)
                .compose(new RxPermissions(this).ensure(Manifest.permission.READ_EXTERNAL_STORAGE))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                        Toast.makeText(MyOtherRxActivity.this, "" + aBoolean, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //打开系统默认的图片选择器
    private void onButtonClick() {
        new RxImagePicker.Builder()
                .with(this)
                .build()
                .create(MyImagePicker.class)
                .openGallery()
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        Uri uri = result.getUri();
                        // 对图片进行处理，比如加载到ImageView中
                        Glide.with(mContext)
                                .load(uri)
                                .into(mIvImg);
                    }
                });
    }
}
