package com.sanshang.li.mybaseframwork.observer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sanshang.li.mybaseframwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestObserverActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.btn_test)
    Button mBtnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_observer);
        ButterKnife.bind(this);

        mBtnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.btn_test :

                testObserver();
                break;
        }
    }

    private void testObserver() {

        User user1 = new User("张三");
        User user2 = new User("李四");
        User user3 = new User("王五");

        News news = new News();
        news.registerObserver(user1);
        news.registerObserver(user2);
        news.registerObserver(user3);

        Phone android = new Phone("安卓");
        Phone ios = new Phone("苹果");

        user1.registerObserver(android);
        user2.registerObserver(ios);

        Log.d("--TAG--", "TestObserverActivity testObserver()" + "新闻发布了");
        news.notifyObserver();
    }
}
