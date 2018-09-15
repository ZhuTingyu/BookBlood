package com.cpigeon.book.module.breeding.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PriringRecommendEntity;

/**
 * Created by Administrator on 2018/9/11.
 */

public class PairingLineageAdapter extends BaseQuickAdapter<PriringRecommendEntity, BaseViewHolder> {


    public PairingLineageAdapter() {
        super(R.layout.item_pairing_recommend, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, PriringRecommendEntity item) {

        helper.setText(R.id.tv_foot,item.getFootRingNum());
        helper.setText(R.id.tv_hint_info,item.getPigeonBloodName());
    }
}
