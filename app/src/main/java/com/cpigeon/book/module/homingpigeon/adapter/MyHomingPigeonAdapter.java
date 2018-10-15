package com.cpigeon.book.module.homingpigeon.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.util.Utils;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BasePigeonListAdapter;

/**
 * Created by Administrator on 2018/10/9 0009.
 */

public class MyHomingPigeonAdapter extends BasePigeonListAdapter {

    public MyHomingPigeonAdapter() {
        super(R.layout.item_breed_pigeon_list, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {


        ImageView imgSex = helper.getView(R.id.imgSex);


        helper.setText(R.id.tvColor, item.getTypeName());

        TextView mPigeonType = helper.getView(R.id.tvColor);

        try {
            switch (item.getTypeID()) {
                case PigeonEntity.ID_BREED_PIGEON:
                    //种鸽
                    mPigeonType.setTextColor(mContext.getResources().getColor(R.color.color_F280FF));
                    break;
                case PigeonEntity.ID_MATCH_PIGEON:
                    //赛鸽
                    mPigeonType.setTextColor(mContext.getResources().getColor(R.color.color_6B994D));

                    break;
                default:
                    mPigeonType.setTextColor(mContext.getResources().getColor(R.color.color_808080));
            }
        } catch (Exception e) {
            mPigeonType.setTextColor(mContext.getResources().getColor(R.color.color_808080));
        }

        helper.setText(R.id.tvTime, item.getFootRingNum());

        Glide.with(mContext)
                .load(item.getCoverPhotoUrl())
                .placeholder(R.drawable.ic_img_default)
                .into((ImageView) helper.getView(R.id.imgHead));


        if (Utils.getString(R.string.text_male_a).equals(item.getPigeonSexName())) {
            imgSex.setImageResource(R.mipmap.ic_male);
        } else if (Utils.getString(R.string.text_female_a).equals(item.getPigeonSexName())) {
            imgSex.setImageResource(R.mipmap.ic_female);
        } else {
            imgSex.setImageResource(R.mipmap.ic_sex_no);
        }


    }


}
