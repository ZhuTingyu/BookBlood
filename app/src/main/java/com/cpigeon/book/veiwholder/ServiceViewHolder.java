package com.cpigeon.book.veiwholder;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.ServiceEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/31.
 */

public class ServiceViewHolder extends BaseViewHolder {

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

    public ServiceViewHolder(View itemView) {
        super(itemView);
        mImgIcon = itemView.findViewById(R.id.imgIcon);
        mTvServiceName = itemView.findViewById(R.id.tvServiceName);
        mTvOpen = itemView.findViewById(R.id.tvOpen);
        mTvCount = itemView.findViewById(R.id.tvCount);

    }

    public void bindData(String type, ServiceEntity serviceEntity, View.OnClickListener onClickListener) {

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(serviceEntity.getSintro());

        mTvServiceName.setText(serviceEntity.getSname());
        if (TYPE_OPEN.equals(type)) {
            mTvOpen.setText(Utils.getString(R.string.text_open_at_once));
        } else {
            mTvOpen.setText(Utils.getString(R.string.text_renew_at_once));
        }

        setGlideImageView(getActivity(), R.id.imgIcon, serviceEntity.getImgurl());

        mTvCount.setText(stringBuilder);

        mTvOpen.setOnClickListener(onClickListener);

    }

}
