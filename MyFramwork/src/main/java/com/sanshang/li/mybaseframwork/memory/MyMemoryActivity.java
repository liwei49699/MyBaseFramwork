package com.sanshang.li.mybaseframwork.memory;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.sanshang.li.mybaseframwork.MainActivity;
import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.memory.thread.MyOtherThread;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyMemoryActivity extends AppCompatActivity {

    private MyThread myThread;
    private MyOtherThread myOtherThread;
    private boolean isAlive;
    private MyStaticThread myStaticThread;
    private MyWeakThread myWeakThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_memory);
        ButterKnife.bind(this);

        isAlive = true;

        myThread = new MyThread();
        myOtherThread = new MyOtherThread();
        myStaticThread = new MyStaticThread();
        myWeakThread = new MyWeakThread(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        isAlive = false;
    }

    @OnClick({R.id.btn_execute, R.id.btn_execute_other,R.id.btn_execute_weak})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_execute:

                myThread.start();
                break;
            case R.id.btn_execute_other:

                //外部类不会隐式持有该类的引用，故不会内存泄漏
                myOtherThread.start();
                break;
            case R.id.btn_execute_weak:

                myWeakThread.start();
                break;
        }
    }

    /**
     * 非静态内部类会隐式持有外部类的引用
     * 造成内存泄漏
     */
    class MyThread extends Thread {

        @Override
        public void run() {

            for (int i = 0; i < 100; i++) {

                SystemClock.sleep(1000L);
                Log.d("--TAG--", "MyThread run()===" + i);
            }
        }
    }

    /**
     * 静态内部类不会持有外部类的引用
     */
    static class MyStaticThread extends Thread {

        @Override
        public void run() {

            for (int i = 0; i < 100; i++) {

                SystemClock.sleep(1000L);
                Log.d("--TAG--", "MyThread run()===" + i);
            }
        }
    }

    static class MyWeakThread extends Thread{

        WeakReference<MyMemoryActivity> mActivityWeakReference;
        public MyWeakThread(MyMemoryActivity activity) {

            mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {

            MyMemoryActivity activity = mActivityWeakReference.get();
            for (int i = 0; i < 100; i++) {

                if(activity != null && activity.isAlive) {

//                    SystemClock.sleep(1000L);
                    Log.d("--TAG--", "MyWeakThread run()===" + i);
                }
            }
        }
    }
}
