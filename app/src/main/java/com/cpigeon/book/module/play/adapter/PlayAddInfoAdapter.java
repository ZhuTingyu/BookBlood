package com.cpigeon.book.module.play.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PlayAdditionalInfoEntity;

/**
 * 附加信息
 * Created by Administrator on 2018/9/5.
 */

public class PlayAddInfoAdapter extends BaseQuickAdapter<PlayAdditionalInfoEntity, BaseViewHolder> {

    public PlayAddInfoAdapter() {
        super(R.layout.item_play_add_info, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, PlayAdditionalInfoEntity item) {

        helper.setText(R.id.tv_content, item.getMatchInfo());
    }
}
