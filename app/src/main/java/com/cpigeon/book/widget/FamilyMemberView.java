package com.cpigeon.book.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.FamilyMemberEntity;

/**
 * Created by Zhu TingYu on 2018/6/21.
 */

public class FamilyMemberView extends LinearLayout {

    RelativeLayout rlMemberInfo;
    ImageView imgHead;
    TextView tvCall;
    TextView tvFootNumber;

    public FamilyMemberView(Context context) {
        super(context);
        initView(context);
    }

    public FamilyMemberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FamilyMemberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_family_member_layout, this);


        rlMemberInfo = view.findViewById(R.id.rlMemberInfo);
        imgHead = view.findViewById(R.id.imgHead);
        tvCall = view.findViewById(R.id.tvCall);
        tvFootNumber = view.findViewById(R.id.tvFootNumber);

    }

    public void bindData(FamilyMemberEntity entity) {
        tvCall.setText(entity.call);
        tvFootNumber.setText(entity.footNumber);
    }

    public RelativeLayout getRlMemberInfo() {
        return rlMemberInfo;
    }
}
