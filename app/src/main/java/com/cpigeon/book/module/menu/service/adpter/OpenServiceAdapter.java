package com.cpigeon.book.module.menu.service.adpter;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.ServiceEntity;
import com.cpigeon.book.module.menu.service.ChoosePayWayDialog;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/31.
 */

public class OpenServiceAdapter extends BaseQuickAdapter<ServiceEntity, BaseViewHolder> {

    public static final String TYPE_OPEN = "TYPE_OPEN";
    public static final String TYPE_RENEW = "TYPE_RENEW";

    private List<Integer> icons = Lists.newArrayList(
            R.mipmap.ic_service_book,
            R.mipmap.ic_service_down_load_league,
            R.mipmap.ic_service_print_blood_book,
            R.mipmap.ic_service_share_pigeon
    );

    private ImageView mImgIcon;
    private TextView mTvServiceName;
    private TextView mTvOpen;
    private TextView mTvCount;
    private TextView mTvTime;
    private String type;
    private boolean mIsOpen;
    double mScore;
    double mBanlance;

    public OpenServiceAdapter(String type, boolean isOpen) {
        super(R.layout.item_service_open, Lists.newArrayList());
        this.type = type;
        this.mIsOpen = isOpen;
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceEntity item) {
        mImgIcon = helper.getView(R.id.imgIcon);
        mTvServiceName = helper.getView(R.id.tvServiceName);
        mTvOpen = helper.getView(R.id.tvOpen);
        mTvCount = helper.getView(R.id.tvCount);
        mTvTime = helper.getView(R.id.tvtime);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(item.getSintro());
        mTvServiceName.setText(item.getSname());
        if (TYPE_OPEN.equals(type)) {
            mTvTime.setVisibility(View.GONE);
            mTvOpen.setText(Utils.getString(R.string.text_open_at_once));
        } else {
            mTvTime.setVisibility(View.VISIBLE);
            mTvTime.setText(item.getExpiredate() + "到期");
            mTvTime.setTextColor(Color.RED);
            mTvOpen.setText(Utils.getString(R.string.text_renew_at_once));

        }

        helper.setGlideImageView(getBaseActivity(), R.id.imgIcon, item.getImgurl());

        mTvCount.setText(stringBuilder);

        mTvOpen.setOnClickListener(v -> {
            ChoosePayWayDialog.show(item, mIsOpen, mScore, mBanlance, getBaseActivity().getSupportFragmentManager());
        });
    }

    public void setScore(double score) {
        mScore = score;
    }

    public void setBanlance(double banlance) {
        mBanlance = banlance;
    }
}
