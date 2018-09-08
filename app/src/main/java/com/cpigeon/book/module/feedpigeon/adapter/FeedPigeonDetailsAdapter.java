package com.cpigeon.book.module.feedpigeon.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.model.UserModel;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/7.
 */

public class FeedPigeonDetailsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public FeedPigeonDetailsAdapter() {
        super(R.layout.item_feed_pigeon_details, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
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
        if(helper.getAdapterPosition() - getHeaderLayoutCount() == 0){
            v3.setVisibility(View.GONE);
        }else {
            v3.setVisibility(View.VISIBLE);
        }



        helper.setTextView(R.id.tvTime, "2018-11-11 12:12:12");
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


        if (helper.getAdapterPosition() == 1) { //随拍
            mLlImgContent.setVisibility(View.VISIBLE);
            mLlTextRoot.setVisibility(View.GONE);
            mTvTitle.setText(R.string.text_nef);
            mTvTitle.setBackgroundResource(R.drawable.shape_bg_corner_3_solid_blue);
            helper.setGlideImageViewHaveRound(mContext, R.id.imgIcon, UserModel.getInstance().getUserData().touxiangurl);
            helper.setText(R.id.tvImageContent,"112112333333333123123123123123123123123123123");

        } else if (helper.getAdapterPosition() == 2) {//用药
            mTvTitle.setText(R.string.text_drug_use);
            mTvTitle.setBackgroundResource(R.drawable.shape_bg_corner_3_solid_red);
            mLlImgContent.setVisibility(View.GONE);
            mLlTextRoot.setVisibility(View.VISIBLE);
            mLlTextContent1.setVisibility(View.VISIBLE);
            mLlTextContent2.setVisibility(View.VISIBLE);
            mLlTextContent3.setVisibility(View.VISIBLE);
            mLlTextContent4.setVisibility(View.GONE);
            mTvLeft1.setText(R.string.text_drug_name);
            mTvRight1.setText("阿莫西林");

            mTvLeft2.setText(R.string.text_drug_after_result);
            mTvRight2.setText("无");

            mTvLeft3.setText(R.string.text_drug_after_status);
            mTvRight3.setText("好转");


        } else if (helper.getAdapterPosition() == 3) {//保健
            mTvTitle.setText(R.string.text_care);
            mTvTitle.setBackgroundResource(R.drawable.shape_bg_corner_3_solid_deep_orange);
            mLlImgContent.setVisibility(View.GONE);
            mLlTextRoot.setVisibility(View.VISIBLE);
            mLlTextContent1.setVisibility(View.VISIBLE);
            mLlTextContent2.setVisibility(View.VISIBLE);
            mLlTextContent3.setVisibility(View.VISIBLE);
            mLlTextContent4.setVisibility(View.VISIBLE);

            mTvLeft1.setText(R.string.text_care_drug_name);
            mTvRight1.setText("阿莫西林");

            mTvLeft2.setText(R.string.text_function);
            mTvRight2.setText("无");

            mTvLeft3.setText(R.string.text_drug_after_result);
            mTvRight3.setText("好转");

            mTvLeft4.setText(R.string.text_drug_use_effect);
            mTvRight4.setText("有");

        } else if (helper.getAdapterPosition() == 4) {//疫苗
            mTvTitle.setText(R.string.text_vaccine);
            mTvTitle.setBackgroundResource(R.drawable.shape_bg_corner_3_solid_green);
            mLlImgContent.setVisibility(View.GONE);
            mLlTextRoot.setVisibility(View.VISIBLE);
            mLlTextContent1.setVisibility(View.VISIBLE);
            mLlTextContent2.setVisibility(View.VISIBLE);
            mLlTextContent3.setVisibility(View.GONE);
            mLlTextContent4.setVisibility(View.GONE);

            mTvLeft1.setText(R.string.text_vaccine_name);
            mTvRight1.setText("阿莫西林");

            mTvLeft2.setText(R.string.text_use_vaccine_reason);
            mTvRight2.setText("无");
        } else if (helper.getAdapterPosition() == 5) {//病情
            mTvTitle.setText(R.string.text_state_illness);
            mTvTitle.setBackgroundResource(R.drawable.shape_bg_corner_3_solid_red);
            mLlImgContent.setVisibility(View.GONE);
            mLlTextRoot.setVisibility(View.VISIBLE);
            mLlTextContent1.setVisibility(View.VISIBLE);
            mLlTextContent2.setVisibility(View.VISIBLE);
            mLlTextContent3.setVisibility(View.VISIBLE);
            mLlTextContent4.setVisibility(View.GONE);

            mTvLeft1.setText(R.string.text_illness_symptom);
            mTvRight1.setText("阿莫西林");

            mTvLeft2.setText(R.string.text_illness_name);
            mTvRight2.setText("无");

            mTvLeft3.setText(R.string.text_is_user_drug);
            mTvRight3.setText("好转");
        }

        addTopAndBttomMargin(helper, 16);

    }
}
