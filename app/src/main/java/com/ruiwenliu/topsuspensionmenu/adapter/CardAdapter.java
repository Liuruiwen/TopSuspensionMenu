package com.ruiwenliu.topsuspensionmenu.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ruiwenliu.topsuspensionmenu.R;
import com.ruiwenliu.topsuspensionmenu.bean.ParentBean;
import com.ruiwenliu.topsuspensionmenu.bean.SubclassBean;

import java.util.List;

/**
 * Created by ruiwen
 * Data:2018/8/23 0023
 * Email:1054750389@qq.com
 * Desc:
 */

public class CardAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_ITEM_PARENT = 0;//父类
    public static final int TYPE_ITEM_SUBCLASS = 1;//子类
    public static final int TYPE_GOODS_ITEM_CARD = 1001;//卡卷类型；

    public CardAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_ITEM_PARENT, R.layout.item_card_parently);
        addItemType(TYPE_ITEM_SUBCLASS, R.layout.item_card_subclassly);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_ITEM_PARENT:
                final ParentBean parentBean = (ParentBean) item;
                helper.setText(R.id.tv_title, parentBean.title);
                TextView tvCard = helper.getView(R.id.tv_card);
                ImageView ivShow = helper.getView(R.id.iv_show);
                tvCard.setBackgroundResource(parentBean.state == 1 ? R.drawable.shape_select : R.drawable.shape_select_no);
                tvCard.setText(parentBean.state == 1 ? "已销卡" : "销卡");
                tvCard.setTextColor(parentBean.state == 1 ? mContext.getResources().getColor(R.color.textGray) : mContext.getResources().getColor(R.color.colorWrite));
                ivShow.setImageResource(parentBean.postionState == 1 ? R.drawable.bottom : R.drawable.top);
                helper.addOnClickListener(R.id.tv_card);
                helper.addOnClickListener(R.id.iv_show);
                break;
            case TYPE_ITEM_SUBCLASS:
                SubclassBean subclassBean = (SubclassBean) item;
                helper.setGone(R.id.iv_cut, subclassBean.number > 0 ? true : false);
                helper.setText(R.id.tv_name, subclassBean.name);
                helper.setText(R.id.tv_price, "￥66.66");
                helper.setText(R.id.tv_num, String.valueOf(((SubclassBean) item).number));
                helper.setGone(R.id.tv_num, subclassBean.number > 0 ? true : false);
                helper.addOnClickListener(R.id.iv_add);
                helper.addOnClickListener(R.id.iv_cut);
                break;
        }
    }

    /**
     * 获取当前位置
     *
     * @param multiItemEntity
     * @return
     */
    public int getItemPostion(MultiItemEntity multiItemEntity) {
        return mData.indexOf(multiItemEntity);

    }

    /**
     * 销卡
     */
    public int  pinCard(int postion) {
        int num=0;
        if (mData.get(postion) instanceof ParentBean) {
            ParentBean parentBean = (ParentBean) mData.get(postion);
            parentBean.state = 1;
            if (!parentBean.isExpanded()) {
                expand(getItemPostion(parentBean));
                parentBean.postionState = 1;
            }
            if (parentBean.getSubItems() != null && parentBean.getSubItems().size() > 0) {
                for (SubclassBean subclassBean : parentBean.getSubItems()) {
                    num+=subclassBean.maxNumber-subclassBean.number;
                    subclassBean.number = subclassBean.maxNumber;

                }
            }
            notifyDataSetChanged();
        }
         return num;
    }

    /**
     * 展开/隐藏列表
     */
    public void showOrHideList(int postion) {
        if (mData.get(postion) instanceof ParentBean) {
            ParentBean parentBean = (ParentBean) mData.get(postion);
            int pos = getItemPostion(parentBean);
            parentBean.postionState = parentBean.isExpanded() ? 0 : 1;
            notifyItemChanged(pos);
            if (parentBean.isExpanded()) {
                collapse(pos);
            } else {
                expand(pos);
            }
        }

    }


    /**
     * 添加卡券
     *
     * @param postion
     */
    public int  addCard(int postion) {
        int num=0;
        if (mData.get(postion) instanceof SubclassBean) {
            SubclassBean subclassBean = (SubclassBean) mData.get(postion);
            if (subclassBean.number < subclassBean.maxNumber) {
                subclassBean.number++;
                num++;
                if (subclassBean.number == subclassBean.maxNumber) {
                    Toast.makeText(mContext, "已经到最大卡券值", Toast.LENGTH_SHORT).show();
                    updateParentAddState(subclassBean.cardId);
                }else {
                    notifyItemChanged(postion);
                }

            }
        }
        return num;
    }

    /**
     * 减少卡券
     *
     * @param postion
     */
    public int  cutCard(int postion) {
        int num=0;
        if (mData.get(postion) instanceof SubclassBean) {
            SubclassBean subclassBean = (SubclassBean) mData.get(postion);
            if (subclassBean.number > 0) {
                subclassBean.number--;
                num++;
                if (subclassBean.number == 9) {
                    updateParentCutState(subclassBean.cardId);
                } else {
                    notifyItemChanged(postion);
                }

            }
        }

        return num;
    }


    /**
     * 增卡更新父窗体操作
     *
     * @param cardId
     */
    private void updateParentAddState(int cardId) {
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i) instanceof ParentBean) {
                ParentBean parentBean = (ParentBean) mData.get(i);
                if (parentBean.cardId == cardId) {
                    for (int c = 0; c < parentBean.getSubItems().size(); c++) {
                        if (parentBean.getSubItems().get(c).number == parentBean.getSubItems().get(c).maxNumber) {
                            if (c == parentBean.getSubItems().size() - 1) {
                                parentBean.state=1;
                                notifyDataSetChanged();
                                break;
                            }
                            continue;
                        }
                        break;
                    }
                }

            }
        }
      notifyDataSetChanged();
    }

    /**
     * 减卡更新父类状态
     *
     * @param cardId
     */
    private void updateParentCutState(int cardId) {
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i) instanceof ParentBean) {
                ParentBean parentBean = (ParentBean) mData.get(i);
                if (parentBean.cardId == cardId && parentBean.state == 1) {
                    parentBean.state = 0;
                    break;
                }
            }
        }
        notifyDataSetChanged();

    }


}
