package com.ruiwenliu.topsuspensionmenu.adapter;

import android.view.ViewGroup;
import android.widget.SectionIndexer;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ruiwenliu.topsuspensionmenu.R;
import com.ruiwenliu.topsuspensionmenu.bean.LeftBean;
import com.ruiwenliu.topsuspensionmenu.bean.SubclassBean;

/**
 * Created by ruiwen
 * Data:2018/10/23 0023
 * Desc:原生的分类写法
 */

public class RightAdapter extends BaseQuickAdapter<Object, BaseViewHolder>  {

    public final int TYPE_RIGHT_HEADER = 1;

    public RightAdapter() {
        super(R.layout.item_card_subclassly);
    }

    @Override
    protected int getDefItemViewType(int position) {
        if (mData.get(position) instanceof LeftBean) {
            return TYPE_RIGHT_HEADER;
        }
        return super.getDefItemViewType(position);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_RIGHT_HEADER) {
            return createBaseViewHolder(getItemView(R.layout.item_leftly, parent));
        }
        return super.onCreateDefViewHolder(parent, viewType);
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_RIGHT_HEADER) {
            convertLeft(holder, mData.get(holder.getLayoutPosition() - getHeaderLayoutCount()));
        } else {
            super.onBindViewHolder(holder, position);
        }

    }


    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        SubclassBean sb= (SubclassBean) item;
        helper.setText(R.id.tv_name, sb.name);
    }


    protected void convertLeft(BaseViewHolder helper, Object item) {
        LeftBean bean = (LeftBean) item;
        helper.setText(R.id.tv_name, bean.name);
    }

   public LeftBean getSelectItem(int postion){
        if(mData.get(postion) instanceof  LeftBean){
            return (LeftBean) mData.get(postion);
        }
        return null;
   }

   public int  movePostion(LeftBean leftBean){
       if(leftBean==null){
           return 0;
       }
       return mData.indexOf(leftBean);
   }
}
