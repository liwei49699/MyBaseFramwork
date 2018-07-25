package com.sanshang.li.mybaseframwork.floatwindow;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.floatwindow.receiver.MyVoiceReceiver;
import com.sanshang.li.mybaseframwork.floatwindow.rom.HuaweiUtils;
import com.sanshang.li.mybaseframwork.floatwindow.rom.MeizuUtils;
import com.sanshang.li.mybaseframwork.floatwindow.rom.MiuiUtils;
import com.sanshang.li.mybaseframwork.floatwindow.rom.OppoUtils;
import com.sanshang.li.mybaseframwork.floatwindow.rom.QikuUtils;
import com.sanshang.li.mybaseframwork.floatwindow.rom.RomUtils;
import com.sanshang.li.mybaseframwork.floatwindow.service.FloatWindowService;
import com.sanshang.li.mybaseframwork.floatwindow.util.FloatWindowManager;

import java.io.IOException;

import java.lang.reflect.Method;

public class FloatWindowActivity extends AppCompatActivity {

    private String TAG = "--TAG--";
    private MyVoiceReceiver mVoiceReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_window);

        findViewById(R.id.btn_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean b = checkPermission(FloatWindowActivity.this);

                if(b) {
                    Intent intent = new Intent(FloatWindowActivity.this, FloatWindowService.class);
                    startService(intent);
                } else {

                    applyPermission(FloatWindowActivity.this);
                }
            }
        });

        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FloatWindowActivity.this, FloatWindowService.class);
                stopService(intent);
            }
        });

        myRegisterReceiver();

    }






    /**
     * 该方法主要根据图片名称获取可用的 Drawable
     *
     * @param imagename 选中和未选中使用的两张图片名称
     * @param context   上下文
     * @return 可用的Drawable
     */
    public Drawable getdrawble(String imagename, Context context) {
        Drawable drawable = null;
        Bitmap bitmap = null;
        try {
            String imagePath = "image/" + imagename + ".png";
            bitmap = BitmapFactory.decodeStream(context.getAssets().open(imagePath));
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
            drawable = bitmapDrawable;
        } catch (IOException e) {
            if (null != bitmap) {
                bitmap.recycle();
            }
            e.printStackTrace();
        }
        return drawable;
    }

    private void toast(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 注册当音量发生变化时接收的广播
     */
    private void myRegisterReceiver(){

        AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);;

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int minVolume = 0;
        // 当前的媒体音量
        int currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) ;

        if (currVolume == maxVolume) {

            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currVolume - 2,
                    AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
            currVolume = currVolume - 2;

        } else if(currVolume == minVolume) {

            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currVolume + 2,
                    AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

            currVolume = currVolume + 2;
        }


        Vibrator vibrator = (Vibrator)getSystemService(this.VIBRATOR_SERVICE);

        //开始注册声音广播
        mVoiceReceiver = new MyVoiceReceiver(audioManager,currVolume,vibrator);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(mVoiceReceiver, filter);
    }

    private void applyPermission(Context context) {
        if (Build.VERSION.SDK_INT < 23) {
            if (RomUtils.checkIsMiuiRom()) {
                miuiROMPermissionApply(context);
            } else if (RomUtils.checkIsMeizuRom()) {
                meizuROMPermissionApply(context);
            } else if (RomUtils.checkIsHuaweiRom()) {
                huaweiROMPermissionApply(context);
            } else if (RomUtils.checkIs360Rom()) {
                ROM360PermissionApply(context);
            } else if (RomUtils.checkIsOppoRom()) {
                oppoROMPermissionApply(context);
            }
        } else {
            commonROMPermissionApply(context);
        }
    }

    private Dialog dialog;

    private void showConfirmDialog(Context context, String message, final OnConfirmResult result) {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog = new AlertDialog.Builder(context).setCancelable(true).setTitle("")
                .setMessage(message)
                .setPositiveButton("现在去开启",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirmResult(true);
                                dialog.dismiss();
                            }
                        }).setNegativeButton("暂不开启",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirmResult(false);
                                dialog.dismiss();
                            }
                        }).create();
        dialog.show();
    }


    private void showConfirmDialog(Context context, OnConfirmResult result) {

        showConfirmDialog(context, "您的手机没有授予悬浮窗权限，请开启后再试", result);
    }

    private void ROM360PermissionApply(final Context context) {

        showConfirmDialog(context, new OnConfirmResult() {
            @Override
            public void confirmResult(boolean confirm) {
                if (confirm) {
                    QikuUtils.applyPermission(context);
                } else {
                    Log.e(TAG, "ROM:360, user manually refuse OVERLAY_PERMISSION");
                }
            }
        });
    }

    private interface OnConfirmResult {
        void confirmResult(boolean confirm);
    }

    private void huaweiROMPermissionApply(final Context context) {
        showConfirmDialog(context, new OnConfirmResult() {
            @Override
            public void confirmResult(boolean confirm) {
                if (confirm) {
                    HuaweiUtils.applyPermission(context);
                } else {
                    Log.e(TAG, "ROM:huawei, user manually refuse OVERLAY_PERMISSION");
                }
            }
        });
    }

    private void meizuROMPermissionApply(final Context context) {
        showConfirmDialog(context, new OnConfirmResult() {
            @Override
            public void confirmResult(boolean confirm) {
                if (confirm) {
                    MeizuUtils.applyPermission(context);
                } else {
                    Log.e(TAG, "ROM:meizu, user manually refuse OVERLAY_PERMISSION");
                }
            }
        });
    }

    private void miuiROMPermissionApply(final Context context) {
        showConfirmDialog(context, new OnConfirmResult() {
            @Override
            public void confirmResult(boolean confirm) {
                if (confirm) {
                    MiuiUtils.applyMiuiPermission(context);
                } else {
                    Log.e(TAG, "ROM:miui, user manually refuse OVERLAY_PERMISSION");
                }
            }
        });
    }

    private void oppoROMPermissionApply(final Context context) {
        showConfirmDialog(context, new OnConfirmResult() {
            @Override
            public void confirmResult(boolean confirm) {
                if (confirm) {
                    OppoUtils.applyOppoPermission(context);
                } else {
                    Log.e(TAG, "ROM:miui, user manually refuse OVERLAY_PERMISSION");
                }
            }
        });
    }

    /**
     * 通用 rom 权限申请
     */
    private void commonROMPermissionApply(final Context context) {
        //这里也一样，魅族系统需要单独适配
        if (RomUtils.checkIsMeizuRom()) {
            meizuROMPermissionApply(context);
        } else {
            if (Build.VERSION.SDK_INT >= 23) {

                showConfirmDialog(context, new OnConfirmResult() {
                    @Override
                    public void confirmResult(boolean confirm) {
                        if (confirm) {
                            try {
                                FloatWindowManager.commonROMPermissionApplyInternal(context);
                            } catch (Exception e) {
                                Log.e(TAG, Log.getStackTraceString(e));
                            }
                        } else {
                            Log.d(TAG, "user manually refuse OVERLAY_PERMISSION");
                            //需要做统计效果
                        }
                    }
                });
            }
        }
    }


    private boolean checkPermission(Context context) {
        //6.0 版本之后由于 google 增加了对悬浮窗权限的管理，所以方式就统一了
        if (Build.VERSION.SDK_INT < 23) {
            if (RomUtils.checkIsMiuiRom()) {
                return miuiPermissionCheck(context);
            } else if (RomUtils.checkIsMeizuRom()) {
                return meizuPermissionCheck(context);
            } else if (RomUtils.checkIsHuaweiRom()) {
                return huaweiPermissionCheck(context);
            } else if (RomUtils.checkIs360Rom()) {
                return qikuPermissionCheck(context);
            } else if (RomUtils.checkIsOppoRom()) {
                return oppoROMPermissionCheck(context);
            }
        }
        return commonROMPermissionCheck(context);
    }

    private boolean huaweiPermissionCheck(Context context) {
        return HuaweiUtils.checkFloatWindowPermission(context);
    }

    private boolean miuiPermissionCheck(Context context) {
        return MiuiUtils.checkFloatWindowPermission(context);
    }

    private boolean meizuPermissionCheck(Context context) {
        return MeizuUtils.checkFloatWindowPermission(context);
    }

    private boolean qikuPermissionCheck(Context context) {
        return QikuUtils.checkFloatWindowPermission(context);
    }

    private boolean oppoROMPermissionCheck(Context context) {
        return OppoUtils.checkFloatWindowPermission(context);
    }

    private boolean commonROMPermissionCheck(Context context) {
        //最新发现魅族6.0的系统这种方式不好用，天杀的，只有你是奇葩，没办法，单独适配一下
        if (RomUtils.checkIsMeizuRom()) {
            return meizuPermissionCheck(context);
        } else {
            Boolean result = true;
            if (Build.VERSION.SDK_INT >= 23) {
                try {
                    Class clazz = Settings.class;
                    Method canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context.class);
                    result = (Boolean) canDrawOverlays.invoke(null, context);
                } catch (Exception e) {
                    Log.e("--TAG--", Log.getStackTraceString(e));
                }
            }
            return result;
        }
    }
}
