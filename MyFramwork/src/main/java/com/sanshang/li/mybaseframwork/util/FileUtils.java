package com.sanshang.li.mybaseframwork.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * Created by li on 2018/6/20.
 * WeChat 18571658038
 * author LiWei
 */

public class FileUtils {

    private static final int BUFFER = 8192;
    /**
     * @Description: 检查目录是否存在,不存在则创建
     *                 f.mkdir() //只能创建一级目录
     *                 f.mkdirs() //能创建多级目录
     * @param filePath
     * @return
     */
    public static boolean checkDir(String filePath) {

        File f = new File(filePath);
        if (!f.exists()) {
            return f.mkdirs();
        }
        return true;
    }

    /**
     * @Description: 判断SD卡是否存在,并且是否具有读写权限
     * @return
     */
    public static boolean isExistSD(){

        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            return true;
        }
        return false;
    }


    /**
     * Java文件操作 获取文件扩展名
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {

        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     *  获取缓存路径
     */
    public static String getCacheDir(Context context) {

        String cacheDir;
        if(FileUtils.isExistSD()){
            //SD存在
            cacheDir = FileUtils.getSDCacheDir(context);
        }else{
            //不存在则使用系统目录
            cacheDir = context.getCacheDir().getPath();
        }
        cacheDir+="/";

        return cacheDir;
    }
    /**
     *  获取SD卡路径
     */
    public static String getSDCardPath() {

        if (isExistSD()) {
            return Environment.getExternalStorageDirectory().toString() + "/";
        }
        return null;
    }

    /**
     * @Description: 获取当前应用SD卡缓存目录
     * @param context
     * @return
     */
    public static String getSDCacheDir(Context context) {

        //api大于8的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            //目录为/mnt/sdcard/Android/data/com.mt.mtpp/cache
            return context.getExternalCacheDir().getPath();
        }
        String cacheDir = "/Android/data/" + context.getPackageName() + "/cache";
        return Environment.getExternalStorageDirectory().getPath() + cacheDir;
    }

    /**
     * 通过文件名获取文件所在外部路径的目录
     * @param file
     * @return
     */
    public static String getgetAbsFilePath(String file) {

        return new File(Environment.getExternalStorageDirectory(), file).getAbsolutePath();
    }
}
