package com.sanshang.li.mybaseframwork.drag;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sanshang.li.mybaseframwork.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by li on 2018/6/21.
 * WeChat 18571658038
 * author LiWei
 */

public class MyDragListAdapter extends RecyclerView.Adapter<MyDragListAdapter.MyDragHolder> implements OnItemMoveListener{

    private MyDragActivity mActivity;
    private List<String> mList;

    public MyDragListAdapter(MyDragActivity activity, List<String> list) {

        mActivity = activity;
        mList = list;
    }

    @NonNull
    @Override
    public MyDragHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View dragItem = LayoutInflater.from(mActivity).inflate(R.layout.item_drag_list, parent, false);
        return new MyDragHolder(dragItem);
    }

    @Override
    public int getItemCount() {

        return mList == null ? 0 : mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyDragHolder holder, int position) {

        holder.mTvItemTitle.setText(mList.get(position));
    }

    /**
     * 移动交换位置
     * @param fromPosition
     * @param toPosition
     */
    @Override
    public void onItemMove(int fromPosition, int toPosition) {

        String item = mList.get(fromPosition);
        mList.remove(fromPosition);
        mList.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    class MyDragHolder extends RecyclerView.ViewHolder implements OnDragVHListener{

        @BindView(R.id.tv_item_title)
        TextView mTvItemTitle;

        public MyDragHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onItemSelected() {

            mTvItemTitle.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.drag_item_select));
        }

        @Override
        public void onItemFinish() {

            mTvItemTitle.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.drag_item_noraml));
        }
    }
}
