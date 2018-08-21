package com.cpigeon.book.adpter;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.system.ScreenTool;
import com.base.util.utility.StringUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.SelectTypeEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/20.
 */

public class FiltrateItemAdapter extends BaseMultiItemQuickAdapter<SelectTypeEntity, BaseViewHolder> {

    int rootWidth;
    int textWidth;
    int textHeight;
    int topMargin;

    EditText mEditText;

    public FiltrateItemAdapter(View rootView) {
        super(Lists.newArrayList());
        addItemType(SelectTypeEntity.TYPE_NORMAL, R.layout.item_filtrate_item);
        addItemType(SelectTypeEntity.TYPE_CUSTOM, R.layout.item_filtrate_custom_item);
        rootWidth = rootView.getWidth() / 3;
        textWidth = ScreenTool.dip2px(90);
        textHeight = ScreenTool.dip2px(24);
        topMargin = ScreenTool.dip2px(16);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectTypeEntity item) {
        RelativeLayout root = helper.getView(R.id.rlRoot);

        switch (helper.getItemViewType()) {
            case SelectTypeEntity.TYPE_NORMAL:
                TextView text = helper.getView(R.id.text);

                if (helper.getAdapterPosition() == 0) {
                    text.setText(Utils.getString(R.string.text_all));
                }

                if (helper.getAdapterPosition() % 3 == 1) {
                    text.setGravity(Gravity.CENTER);
                }

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(rootWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, topMargin, 0, 0);
                root.setLayoutParams(layoutParams);

                if (item.isSelect()) {
                    text.setBackgroundResource(R.drawable.shape_bg_filtrate_item_select);
                    text.setTextColor(Utils.getColor(R.color.colorPrimary));
                } else {
                    text.setBackgroundResource(R.drawable.shape_bg_corner_5_solid_gray);
                    text.setTextColor(Utils.getColor(R.color.black));
                }

                text.setOnClickListener(v -> {
                    if(helper.getAdapterPosition() == 0){
                        resetSelect();
                        if(mEditText != null){
                            mEditText.setText(StringUtil.emptyString());
                        }
                    }else {
                        multiSelect(helper.getAdapterPosition());
                    }
                });

                break;

            case SelectTypeEntity.TYPE_CUSTOM:
                mEditText = helper.getView(R.id.edText);
                mEditText.setOnFocusChangeListener((v, hasFocus) -> {
                    if(hasFocus){
                        resetSelect();
                    }
                });
                break;
        }


    }

    public void multiSelect(int position) {
        SelectTypeEntity entity = getData().get(position);
        setSelect(entity, !entity.isSelect());
        notifyItemChanged(position);
    }

    private void setSelect(SelectTypeEntity entity, boolean isSelect) {
        entity.setSelect(isSelect);
    }

    public void resetSelect() {
        List<SelectTypeEntity> data = getData();
        for (SelectTypeEntity entity : data) {
            setSelect(entity, false);
        }
        data.get(0).setSelect(true);
        notifyDataSetChanged();
    }

    public List<SelectTypeEntity> getSelectItem() {
        List<SelectTypeEntity> selectItem = Lists.newArrayList();
        List<SelectTypeEntity> data = getData();
        for (SelectTypeEntity entity : data) {
            if(entity.isSelect()){
                selectItem.add(entity);
            }
        }
        return selectItem;
    }

}