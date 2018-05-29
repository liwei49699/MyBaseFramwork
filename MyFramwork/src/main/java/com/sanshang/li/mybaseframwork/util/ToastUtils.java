package com.sanshang.li.mybaseframwork.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sanshang.li.mybaseframwork.base.MyApplication;

/**
 * Created by li on 2018/5/24.
 * WeChat 18571658038
 * author LiWei
 */

public class ToastUtils {

    /**
     * 默认显示
     */
    private static boolean isShow = true;
    /**
     * 全局唯一的Toast
     */
    private static Toast mToast;
    /**
     * private控制不应该被实例化
     */
    private ToastUtils() {
        throw new UnsupportedOperationException("不能被实例化");
    }

    /**
     * 全局控制是否显示Toast
     * @param isShowToast 是否显示
     */
    public static void controlShow(boolean isShowToast){

        isShow = isShowToast;
    }

    /**
     * Toast
     * @param object
     */
    public static void tMessage(Object object) {

        if(object != null) {

            showShort(MyApplication.getAppInstance(),object.toString());
        }
    }

    /**
     * 取消Toast显示
     */
    public void cancelToast() {

        if(isShow && mToast != null){
            mToast.cancel();
        }
    }

    /**
     * 短时间显示Toast
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {

        if (isShow){
            if (mToast == null) {
                mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(message);
            }
            mToast.show();
        }
    }

    /**
     * 长时间显示Toast
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow){
            if (mToast == null) {
                mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            } else {
                mToast.setText(message);
            }
            mToast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration 单位:毫秒
     */
    public static void show(Context context, CharSequence message, int duration) {

        if (isShow){
            if (mToast == null) {
                mToast = Toast.makeText(context, message, duration);
            } else {
                mToast.setText(message);
            }
            mToast.show();
        }
    }

    /**
     * 自定义Toast的View
     * @param context
     * @param message
     * @param duration 单位:毫秒
     * @param view 显示自己的View
     */
    public static void customToastView(Context context, CharSequence message, int duration,View view) {
        if (isShow){
            if (mToast == null) {
                mToast = Toast.makeText(context, message, duration);
            } else {
                mToast.setText(message);
            }
            if(view != null){
                mToast.setView(view);
            }
            mToast.show();
        }
    }

    /**
     * 自定义Toast的位置
     * @param context
     * @param message
     * @param duration 单位:毫秒
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void customToastGravity(Context context, CharSequence message, int duration,int gravity, int xOffset, int yOffset) {
        if (isShow){
            if (mToast == null) {
                mToast = Toast.makeText(context, message, duration);
            } else {
                mToast.setText(message);
            }
            mToast.setGravity(gravity, xOffset, yOffset);
            mToast.show();
        }
    }

    /**
     * 自定义带图片和文字的Toast，最终的效果就是上面是图片，下面是文字
     * @param context
     * @param message
     * @param iconResId 图片的资源id,如:R.drawable.icon
     * @param duration
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void showToastWithImageAndText(Context context, CharSequence message, int iconResId,int duration,int gravity, int xOffset, int yOffset) {
        if (isShow){
            if (mToast == null) {
                mToast = Toast.makeText(context, message, duration);
            } else {
                mToast.setText(message);
            }
            mToast.setGravity(gravity, xOffset, yOffset);
            LinearLayout toastView = (LinearLayout) mToast.getView();
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(iconResId);
            toastView.addView(imageView, 0);
            mToast.show();
        }
    }

    /**
     * 自定义Toast,针对类型CharSequence
     * @param context
     * @param message
     * @param duration
     * @param view
     * @param isGravity true,表示后面的三个布局参数生效,false,表示不生效
     * @param gravity
     * @param xOffset
     * @param yOffset
     * @param isMargin true,表示后面的两个参数生效，false,表示不生效
     * @param horizontalMargin
     * @param verticalMargin
     */
    public static void customToastAll(Context context, CharSequence message, int duration,View view,boolean isGravity,int gravity, int xOffset, int yOffset,boolean isMargin,float horizontalMargin, float verticalMargin) {
        if (isShow){
            if (mToast == null) {
                mToast = Toast.makeText(context, message, duration);
            } else {
                mToast.setText(message);
            }
            if(view != null){
                mToast.setView(view);
            }
            if(isMargin){
                mToast.setMargin(horizontalMargin, verticalMargin);
            }
            if(isGravity){
                mToast.setGravity(gravity, xOffset, yOffset);
            }
            mToast.show();
        }
    }

}
