package com.cpigeon.book.module.trainpigeon.adpter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.TrainAnalyseEntity;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.util.MathUtil;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/7.
 */

public class TrainAnalyzeAdapter extends BaseQuickAdapter<TrainAnalyseEntity, BaseViewHolder>{

    List<Integer> mIcon;

    public TrainAnalyzeAdapter() {
        super(R.layout.item_train_analyze, Lists.newArrayList());
        mIcon = Lists.newArrayList(R.mipmap.ic_train_frist
                , R.mipmap.ic_train_second
                , R.mipmap.ic_train_thrid);
    }

    @Override
    protected void convert(BaseViewHolder helper, TrainAnalyseEntity item) {
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

        helper.setText(R.id.tvFootNumber, item.getFootRingNum());
        helper.setText(R.id.tvColor,item.getPlume());
        helper.setText(R.id.tvBlood,item.getPigeonBloodName());
        helper.setText(R.id.tvScore, Utils.getString(R.string.text_all_score
                , MathUtil.doubleformat(item.getScore(), 3)));
        helper.setText(R.id.tvSpeed,Utils.getString(R.string.text_all_speed,MathUtil.doubleformat(item.getFraction(),3 )));

    }
}
