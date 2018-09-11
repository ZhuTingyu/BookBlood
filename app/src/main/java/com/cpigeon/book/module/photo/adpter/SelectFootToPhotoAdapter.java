package com.cpigeon.book.module.photo.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.module.photo.PigeonPhotoHomeActivity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/11.
 */

public class SelectFootToPhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SelectFootToPhotoAdapter() {
        super(R.layout.item_selecte_foot_to_photo, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.itemView.setOnClickListener(v -> {
            PigeonPhotoHomeActivity.start(getBaseActivity());
        });
    }
}
