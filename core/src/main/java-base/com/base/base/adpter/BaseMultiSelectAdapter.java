package com.base.base.adpter;


import android.support.annotation.LayoutRes;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;


import com.base.base.BaseViewHolder;
import com.base.entity.MultiSelectEntity;
import com.base.http.R;
import com.base.util.Lists;

import java.util.List;

/** checkbox 选择列表基类
 * Created by Zhu TingYu on 2017/11/20.
 */

public abstract class BaseMultiSelectAdapter<K extends MultiSelectEntity, B extends BaseViewHolder> extends BaseQuickAdapter<K, B> {

    protected List<Integer> selectedPositions;
    protected AppCompatImageView imgChoose;

    public BaseMultiSelectAdapter(@LayoutRes int id, List<K> data) {
        super(id, data);
    }


    @Override
    protected void convert(B holder, K item) {

        imgChoose = holder.findViewById(R.id.checkbox);

        imgChoose.setVisibility(item.isVisible ? View.VISIBLE : View.GONE);

        imgChoose.setBackgroundResource(item.isChoose ? getChooseDrawable() : getNotChooseDrawable());
    }

    protected abstract int getChooseDrawable();

    protected abstract int getNotChooseDrawable();

    protected void setChoose(MultiSelectEntity entity, boolean isChoose) {
        entity.isChoose = isChoose;
    }

    private void setAllChoose(List<K> productEntities, boolean isChoose){
        for(MultiSelectEntity productEntity : productEntities){
            setChoose(productEntity, isChoose);
        }
    }

    /**
     * 获取选择的下标
     * @return
     */

    public List<Integer> getSelectedPotion(){
        selectedPositions = Lists.newArrayList();
        for(int i = 0; i < mData.size(); i++){
            if(mData.get(i).isChoose){
                selectedPositions.add(i);
            }
        }
        return selectedPositions;
    }

    /**
     * 获取选择的数据
     * @return
     */

    public List<K> getSelectedEntity(){
        List<K> list = Lists.newArrayList();
        for(int i = 0; i < mData.size(); i++){
            if(mData.get(i).isChoose){
                list.add(mData.get(i));
            }
        }
        return list;
    }

    /**
     * 删除选择的项
     */

    public void deleteChoose() {
        for (int i = 0; i < mData.size();) {
            if (mData.get(i).isChoose) {
                remove(i);
                continue;
            }
            i++;
        }
    }

    /**
     * 是否全选
     * @param isChooseAll
     */

    public void isChooseAll(boolean isChooseAll) {
        setAllChoose(mData ,isChooseAll);
        notifyDataSetChanged();
    }

    /**
     * 多选
     * @param item
     * @param position
     */

    public void setMultiSelectItem(K item, int position){
        setChoose(item, !item.isChoose);
        notifyItemChanged(position);
    }

    /**
     * 单选
     * @param item
     * @param position
     */

    public void setSingleItem(K item, int position){
        for (int i = 0; i < mData.size(); i++) {
            if(mData.get(i).isChoose){
                mData.get(i).isChoose = false;
            }
        }
        setChoose(item, true);
        notifyDataSetChanged();
    }
}
