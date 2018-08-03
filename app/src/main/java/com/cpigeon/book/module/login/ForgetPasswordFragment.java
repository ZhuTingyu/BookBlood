package com.cpigeon.book.module.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.util.RxUtils;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.login.viewmodel.ForgetPasswordViewModel;
import com.cpigeon.book.util.VerifyCountdownUtil;

/**
 * Created by Zhu TingYu on 2018/7/26.
 */

public class ForgetPasswordFragment extends BaseBookFragment {

    ForgetPasswordViewModel mViewModel;

    private ImageView mImgClose;
    private EditText edUserPhone;
    private EditText mEdAuthCode;
    private TextView mTvGetCode;
    private EditText mEdPassword;
    private EditText mEdPassword2;
    private EditText edInviteCode;
    private LinearLayout mLlAgreement;
    private TextView mTvAgreement;
    private TextView mTvOk;

    private Thread thread;
    private LoginActivity mLoginActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_and_forget_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LoginActivity activity = (LoginActivity) getBaseActivity();

        mViewModel = new ForgetPasswordViewModel();
        initViewModel(mViewModel);

        mViewModel.normalResult.observe(this, s -> {
            setProgressVisible(false);
            ToastUtils.showLong(getBaseActivity(), s);
            activity.replace(LoginActivity.TYPE_LOGIN);
        });

        mLoginActivity = (LoginActivity) getBaseActivity();

        mImgClose = findViewById(R.id.imgClose);
        edUserPhone = findViewById(R.id.edUserPhone);
        mEdAuthCode = findViewById(R.id.edAuthCode);
        mTvGetCode = findViewById(R.id.tvGetCode);
        mEdPassword = findViewById(R.id.edPassword);
        mEdPassword2 = findViewById(R.id.edPassword2);
        edInviteCode = findViewById(R.id.edInviteCode);
        mLlAgreement = findViewById(R.id.llAgreement);
        mTvAgreement = findViewById(R.id.tvAgreement);
        mTvOk = findViewById(R.id.tvOk);

        bindUi(RxUtils.textChanges(edUserPhone), mViewModel.setPhone());
        bindUi(RxUtils.textChanges(mEdAuthCode), mViewModel.setAuthCode());
        bindUi(RxUtils.textChanges(mEdPassword), mViewModel.setPassword());
        bindUi(RxUtils.textChanges(mEdPassword2), mViewModel.setPassword2());

        mTvOk.setText(getString(R.string.text_sure_commit));

        mLlAgreement.setVisibility(View.GONE);
        edInviteCode.setVisibility(View.GONE);

        mImgClose.setOnClickListener(v -> {
            mLoginActivity.replace(LoginActivity.TYPE_LOGIN);
        });

        mTvOk.setOnClickListener(v -> {
            setProgressVisible(true);
            mViewModel.retrievePassword();
        });

        mTvGetCode.setOnClickListener(v -> {
            thread = new Thread(VerifyCountdownUtil.getYzm(mTvGetCode, getActivity()));
            thread.start();
        });

    }

    @Override
    public void onDestroy() {
        if (thread != null && thread.isAlive()) {//停止线程
            thread.interrupt();
        }
        super.onDestroy();
    }

}
