package com.cpigeon.book.module.menu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.RxUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.menu.viewmodel.RevisePsdViewModel;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * hl  修改登录密码
 * Created by Administrator on 2018/8/8.
 */

public class ReviseLoginPsdFragment extends BaseBookFragment {

    @BindView(R.id.ll1)
    LineInputListLayout ll1;
    @BindView(R.id.modify_original_psd)
    LineInputView modifyOriginalPsd;
    @BindView(R.id.modify_new_psd)
    LineInputView modifyNewPsd;
    @BindView(R.id.modify_new_psd2)
    LineInputView modifyNewPsd2;
    @BindView(R.id.tv_next_step)
    TextView tvNextStep;

    private RevisePsdViewModel mRevisePsdViewModel;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, ReviseLoginPsdFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRevisePsdViewModel = new RevisePsdViewModel();
        initViewModels(mRevisePsdViewModel);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revise_login_psd, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("修改登录密码");
        setToolbarRight("短信验证", item -> {
            return true;
        });

        bindUi(RxUtils.textChanges(modifyOriginalPsd.getEditText()), mRevisePsdViewModel.setModifyOriginalPsd());
        bindUi(RxUtils.textChanges(modifyNewPsd.getEditText()), mRevisePsdViewModel.setModifyNewPsd());
        bindUi(RxUtils.textChanges(modifyNewPsd2.getEditText()), mRevisePsdViewModel.setModifyNewPsd2());

    }


    @Override
    protected void initObserve() {

        mRevisePsdViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(tvNextStep, aBoolean);
        });

    }


    @OnClick({R.id.tv_next_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next_step:
                mRevisePsdViewModel.getZGW_Users_GetLogData();
                break;
        }
    }
}
