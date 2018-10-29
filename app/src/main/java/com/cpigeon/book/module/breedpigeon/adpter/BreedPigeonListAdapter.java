package com.cpigeon.book.module.breedpigeon.adpter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class BreedPigeonListAdapter extends BasePigeonListAdapter {

    public BreedPigeonListAdapter() {
        super(R.layout.item_breed_pigeon_list, null);
    }

    public BreedPigeonListAdapter(int layoutResId, List<PigeonEntity> data) {
        super(layoutResId, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {

        ImageView imgSex = helper.getView(R.id.imgSex);
        TextView color = helper.getView(R.id.tvColor);
        TextView blood = helper.getView(R.id.blood);
        defultParams(color,R.drawable.textcircledefult);
        defultParams(blood,R.drawable.textcircledefult);
        if (!item.getPigeonPlumeName().trim().equals(""))
        {
            setParams(color,R.drawable.textcirclecolor);
        }
        if (!item.getPigeonBloodName().trim().equals(""))
        {
            setParams(blood,R.drawable.textcircleblood);
        }

        helper.setText(R.id.tvColor, "  "+item.getPigeonPlumeName()+"  ");
        helper.setText(R.id.blood,"  "+item.getPigeonBloodName()+"  ");
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
    public void  setParams(TextView tv,int Resource)
    {
        tv.setPadding(5,2,5,2);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)tv.getLayoutParams();
        params.height=60;
        params.setMargins(0,0,10,0);
        tv.setBackgroundResource(Resource);
        tv.setLayoutParams(params);
    }
    public void  defultParams(TextView tv,int Resource)
    {
        tv.setPadding(0,0,0,0);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)tv.getLayoutParams();
        params.height=60;
        params.setMargins(0,0,0,0);
        tv.setBackgroundResource(Resource);
        tv.setLayoutParams(params);
    }
}
