package com.sanshang.li.mybaseframwork.floatwindow.view;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.floatwindow.util.FloatWindowManager;
import com.sanshang.li.mybaseframwork.systemfloat.FloatMenuActivity;

public class FloatWindowHintView extends LinearLayout {

	private final LinearLayout llRoot;
	/**
	 * 记录大悬浮窗的宽度
	 */
	public int viewWidth;

	/**
	 * 记录大悬浮窗的高度
	 */
	public int viewHeight;
	/**
	 * 提示是否显示
	 */
	private boolean mIsShowing = false;
	private Context mContext;

	public FloatWindowHintView(final Context context, String hint1) {
		super(context);
		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.float_window_hint, this);

		llRoot = findViewById(R.id.ll_root);
		viewWidth = llRoot.getLayoutParams().width;
		viewHeight = llRoot.getLayoutParams().height;

		((Button)findViewById(R.id.btn_do_thing)).setText(hint1);

		findViewById(R.id.btn_do_thing).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				//做逻辑操作
//				Toast.makeText(context, "21313123", Toast.LENGTH_SHORT).show();
//
//				//跳到填写报警信息界面
//				Intent intent = new Intent(mContext, FloatMenuActivity.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
//				try {
//					// 1.监听到Home键按下后 2.在桌面打开 activity
//					// 立即调用startActivity启动Activity会有5s延迟
//					pendingIntent.send();
//				} catch (PendingIntent.CanceledException e) {
//					e.printStackTrace();
//				}
//
//				FloatWindowManager.getInstance().dismissHintWindow();
//				FloatWindowManager.getInstance().applyOrShowFloatWindow(mContext);

				Button button = new Button(mContext);
				button.setText("1234");
				button.setWidth(200);
				button.setHeight(300);
				int height = button.getHeight();

				Toast.makeText(mContext, "" + height, Toast.LENGTH_SHORT).show();
				llRoot.addView(button);
			}
		});

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				//返回 销毁对话框
				FloatWindowManager.getInstance().dismissHintWindow();
//				FloatWindowManager.getInstance().applyOrShowFloatWindow(mContext);
			}
		});
	}

	public void setIsShowing(boolean isShowing) {

		mIsShowing = isShowing;
	}

	public boolean getIsShowing(){

		return mIsShowing;
	}
}
