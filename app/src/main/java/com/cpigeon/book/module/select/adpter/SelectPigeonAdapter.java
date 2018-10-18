package com.cpigeon.book.module.select.adpter;

import android.widget.ImageView;

import com.base.base.BaseViewHolder;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BasePigeonListAdapter;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.select.BaseSelectPigeonFragment;
import com.cpigeon.book.util.PigeonPublicUtil;

/**
 * Created by Zhu TingYu on 2018/8/28.
 */

public class SelectPigeonAdapter extends BasePigeonListAdapter {

    String mType;

    public SelectPigeonAdapter(String type) {
        super(R.layout.item_select_pigeon, null);
        mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {

        ImageView imgSex = helper.getView(R.id.imgSex);
        helper.setText(R.id.tvFootNumber, item.getFootRingNum());
        helper.setText(R.id.tvColor, item.getPigeonPlumeName());
        helper.setText(R.id.tvEye, item.getPigeonEyeName());
        helper.setText(R.id.tvBlood, item.getPigeonBloodName());
        helper.setText(R.id.tvStatus, item.getStateName());
        PigeonPublicUtil.setPigeonSexImg(item.getPigeonSexName(), imgSex);
    }
}
