package com.cpigeon.book.module.feedpigeon.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.utility.TimeUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.FeedPigeonEntity;
import com.loc.v;

/**
 * Created by Zhu TingYu on 2018/9/7.
 */

public class FeedPigeonDetailsAdapter extends BaseQuickAdapter<FeedPigeonEntity, BaseViewHolder> {

    public FeedPigeonDetailsAdapter() {
        super(R.layout.item_feed_pigeon_details, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, FeedPigeonEntity item) {
        TextView mTvTitle;
        LinearLayout mLlImgContent;
        LinearLayout mLlTextRoot;
        LinearLayout mLlTextContent1;
        TextView mTvLeft1;
        TextView mTvRight1;
        LinearLayout mLlTextContent2;
        TextView mTvLeft2;
        TextView mTvRight2;
        LinearLayout mLlTextContent3;
        TextView mTvLeft3;
        TextView mTvRight3;
        LinearLayout mLlTextContent4;
        TextView mTvLeft4;
        TextView mTvRight4;

        View v3 = helper.getView(R.id.v3);
        if (helper.getAdapterPosition() - getHeaderLayoutCount() == 0) {
            v3.setVisibility(View.GONE);
        } else {
            v3.setVisibility(View.VISIBLE);
        }


        mTvTitle = helper.getView(R.id.tvTitle);
        mLlImgContent = helper.getView(R.id.llImgContent);
        mLlTextRoot = helper.getView(R.id.llTextRoot);
        mLlTextContent1 = helper.getView(R.id.llTextContent1);
        mLlTextContent2 = helper.getView(R.id.llTextContent2);
        mLlTextContent3 = helper.getView(R.id.llTextContent3);
        mLlTextContent4 = helper.getView(R.id.llTextContent4);

        mTvLeft1 = helper.getView(R.id.tvLeft1);
        mTvRight1 = helper.getView(R.id.tvRight1);

        mTvLeft2 = helper.getView(R.id.tvLeft2);
        mTvRight2 = helper.getView(R.id.tvRight2);

        mTvLeft3 = helper.getView(R.id.tvLeft3);
        mTvRight3 = helper.getView(R.id.tvRight3);

        mTvLeft4 = helper.getView(R.id.tvLeft4);
        mTvRight4 = helper.getView(R.id.tvRight4);

        helper.setTextView(R.id.tvTime, TimeUtil.getTimeFormat(item.getUseTime(), TimeUtil.FORMAT_YYYYMMDD));

        if (item.getTypeID() == 5) { //随拍
            mLlImgContent.setVisibility(View.VISIBLE);
            mLlTextRoot.setVisibility(View.GONE);
            mTvTitle.setText(R.string.text_nef);
            mTvTitle.setBackgroundResource(R.drawable.shape_bg_corner_3_solid_blue);
            helper.setGlideImageViewHaveRound(mContext, R.id.imgIcon, item.getName());
            helper.setText(R.id.tvImageContent, item.getListInfo());

        } else if (item.getTypeID() == 3) {//用药
            mTvTitle.setText(R.string.text_drug_use);
            mTvTitle.setBackgroundResource(R.drawable.shape_bg_corner_3_solid_red);
            mLlImgContent.setVisibility(View.GONE);
            mLlTextRoot.setVisibility(View.VISIBLE);
            mLlTextContent1.setVisibility(View.VISIBLE);
            mLlTextContent2.setVisibility(View.VISIBLE);
            mLlTextContent3.setVisibility(View.VISIBLE);
            mLlTextContent4.setVisibility(View.GONE);
            mTvLeft1.setText(R.string.text_drug_name);
            mTvRight1.setText(item.getName());

            mTvLeft2.setText(R.string.text_illness_name);
            mTvRight2.setText(item.getListInfo());

            mTvLeft3.setText(R.string.text_drug_after_status);
            mTvRight3.setText(item.getState());


        } else if (item.getTypeID() == 1) {//保健
            mTvTitle.setText(R.string.text_care);
            mTvTitle.setBackgroundResource(R.drawable.shape_bg_corner_3_solid_deep_orange);
            mLlImgContent.setVisibility(View.GONE);
            mLlTextRoot.setVisibility(View.VISIBLE);
            mLlTextContent1.setVisibility(View.VISIBLE);
            mLlTextContent2.setVisibility(View.VISIBLE);
            mLlTextContent3.setVisibility(View.GONE);
            mLlTextContent4.setVisibility(View.GONE);

            mTvLeft1.setText(R.string.text_care_drug_name);
            mTvRight1.setText(item.getName());

            mTvLeft2.setText(R.string.text_care_drug_function);
            mTvRight2.setText(item.getListInfo());

            mTvLeft3.setText(R.string.text_drug_after_result);
            mTvRight3.setText(item.getBitEffect());

            mTvLeft4.setText(R.string.text_drug_use_effect);
            mTvRight4.setText(item.getState());

        } else if (item.getTypeID() == 2) {//疫苗
            mTvTitle.setText(R.string.text_vaccine);
            mTvTitle.setBackgroundResource(R.drawable.shape_bg_corner_3_solid_green);
            mLlImgContent.setVisibility(View.GONE);
            mLlTextRoot.setVisibility(View.VISIBLE);
            mLlTextContent1.setVisibility(View.VISIBLE);
            mLlTextContent2.setVisibility(View.VISIBLE);
            mLlTextContent3.setVisibility(View.GONE);
            mLlTextContent4.setVisibility(View.GONE);

            mTvLeft1.setText(R.string.text_vaccine_name);
            mTvRight1.setText(item.getName());

            mTvLeft2.setText(R.string.text_use_vaccine_reason);
            mTvRight2.setText(item.getListInfo());
        } else if (item.getTypeID() == 4) {//病情
            mTvTitle.setText(R.string.text_state_illness);
            mTvTitle.setBackgroundResource(R.drawable.shape_bg_corner_3_solid_red);
            mLlImgContent.setVisibility(View.GONE);
            mLlTextRoot.setVisibility(View.VISIBLE);
            mLlTextContent1.setVisibility(View.VISIBLE);
            mLlTextContent2.setVisibility(View.VISIBLE);
            mLlTextContent3.setVisibility(View.GONE);
            mLlTextContent4.setVisibility(View.GONE);

            mTvLeft1.setText(R.string.text_illness_symptom);
            mTvRight1.setText(item.getListInfo());

            mTvLeft2.setText(R.string.text_illness_name);
            mTvRight2.setText(item.getName());

//            mTvLeft3.setText(R.string.text_is_user_drug);
//            mTvRight3.setText(item.getState());
        }

        addTopAndBottomMargin(helper, 16);

    }
}
