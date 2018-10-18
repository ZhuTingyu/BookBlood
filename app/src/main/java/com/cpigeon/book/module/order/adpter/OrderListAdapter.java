package com.cpigeon.book.module.order.adpter;

import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.OrderEntity;

/**
 * Created by Zhu TingYu on 2018/8/27.
 */

public class OrderListAdapter extends BaseQuickAdapter<OrderEntity, BaseViewHolder> {
    public OrderListAdapter() {
        super(R.layout.item_order_list, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderEntity item) {

        helper.setText(R.id.tvTime, item.getDatetime());


        TextView tv_status = helper.getView(R.id.tvStatus);
        if (item.getState().equals("0")) {
            //待支付
            tv_status.setText("去支付");
            tv_status.setTextColor(mContext.getResources().getColor(R.color.color_order_price));
        } else if (item.getState().equals("1")) {
            //已支付
            tv_status.setText("已完成");
            tv_status.setTextColor(mContext.getResources().getColor(R.color.color_feedback_not_handle));
        } else if (item.getState().equals("2")) {
            //已过期
            tv_status.setText("已过期");
            tv_status.setTextColor(mContext.getResources().getColor(R.color.color_4c4c4c));
        }

        helper.setText(R.id.tvOrderContent, item.getItem());//名称

        helper.setText(R.id.tvOrderId, "订单编号：" + item.getDdbh());//订单编号

        helper.setText(R.id.tvOderPrice, item.getRmb() + "元");

        helper.setText(R.id.tvOrderSource, "(" + item.getLy() + ")");//订单来源
    }
}
