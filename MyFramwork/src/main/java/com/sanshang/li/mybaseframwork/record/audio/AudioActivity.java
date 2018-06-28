package com.sanshang.li.mybaseframwork.record.audio;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.record.TimeMethod;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cafe.adriel.androidaudioconverter.AndroidAudioConverter;
import cafe.adriel.androidaudioconverter.callback.IConvertCallback;
import cafe.adriel.androidaudioconverter.model.AudioFormat;

public class AudioActivity extends AppCompatActivity {

    @BindView(R.id.btn_start_record)
    Button mBtnStartRecord;
    @BindView(R.id.btn_stop_record)
    Button mBtnStopRecord;
    private AudioRecorder recorder;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        ButterKnife.bind(this);

        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

            }
        };
        recorder = new AudioRecorder();
        recorder.setListener(new RecordStreamListener() {
            @Override
            public void onRecording(byte[] audioData, int i, int length) {

                Log.d("--TAG--", "AudioActivity onRecording()" + audioData);
                Log.d("--TAG--", "AudioActivity onRecording()" + i);
                Log.d("--TAG--", "AudioActivity onRecording()" + length);
            }

            @Override
            public void finishRecord() {

                String mMinute1 = TimeMethod.getTime();
                Log.d("--TAG--" + Thread.currentThread().getName(), "AudioActivity finishRecord()" + "完成" + mMinute1);
            }

            @Override
            public void parseWavFinish(String fileName) {

                String mMinute1 = TimeMethod.getTime();
                Log.d("--TAG--" + Thread.currentThread().getName(), "AudioActivity parseWavFinish()" + "转化完成" + mMinute1);

                //已在子线程转换完成
                startParseMp3(fileName + ".wav");
            }
        });


    }

    private void startParseMp3(String filePath) {

        String path = new File(Environment.getExternalStorageDirectory().getPath()).getAbsolutePath();

        File wavFile = new File(path,filePath );
        IConvertCallback callback = new IConvertCallback() {
            @Override
            public void onSuccess(File convertedFile) {

                Log.d("--TAG--", "AudioActivity onSuccess()" + "Mp3转换成功" + convertedFile.getPath());
            }

            @Override
            public void onFailure(Exception error) {

                Log.d("--TAG--", "AudioActivity onSuccess()" + "Mp3转换失败" + error.getMessage());

            }
        };

        Log.d("--TAG--", "AudioActivity startParseMp3()" + "正在转换");
        AndroidAudioConverter.with(this)

                .setFile(wavFile)
                .setFormat(AudioFormat.MP3)
                .setCallback(callback)
                .convert();
    }

    @OnClick({R.id.btn_start_record, R.id.btn_stop_record, R.id.btn_pause_record,R.id.btn_parse_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start_record:

                String timeSign = TimeMethod.getTime();
                recorder.createDefaultAudio(timeSign);
                recorder.startRecord();

                break;
            case R.id.btn_pause_record:

                recorder.pauseRecord();
                break;
            case R.id.btn_stop_record:

                recorder.stopRecord();
                break;
            case R.id.btn_parse_main:

                startParseMp3("20180628-112122.wav");
                break;
        }
    }
}
