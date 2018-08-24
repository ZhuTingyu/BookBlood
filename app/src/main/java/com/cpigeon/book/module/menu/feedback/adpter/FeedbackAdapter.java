package com.cpigeon.book.module.menu.feedback.adpter;

import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.FeedbackListEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 */

public class FeedbackAdapter extends BaseQuickAdapter<FeedbackListEntity, BaseViewHolder> {

    public FeedbackAdapter(List<FeedbackListEntity> data) {
        super(R.layout.item_feedback, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FeedbackListEntity item) {

        TextView status = helper.getView(R.id.tvStatus);
        String statusName = item.getStatusName();

        helper.setText(R.id.tvContent, item.getContent());
        helper.setText(R.id.tvTime, item.getDatetime());

        status.setText(statusName);

        if(Utils.getString(R.string.text_not_handle).equals(statusName)){
            status.setTextColor(Utils.getColor(R.color.color_feedback_not_handle));
        }else if(Utils.getString(R.string.text_handled).equals(statusName)){
            status.setTextColor(Utils.getColor(R.color.color_feedback_handled));
        }else if(Utils.getString(R.string.text_not_reply).equals(statusName)){
            status.setTextColor(Utils.getColor(R.color.colorPrimary));
        }
    }
}