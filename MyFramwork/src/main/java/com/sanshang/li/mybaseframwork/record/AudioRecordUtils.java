package com.sanshang.li.mybaseframwork.record;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Handler;
import android.util.Log;

import com.sanshang.li.mybaseframwork.util.DateUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sanshang.li.mybaseframwork.record.CommonDefine.STATE_UNDEFINE;

/**
 * Created by Administrator on 2017/7/5.
 */

public class AudioRecordUtils {

    //单个文件路径
    private String mFilePath;
    //文件夹路径
    private String FolderPath;

    private MediaRecorder mMediaRecorder;
    private final String TAG = "--TAG--";
    public static final int MAX_LENGTH = 1000 * 60 * 10;// 最大录音时长1000*60*10;

    private long startTime;
    private long endTime;

    /**
     * 多媒体例如声音的状态
     */
    public final static int STATE_UNDEFINE        = 0X0001;

    public final static int STATE_RECORD_START    = 0X0002;
    public final static int STATE_RECORD_PAUSE    = 0X0003;
    public final static int STATE_RECORD_STOP     = 0X0004;

    private int mRecordState = STATE_UNDEFINE;

    /**
     * 录制时间
     */
    private long mTimeRecord = 0L;

    /**
     * 录制文件
     */
    private static List<File> mRecordList = new ArrayList<>();

    //录制音频状态监听器
    private OnAudioStatusUpdateListener audioStatusUpdateListener;

    /**
     * 文件存储默认sdcard/record
     */
    public AudioRecordUtils(Context context){

        // adnroid/data/包名/cache
        this(context.getExternalCacheDir().toString() + "/audios/");
    }

    public AudioRecordUtils(String filePath) {

        File path = new File(filePath);
        if(!path.exists())
            path.mkdirs();

        FolderPath = filePath;
    }

    /**
     * 开始录音 使用amr格式
     *      录音文件
     * @return
     */
    public void startRecord() {

        // 开始录音
        /* ①Initial：实例化MediaRecorder对象 */
        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();
        try {
            /* ②setAudioSource/setVedioSource */
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            mFilePath = FolderPath + DateUtils.getTime() + ".amr" ;
            /* ③准备 */
            mMediaRecorder.setOutputFile(mFilePath);
            mMediaRecorder.setMaxDuration(MAX_LENGTH);
            mMediaRecorder.prepare();
            /* ④开始 */
            mMediaRecorder.start();
            // AudioRecord audioRecord.
            /* 获取开始时间* */
            startTime = System.currentTimeMillis();

            updateMicStatus();

            mRecordList.add(new File(mFilePath));

            mRecordState = STATE_RECORD_START;

            Log.d("--TAG--", "AudioRecordUtils startRecord()" + startTime);
        } catch (IllegalStateException e) {
            Log.i(TAG, "call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        } catch (IOException e) {
            Log.i(TAG, "call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        }
    }

    public void pauseRecord(){

        mRecordState = STATE_RECORD_PAUSE;

        if (mMediaRecorder == null){

            return;
        }
        endTime = System.currentTimeMillis();

        //有一些网友反应在5.0以上在调用stop的时候会报错，翻阅了一下谷歌文档发现上面确实写的有可能会报错的情况，捕获异常清理一下就行了，感谢大家反馈！
        try {
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;

            audioStatusUpdateListener.onStop(mFilePath);
            mFilePath = "";

        }catch (RuntimeException e){
            /*mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;*/
            e.printStackTrace();

            File file = new File(mFilePath);
            if (file.exists())
                file.delete();

            mFilePath = "";
        }
    }

    /**
     * 停止录音
     */
    public void stopRecord() {

        if(mRecordState == CommonDefine.STATE_RECORD_PAUSE) {

        } else {

            if (mMediaRecorder == null){

                return;
            }
            endTime = System.currentTimeMillis();

            //有一些网友反应在5.0以上在调用stop的时候会报错，翻阅了一下谷歌文档发现上面确实写的有可能会报错的情况，捕获异常清理一下就行了，感谢大家反馈！
            try {
                mMediaRecorder.stop();
                mMediaRecorder.reset();
                mMediaRecorder.release();
                mMediaRecorder = null;

                audioStatusUpdateListener.onStop(mFilePath);
                mFilePath = "";

            }catch (RuntimeException e){
            /*mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;*/

                File file = new File(mFilePath);
                if (file.exists())
                    file.delete();

                mFilePath = "";

            }
        }

        mRecordState = STATE_RECORD_STOP;

        File file = getOutputVoiceFile(mRecordList);

        if (file != null && file.length() > 0) {

            for (File file1 : mRecordList) {

                file1.delete();
            }
            mRecordList.clear();
            Log.d("--TAG--", "RecordActivity clickRecordFinish()" + "录制完成");
        }
    }

    private File getOutputVoiceFile(List<File> list) {

        String mMinute = TimeMethod.getTime();
        //文件保存的文件夹
//        String recFilePath = FileUtils.recAudioPath(FolderPath);
        File recDirFile = FileUtils.recAudioDir(FolderPath);

        if(recDirFile.exists()) {

            Log.d("--TAG--", "RecordActivity getOutputVoiceFile()" + "存在");
        }
        //创建音频文件,合并的文件放这里
        File resFile = new File(recDirFile, mMinute + ".mp3");
        FileOutputStream fileOutputStream = null;

        if (!resFile.exists()) {
            try {
                resFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        Log.d("--TAG--", "AudioRecordUtils getOutputVoiceFile()" + resFile.exists());
        
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
                // 注意:amr格式的头文件为6个字节的长度
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
     * 取消录音
     */
    public void cancelRecord(){

        try {
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;

        }catch (RuntimeException e){
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
        File file = new File(mFilePath);
        if (file.exists())
            file.delete();

        mFilePath = "";
    }

    private final Handler mHandler = new Handler();

    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {

            updateMicStatus();
        }
    };


    private int BASE = 1;

    /**
     * 间隔取样时间
     */
    private static final int SPACE = 1000;

    public void setOnAudioStatusUpdateListener(OnAudioStatusUpdateListener audioStatusUpdateListener) {
        this.audioStatusUpdateListener = audioStatusUpdateListener;
    }

    /**
     * 更新麦克状态
     */
    private void updateMicStatus() {

        if (mMediaRecorder != null && audioStatusUpdateListener != null) {

            audioStatusUpdateListener.onUpdate(System.currentTimeMillis()-startTime);
        }
        mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
    }

    public interface OnAudioStatusUpdateListener {
        /**
         * 录音中...
         * @param time 录音时长
         */
        void onUpdate(long time);

        /**
         * 停止录音
         * @param filePath 保存路径
         */
        void onStop(String filePath);
    }
}
