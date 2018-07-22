package com.sanshang.li.mybaseframwork.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018\6\17 0017.
 * 自定义开关
 */

public class MyToggleButton extends View{

    private Context mContext;

    public MyToggleButton(Context context) {
        this(context,null,0);
    }

    public MyToggleButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyToggleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
    }

}
