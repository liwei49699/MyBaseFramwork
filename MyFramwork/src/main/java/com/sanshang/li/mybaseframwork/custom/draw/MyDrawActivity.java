package com.sanshang.li.mybaseframwork.custom.draw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.custom.progress.CompletedView;

public class MyDrawActivity extends AppCompatActivity {

    private int mTotalProgress = 90;
    private int mCurrentProgress = 0;
    //进度条
    private CompletedView mTasksView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_draw);

        mTasksView = findViewById(R.id.tasks_view);

        //开一个子线程去刷新进度，如果用系统handler去更新，
        // 阻塞主线程会出现界面刷新的卡顿情况，因为handler就是程操作主线程的
        new Thread(new ProgressRunable()).start();

        final MyDrawBoardView id = findViewById(R.id.mdbv_draw);

        findViewById(R.id.rl_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MyDrawActivity.this, "6756568", Toast.LENGTH_SHORT).show();
                id.clearContent();
            }
        });
    }

    class ProgressRunable implements Runnable {

        @Override
        public void run() {
            while (mCurrentProgress < mTotalProgress) {
                mCurrentProgress += 1;
                mTasksView.setProgress(mCurrentProgress);
                try {
                    Thread.sleep(90);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
