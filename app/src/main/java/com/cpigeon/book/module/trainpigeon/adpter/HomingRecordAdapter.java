package com.cpigeon.book.module.trainpigeon.adpter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseExpandAdapter;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.HomingRecordEntity;
import com.cpigeon.book.model.entity.HomingRecordExpandEntity;
import com.cpigeon.book.module.trainpigeon.FootNumberTrainDetailsFragment;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class HomingRecordAdapter extends BaseExpandAdapter {

    List<Integer> mIcon;

    public HomingRecordAdapter() {
        super(Lists.newArrayList());
        addItemType(TYPE_ORG, R.layout.item_homing_record);
        addItemType(TYPE_RACE, R.layout.item_homing_record_expand);
        mIcon = Lists.newArrayList(R.mipmap.ic_train_frist
                , R.mipmap.ic_train_second
                , R.mipmap.ic_train_thrid);
    }

    @Override
    public void initOrg(BaseViewHolder helper, MultiItemEntity item) {
        OrgItem orgItem = (OrgItem) item;
        HomingRecordEntity entity = (HomingRecordEntity) ((OrgItem) item).getOrgInfo();
        View line = helper.getView(R.id.line);
        line.setVisibility(orgItem.isExpanded() ? View.GONE : View.VISIBLE);

        TextView tvOrder = helper.getView(R.id.tvOrder);
        ImageView imgOrder = helper.getView(R.id.imgOrder);

        if (entity.order == 0) {
            tvOrder.setVisibility(View.GONE);
            imgOrder.setVisibility(View.VISIBLE);
            imgOrder.setImageResource(mIcon.get(entity.order));
        } else if (entity.order == 1) {
            tvOrder.setVisibility(View.GONE);
            imgOrder.setVisibility(View.VISIBLE);
            imgOrder.setImageResource(mIcon.get(entity.order));
        } else if (entity.order == 2) {
            tvOrder.setVisibility(View.GONE);
            imgOrder.setVisibility(View.VISIBLE);
            imgOrder.setImageResource(mIcon.get(entity.order));
        } else {
            tvOrder.setVisibility(View.VISIBLE);
            imgOrder.setVisibility(View.GONE);
            tvOrder.setText(String.valueOf(entity.order + 1));
        }

        helper.setText(R.id.tvFootNumber, "2018-22-1234567");
        helper.setText(R.id.tvSpeed, Utils.getString(R.string.text_speed_content, "1000"));

    }

    @Override
    public void initRace(BaseViewHolder helper, MultiItemEntity item) {
        RaceItem raceItem = (RaceItem) item;
        HomingRecordExpandEntity expandEntity = (HomingRecordExpandEntity) raceItem.getRace();
        helper.setText(R.id.tvTime,Utils.getString(R.string.text_fly_back_time_content,"2018-11-12 12:30"));
        helper.setText(R.id.tvScore,Utils.getString(R.string.text_score,"0.08"));
        helper.setText(R.id.tvColor,Utils.getString(R.string.text_feather,"白色"));
        helper.setText(R.id.tvBlood,Utils.getString(R.string.text_blood,"詹森"));
        helper.itemView.setOnClickListener(v -> {
            IntentBuilder.Builder().startParentActivity((Activity)mContext, FootNumberTrainDetailsFragment.class);
        });
    }
}
