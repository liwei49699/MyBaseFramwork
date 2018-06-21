package com.sanshang.li.mybaseframwork.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sanshang.li.mybaseframwork.base.MyApplication;

import java.util.List;

/**
 * Created by li on 2018/6/20.
 * WeChat 18571658038
 * author LiWei
 */

public class SPUtils {

    private static final String SP_NAME = "APP_NAME";
    private static SPUtils instance = new SPUtils();
    private SPUtils() {
    }

    public static SPUtils getInstance() {

        if (instance == null) {
            synchronized (SPUtils.class){
                if(instance == null) {

                    instance = new SPUtils();
                }
            }
        }
        return instance;
    }

    private SharedPreferences getSp() {

        return MyApplication.getAppInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public int getInt(String key, int def) {

        try {
            SharedPreferences sp = getSp();
            if (sp != null){

                def = sp.getInt(key, def);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putInt(String key, int val) {

        try {
            SharedPreferences sp = getSp();
            if (sp != null) {
                SharedPreferences.Editor e = sp.edit();
                e.putInt(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getLong(String key, long def) {

        try {
            SharedPreferences sp = getSp();
            if (sp != null){

                def = sp.getLong(key, def);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putLong(String key, long val) {

        try {
            SharedPreferences sp = getSp();
            if (sp != null) {
                SharedPreferences.Editor e = sp.edit();
                e.putLong(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getString(String key, String def) {

        try {
            SharedPreferences sp = getSp();
            if (sp != null){

                def = sp.getString(key, def);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putString(String key, String val) {

        try {
            SharedPreferences sp = getSp();
            if (sp != null) {
                SharedPreferences.Editor e = sp.edit();
                e.putString(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getBoolean(String key, boolean def) {

        try {
            SharedPreferences sp = getSp();
            if (sp != null){

                def = sp.getBoolean(key, def);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putBoolean(String key, boolean val) {

        try {
            SharedPreferences sp = getSp();
            if (sp != null) {
                SharedPreferences.Editor e = sp.edit();
                e.putBoolean(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeKey(String key) {

        try {
            SharedPreferences sp = getSp();
            if (sp != null) {
                SharedPreferences.Editor e = sp.edit();
                e.remove(key);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearSp() {

        try {
            SharedPreferences sp = getSp();
            if (sp != null) {
                SharedPreferences.Editor e = sp.edit();
                e.clear();
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用默认的SP
     * 当SP需要清除时使用
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getPrefBoolean(String key,boolean defaultValue) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.getAppInstance());

        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void setPrefBoolean(String key,boolean value) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.getAppInstance());

        sharedPreferences.edit().putBoolean(key, value).commit();
    }
}
