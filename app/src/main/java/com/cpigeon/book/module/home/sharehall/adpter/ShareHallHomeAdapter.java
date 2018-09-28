package com.cpigeon.book.module.home.sharehall.adpter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Utils;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;

/**
 * Created by Zhu TingYu on 2018/9/15.
 */

public class ShareHallHomeAdapter extends BaseQuickAdapter<PigeonEntity, BaseViewHolder> {

    boolean isHaveDelete;

    public ShareHallHomeAdapter(boolean isHaveDelete) {
        super(R.layout.item_share_hall_home, null);
        this.isHaveDelete = isHaveDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonEntity item) {
        ImageView mImgHead;
        ImageView mImgTagMy;
        TextView mTvFootNumber;
        ImageView mImgSex;
        TextView mTvBlood;
        TextView mTvEye;
        TextView mTvColor;
        TextView mTvLocation;
        TextView mTvTime;
        LinearLayout mLlDelete;

        mTvFootNumber = helper.getView(R.id.tvFootNumber);
        mImgSex = helper.getView(R.id.imgSex);
        mTvBlood = helper.getView(R.id.tvBlood);
        mTvEye = helper.getView(R.id.tvEye);
        mTvColor = helper.getView(R.id.tvColor);
        mTvLocation = helper.getView(R.id.tvLocation);
        mTvTime = helper.getView(R.id.tvTime);
        mLlDelete = helper.getView(R.id.llDelete);
        mImgTagMy = helper.getView(R.id.imgTagMy);

        if (isHaveDelete) {
            mLlDelete.setVisibility(View.VISIBLE);
            mTvLocation.setVisibility(View.GONE);
            mLlDelete.setOnClickListener(v -> {
                if (mOnDeleteClickListener != null) {
                    mOnDeleteClickListener.delete(helper.getAdapterPosition(), getItem(helper.getAdapterPosition()));
                }
            });
        }

        mTvLocation.setVisibility(View.VISIBLE);

        if (UserModel.getInstance().getUserId().equals(item.getUserID())) {
            if (!isHaveDelete) {
                mImgTagMy.setVisibility(View.VISIBLE);
            }
            helper.itemView.setOnClickListener(v -> {
                BreedPigeonDetailsFragment.start(getBaseActivity(), item.getPigeonID()
                        , item.getFootRingID(), BreedPigeonDetailsFragment.TYPE_MY_SHARE, item.getUserID());
            });
        } else {
            mImgTagMy.setVisibility(View.GONE);
            helper.itemView.setOnClickListener(v -> {
                BreedPigeonDetailsFragment.start(getBaseActivity(), item.getPigeonID()
                        , item.getFootRingID(), BreedPigeonDetailsFragment.TYPE_HIS_SHARE, item.getUserID());
            });
        }

        if (item.getPigeonSexName().equals(Utils.getString(R.string.text_male_a))) {
            mImgSex.setImageResource(R.mipmap.ic_male);
        } else {
            mImgSex.setImageResource(R.mipmap.ic_female);
        }

        helper.setGlideImageView(mContext, R.id.imgHead, item.getCoverPhotoUrl());
        mTvFootNumber.setText(item.getFootRingNum());
        mTvBlood.setText(item.getPigeonBloodName());
        mTvEye.setText(item.getPigeonEyeName());
        mTvColor.setText(item.getPigeonPlumeName());
        mTvLocation.setText(item.getCity());
        mTvTime.setText(item.getShareTime());
    }

    public interface OnDeleteClickListener {
        void delete(int position, PigeonEntity pigeonEntity);
    }

    private OnDeleteClickListener mOnDeleteClickListener;

    public void setOnDletetClickLisenter(OnDeleteClickListener mOnDeleteClickListener) {
        this.mOnDeleteClickListener = mOnDeleteClickListener;
    }
}
