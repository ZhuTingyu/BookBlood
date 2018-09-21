package com.cpigeon.book.module.trainpigeon.adpter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseExpandAdapter;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.FlyBackRecordEntity;
import com.cpigeon.book.module.trainpigeon.PigeonTrainDetailsFragment;
import com.cpigeon.book.util.MathUtil;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class FlyBackRecordAdapter extends BaseExpandAdapter {

    List<Integer> mIcon;

    public FlyBackRecordAdapter() {
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
        FlyBackRecordEntity entity = (FlyBackRecordEntity) ((OrgItem) item).getOrgInfo();
        View line = helper.getView(R.id.line);
        line.setVisibility(orgItem.isExpanded() ? View.GONE : View.VISIBLE);

        TextView tvOrder = helper.getView(R.id.tvOrder);
        ImageView imgOrder = helper.getView(R.id.imgOrder);

        if (entity.order == 1) {
            tvOrder.setVisibility(View.GONE);
            imgOrder.setVisibility(View.VISIBLE);
            imgOrder.setImageResource(mIcon.get(entity.order - 1));
        } else if (entity.order == 2) {
            tvOrder.setVisibility(View.GONE);
            imgOrder.setVisibility(View.VISIBLE);
            imgOrder.setImageResource(mIcon.get(entity.order - 1));
        } else if (entity.order == 3) {
            tvOrder.setVisibility(View.GONE);
            imgOrder.setVisibility(View.VISIBLE);
            imgOrder.setImageResource(mIcon.get(entity.order - 1));
        } else {
            tvOrder.setVisibility(View.VISIBLE);
            imgOrder.setVisibility(View.GONE);
            tvOrder.setText(String.valueOf(entity.order));
        }

        helper.setText(R.id.tvFootNumber, entity.getFootRingNum());
        helper.setText(R.id.tvSpeed, Utils.getString(R.string.text_speed_content, entity.getFraction()));

    }

    @Override
    public void initRace(BaseViewHolder helper, MultiItemEntity item) {
        RaceItem raceItem = (RaceItem) item;
        FlyBackRecordEntity expandEntity = (FlyBackRecordEntity) raceItem.getRace();
        helper.setText(R.id.tvTime, Utils.getString(R.string.text_fly_back_time_content, expandEntity.getEndFlyTime()));
        double score = (double) expandEntity.order / (double) expandEntity.getFlyCount();
        helper.setText(R.id.tvScore, Utils.getString(R.string.text_score, MathUtil.doubleformat(score, 3)));
        helper.setText(R.id.tvColor, Utils.getString(R.string.text_feather, expandEntity.getPigeonPlumeName()));
        helper.setText(R.id.tvBlood, Utils.getString(R.string.text_blood, expandEntity.getPigeonBloodName()));
        helper.itemView.setOnClickListener(v -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, expandEntity.getFootRingID())
                    .putExtra(IntentBuilder.KEY_DATA_2, expandEntity.getPigeonID())
                    .startParentActivity((Activity) mContext, PigeonTrainDetailsFragment.class);
        });
    }
}
