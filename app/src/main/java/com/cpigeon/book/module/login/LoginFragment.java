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

import com.base.util.Lists;
import com.base.util.RxUtils;
import com.base.util.dialog.DialogUtils;
import com.base.util.system.AppManager;
import com.base.util.utility.ToastUtils;
import com.base.widget.BottomSheetAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cpigeon.book.MyApp;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.MainActivity;
import com.cpigeon.book.module.login.viewmodel.LoginViewModel;
import com.cpigeon.book.module.pigeonhouse.PigeonHouseInfoFragment;
import com.cpigeon.book.service.SingleLoginService;
import com.cpigeon.book.service.SingleLoginViewModel;
import com.cpigeon.book.util.EditTextUtil;

import cn.pedant.SweetAlert.SweetAlertDialog;
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

    private SingleLoginViewModel mSingleLoginViewModel;
    private SweetAlertDialog dialogPrompt;

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


        mSingleLoginViewModel = new SingleLoginViewModel();
        initViewModel(mSingleLoginViewModel);

        mViewModel.loginR.observe(this, s -> {
//            SingleLoginService.start(getActivity());

//            MainActivity.start(getActivity());
            setProgressVisible(false);
            if (!UserModel.getInstance().isHaveHouseInfo()) {
                //未完善鸽舍信息
                PigeonHouseInfoFragment.start(getActivity(), false);
            } else {
                //已完善鸽舍信息
                MainActivity.start(getActivity());
                finish();
            }
        });

        mViewModel.normalResult.observe(this, s -> {
            ToastUtils.showLong(getBaseActivity(), s);
        });

        mViewModel.head.observe(this, s -> {

            Glide.with(getActivity())
                    .load(s)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_logo)
                    .error(R.mipmap.ic_logo)
                    .dontAnimate()
                    .into(imgHead);
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

        imgHead.setOnClickListener(v -> {
            BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList("外网", "内网"),p -> {
               if(p == 0){
                   MyApp.getMyApp().setOutNet(true);
               }else {
                   MyApp.getMyApp().setOutNet(false);
               }
            });
        });

        tvRegister.setOnClickListener(v -> {
            loginActivity.replace(LoginActivity.TYPE_REGISTER);
        });

        tvForgetPassword.setOnClickListener(v -> {
            loginActivity.replace(LoginActivity.TYPE_FORGET_PASSWORD);
        });

        tvLogin.setOnClickListener(v -> {

            //MainActivity.start(getActivity());

            if (UserModel.getInstance().getUserData() == null) {
                setProgressVisible(true);
                mViewModel.login();
                return;
            }

            mSingleLoginViewModel.getSingleLogin2(o -> {
                        setProgressVisible(true);
                        if (!o.isEmpty()) {
                            if (dialogPrompt == null || !dialogPrompt.isShowing()) {
                                dialogPrompt = DialogUtils.createDialogWithLeft2(AppManager.getAppManager().getTopActivity(), o, true, dialog1 -> {
                                    dialog1.dismiss();
                                }, dialog2 -> {
                                    dialog2.dismiss();

                                    SingleLoginService.stopService();
                                    mViewModel.login();
                                });
                            }
                        } else {
                            mViewModel.login();
                        }
                    }, edUserName.getText().toString()
                    , edPassword.getText().toString());

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

            edUserName.setText(UserModel.getInstance().getUserData().handphone);
            edPassword.setText(UserModel.getInstance().getUserData().password);
        }
    }
}
