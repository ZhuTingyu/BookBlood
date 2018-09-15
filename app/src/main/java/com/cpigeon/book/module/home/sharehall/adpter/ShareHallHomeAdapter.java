package com.cpigeon.book.module.home.sharehall.adpter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/15.
 */

public class ShareHallHomeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    boolean isMyShare;

    public ShareHallHomeAdapter(boolean isMyShare) {
        super(R.layout.item_share_hall_home, null);
        this.isMyShare = isMyShare;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView mImgHead;
        TextView mTvFootNumber;
        ImageView mImgSex;
        TextView mTvBlood;
        TextView mTvEye;
        TextView mTvColor;
        TextView mTvLocation;
        TextView mTvTime;
        LinearLayout mLlDelete;

        mImgHead = helper.getView(R.id.imgHead);
        mTvFootNumber = helper.getView(R.id.tvFootNumber);
        mImgSex = helper.getView(R.id.imgSex);
        mTvBlood = helper.getView(R.id.tvBlood);
        mTvEye = helper.getView(R.id.tvEye);
        mTvColor = helper.getView(R.id.tvColor);
        mTvLocation = helper.getView(R.id.tvLocation);
        mTvTime = helper.getView(R.id.tvTime);
        mLlDelete = helper.getView(R.id.llDelete);

        if(isMyShare){
            mLlDelete.setVisibility(View.VISIBLE);
            mTvLocation.setVisibility(View.GONE);
            helper.itemView.setOnClickListener(v -> {
                BreedPigeonDetailsFragment.start(getBaseActivity(), StringUtil.emptyString()
                        ,StringUtil.emptyString(),BreedPigeonDetailsFragment.TYPE_MY_SHARE);
            });
        }else {
            helper.itemView.setOnClickListener(v -> {
                BreedPigeonDetailsFragment.start(getBaseActivity(), StringUtil.emptyString()
                        ,StringUtil.emptyString(),BreedPigeonDetailsFragment.TYPE_HIS_SHARE);
            });
        }

        mTvFootNumber.setText("2018-22-123456");
        mTvBlood.setText("詹森");
        mTvEye.setText("眼砂");
        mTvColor.setText("雨色");
        mTvLocation.setText("四川省成都市");
        mTvTime.setText("2017-11-12");

    }
}
