package com.cpigeon.book.adpter;

import android.text.Editable;
import android.text.TextWatcher;
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
import com.base.util.utility.KeyboardUtils;
import com.base.util.utility.StringUtil;
import com.base.util.utility.ToastUtils;
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
    int leftMargin;
    int rightMargin;

    EditText mEditText;

    public FiltrateItemAdapter() {
        super(Lists.newArrayList());
        addItemType(SelectTypeEntity.TYPE_NORMAL, R.layout.item_filtrate_item);
        addItemType(SelectTypeEntity.TYPE_CUSTOM, R.layout.item_filtrate_custom_item);
        textWidth = ScreenTool.dip2px(90);
        textHeight = ScreenTool.dip2px(24);
        topMargin = ScreenTool.dip2px(16);
        leftMargin = ScreenTool.dip2px(10);
        rightMargin = ScreenTool.dip2px(10);
        rootWidth = ScreenTool.dip2px(280) / 3;
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectTypeEntity item) {
        //helper.setIsRecyclable(false);

        RelativeLayout root = helper.getView(R.id.rlRoot);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(rootWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, topMargin, 0, 0);
        root.setLayoutParams(layoutParams);

        if (helper.getAdapterPosition() % 3 == 0) {
            root.setGravity(Gravity.START);
        }

        if (helper.getAdapterPosition() % 3 == 1) {
            root.setGravity(Gravity.CENTER);
        }

        if (helper.getAdapterPosition() % 3 == 2) {
            root.setGravity(Gravity.END);
        }

        switch (helper.getItemViewType()) {
            case SelectTypeEntity.TYPE_NORMAL:
                TextView text = helper.getView(R.id.text);

                if (helper.getAdapterPosition() == 0) {
                    text.setText(Utils.getString(R.string.text_all));
                } else {
                    text.setText(item.getTypeName());
                }

                if (item.isSelect()) {
                    text.setBackgroundResource(R.drawable.shape_bg_filtrate_item_select);
                    text.setTextColor(Utils.getColor(R.color.colorPrimary));
                } else {
                    text.setBackgroundResource(R.drawable.shape_bg_corner_5_solid_gray);
                    text.setTextColor(Utils.getColor(R.color.black));
                }

                text.setOnClickListener(v -> {
                    if (helper.getAdapterPosition() == 0) {
                        resetSelect(true);
                        if (mEditText != null) {
                            mEditText.setText(StringUtil.emptyString());
                            mEditText.clearFocus();
                        }
                    } else {
                        multiSelect(helper.getAdapterPosition());
                        isAll(false);
                    }
                });

                break;

            case SelectTypeEntity.TYPE_CUSTOM:
                SelectTypeEntity custom = getData().get(getData().size() - 1);
                mEditText = helper.getView(R.id.edText);

                mEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String content = mEditText.getText().toString();
                        if (StringUtil.isStringValid(content)) {
                            resetSelect(false);
                            custom.setTypeName(mEditText.getText().toString());
                            custom.setSelect(true);
                        }else {
                            custom.setSelect(false);
                        }
                    }
                });
                break;
        }


    }

    public void multiSelect(int position) {
        isAll(false);
        SelectTypeEntity entity = getData().get(position);
        setSelect(entity, !entity.isSelect());
        notifyItemChanged(position);
    }

    private void isAll(boolean isSelect) {
        getData().get(0).setSelect(isSelect);
        notifyItemChanged(0);
    }

    private void setSelect(SelectTypeEntity entity, boolean isSelect) {
        entity.setSelect(isSelect);
    }

    public void resetSelect(boolean isHaveAll) {
        List<SelectTypeEntity> data = getData();
        for (int i = 0, len = data.size(); i < len; i++) {
            SelectTypeEntity entity = data.get(i);
            if (StringUtil.isStringValid(entity.getTypeID())) {
                setSelect(entity, false);
                notifyItemChanged(i);
            }
        }
        isAll(isHaveAll);
    }

    public List<SelectTypeEntity> getSelectItem() {
        List<SelectTypeEntity> selectItem = Lists.newArrayList();
        List<SelectTypeEntity> data = getData();
        for (SelectTypeEntity entity : data) {
            if (entity.isSelect()) {
                selectItem.add(entity);
            }
        }
        return selectItem;
    }

}