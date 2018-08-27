package com.ruiwenliu.topsuspensionmenu.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ruiwenliu.topsuspensionmenu.adapter.CardAdapter;
import com.ruiwenliu.topsuspensionmenu.inter.CommonItem;

/**
 * Created by ruiwen
 * Data:2018/8/23 0023
 * Email:1054750389@qq.com
 * Desc:子类
 */

public class SubclassBean implements MultiItemEntity,CommonItem {
    public String name;
    public String  price;
    public int number;
    public int maxNumber;
    public int cardId;
    public int  state;//是否销卡0、未销卡1、销卡

    @Override
    public int getItemType() {
        return CardAdapter.TYPE_ITEM_SUBCLASS;
    }

    @Override
    public int getGoodsItemType() {
        return CardAdapter.TYPE_GOODS_ITEM_CARD;
    }
}
