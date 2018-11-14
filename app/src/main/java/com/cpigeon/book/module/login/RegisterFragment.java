package com.cpigeon.book.module.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseWebViewActivity;
import com.base.util.RxUtils;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.basepigeon.AuthCodeViewModel;
import com.cpigeon.book.module.login.viewmodel.RegisterViewModel;
import com.cpigeon.book.util.VerifyCountdownUtil;

/**
 * 注册
 * Created by Zhu TingYu on 2018/7/26.
 */

public class RegisterFragment extends BaseBookFragment {

    RegisterViewModel mViewModel;
    AuthCodeViewModel mAuthCodeViewModel;

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
    private NestedScrollView mSvRoot;
    private LoginActivity mLoginActivity;

    private Thread thread;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new RegisterViewModel();
        mAuthCodeViewModel = new AuthCodeViewModel();
        mAuthCodeViewModel.mType = AuthCodeViewModel.TYPE_REGISTER;
        initViewModels(mViewModel, mAuthCodeViewModel);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_and_forget_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

        mAuthCodeViewModel.mDataCode.observe(this, code -> {
            setProgressVisible(false);
            thread = new Thread(VerifyCountdownUtil.getYzm(mTvGetCode, getActivity()));
            thread.start();
            ToastUtils.showLong(getBaseActivity(), "成功发送验证码！");
        });

        mLoginActivity = (LoginActivity) getBaseActivity();
        mSvRoot = findViewById(R.id.svRoot);
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

        mSvRoot.setBackgroundResource(R.drawable.ic_bg_register);

        bindUi(RxUtils.textChanges(mEdUserPhone), mViewModel.setPhone());
        bindUi(RxUtils.textChanges(mEdAuthCode), mViewModel.setAuthCode());
        bindUi(RxUtils.textChanges(mEdPassword), mViewModel.setPassword());
        bindUi(RxUtils.textChanges(mEdPassword2), mViewModel.setPassword2());
        bindUi(RxUtils.textChanges(mEdInviteCode), mViewModel.setInviteCode());
        bindUi(RxUtils.textChanges(mEdUserPhone), mAuthCodeViewModel.setPhoneNumber());

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
            setProgressVisible(true);
            mAuthCodeViewModel.getAuthCode();
        });


        mTvAgreement.setOnClickListener(v -> {
            BaseWebViewActivity.start(getBaseActivity(), Utils.getString(R.string.baseUr_j)
                    + Utils.getString(R.string.baseUr_j) );
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
