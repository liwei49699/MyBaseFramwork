package com.sanshang.li.mybaseframwork.custom.gesture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;

import com.sanshang.li.mybaseframwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GestureActivity extends AppCompatActivity {

    @BindView(R.id.btn_change_ontouch)
    Button mBtnChangeOntouch;
    private GestureDetector mGestureDetector;
    private VelocityTracker mVelocityTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        ButterKnife.bind(this);

        initGesture();
        initClick();

        initVelocityTracker();

        //移动的只是view的内容 view的背景没有被移动
        //并不是向下移动25 **而是相反
        //mBtnChangeOntouch.scrollTo(0,25);
        //mBtnChangeOntouch.scrollBy();
    }

    private void initVelocityTracker() {

    }

    /**
     * 第一步：开始速度追踪
     * @param event
     */
    private void startVelocityTracker(MotionEvent event) {

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 第二步：获取追踪到的速度
     * @return
     */
    private int getScrollVelocity() {

        // 设置VelocityTracker单位.1000表示1秒时间内运动的像素
        mVelocityTracker.computeCurrentVelocity(1000);
        // 获取在1秒内X方向所滑动像素值
        int xVelocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(xVelocity);
    }

    /**
     * 第三步：解除速度追踪
     */
    private void stopVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private void initClick() {

        //将Button的onTouch事件交由GestureDetector处理
        mBtnChangeOntouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return mGestureDetector.onTouchEvent(event);
            }
        });
    }

    private void initGesture() {

        mGestureDetector = new GestureDetector(this, new GestureListenerImpl());
    }

    /**
     * 将Activity的onTouch时间交由GestureDetector处理
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return mGestureDetector.onTouchEvent(event);
    }
}