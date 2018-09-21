package com.cpigeon.book.veiwholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.module.trainpigeon.FlyBackRecordFragment;
import com.cpigeon.book.module.trainpigeon.OpenAndCloseTrainFragment;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class TrainProjectViewHolder extends BaseViewHolder {

    LinearLayout mLlCheck;


    public TrainProjectViewHolder(View itemView) {
        super(itemView);
        mLlCheck = getView(R.id.llCheck);
    }

    public void setLlCheckVisibility(boolean visibility) {
        mLlCheck.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    public void bindData(TrainEntity trainEntity, boolean isChoose) {

        TextView tvStatus = getView(R.id.tvStatus);

        setText(R.id.tvLocation, trainEntity.getFromPlace());
        setText(R.id.tvCount, String.valueOf(trainEntity.getReturnCount()));

        setText(R.id.tvTime, trainEntity.getFromFlyTime());
        setText(R.id.tvTrainedOrder, String.valueOf(trainEntity.getTrainCount()));
        tvStatus.setText(trainEntity.getTrainStateName());

        if(Utils.getString(R.string.text_end_yet).equals(trainEntity.getTrainStateName())){
            itemView.setOnClickListener(v -> {
                FlyBackRecordFragment.start(getActivity(), trainEntity, true);
            });
            tvStatus.setTextColor(Utils.getColor(R.color.color_text_title));
        }else if(Utils.getString(R.string.text_training).equals(trainEntity.getTrainStateName())){
            tvStatus.setTextColor(Utils.getColor(R.color.color_text_red));
            itemView.setOnClickListener(v -> {
                FlyBackRecordFragment.start(getActivity(), trainEntity, false);
            });
            tvStatus.setTextColor(Utils.getColor(R.color.color_text_title));
        }else if(Utils.getString(R.string.text_start_not).equals(trainEntity.getTrainStateName())){
            itemView.setOnClickListener(v -> {
                OpenAndCloseTrainFragment.start(getActivity(), true, trainEntity);
            });
        }
    }
}
