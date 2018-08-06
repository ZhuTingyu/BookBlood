package com.cpigeon.book.module.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.base.util.RxUtils;
import com.base.util.utility.ToastUtils;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.MainActivity;
import com.cpigeon.book.module.login.viewmodel.LoginViewModel;
import com.cpigeon.book.service.SingleLoginService;
import com.cpigeon.book.util.EditTextUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zhu TingYu on 2018/7/26.
 */

public class LoginFragment extends BaseBookFragment {
    private CircleImageView imgHead;
    private EditText edUserName;
    private ImageButton imgLookPwd;
    private EditText edPassword;
    private TextView tvRegister;
    private TextView tvForgetPassword;
    private TextView tvLogin;
    LoginViewModel mViewModel;

    private boolean userInfoTag = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new LoginViewModel();
        initViewModel(mViewModel);

        mViewModel.loginR.observe(this, s -> {
            SingleLoginService.start(getActivity());

            MainActivity.start(getActivity());

//            if (!UserModel.getInstance().isHaveHouseInfo()) {
//                //未完善鸽舍信息
//                PigeonHouseInfoFragment.start(getActivity());
//            } else {
//                //已完善鸽舍信息
//                MainActivity.start(getActivity());
//            }
        });

        mViewModel.normalResult.observe(this, s -> {
            ToastUtils.showLong(getBaseActivity(), s);
        });

        mViewModel.head.observe(this, s -> {
            Glide.with(getActivity()).load(s).into(imgHead);
        });

        LoginActivity loginActivity = (LoginActivity) getBaseActivity();

        imgHead = findViewById(R.id.imgHead);
        edUserName = findViewById(R.id.edUserName);
        imgLookPwd = findViewById(R.id.imgLookPwd);
        edPassword = findViewById(R.id.edPassword);
        tvRegister = findViewById(R.id.tv_register);
        tvForgetPassword = findViewById(R.id.tv_forget_password);
        tvLogin = findViewById(R.id.tvLogin);

        bindUi(RxUtils.textChanges(edUserName), mViewModel.setPhone());
        bindUi(RxUtils.textChanges(edPassword), mViewModel.setPassword());

        tvRegister.setOnClickListener(v -> {
            loginActivity.replace(LoginActivity.TYPE_REGISTER);
        });

        tvForgetPassword.setOnClickListener(v -> {
            loginActivity.replace(LoginActivity.TYPE_FORGET_PASSWORD);
        });

        tvLogin.setOnClickListener(v -> {
            mViewModel.login();
        });

        imgLookPwd.setOnClickListener(v -> {
            if (userInfoTag) {
                //保存用户信息
                userInfoTag = false;
                edPassword.setText("");
                //选择状态 显示明文--设置为可见的密码
                edPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                imgLookPwd.setImageResource(R.mipmap.show2x);//设置图片
            } else {
                EditTextUtil.setPasHint(edPassword, imgLookPwd);//显示隐藏密码
            }
        });

        if (UserModel.getInstance().getUserData() != null) {
            // 保存的用户信息
            userInfoTag = true;

            edUserName.setText(UserModel.getInstance().getUserData().yonghuming);
            edPassword.setText(UserModel.getInstance().getUserData().password);
        }
    }
}
