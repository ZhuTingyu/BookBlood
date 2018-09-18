package com.cpigeon.book.module.feedpigeon.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.DrugUseCaseEntity;
import com.cpigeon.book.model.entity.StatusIllnessRecordEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/10.
 */

public class SelectIllnessRecordAdapter extends BaseQuickAdapter<StatusIllnessRecordEntity, BaseViewHolder> {
    public SelectIllnessRecordAdapter() {
        super(R.layout.item_selecte_illness_record, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, StatusIllnessRecordEntity item) {
        helper.setText(R.id.tvFootNumber, item.getDiseaseTime());
        helper.setText(R.id.tvIllnessName, item.getPigeonDiseaseName());
    }
}
