package com.cpigeon.book.module.trainpigeon.adpter;

import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.module.trainpigeon.FlyBackRecordFragment;
import com.cpigeon.book.module.trainpigeon.TrainProjectInListFragment;

/**
 * Created by Zhu TingYu on 2018/8/31.
 */

public class TrainPigeonAdapter extends BaseQuickAdapter<TrainEntity, BaseViewHolder> {

    private TextView mTvName;
    private TextView mTvTime;
    private TextView mTvPigeonCount;
    private TextView mTvTrainedCount;
    private TextView mTvStatus;

    public TrainPigeonAdapter() {
        super(R.layout.item_train_pigeon_list, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, TrainEntity trainEntity) {

        mTvName = helper.getView(R.id.tvName);
        mTvTime = helper.getView(R.id.tvTime);
        mTvPigeonCount = helper.getView(R.id.tvPigeonCount);
        mTvTrainedCount = helper.getView(R.id.tvTrainedCount);
        mTvStatus = helper.getView(R.id.tvStatus);

        mTvName.setText(trainEntity.getPigeonTrainName());
        mTvTime.setText(StringUtil.isStringValid(trainEntity.getFromFlyTime()) ? trainEntity.getFromFlyTime()
                : Utils.getString(R.string.text_not_setting));
        mTvPigeonCount.setText(String.valueOf(trainEntity.getFlyCount()));
        mTvTrainedCount.setText(String.valueOf(trainEntity.getTrainCount()));
        mTvStatus.setText(trainEntity.getTrainStateName());

        if (Utils.getString(R.string.text_pigeon_training).equals(trainEntity.getTrainStateName())) {
            mTvStatus.setTextColor(Utils.getColor(R.color.color_text_red));
        } else {
            mTvStatus.setTextColor(Utils.getColor(R.color.color_text_title));
        }

        helper.itemView.setOnClickListener(v -> {
            if(trainEntity.getTrainStateName().equals(Utils.getString(R.string.text_training))){
                if (trainEntity.getTrainCount() == 0) {
                    FlyBackRecordFragment.start(getBaseActivity(), trainEntity, false);
                }else {
                    TrainProjectInListFragment.start(getBaseActivity(), trainEntity);
                }
            }else if(trainEntity.getTrainStateName().equals(Utils.getString(R.string.text_end_yet))){
                TrainProjectInListFragment.start(getBaseActivity(), trainEntity);
            }
        });

    }
}
