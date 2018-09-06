package com.cpigeon.book.veiwholder;

import android.view.View;
import android.widget.LinearLayout;

import com.base.base.BaseViewHolder;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.module.trainpigeon.HomingRecordFragment;
import com.cpigeon.book.module.trainpigeon.OpenAndCloseTrainFragment;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class TrainProjectViewModel extends BaseViewHolder {

    LinearLayout mLlCheck;


    public TrainProjectViewModel(View itemView) {
        super(itemView);
        mLlCheck = getView(R.id.llCheck);
    }

    public void setLlCheckVisibility(boolean visibility) {
        mLlCheck.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    public void bindData() {
        setText(R.id.tvLocation, "中开");
        setText(R.id.tvCount, "123");
        setText(R.id.tvTime, "2018-11-12");
        setText(R.id.tvTrainedCount, "12");

        if (getAdapterPosition() == 0) {
            setText(R.id.tvStatus, Utils.getString(R.string.text_end_yet));
            itemView.setOnClickListener(v -> {
                HomingRecordFragment.start(getActivity(), true);
            });
        } else if (getAdapterPosition() == 1) {
            setText(R.id.tvStatus, Utils.getString(R.string.text_training));
            itemView.setOnClickListener(v -> {
                HomingRecordFragment.start(getActivity(), false);
            });

        } else if (getAdapterPosition() == 2) {
            setText(R.id.tvStatus, Utils.getString(R.string.text_start_not));
            itemView.setOnClickListener(v -> {
                OpenAndCloseTrainFragment.start(getActivity());
            });
        }
    }
}
