package com.ruiwenliu.topsuspensionmenu;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ruiwenliu.topsuspensionmenu.adapter.GoodsAdapter;
import com.ruiwenliu.topsuspensionmenu.adapter.TabAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ruiwen
 * Data:2018/8/16 0016
 * Email:1054750389@qq.com
 * Desc:
 */

public class GoodsFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {
    private GoodsAdapter mAdapter = null;
    private int type;


    public static GoodsFragment getInstance(int type) {
        Bundle bundl = new Bundle();
        bundl.putInt("type", type);
        GoodsFragment goodsFragment = new GoodsFragment();
        goodsFragment.setArguments(bundl);
        return goodsFragment;
    }


    @Override
    protected void initView() {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        mAdapter = new GoodsAdapter();
        mAdapter.setOnLoadMoreListener(this, goodsRv);
        goodsRv.setLayoutManager(new LinearLayoutManager(_mActivity));
        goodsRv.setAdapter(mAdapter);
        mAdapter.setNewData(getListData(type));

    }

    @Override
    protected void setData() {

    }

    @Override
    public void onLoadMoreRequested() {

        mAdapter.addData(getListData(type));
        mAdapter.loadMoreComplete();
    }

    private List<String> getListData(int type) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("买商品，卖商品，买卖商品,绝对的好商品，快来买咯" + type);
        }

        return list;
    }

}
