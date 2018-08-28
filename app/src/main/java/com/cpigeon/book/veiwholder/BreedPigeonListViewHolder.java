package com.cpigeon.book.veiwholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.util.glide.GlideUtil;
import com.base.util.utility.TimeUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.UserModel;

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

    public void bindData(String data){
        GlideUtil.setGlideImageView(itemView.getContext(), UserModel.getInstance().getUserData().touxiangurl,mImgHead);
        mTvColor.setText("白");
        mImgSex.setImageResource(R.mipmap.ic_male);
        mTvTime.setText(TimeUtil.format(System.currentTimeMillis(), TimeUtil.FORMAT_YYYYMMDDHHMM));
    }
}
