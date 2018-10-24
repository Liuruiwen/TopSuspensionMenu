package com.ruiwenliu.topsuspensionmenu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ruiwenliu.topsuspensionmenu.adapter.LeftAdapter;
import com.ruiwenliu.topsuspensionmenu.adapter.RightAdapter;
import com.ruiwenliu.topsuspensionmenu.bean.LeftBean;
import com.ruiwenliu.topsuspensionmenu.bean.SubclassBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ruiwen
 * Data:2018/10/23 0023
 * Desc:双向滚动的Recyclerview
 */

public class TwoWayActivity extends AppCompatActivity {

    @BindView(R.id.left_rv)
    RecyclerView leftRv;
    @BindView(R.id.right_rv)
    RecyclerView rightRv;

    public static Intent getIntent(Context context) {
        return new Intent(context, TwoWayActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_wayly);
        ButterKnife.bind(this);
        final LeftAdapter leftAdapter = new LeftAdapter();
        leftRv.setLayoutManager(new LinearLayoutManager(this));
        leftRv.setAdapter(leftAdapter);
        final RightAdapter rightAdapter = new RightAdapter();
        final LinearLayoutManager rightLayoutManager = new LinearLayoutManager(this);
        rightRv.setLayoutManager(rightLayoutManager);
        rightRv.setAdapter(rightAdapter);
        addItem(leftAdapter, rightAdapter);
        leftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                leftAdapter.setSelectItem(position);
                if (rightLayoutManager != null && rightAdapter != null) {
                    rightLayoutManager.scrollToPositionWithOffset(rightAdapter.movePostion(leftAdapter.getItem(position)), 0);
                    rightLayoutManager.setStackFromEnd(false);
                }
//                rightRv.smoothScrollToPosition(rightAdapter.moveItem(leftAdapter.getItem(position)));
            }
        });

        rightRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (rightAdapter != null && leftAdapter != null && rightAdapter.getSelectItem(firstVisibleItemPosition) != null) {
                    int postion = leftAdapter.getData().indexOf(rightAdapter.getSelectItem(firstVisibleItemPosition));
                    leftAdapter.setSelectItem(postion);
                    leftRv.scrollToPosition(postion);
                }

            }
        });

    }

    public void addItem(LeftAdapter leftAdapter, RightAdapter rightAdapter) {
        String[] nameGroup = {"张三", "李四", "王二", "刘亦菲", "张慧雯", "李连杰", "成龙", "吴倩", "李小龙", "韩红", "周杰伦", "那英", "刘飞", "王勃", "孙红雷"};
        List<LeftBean> leftBeans = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < nameGroup.length; i++) {
            LeftBean bean = new LeftBean();
            bean.name = nameGroup[i];
            leftBeans.add(bean);
            list.add(bean);
            for (int c = 0; c < 10; c++) {
                SubclassBean subclassBean = new SubclassBean();
                subclassBean.name = bean.name + i;
                list.add(subclassBean);
            }
        }
        leftAdapter.setNewData(leftBeans);
        rightAdapter.setNewData(list);
    }
}
