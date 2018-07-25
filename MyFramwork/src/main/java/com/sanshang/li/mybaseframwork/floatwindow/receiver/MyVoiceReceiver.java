package com.sanshang.li.mybaseframwork.floatwindow.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Vibrator;
import android.util.Log;

/**
 * 音量变化
 * Created by li on 2018/7/11.
 * WeChat 18571658038
 * author LiWei
 */

public class MyVoiceReceiver extends BroadcastReceiver {

    private AudioManager mAudioManager;
    private Vibrator mVibrator;
    /**
     * 系统最大音量
     */
    private int maxVolume;
    private int minVolume = 0;

    private int alarmCount = 3;
    private int upCount;
    private int downCount;
    private int tempVolume;

    /**
     * 判断报警按键的时间间隔
     */
    private long startTime;
    private long endTime;
    private long intervalTime;

    /**
     * 警报最短间隔时间
     */
    private long intervalAlarmTime = 3000L;

    public MyVoiceReceiver(AudioManager audioManager, int currVolume, Vibrator vibrator) {

        mAudioManager = audioManager;
        mVibrator = vibrator;
        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        tempVolume = currVolume;
    }

    @Override
    public void onReceive(Context context, Intent intent) {



        if(intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")){

            int currVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) ;// 当前的媒体音量
            Log.d("--TAG--", "MyVolumeReceiver onReceive()" + currVolume);

            boolean isMax = false;
            boolean isMin = false;
            if (currVolume == maxVolume) {

                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currVolume - 1,
                        AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

                isMax = true;

                addVoiceCount();

            } else if(currVolume == minVolume) {

                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currVolume + 1,
                        AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

                isMin = true;

                reduceVoiceCount();

            }

            //最小时会自动加一
            if((currVolume == tempVolume + 1) && !isMax) {

                addVoiceCount();

                //最大时会减一
            } else if((currVolume == tempVolume - 1) && !isMin) {

                reduceVoiceCount();

            }

            if(!(currVolume == maxVolume || currVolume == minVolume)) {

                tempVolume = currVolume;
            }

        }
    }

    private void reduceVoiceCount() {

        upCount = 0;
        downCount ++;

        endTime = System.currentTimeMillis();

        intervalTime = endTime - startTime;
        Log.d("--TAG--", "MyVoiceReceiver onReceive()" + "减1");

        if(downCount == 1) {

            startTime = System.currentTimeMillis();

        } else if(downCount >= alarmCount && intervalTime < intervalAlarmTime) {

            startAlarm(false);
        }
    }

    private void addVoiceCount() {

        downCount = 0;
        upCount ++;

        endTime = System.currentTimeMillis();

        intervalTime = endTime - startTime;

        Log.d("--TAG--", "MyVoiceReceiver onReceive()" + "加1");

        if(upCount == 1) {

            startTime = System.currentTimeMillis();

        } else if(upCount >= alarmCount && intervalTime < intervalAlarmTime) {

            startAlarm(true);
        }
    }

    /**
     * 报警方式
     * @param isAdd 是否是+按键
     */
    private void startAlarm(boolean isAdd) {


        if(isAdd) {

            upCount = 0;

        } else {

            downCount = 0;
        }

        mVibrator.vibrate(500);
    }
}
