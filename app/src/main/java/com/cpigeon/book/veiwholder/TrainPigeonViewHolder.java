package com.cpigeon.book.veiwholder;

import android.view.View;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.cpigeon.book.R;

/**
 * Created by Zhu TingYu on 2018/8/31.
 */

public class TrainPigeonViewHolder extends BaseViewHolder {

    private TextView mTvName;
    private TextView mTvTime;
    private TextView mTvCount;
    private TextView mTvTrainedCount;
    private TextView mTvStatus;


    public TrainPigeonViewHolder(View itemView) {
        super(itemView);
        mTvName = itemView.findViewById(R.id.tvName);
        mTvTime = itemView.findViewById(R.id.tvTime);
        mTvCount = itemView.findViewById(R.id.tvCount);
        mTvTrainedCount = itemView.findViewById(R.id.tvTrainedCount);
        mTvStatus = itemView.findViewById(R.id.tvStatus);
    }

    public void bindData(){
        mTvName.setText("训练1");
        mTvTime.setText("2018-11");
        mTvCount.setText("22");
        mTvTrainedCount.setText("11");
        mTvStatus.setText("已结束");
    }
}
