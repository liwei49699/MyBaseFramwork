package com.sanshang.li.mybaseframwork.record;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sanshang.li.mybaseframwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtherRecordActivity extends AppCompatActivity {

    @BindView(R.id.btn_start_record)
    Button mBtnStartRecord;
    @BindView(R.id.btn_pause_record)
    Button mBtnPauseRecord;
    @BindView(R.id.btn_finish_record)
    Button mBtnFinishRecord;
    @BindView(R.id.btn_start_play)
    Button mBtnStartPlay;
    @BindView(R.id.btn_pause_play)
    Button mBtnPausePlay;
    @BindView(R.id.btn_finish_play)
    Button mBtnFinishPlay;
    private AudioRecordUtils recordUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_record);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_start_record, R.id.btn_pause_record, R.id.btn_finish_record, R.id.btn_start_play, R.id.btn_pause_play, R.id.btn_finish_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start_record:

                startRecord();
                break;
            case R.id.btn_pause_record:

                pauseRecord();
                break;
            case R.id.btn_finish_record:

                stopRecord();
                break;
            case R.id.btn_start_play:
                break;
            case R.id.btn_pause_play:
                break;
            case R.id.btn_finish_play:
                break;
        }
    }

    private void pauseRecord() {

        recordUtils.pauseRecord();
    }

    private void stopRecord() {

        recordUtils.stopRecord();
    }

    private void startRecord() {

        recordUtils = new AudioRecordUtils(this);
        recordUtils.startRecord();

        recordUtils.setOnAudioStatusUpdateListener(new AudioRecordUtils.OnAudioStatusUpdateListener() {
            @Override
            public void onUpdate(long time) {
                
                Log.d("--TAG--" , "OtherRecordActivity onUpdate()" + time);
            }

            @Override
            public void onStop(String filePath) {

                Log.d("--TAG--", "OtherRecordActivity onStop()" + filePath);
            }
        });
    }
}
