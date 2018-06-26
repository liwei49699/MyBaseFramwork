package com.sanshang.li.mybaseframwork.drag;

/**
 * ViewHolder 被选中 以及 拖拽释放 触发监听器
 * Created by li on 2018/6/21.
 * WeChat 18571658038
 * author LiWei
 */

public interface OnDragVHListener {

    /**
     * Item被选中时触发
     */
    void onItemSelected();

    /**
     * Item在拖拽结束/滑动结束后触发
     */
    void onItemFinish();
}
