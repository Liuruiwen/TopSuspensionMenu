package com.ruiwenliu.topsuspensionmenu.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ruiwenliu.topsuspensionmenu.R;

import java.util.List;

/**
 * Created by ruiwen
 * Data:2018/8/20 0020
 * Email:1054750389@qq.com
 * Desc:
 */

public class HorizontalAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public HorizontalAdapter( @Nullable List<String> data) {
        super(R.layout.item_horizontaly, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Glide.with(mContext).load(item).into((ImageView) helper.getView(R.id.iv_mm));
    }
}
