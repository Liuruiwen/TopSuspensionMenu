package com.ruiwenliu.topsuspensionmenu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ruiwenliu.topsuspensionmenu.R;

import java.util.List;

/**
 * Created by ruiwen
 * Data:2018/8/16 0016
 * Email:1054750389@qq.com
 * Desc:
 */

public class GoodsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public GoodsAdapter() {
        super(R.layout.item_goodsly);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_context, item);
    }
}
