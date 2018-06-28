package com.sanshang.li.mybaseframwork.record.audio;

import android.os.Environment;

import com.sanshang.li.mybaseframwork.record.TimeMethod;

import java.io.File;

/**
 * Created by li on 2018/6/28.
 * WeChat 18571658038
 * author LiWei
 */

public class FileUtils {

    /**
     * 通过文件名获取文件所在外部路径的目录
     * @param file
     * @return
     */
    public static String getAbsFilePath(String file) {

        return new File(Environment.getExternalStorageDirectory(), file).getAbsolutePath();
    }

    public static String getPcmFileAbsolutePath(String name) {

        //文件保存的文件夹
        String path = new File(Environment.getExternalStorageDirectory().getPath()).getAbsolutePath();
        File file = new File(path, name + ".pcm");
        return file.getAbsolutePath();
    }

    public static String getWavFileAbsolutePath(String name) {

        //文件保存的文件夹
        String path = new File(Environment.getExternalStorageDirectory().getPath()).getAbsolutePath();
        File file = new File(path, name + ".wav");
        return file.getAbsolutePath();
    }
}
