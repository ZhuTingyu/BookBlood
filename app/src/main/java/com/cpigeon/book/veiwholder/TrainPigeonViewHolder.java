package com.cpigeon.book.veiwholder;

import android.view.View;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.util.Utils;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.module.trainpigeon.FlyBackRecordFragment;
import com.cpigeon.book.module.trainpigeon.OpenAndCloseTrainFragment;
import com.cpigeon.book.module.trainpigeon.TrainProjectInListFragment;

/**
 * Created by Zhu TingYu on 2018/8/31.
 */

public class TrainPigeonViewHolder extends BaseViewHolder {

    private TextView mTvName;
    private TextView mTvTime;
    private TextView mTvPigeonCount;
    private TextView mTvTrainedCount;
    private TextView mTvStatus;


    public TrainPigeonViewHolder(View itemView) {
        super(itemView);
        mTvName = itemView.findViewById(R.id.tvName);
        mTvTime = itemView.findViewById(R.id.tvTime);
        mTvPigeonCount = itemView.findViewById(R.id.tvPigeonCount);
        mTvTrainedCount = itemView.findViewById(R.id.tvTrainedCount);
        mTvStatus = itemView.findViewById(R.id.tvStatus);
    }

    public void bindData(TrainEntity trainEntity) {
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

        itemView.setOnClickListener(v -> {
           if(trainEntity.getTrainStateName().equals(Utils.getString(R.string.text_training))){
                if (trainEntity.getTrainCount() == 0) {
                    FlyBackRecordFragment.start(getActivity(), trainEntity, false);
                }else {
                    TrainProjectInListFragment.start(getActivity(), trainEntity);
                }
            }else if(trainEntity.getTrainStateName().equals(Utils.getString(R.string.text_end_yet))){
                TrainProjectInListFragment.start(getActivity(), trainEntity);
            }
        });

    }
}
