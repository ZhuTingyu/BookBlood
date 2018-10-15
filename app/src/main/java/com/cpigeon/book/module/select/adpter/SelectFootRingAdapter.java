package com.cpigeon.book.module.select.adpter;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.IntentBuilder;
import com.base.util.Utils;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.FootEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.select.SetPigeonDeathDialog;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/10/13.
 */

public class SelectFootRingAdapter extends BaseQuickAdapter<FootEntity, BaseViewHolder> {

    private boolean mIsCanSetDeath;

    public SelectFootRingAdapter(boolean isCanSetDeath) {
        super(R.layout.item_select_foot_ring, null);
        mIsCanSetDeath = isCanSetDeath;
    }

    @Override
    protected void convert(BaseViewHolder helper, FootEntity item) {
        helper.setText(R.id.tvTitle, item.getFootRingNum());

        if (Utils.getString(R.string.text_status_set_foot_ring).equals(item.getStateName())) {
            helper.setText(R.id.tvPigeonType, Utils.getString(R.string.text_bracket
                    , StringUtil.isStringValid(item.getPigeonTypeName()) ? item.getPigeonTypeName()
                            : Utils.getString(R.string.text_young_pigeon)));
            helper.setVisible(R.id.tvPigeonType, true);
        } else {
            helper.setVisible(R.id.tvPigeonType, false);
        }


        helper.itemView.setOnClickListener(v -> {

             if (Utils.getString(R.string.text_status_set_foot_ring).equals(item.getStateName())
                    && mIsCanSetDeath) {

                SetPigeonDeathDialog.show(getBaseActivity().getSupportFragmentManager(), item);

            } else {
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_DATA, item)
                        .finishForResult(getBaseActivity());

            }

        });


    }
}
