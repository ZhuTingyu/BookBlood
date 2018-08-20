package com.cpigeon.book.module.foot.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseLetterSelectAdapter;
import com.base.util.Lists;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.CountyAreaEntity;
import com.cpigeon.book.model.entity.CountyEntity;

/**
 * Created by Zhu TingYu on 2018/8/20.
 */

public class SelectCountyAdapter extends BaseLetterSelectAdapter<CountyEntity, BaseViewHolder> {

    public SelectCountyAdapter() {
        super(R.layout.item_select_county, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, CountyEntity item) {

        ImageView imgRight = helper.getView(R.id.imgRight);
        TextView tvCode = helper.getView(R.id.tvCountyCode);
        if(StringUtil.isStringValid(item.getCode())){
            imgRight.setVisibility(View.GONE);
            tvCode.setVisibility(View.VISIBLE);
            tvCode.setText(item.getCode());
        }else {
            imgRight.setVisibility(View.VISIBLE);
            tvCode.setVisibility(View.GONE);
        }

        helper.setText(R.id.tvCountyName, item.getCountry());

    }
}
