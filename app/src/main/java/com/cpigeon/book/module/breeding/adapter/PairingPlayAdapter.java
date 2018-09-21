package com.cpigeon.book.module.breeding.adapter;

import android.content.Intent;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PriringRecommendEntity;
import com.cpigeon.book.module.breeding.PairingInfoRecommendFragment;

/**
 * Created by Administrator on 2018/9/11.
 */

public class PairingPlayAdapter extends BaseQuickAdapter<PriringRecommendEntity, BaseViewHolder> {


    public PairingPlayAdapter() {
        super(R.layout.item_pairing_recommend, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, PriringRecommendEntity item) {
        helper.setText(R.id.tv_foot, item.getFootRingNum());
        helper.setText(R.id.tv_hint_info, "第" + item.getMatchNumber() + "名");


        helper.getView(R.id.btn_click).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(IntentBuilder.KEY_DATA, item);
            getBaseActivity().setResult(PairingInfoRecommendFragment.RECOMMEND_REQUEST, intent);
            getBaseActivity().finish();
        });

    }
}
