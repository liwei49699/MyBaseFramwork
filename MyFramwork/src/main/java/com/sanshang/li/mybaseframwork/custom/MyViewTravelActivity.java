package com.sanshang.li.mybaseframwork.custom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.custom.view.StatisticsView;

public class MyViewTravelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view_travel);

        //折线图
        startStatistics();
    }

    private void startStatistics() {

        final StatisticsView view = findViewById(R.id.statisticsView);
        view.setBottomStr(new String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期天"});
        view.setValues(new float[]{10f,90f,33f,66f,42f,99f,0f});

        findViewById(R.id.statisticsView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view.setBottomStr(new String[]{"星期一","星期二","星期三"});
                view.setValues(new float[]{10f,90f,33f});
            }
        });
    }
}
