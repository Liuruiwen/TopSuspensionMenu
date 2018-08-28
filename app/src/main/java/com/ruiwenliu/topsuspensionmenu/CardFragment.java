package com.ruiwenliu.topsuspensionmenu;

import android.animation.Animator;
import android.nfc.NfcAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ruiwenliu.topsuspensionmenu.adapter.CardAdapter;
import com.ruiwenliu.topsuspensionmenu.bean.ParentBean;
import com.ruiwenliu.topsuspensionmenu.bean.SubclassBean;
import com.ruiwenliu.topsuspensionmenu.inter.OnDelayClickListener;
import com.ruiwenliu.topsuspensionmenu.util.AnimatorInter;
import com.ruiwenliu.topsuspensionmenu.util.CardAnimManager;
import com.ruiwenliu.topsuspensionmenu.util.DelayClickUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruiwen
 * Data:2018/8/23 0023
 * Email:1054750389@qq.com
 * Desc:复杂的列表处理
 */

public class CardFragment extends BaseFragment implements OnDelayClickListener{

    CardAdapter mAdapter = null;

    public static CardFragment getInstance() {
        return new CardFragment();
    }
    @Override
    protected void setData() {
        mAdapter = new CardAdapter(getList());
        goodsRv.setLayoutManager(new LinearLayoutManager(_mActivity));
        goodsRv.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new DelayClickUtil(this));
    }

    @Override
    protected void initView() {

    }
    @Override
    public void onDelayClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.tv_card:
                addItem(mAdapter.pinCard(position));
                break;
            case R.id.iv_show:
                mAdapter.showOrHideList(position);
                break;
            case R.id.iv_add:
                final int addNum=mAdapter.addCard(position);
                if(addNum>0){
                    CardAnimManager.getInstance(_mActivity).addGoodToCar(frameLayout, view, getCardView(), new AnimatorInter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            addItem(addNum);
                            //移除view
                            frameLayout.removeView( CardAnimManager.getInstance(_mActivity).getAnimView());
                        }
                    });
                }
                break;
            case R.id.iv_cut:
                cutItem(mAdapter.cutCard(position));
                break;
        }
    }

    private List<MultiItemEntity> getList() {
        List<MultiItemEntity> list = new ArrayList<>();
        for (int c = 0; c < 30; c++) {
            ParentBean parentBean = new ParentBean();
            parentBean.title = "王者卡";
            parentBean.cardId = 100 + c;
            for (int i = 0; i < 5; i++) {
                SubclassBean sb = new SubclassBean();
                sb.name = "南非砖石，你值得拥有";
                sb.maxNumber = 10;
                sb.cardId = parentBean.cardId;
                parentBean.addSubItem(sb);
            }
            list.add(parentBean);
        }

        return list;
    }



}
