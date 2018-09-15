package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.RxUtils;
import com.base.util.Utils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.select.SearchFootRingActivity;
import com.cpigeon.book.module.trainpigeon.viewmodel.AddHomingRecordViewModel;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class AddHomingRecordFragment extends BaseBookFragment {

    private static final int CODE_FOOT_NUMBER = 0x123;

    private LineInputListLayout mLlRoot;
    private LineInputView mLvFoot;
    private LineInputView mLvTime;
    private LineInputView mLvSpeed;
    private TextView mTvOk;

    AddHomingRecordViewModel mViewModel;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, AddHomingRecordFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new AddHomingRecordViewModel();
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_homing_record, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.text_add_homing_recode);
        mLlRoot = findViewById(R.id.llRoot);
        mLvFoot = findViewById(R.id.lvFoot);
        mLvTime = findViewById(R.id.lvTime);
        mLvSpeed = findViewById(R.id.lvSpeed);
        mTvOk = findViewById(R.id.tvOk);

        composite.add(RxUtils.delayed(50, aLong -> {
            mLlRoot.setLineInputViewState(false);
        }));

        bindUi(RxUtils.textChanges(mLvFoot.getEditText()), mViewModel.setFootNumber());
        bindUi(RxUtils.textChanges(mLvTime.getEditText()), mViewModel.setHomingTime());

        mLvFoot.setOnClickListener(v -> {
            SearchFootRingActivity.start(getBaseActivity(), CODE_FOOT_NUMBER);
        });

        mLvTime.setOnClickListener(v -> {
            SelectTimeHaveHMSDialog dialog = new SelectTimeHaveHMSDialog();
            dialog.setOnTimeSelectListener((hours, minute, second) -> {
                mLvTime.setRightText(Utils.getString(R.string.text_time_h_m_s, hours, minute, second));
            });
            dialog.show(getBaseActivity().getSupportFragmentManager());
        });

    }

    @Override
    protected void initObserve() {
        mViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(mTvOk, aBoolean);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == CODE_FOOT_NUMBER) {
            mLvFoot.setRightText(data.getStringExtra(IntentBuilder.KEY_DATA));
        }
    }
}
