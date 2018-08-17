package com.cpigeon.book.module.menu.adapter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.AnnouncementNoticeEntity;

import java.util.List;

/**
 * 公告通知适配器
 * Created by Administrator on 2018/8/9.
 */

public class AnnouncementNoticeAdapter extends BaseQuickAdapter<AnnouncementNoticeEntity, BaseViewHolder> {

    public AnnouncementNoticeAdapter(List<AnnouncementNoticeEntity> data) {
        super(R.layout.item_annoucenment_notice, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnnouncementNoticeEntity item) {

        helper.setTextView(R.id.tv_title, item.getTitle());
        helper.setTextView(R.id.tv_time, item.getDate());
    }
}