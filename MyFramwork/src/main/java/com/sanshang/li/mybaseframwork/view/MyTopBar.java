package com.sanshang.li.mybaseframwork.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sanshang.li.mybaseframwork.R;

/**
 * Created by Administrator on 2018\6\15 0015.
 */

public class MyTopBar extends LinearLayout {


    private Context mContext;

    public MyTopBar(Context context) {
        this(context,null);
    }

    public MyTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        initView(context,attrs);
    }


    /**
     * 初始化View
     * @param context
     * @param attrs attrs文件
     */
    private void initView(Context context, AttributeSet attrs){
        //获取自定义View的值
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.MyTopBar);

//          <!--左边的返回键-->
//        <attr name="leftBack" format="reference"/>
//        <attr name="padding" format="dimension"/>
//
//        <!-- 标题 -->
//        <attr name="title" format="string"/>
//        <attr name="textSize" format="string"/>
//        <attr name="textColor" format="color"/>
//
//        <!--右边菜单-->
//        <attr name="rightText" format="string"/>
//        <attr name="rightMore" format="reference"/>
//
//        <!-- 背景 -->
//        <attr name="barBack" format="reference|color"/>
        Drawable leftBack = ta.getDrawable(R.styleable.MyTopBar_leftBack);
        float padding = ta.getDimension(R.styleable.MyTopBar_padding, 0);

        String title = ta.getString(R.styleable.MyTopBar_title);
        float textSize = ta.getDimension(R.styleable.MyTopBar_textSize, 0);
        int textColor = ta.getColor(R.styleable.MyTopBar_textColor,0);

        String rightText = ta.getString(R.styleable.MyTopBar_rightText);
        Drawable rightMore = ta.getDrawable(R.styleable.MyTopBar_rightMore);

        Drawable barBack = ta.getDrawable(R.styleable.MyTopBar_barBack);

        ta.recycle();  //回收

        View myTopBar = LayoutInflater.from(context).inflate(R.layout.bar_my_top, this, false);
        //初始化控件
        ImageView ivLeftBack = myTopBar.findViewById(R.id.iv_left_back);
        TextView tvTitleName = myTopBar.findViewById(R.id.tv_top_title);
        TextView tvRightMore = myTopBar.findViewById(R.id.tv_right_more);
        ImageView ivRightMore = myTopBar.findViewById(R.id.iv_right_more);

        //为控件设置值
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            myTopBar.setBackground(barBack);
        } else {
            myTopBar.setBackgroundDrawable(barBack);
        }

        ivLeftBack.setImageDrawable(leftBack);
//        int paddingPx = DeviceUtil.dp2px(context, padding);
        int paddingPx = (int) padding;
        ivLeftBack.setPadding(paddingPx,paddingPx,paddingPx,paddingPx);

        tvTitleName.setText(title);
        tvTitleName.setTextSize(textSize);
        tvTitleName.setTextColor(textColor);

        tvRightMore.setTextColor(textColor);
        tvRightMore.setText(rightText);
        tvRightMore.setTextSize(textSize);

        ivRightMore.setImageDrawable(rightMore);
        ivRightMore.setPadding(paddingPx,paddingPx,paddingPx,paddingPx);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(myTopBar,layoutParams);
    }

}
