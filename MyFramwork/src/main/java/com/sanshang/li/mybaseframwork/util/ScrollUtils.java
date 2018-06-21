package com.sanshang.li.mybaseframwork.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by li on 2018/6/20.
 * WeChat 18571658038
 * author LiWei
 */

public class ScrollUtils {

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public static void setListViewHeight(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
//            return;
        }

        int totalHeight = 0;
        View listItem = listAdapter.getView(0, null, listView);
        listItem.measure(0, 0);  //计算子项View 的宽高
        totalHeight = listItem.getMeasuredHeight() * 5;  //统计所有子项的总高度

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public static void setGridViewSelfHeight(GridView gridView , int Columns) {
        // 获取GridView对应的Adapter
        ListAdapter adapter = gridView.getAdapter();
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = adapter.getCount(); i < len; i++) {
            // gridView.getCount()返回数据项的数目
            View listItem = adapter.getView(i, null, gridView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            //此处方法并不好
            //Xml中的android:numColumns= getNumColumns()获取不到
            Columns = 3;
            //FontDisplayUtil.dip2px(MyGlobalApplication.getContext(),3)设置下边据
            totalHeight = totalHeight + listItem.getMeasuredHeight()/Columns + DeviceUtils.dp2px(gridView.getContext(),3);
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }
}
