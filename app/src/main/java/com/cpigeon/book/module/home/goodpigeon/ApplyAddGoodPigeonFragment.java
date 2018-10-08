package com.cpigeon.book.module.home.goodpigeon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.event.GoodPigeonEvent;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.home.goodpigeon.viewmodel.ApplyAddGoodPigeonViewModel;
import com.cpigeon.book.module.select.SelectPigeonFragment;
import com.cpigeon.book.widget.LineInputView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Zhu TingYu on 2018/9/14.
 */

public class ApplyAddGoodPigeonFragment extends BaseBookFragment {

    private static final int CODE_FOOT = 0x123;

    private LineInputView mLvFoot;
    private LineInputView mLvFeedPerson;
    private LineInputView mLvFlyPerson;
    private TextView mTvContent;
    private TextView mTvOk;
    private RelativeLayout mRlContent;
    ApplyAddGoodPigeonViewModel mViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new ApplyAddGoodPigeonViewModel();
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apply_add_good_pigeon, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.text_apply_add_good_pigeon);
        mLvFoot = findViewById(R.id.lvFoot);
        mLvFeedPerson = findViewById(R.id.lvFeedPerson);
        mLvFlyPerson = findViewById(R.id.lvFlyPerson);
        mTvContent = findViewById(R.id.tvContent);
        mTvOk = findViewById(R.id.tvOk);
        mRlContent = findViewById(R.id.rlContent);

        mLvFoot.setOnRightClickListener(lineInputView -> {
            SelectPigeonFragment.start(getBaseActivity(), CODE_FOOT);
        });

        mTvOk.setOnClickListener(v -> {
            setProgressVisible(true);
            mViewModel.breedName = mLvFeedPerson.getContent();
            mViewModel.flyName = mLvFlyPerson.getContent();
            mViewModel.applyAddGoodPigeon();
        });
    }

    @Override
    protected void initObserve() {
        mViewModel.normalResult.observe(this, s -> {
            EventBus.getDefault().post(new GoodPigeonEvent(3));
            setProgressVisible(false);
            DialogUtils.createHintDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                finish();
            });
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK) return;
        if(requestCode == CODE_FOOT){
            mRlContent.setVisibility(View.VISIBLE);
            PigeonEntity pigeonEntity = (PigeonEntity) data.getSerializableExtra(IntentBuilder.KEY_DATA);
            SpannableStringBuilder sb = new SpannableStringBuilder();
            sb.append(Utils.getString(R.string.text_apply_add_content_1));
            SpannableString foot = new SpannableString(pigeonEntity.getFootRingNum());
            foot.setSpan(new AbsoluteSizeSpan(18,true),0, foot.length(),0);
            sb.append(foot);
            sb.append(Utils.getString(R.string.text_apply_add_content_2));
            mTvContent.setText(sb);
            mLvFoot.setRightText(pigeonEntity.getFootRingNum());

            mViewModel.foodId = pigeonEntity.getFootRingID();
            mViewModel.pigeonId = pigeonEntity.getPigeonID();
        }
    }
}
