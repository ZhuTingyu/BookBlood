package com.cpigeon.book.module.home.goodpigeon;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.widget.LineInputView;

/**
 * Created by Zhu TingYu on 2018/9/14.
 */

public class ApplyAddGoodPigeonFragment extends BaseBookFragment {

    private static final int CODE_FOOT = 0;

    private LineInputView mLvFoot;
    private TextView mTvContent;
    private TextView mTvOk;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apply_add_good_pigeon, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLvFoot = findViewById(R.id.lvFoot);
        mTvContent = findViewById(R.id.tvContent);
        mTvOk = findViewById(R.id.tvOk);

        mLvFoot.setOnRightClickListener(lineInputView -> {
           // SearchFootRingActivity.start(getBaseActivity(), CODE_FOOT);
        });

        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(Utils.getString(R.string.text_apply_add_content_1));
        SpannableString foot = new SpannableString("2018-22-1234678");
        foot.setSpan(new AbsoluteSizeSpan(18,true),0, foot.length(),0);
        sb.append(foot);
        sb.append(Utils.getString(R.string.text_apply_add_content_2));
        mTvContent.setText(sb);
    }

    @Override
    protected void initObserve() {

    }
}
