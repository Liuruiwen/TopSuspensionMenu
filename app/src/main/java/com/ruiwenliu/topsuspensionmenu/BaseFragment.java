package com.ruiwenliu.topsuspensionmenu;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ruiwen
 * Data:2018/8/16 0016
 * Email:1054750389@qq.com
 * Desc:简写BaseFragment
 */

public abstract class BaseFragment extends Fragment {

    @BindView(R.id.goods_rv)
    RecyclerView goodsRv;
    @BindView(R.id.framelayout)
    FrameLayout frameLayout;
    private View mContentView;
    private Context mContext;
    Unbinder unbinder;
    public Activity _mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_goodsly, container, false);

        unbinder = ButterKnife.bind(this, mContentView);
        mContext = getContext();
        initView();
        setData();
        return mContentView;
    }

    /**
     * 一些Data的相关操作
     */
    protected abstract void setData();

    /**
     * 初始化view
     */
    protected abstract void initView();

    public View getContentView() {
        return mContentView;
    }

    public Context getMContext() {
        return mContext;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void addItem(int num){
        if(_mActivity instanceof MainActivity){
            ((MainActivity) _mActivity).addItem(num);
        }
    }

    public void cutItem(int num){
        if(_mActivity instanceof MainActivity){
            ((MainActivity) _mActivity).cutItem(num);
        }
    }

    public TextView getCardView(){
        if(_mActivity instanceof MainActivity){
           return  ((MainActivity) _mActivity).tvNum;
        }

        return null;
    }

}


