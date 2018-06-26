package com.sanshang.li.mybaseframwork.drag;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by li on 2018/6/21.
 * WeChat 18571658038
 * author LiWei
 */

public class ItemSpaceDecoration extends RecyclerView.ItemDecoration{

    private int mSpace;

    public ItemSpaceDecoration(int space) {
        mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = mSpace;
        outRect.bottom = mSpace;
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) %4==0) {
            outRect.left = 0;
        }
    }
}
