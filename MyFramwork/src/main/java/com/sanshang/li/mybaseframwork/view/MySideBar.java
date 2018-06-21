package com.sanshang.li.mybaseframwork.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.util.DeviceUtils;

/**
 * 英文字母A-Z过滤控件
 * Created by li on 2018/6/20.
 * WeChat 18571658038
 * author LiWei
 */

public class MySideBar extends View {

    private char[] l;
    private SectionIndexer sectionIndexter = null;
    private ListView list;
    private TextView mDialogText;
    //字母所占高度
    private int m_nItemHeight;
    private boolean isFirst = true;
    public MySideBar(Context context) {
        this(context,null,0);
    }

    public MySideBar(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }


    public MySideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {

        l = new char[] { '↑','#', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'X', 'Y', 'Z' };
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //改进为  获取SideBar视图本身的高度 进行计算 设置字母所占高度
        if(isFirst){
            m_nItemHeight = (h- DeviceUtils.dp2px(getContext(),12))/l.length;
            isFirst = false;
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 获得 和索引列表匹配的 listview。
     * @param list0
     */
    public void setListView(ListView list0) {
        list = list0;
    }

    /**
     * 获得 和索引列表匹配的 TextView（提示字母）
     * @param mDialogText
     */
    public void setTextView(TextView mDialogText) {
        this.mDialogText = mDialogText;
    }

    /**
     * 触屏事件
     */
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int i = (int) event.getY();

        //根据触摸位置  计算出所触摸的字母
        int idx = i / m_nItemHeight;
        if (idx >= l.length) {
            idx = l.length - 1;
        } else if (idx < 0) {
            idx = 0;
        }
        //如果是触摸到 且滑动
        if (event.getAction() == MotionEvent.ACTION_DOWN
                || event.getAction() == MotionEvent.ACTION_MOVE) {
            mDialogText.setVisibility(View.VISIBLE);
            mDialogText.setText("" + l[idx]);
            if (sectionIndexter == null) {
                //获得 listview的adapter  带头视图的adapter
                HeaderViewListAdapter ha = (HeaderViewListAdapter) list.getAdapter();
                // 转为索引器
                sectionIndexter = (SectionIndexer) ha.getWrappedAdapter();
            }
            //根据选中的字母获取在ListView中的位置

            //  使用索引器获取当前字符对应的位置
            int position = sectionIndexter.getPositionForSection(l[idx]);

            //返回值为-2说明未找到   未找到不对list进行滑动处理
            if (position == -2) {
                return true;
            }else{
                list.setSelection(position+1);
            }
        } else {
            mDialogText.setVisibility(View.GONE);
        }
        return true;
    }

    /**
     * 绘制A-Z字母
     */
    protected void onDraw(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.color_gray_text));
        paint.setTextSize(DeviceUtils.sp2Px(getContext(),13));
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        paint.setTypeface(font);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setTextAlign(Paint.Align.CENTER);
        float widthCenter = getMeasuredWidth()/2;
        for (int i = 0; i < l.length; i++) {
            canvas.drawText(String.valueOf(l[i]), widthCenter, m_nItemHeight + (i * m_nItemHeight), paint);
        }
        super.onDraw(canvas);
    }
}
