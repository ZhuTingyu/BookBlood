package com.cpigeon.book.module.foot.adapter;

import android.view.View;
import android.widget.ImageView;

import com.base.base.BaseActivity;
import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.FootEntity;
import com.cpigeon.book.module.foot.FootAdminDetailsMultipleFragment;
import com.cpigeon.book.module.foot.FootAdminSingleFragment;

/**
 * Created by Administrator on 2018/8/17.
 */

public class FootAdminListAdapter extends BaseQuickAdapter<FootEntity, BaseViewHolder> {

    BaseActivity mBaseActivity;

    public FootAdminListAdapter(BaseActivity baseActivity) {
        super(R.layout.item_one_foot_admin, Lists.newArrayList());
        mBaseActivity = baseActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, FootEntity item) {
        ImageView status = helper.getView(R.id.imgStatus);
        helper.setTextView(R.id.tvStatus, item.getTypeName());

        if (StringUtil.isStringValid(item.getEndFootRingNum())) {
            helper.setTextView(R.id.tvFootNumber, Utils.getString(R.string.text_foots, item.getFootRingNum()
                    , item.getEndFootRingNum()));
            helper.itemView.setOnClickListener(v -> {
                FootAdminDetailsMultipleFragment.start(getBaseActivity()
                        , String.valueOf(getItem(helper.getAdapterPosition()).getFootRingID()));
            });
            status.setVisibility(View.GONE);
        } else {
            helper.setTextView(R.id.tvFootNumber, item.getFootRingNum());
            helper.itemView.setOnClickListener(v -> {
                FootAdminSingleFragment.start(getBaseActivity()
                        , String.valueOf(getItem(helper.getAdapterPosition()).getFootRingID()));
            });
            status.setVisibility(View.VISIBLE);
            if (item.getStateName().equals(Utils.getString(R.string.text_status_not_set_foot_ring))) {
                status.setImageResource(R.mipmap.ic_set_not_foot_ring);
            } else {
                status.setImageResource(R.mipmap.ic_set_foot_ring);
            }
        }

    }
}
