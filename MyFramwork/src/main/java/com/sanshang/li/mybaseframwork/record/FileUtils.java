package com.sanshang.li.mybaseframwork.record;

import android.os.Environment;

import java.io.File;

/**
 * Created by li on 2018/6/25.
 * WeChat 18571658038
 * author LiWei
 */

public class FileUtils {

    /**
     * SD卡是否可用
     */
    public static boolean isSDCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static String recAudioPath(String file) {
        return new File(Environment.getExternalStorageDirectory(), file).getAbsolutePath();
    }

    public static File recAudioDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
