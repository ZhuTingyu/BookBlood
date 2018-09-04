package com.cpigeon.book.module.menu.service.adpter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseMultiSelectAdapter;
import com.base.entity.MultiSelectEntity;
import com.base.util.Lists;
import com.base.util.Utils;
import com.cpigeon.book.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/4.
 */

public class PayOpenServiceAdapter extends BaseMultiSelectAdapter<MultiSelectEntity, BaseViewHolder> {

    private List<Integer> icons = Lists.newArrayList(R.mipmap.ic_pay_by_score
            , R.mipmap.ic_pay_by_balance, R.mipmap.ic_pay_by_weixin);

    private List<String> titles;

    public PayOpenServiceAdapter() {
        super(R.layout.item_pay_open_service, Lists.newArrayList(new MultiSelectEntity()
                , new MultiSelectEntity(), new MultiSelectEntity()));
        titles = Lists.newArrayList(Utils.getApp().getResources().getStringArray(R.array.pay_ways));
    }

    @Override
    protected int getChooseDrawable() {
        return R.mipmap.ic_select_click;
    }

    @Override
    protected int getNotChooseDrawable() {
        return R.mipmap.ic_select_no;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiSelectEntity item) {
        super.convert(helper, item);
        helper.setText(R.id.tvName, titles.get(helper.getAdapterPosition()));
        ImageView icon = helper.getView(R.id.imgIcon);
        icon.setImageResource(icons.get(helper.getAdapterPosition()));

    }

}
