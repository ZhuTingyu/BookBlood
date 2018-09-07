package com.cpigeon.book.module.trainpigeon.adpter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.cpigeon.book.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/7.
 */

public class TrainAnalyzeAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

    List<Integer> mIcon;

    public TrainAnalyzeAdapter() {
        super(R.layout.item_train_analyze, Lists.newArrayList());
        mIcon = Lists.newArrayList(R.mipmap.ic_train_frist
                , R.mipmap.ic_train_second
                , R.mipmap.ic_train_thrid);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imgOrder = helper.getView(R.id.imgOrder);
        TextView tvOrder = helper.getView(R.id.tvOrder);

        if(helper.getAdapterPosition() < 3){
            imgOrder.setVisibility(View.VISIBLE);
            tvOrder.setVisibility(View.GONE);
            imgOrder.setImageResource(mIcon.get(helper.getAdapterPosition()));
        }else {
            imgOrder.setVisibility(View.GONE);
            tvOrder.setVisibility(View.VISIBLE);
            tvOrder.setText(String.valueOf(helper.getAdapterPosition()));
        }

        helper.setText(R.id.tvFootNumber,"2018-22-1234567");
        helper.setText(R.id.tvColor,"雨色");
        helper.setText(R.id.tvBlood,"詹森");
        helper.setText(R.id.tvScore, Utils.getString(R.string.text_all_score,"1000"));
        helper.setText(R.id.tvSpeed,Utils.getString(R.string.text_all_speed,"1000"));

    }
}
