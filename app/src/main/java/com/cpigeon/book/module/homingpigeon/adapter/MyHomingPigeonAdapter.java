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
        TextView mPigeonType = helper.getView(R.id.zl);
        TextView blood = helper.getView(R.id.blood);
        TextView color = helper.getView(R.id.tvColor);
        defultParams(mPigeonType,R.drawable.textcircledefult);
        defultParams(blood,R.drawable.textcircledefult);
        defultParams(color,R.drawable.textcircledefult);
        mPigeonType.setTextColor(mContext.getResources().getColor(R.color.white));
        try {
            switch (item.getTypeID()) {
                case PigeonEntity.ID_BREED_PIGEON:
                    //种鸽
                    setParams(mPigeonType,R.drawable.textcirclebreed);
                    break;
                case PigeonEntity.ID_MATCH_PIGEON:
                    //赛鸽
                    setParams(mPigeonType,R.drawable.textcirclematch);
                    break;
                default:
                    setParams(mPigeonType,R.drawable.textcirclechild);
            }
        } catch (Exception e) {
            defultParams(color,R.drawable.textcircledefult);
        }

        if (!item.getPigeonPlumeName().trim().equals(""))
        {
          setParams(color,R.drawable.textcirclecolor);
        }
        if (!item.getPigeonBloodName().trim().equals(""))
        {
            setParams(blood,R.drawable.textcircleblood);
        }
        helper.setText(R.id.zl, " "+item.getTypeName()+" ");
        helper.setText(R.id.tvColor, " "+item.getPigeonPlumeName()+" ");
        helper.setText(R.id.blood," "+item.getPigeonBloodName()+" ");
        helper.setText(R.id.state,item.getStateName());
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
