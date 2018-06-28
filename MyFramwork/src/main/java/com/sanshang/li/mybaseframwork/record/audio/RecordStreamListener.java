package com.sanshang.li.mybaseframwork.record.audio;

/**
 * Created by li on 2018/6/28.
 * WeChat 18571658038
 * author LiWei
 */

public interface RecordStreamListener {

    /**
     * 录制中
     * @param audiodata
     * @param i
     * @param length
     */
    void onRecording(byte[] audiodata, int i, int length);

    /**
     * 录制结束
     */
    void finishRecord();

    /**
     * 转为wav
     * @param fileName
     */
    void parseWavFinish(String fileName);
}
