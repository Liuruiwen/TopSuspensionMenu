package com.ruiwenliu.topsuspensionmenu.util;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ruiwenliu.topsuspensionmenu.inter.OnDelayClickListener;

import java.net.PortUnreachableException;
import java.util.Calendar;

/**
 * Created by ruiwen
 * Data:2018/8/28 0028
 * Email:1054750389@qq.com
 * Desc:
 */

public class DelayClickUtil implements BaseQuickAdapter.OnItemChildClickListener {
    private long lastClickTime=0;
    public OnDelayClickListener onDelayClickListener;
    public DelayClickUtil(OnDelayClickListener onDelayClickListener){
          this.onDelayClickListener=onDelayClickListener;
    }
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > CardAnimManager.actionTime) {
            lastClickTime = currentTime;
            onDelayClickListener.onDelayClick(adapter,view,position);
        }
    }
}
