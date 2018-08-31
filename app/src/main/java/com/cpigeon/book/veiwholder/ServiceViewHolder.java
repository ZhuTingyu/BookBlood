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
import com.base.util.Utils;
import com.cpigeon.book.R;

/**
 * Created by Zhu TingYu on 2018/8/31.
 */

public class ServiceViewHolder extends BaseViewHolder {

    public static final String TYPE_OPEN = "TYPE_OPEN";
    public static final String TYPE_RENEW = "TYPE_RENEW";

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

    public void bindData(String type, View.OnClickListener onClickListener){
        mTvServiceName.setText("天下鸽谱");
        if(TYPE_OPEN.equals(type)){
            mTvOpen.setText(Utils.getString(R.string.text_open_at_once));
        }else {
            mTvOpen.setText(Utils.getString(R.string.text_renew_at_once));
        }

        SpannableStringBuilder span = new SpannableStringBuilder("享用所有功能，不受限制，只要:");
        SpannableString odlMoney = new SpannableString("188");
        odlMoney.setSpan(new StrikethroughSpan(), 0, odlMoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString newMoney = new SpannableString("188");
        newMoney.setSpan(new ForegroundColorSpan(Color.RED), 0, newMoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.append(newMoney);

        span.append("不要");
        span.append(odlMoney);

        mTvCount.setText(span);

        mTvOpen.setOnClickListener(onClickListener);

    }

}
