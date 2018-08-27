package com.ruiwenliu.topsuspensionmenu.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ruiwenliu.topsuspensionmenu.adapter.CardAdapter;

import java.util.List;

/**
 * Created by ruiwen
 * Data:2018/8/23 0023
 * Email:1054750389@qq.com
 * Desc:父类
 */

public class ParentBean extends AbstractExpandableItem<SubclassBean> implements MultiItemEntity {

    public String title;
    public int  state;//是否销卡0、未销卡1、销卡
    public int postionState;//1展开、0、隐藏
    public int cardId;

    @Override
    public int getLevel() {
        return CardAdapter.TYPE_ITEM_PARENT;
    }

    @Override
    public int getItemType() {
        return CardAdapter.TYPE_ITEM_PARENT;
    }
}
