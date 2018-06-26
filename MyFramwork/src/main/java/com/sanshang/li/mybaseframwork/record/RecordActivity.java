package com.sanshang.li.mybaseframwork.record;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.base.BaseActivity;
import com.sanshang.li.mybaseframwork.util.DateUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sanshang.li.mybaseframwork.record.CommonDefine.MSG_TIME_INTERVAL;
import static com.sanshang.li.mybaseframwork.record.CommonDefine.STATE_RECORD_START;
import static com.sanshang.li.mybaseframwork.record.CommonDefine.STATE_UNDEFINE;

public class RecordActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);

        setVoiceListener(new VoiceCallBack() {
            @Override
            public void voicePath(String path) {

                Log.d("--TAG--", "RecordActivity voicePath()" + path);
            }

            @Override
            public void recFinish() {

                Log.d("--TAG--", "RecordActivity recFinish()" + "完成");
            }
        });
    }

    @OnClick({R.id.btn_start_record, R.id.btn_pause_record, R.id.btn_finish_record, R.id.btn_start_play, R.id.btn_pause_play, R.id.btn_finish_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start_record:

                startRecord(true);
                break;
            case R.id.btn_pause_record:

                pauseRecord();
                break;
            case R.id.btn_finish_record:

                clickRecordFinish();
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

        if (mDeviceState == CommonDefine.STATE_RECORD_START) {

            mDeviceState = CommonDefine.STATE_RECORD_PAUSE;
            stopRecorder(mMediaRecorder, true);
            mMediaRecorder = null;
            Log.d("--TAG--", "RecordActivity pauseRecord()" + "暂停录制");
        } else {

            startRecord(false);
            Log.d("--TAG--", "RecordActivity pauseRecord()" + "继续录制");
        }
    }

    private VoiceCallBack voiceCallBack;

    public void setVoiceListener (VoiceCallBack callBack) {
        voiceCallBack = callBack;
    }

    /**
     * 完成录音
     */
    public void clickRecordFinish() {

        try {
            //停止录制
            mHandler.removeMessages(CommonDefine.MSG_TIME_INTERVAL);
            mDeviceState = CommonDefine.STATE_RECORD_STOP;
            stopRecorder(mMediaRecorder, true);
            mMediaRecorder = null;


            if (TimeMethod.timeSpanSecond(mRecTimeSum).mSpanSecond == 0) {

                Toast.makeText(mContext, "时间过短", Toast.LENGTH_SHORT).show();
            } else {

                File file = getOutputVoiceFile(mRecList);
                if (file != null && file.length() > 0) {
                    cleanFieArrayList(mRecList);
                    //TODO 这里可以返回数据 setResult
                    voiceCallBack.recFinish();
                    voiceCallBack.voicePath(file.getAbsolutePath());

                    Log.d("--TAG--", "RecordActivity clickRecordFinish()" + "录制完成");
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * 合并录音
     * @param list
     * @return
     */
    private File getOutputVoiceFile(List<File> list) {

        String mMinute1 = TimeMethod.getTime();
        //文件保存的文件夹
        String recFilePath = FileUtils.recAudioPath(filePath);
        File recDirFile = FileUtils.recAudioDir(recFilePath);

        if(recDirFile.exists()) {
         
            Log.d("--TAG--", "RecordActivity getOutputVoiceFile()" + "存在");
        }
        //创建音频文件,合并的文件放这里
        File resFile = new File(recDirFile, mMinute1 + ".amr");
        FileOutputStream fileOutputStream = null;

        if (!resFile.exists()) {
            try {
                resFile.createNewFile();
            } catch (IOException e) {
            }
        }
        try {
            fileOutputStream = new FileOutputStream(resFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // list里面为暂停录音 所产生的 几段录音文件的名字，中间几段文件的减去前面的6个字节头文件
        for (int i = 0; i < list.size(); i++) {
            File file = list.get(i);
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] myByte = new byte[fileInputStream.available()];
                // 文件长度
                int length = myByte.length;
                // 头文件
                if (i == 0) {
                    while (fileInputStream.read(myByte) != -1) {
                        fileOutputStream.write(myByte, 0, length);
                    }
                }
                // 之后的文件，去掉头文件就可以了
                else {
                    while (fileInputStream.read(myByte) != -1) {
                        fileOutputStream.write(myByte, 6, length - 6);
                    }
                }
                fileOutputStream.flush();
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 结束后关闭流
        try {
            fileOutputStream.close();
        } catch (IOException e) {
        }
        return resFile;
    }

    /**
     * 录制时间
     */
    private long mRecTimeSum = 0;

    /**
     * 录制文件
     */
    private List<File> mRecList = new ArrayList<>();

    /**
     * 录制的媒体类
     */
    private MediaRecorder mMediaRecorder = null;
    /**
     * 播放的媒体类
     */
    private MediaPlayer mMediaPlayer = null;
    /**
     * 录音所存放的位置
     * sd./audio
     */
    private String filePath = "/com.li.base/audio";
    private String mRecTimePrev;
    private String mPlaFilePath;

    private int mDeviceState = STATE_UNDEFINE;

    /**
     * 开始录制
     * @param init 是否是重新开始录制
     */
    private void startRecord(boolean init) {

        Log.d("--TAG--", "RecordActivity startRecord()" + "点击了");
        if (!FileUtils.isSDCardAvailable()){

            return;
        }
        if (init) {
            //清空暂停的临时文件
            mRecTimeSum = 0;
            cleanFieArrayList(mRecList);
        }
        //停止录制
        stopRecorder(mMediaRecorder, true);
        mMediaRecorder = null;

        //停止播放
        stopMedia(mMediaPlayer, true);
        mMediaPlayer = null;

        //初始化
        mMediaRecorder = new MediaRecorder();
        File file = prepareRecorder(mMediaRecorder, true);
        if (file != null) {
            //开始录制
            mDeviceState = STATE_RECORD_START;
            String timeStr = DateUtils.parseLongToString(System.currentTimeMillis(), DateUtils.DATE_SDF);
            
            Log.d("--TAG--", "RecordActivity startRecord()" + timeStr);
            mRecList.add(file);

            mHandler.removeMessages(MSG_TIME_INTERVAL);
            mHandler.sendEmptyMessage(MSG_TIME_INTERVAL);
        }
    }

    @Override
    protected void handlerMsg(Message msg) {

        TimeMethod ts;
        int current;
        try {
            switch (msg.what) {
                case MSG_TIME_INTERVAL:

                    //开始录制
                    if (mDeviceState == STATE_RECORD_START) {

                        ts = TimeMethod.timeSpanToNow(mRecTimePrev);
                        mRecTimeSum += ts.mDiffSecond;
                        mRecTimePrev = TimeMethod.getTimeStrFromMillis(ts.mNowTime);

                        ts = TimeMethod.timeSpanSecond(mRecTimeSum);

                        String format = String.format("%02d:%02d:%02d",
                                ts.mSpanHour, ts.mSpanMinute, ts.mSpanSecond);
                        Log.d("--TAG--", "RecordActivity handlerMsg()" + format);

                        mHandler.sendEmptyMessageDelayed(CommonDefine.MSG_TIME_INTERVAL, 1000);

                    //开始播放
                    } else if (mDeviceState == CommonDefine.STATE_PLAY_START) {
                        current = mMediaPlayer.getCurrentPosition();
                        Log.d("--TAG--", "RecordActivity handlerMsg()" + "进度:" + current);

                        ts = TimeMethod.timeSpanSecond(current / 1000);

                        String format = String.format("%02d:%02d:%02d",
                                ts.mSpanHour, ts.mSpanMinute, ts.mSpanSecond);
                        
                        Log.d("--TAG--", "RecordActivity handlerMsg()" + format);

                        mHandler.sendEmptyMessageDelayed(CommonDefine.MSG_TIME_INTERVAL, 1000);
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
        }
    }

    /**
     * 录音准备工作 ，开始录音
     * @param mr
     * @param start
     * @return 录制的文件
     */
    private File prepareRecorder(MediaRecorder mr, boolean start) {
        File recFile = null;
        if (mr == null)
            return null;

        try {
            //设置保存的文件夹
            String path = FileUtils.recAudioPath(filePath);
            
            Log.d("--TAG--", "RecordActivity prepareRecorder()" + path);

            //设置录制的文件
            recFile = new File(path, DateUtils.getTime() + ".amr");

            //配置
            mr.setAudioSource(MediaRecorder.AudioSource.MIC);
            mr.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mr.setOutputFile(recFile.getAbsolutePath());
            mr.prepare();

            //开始录制
            if (start) {
                mr.start();
            }
        } catch (Exception e) {
        }
        return recFile;
    }

    /**
     * 停止播放
     * @param mp
     * @param release
     * @return
     */
    private boolean stopMedia(MediaPlayer mp, boolean release) {
        boolean result = false;

        try {
            if (mp != null) {
                mp.stop();

                if (release) {
                    mp.release();
                }
                result = true;
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 停止录音
     * @param mr
     * @param release
     * @return
     */
    private boolean stopRecorder(MediaRecorder mr, boolean release) {
        boolean result = false;
        try {
            if (mr != null) {
                mr.stop();
                if (release) {
                    mr.release();
                }
                result = true;
            }
        } catch (Exception e) {
        }
        return result;
    }

    private void cleanFieArrayList(List<File> list) {
        for (File file : list) {
            file.delete();
        }
        list.clear();
    }
}
