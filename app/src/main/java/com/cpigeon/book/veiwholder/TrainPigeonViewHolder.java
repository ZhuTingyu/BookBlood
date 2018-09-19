package com.cpigeon.book.veiwholder;

import android.view.View;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.util.Utils;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.module.trainpigeon.OpenAndCloseTrainFragment;

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

    public void bindData(TrainEntity trainEntity){
        mTvName.setText(trainEntity.getPigeonTrainName());
        mTvTime.setText(StringUtil.isStringValid(trainEntity.getTime()) ? trainEntity.getTime()
                : Utils.getString(R.string.text_not_setting));
        mTvPigeonCount.setText(String.valueOf(trainEntity.getFlyCount()));
        mTvTrainedCount.setText(String.valueOf(trainEntity.getTrainCount()));
        mTvStatus.setText(trainEntity.getTrainStateName());

        if(Utils.getString(R.string.text_pigeon_training).equals(trainEntity.getPigeonTrainName())){
            mTvStatus.setTextColor(Utils.getColor(R.color.color_text_red));
        }else {
            mTvStatus.setTextColor(Utils.getColor(R.color.color_text_title));
        }

        itemView.setOnClickListener(v -> {
            if(trainEntity.getTrainCount() == 0
                    && trainEntity.getTrainStateName().equals(Utils.getString(R.string.text_start_not))){
                OpenAndCloseTrainFragment.start(getActivity(),true, trainEntity);
            }
        });

    }
}
