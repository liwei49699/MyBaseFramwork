package com.sanshang.li.mybaseframwork.drag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyDragActivity extends AppCompatActivity {

    @BindView(R.id.rv_my_drag)
    RecyclerView mRvMyDrag;
    private MyDragListAdapter mDragadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_drag);
        ButterKnife.bind(this);

//        setTitleName("可拖拽的列表");

        initData();
        initEvent();
    }

    private void initEvent() {

        ItemTouchHelper helper = new ItemTouchHelper(new ItemDragHelperCallback(){

            @Override
            public boolean isLongPressDragEnabled() {
                // 长按拖拽打开
                return true;
            }
        });
        helper.attachToRecyclerView(mRvMyDrag);
        mRvMyDrag.setAdapter(mDragadapter);
    }

    private void initData() {

        List<String> strList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            strList.add(i + "");
        }

        mDragadapter = new MyDragListAdapter(this, strList);
//        mRvMyDrag.setAdapter(mDragadapter);
        mRvMyDrag.addItemDecoration(new ItemSpaceDecoration(getResources().getDimensionPixelSize(R.dimen.space_item)));
        mRvMyDrag.setLayoutManager(new GridLayoutManager(this,4));
    }
}
