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
        super(R.layout.item_pigeon_friend_msg, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonFriendMsgListEntity item) {

//        TextView tv_circle = helper.getView(R.id.tv_circle);
//        tv_circle.setVisibility(View.GONE);

        helper.setTextView(R.id.tv_title, item.getTitle());
        helper.setTextView(R.id.tv_time, item.getDate());
        helper.setTextView(R.id.tv_send_address, item.getSource());


//        TextView tv_title = helper.getView(R.id.tv_title);
//
//        if (item.getIsread().equals("1")) {
//            //消息已读
//            tv_circle.setVisibility(View.GONE);
//            tv_title.setTextColor(mContext.getResources().getColor(R.color.color_999999));
//        } else {
//            //消息未读
//            tv_circle.setVisibility(View.VISIBLE);
//            tv_title.setTextColor(mContext.getResources().getColor(R.color.color_010101));
//        }

        helper.setText(R.id.tv_content, item.getContent());

    }
}