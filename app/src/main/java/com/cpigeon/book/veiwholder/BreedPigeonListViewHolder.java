package com.cpigeon.book.veiwholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.util.Utils;
import com.base.util.glide.GlideUtil;
import com.base.util.utility.TimeUtil;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.BreedPigeonEntity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zhu TingYu on 2018/8/28.
 */

public class BreedPigeonListViewHolder extends BaseViewHolder {
    private CircleImageView mImgHead;
    private TextView mTvColor;
    private ImageView mImgSex;
    private TextView mTvTime;


    public BreedPigeonListViewHolder(View itemView) {
        super(itemView);

        mImgHead = itemView.findViewById(R.id.imgHead);
        mTvColor = itemView.findViewById(R.id.tvColor);
        mImgSex = itemView.findViewById(R.id.imgSex);
        mTvTime = itemView.findViewById(R.id.tvTime);

    }

    public void bindData(BreedPigeonEntity data){
        ImageView imgSex = getView(R.id.imgSex);

        setText(R.id.tvColor, data.getPigeonPlumeName());

        setText(R.id.tvTime, data.getFootRingNum());

        Glide.with(getActivity())
                .load(data.getCoverPhotoUrl())
                .placeholder(R.drawable.ic_img_default)
                .into((ImageView)getView(R.id.imgHead));


        if (data.getPigeonSexName().equals(Utils.getString(R.string.text_male_a))) {
            imgSex.setImageResource(R.mipmap.ic_female);
        } else if (data.getPigeonSexName().equals(Utils.getString(R.string.text_female_a))) {
            imgSex.setImageResource(R.mipmap.ic_male);
        } else {
            imgSex.setImageResource(R.mipmap.ic_sex_no);
        }

    }
}
