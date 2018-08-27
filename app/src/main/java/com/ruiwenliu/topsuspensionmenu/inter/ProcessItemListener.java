package com.ruiwenliu.topsuspensionmenu.inter;

/**
 * Created by ruiwen
 * Data:2018/8/24 0024
 * Email:1054750389@qq.com
 * Desc:处理购物车加减事件
 */

public interface ProcessItemListener {
    void addItem(CommonItem item);

    void cutItem(CommonItem item);

}
