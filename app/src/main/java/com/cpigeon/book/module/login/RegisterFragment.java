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
import android.widget.TextView;

import com.base.util.RxUtils;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.login.viewmodel.RegisterViewModel;
import com.cpigeon.book.util.VerifyCountdownUtil;

/**
 * 注册
 * Created by Zhu TingYu on 2018/7/26.
 */

public class RegisterFragment extends BaseBookFragment {

    RegisterViewModel mViewModel;

    private ImageView mImgClose;
    private EditText mEdUserPhone;
    private EditText mEdAuthCode;
    private TextView mTvGetCode;
    private EditText mEdPassword;
    private EditText mEdPassword2;
    private EditText mEdInviteCode;
    private AppCompatCheckBox mChkAgree;
    private TextView mTvAgreement;
    private TextView mTvOk;
    private LoginActivity mLoginActivity;

    private Thread thread;

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

        mViewModel = new RegisterViewModel();
        initViewModel(mViewModel);

        mViewModel.registerR.observe(this, s -> {
            setProgressVisible(false);
            DialogUtils.createHintDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                mLoginActivity.replace(LoginActivity.TYPE_LOGIN);
            });
        });

        mViewModel.getError().observe(this, restErrorInfo -> {
            if (restErrorInfo != null) {
                error(restErrorInfo.code, restErrorInfo.message);
            }
        });

        mLoginActivity = (LoginActivity) getBaseActivity();
        mImgClose = findViewById(R.id.imgClose);
        mEdUserPhone = findViewById(R.id.edUserPhone);
        mEdAuthCode = findViewById(R.id.edAuthCode);
        mTvGetCode = findViewById(R.id.tvGetCode);
        mEdPassword = findViewById(R.id.edPassword);
        mEdPassword2 = findViewById(R.id.edPassword2);
        mChkAgree = findViewById(R.id.chkAgree);
        mTvAgreement = findViewById(R.id.tvAgreement);
        mEdInviteCode = findViewById(R.id.edInviteCode);
        mTvOk = findViewById(R.id.tvOk);

        bindUi(RxUtils.textChanges(mEdUserPhone), mViewModel.setPhone());
        bindUi(RxUtils.textChanges(mEdAuthCode), mViewModel.setAuthCode());
        bindUi(RxUtils.textChanges(mEdPassword), mViewModel.setPassword());
        bindUi(RxUtils.textChanges(mEdPassword2), mViewModel.setPassword2());
        bindUi(RxUtils.textChanges(mEdInviteCode), mViewModel.setInviteCode());

        mImgClose.setOnClickListener(v -> {
            mLoginActivity.replace(LoginActivity.TYPE_LOGIN);
        });

        mTvOk.setOnClickListener(v -> {
            if(!mChkAgree.isChecked()){
                DialogUtils.createHintDialog(getBaseActivity(), Utils.getString(R.string.text_agree_agreement));
                return;
            }
            setProgressVisible(true);
            mViewModel.register();
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
