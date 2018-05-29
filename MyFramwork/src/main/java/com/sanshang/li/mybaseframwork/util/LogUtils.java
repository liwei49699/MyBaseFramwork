package com.sanshang.li.mybaseframwork.util;
import android.util.Log;

/**
 * log工具类
 * @author liwei
 *
 */
public class LogUtils {

	private static boolean isDebug = true;
	public static String TAG = "--TAG--";

	public static void hideDebug(boolean isDebug){

		LogUtils.isDebug = !isDebug;
	}

	public static void d(String msg) {

		if (isDebug) {

			Log.d(TAG, msg);
		}
	}
	public static void e(String msg,Throwable e) {

		if (isDebug) {

			Log.d(TAG, msg,e);
		}
	}

	public static void d(String tag,String msg) {

		if (isDebug) {

			Log.d(TAG + tag, msg);
		}
	}
	public static void d(String tag,String msg,Throwable e) {

		if (isDebug) {
			Log.d(TAG + tag, msg,e);
		}
	}

	public static void e(String tag,String msg) {

		if (isDebug) {
			Log.e(TAG + tag, msg);
		}
	}

	public static void e(String tag,String msg,Throwable e) {

		if (isDebug) {
			Log.e(TAG + tag, msg,e);
		}
	}
}
