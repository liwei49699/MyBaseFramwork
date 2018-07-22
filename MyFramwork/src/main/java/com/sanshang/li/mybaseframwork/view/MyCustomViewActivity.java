package com.sanshang.li.mybaseframwork.view;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCustomViewActivity extends BaseActivity {

//    @BindView(R.id.vf_imp)
//    ViewFlipper vfImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_custom_view);
        ButterKnife.bind(this);

//        for (int i = 0; i < 10; i++) {
//
//            TextView textView = new TextView(this);
//            textView.setText("wenbre" + i);
//
//            vfImp.addView(textView);
//        }
//
//        vfImp.setFlipInterval(2000);
//        vfImp.startFlipping();
    }
}
