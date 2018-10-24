package com.ruiwenliu.topsuspensionmenu.adapter;

import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ruiwenliu.topsuspensionmenu.R;
import com.ruiwenliu.topsuspensionmenu.bean.LeftBean;

/**
 * Created by ruiwen
 * Data:2018/10/23 0023
 * Desc:
 */

public class LeftAdapter extends BaseQuickAdapter<LeftBean, BaseViewHolder> {
    public LeftAdapter() {
        super(R.layout.item_leftly);
    }

    @Override
    protected void convert(BaseViewHolder helper, LeftBean item) {
        helper.setText(R.id.tv_name,item.name);
        helper.setBackgroundColor(R.id.tv_name, ContextCompat.getColor(mContext, selectPostion == getItemPostion(item) ? R.color.colorWrite : R.color.colorLine));
    }

    int selectPostion;

    public void setSelectItem(int position) {
        selectPostion = position;
        notifyDataSetChanged();
    }

    public int getItemPostion(LeftBean item) {
        return mData.indexOf(item);
    }
}
