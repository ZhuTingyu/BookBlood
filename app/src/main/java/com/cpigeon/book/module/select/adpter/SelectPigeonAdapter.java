package com.cpigeon.book.module.select.adpter;

import android.widget.ImageView;

import com.base.base.BaseViewHolder;
import com.base.util.Utils;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BasePigeonListAdapter;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/28.
 */

public class SelectPigeonAdapter extends BasePigeonListAdapter {

    public SelectPigeonAdapter() {
        super(R.layout.item_search_all_pigeon, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {

        ImageView imgSex = helper.getView(R.id.imgSex);

        helper.setText(R.id.tvFootNumber, item.getFootRingNum());

        helper.setText(R.id.tvColor, item.getPigeonPlumeName());

        helper.setText(R.id.tvBlood, item.getPigeonBloodName());

        if (Utils.getString(R.string.text_male_a).equals(item.getPigeonSexName())) {
            imgSex.setImageResource(R.mipmap.ic_male);
        } else if (Utils.getString(R.string.text_female_a).equals(item.getPigeonSexName())) {
            imgSex.setImageResource(R.mipmap.ic_female);
        } else {
            imgSex.setImageResource(R.mipmap.ic_sex_no);
        }

    }
}
