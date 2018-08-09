package com.cpigeon.book.module.menu.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonFriendMsgListEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 */

public class PigeonFriendMsgAdapter extends BaseQuickAdapter<PigeonFriendMsgListEntity, BaseViewHolder> {

    public PigeonFriendMsgAdapter(List<PigeonFriendMsgListEntity> data) {
        super(R.layout.item_logbook, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonFriendMsgListEntity item) {

    }
}