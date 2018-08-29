package com.cpigeon.book.module.menu.account_security;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.RxUtils;
import com.base.util.dialog.DialogUtils;
import com.base.util.system.AppManager;
import com.base.util.system.ScreenTool;
import com.base.util.utility.ToastUtils;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.login.LoginActivity;
import com.cpigeon.book.module.menu.viewmodel.RevisePsdViewModel;
import com.cpigeon.book.service.SingleLoginService;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.util.VerifyCountdownUtil;
import com.cpigeon.book.widget.CodeUtils;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * hl  修改登录密码
 * IntentBuilder.KEY_DATA  1  修改登录密码
 * IntentBuilder.KEY_DATA  2  修改支付密码
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

    @BindView(R.id.ll2)
    LineInputListLayout ll2;

    @BindView(R.id.ll2_phone)
    LineInputView ll2_phone;
    @BindView(R.id.et_imgVerCode)
    EditText et_imgVerCode;
    @BindView(R.id.et_phoneVerCode)
    EditText etPhoneVerCode;
    @BindView(R.id.ll2_input_new_psd)
    LineInputView ll2_input_new_psd;

    @BindView(R.id.tv_ver_code)
    TextView tv_ver_code;

    @BindView(R.id.img_verCode)
    ImageView img_verCode;

    private Thread thread;

    private boolean isNeedVerCode = false;//是否需要验证码
    private int type = -1;//1  修改登录密码   2  修改支付密码

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

        type = getBaseActivity().getIntent().getIntExtra(IntentBuilder.KEY_DATA, -1);

        if (type == 1) {
            //修改登录密码
            setTitle("修改登录密码");

            //不需要验证码
            setToolbarRight("短信验证", item -> {

                if (isNeedVerCode) {
                    setToolbarRight("短信验证");

                    isNeedVerCode = false;
                    ll1.setVisibility(View.VISIBLE);
                    ll2.setVisibility(View.GONE);

                } else {
                    //需要验证码
                    setToolbarRight("已知旧密码");

                    isNeedVerCode = true;
                    ll1.setVisibility(View.GONE);
                    ll2.setVisibility(View.VISIBLE);

                }
                return true;
            });

        } else if (type == 2) {
            //支付密码
            setTitle("修改支付密码");
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.VISIBLE);
            ll2_input_new_psd.getEditText().setHint("请输入6位数字");
            ll2_input_new_psd.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});//支付密码限制6位
            ll2_input_new_psd.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        ll2_phone.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});//手机号限制11位
        et_imgVerCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});//验证码限制4位
        etPhoneVerCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});//验证码限制4位

        bindUi(RxUtils.textChanges(modifyOriginalPsd.getEditText()), mRevisePsdViewModel.setModifyOriginalPsd());
        bindUi(RxUtils.textChanges(modifyNewPsd.getEditText()), mRevisePsdViewModel.setModifyNewPsd());
        bindUi(RxUtils.textChanges(modifyNewPsd2.getEditText()), mRevisePsdViewModel.setModifyNewPsd2());

        bindUi(RxUtils.textChanges(ll2_phone.getEditText()), mRevisePsdViewModel.setPhoneStr());
        bindUi(RxUtils.textChanges(et_imgVerCode), mRevisePsdViewModel.setImgVerCode());
        bindUi(RxUtils.textChanges(etPhoneVerCode), mRevisePsdViewModel.setPhoneVerCode());
        bindUi(RxUtils.textChanges(ll2_input_new_psd.getEditText()), mRevisePsdViewModel.setNewPsdStr());

        //设置图片验证码
        setImgVerCode();
    }


    @Override
    protected void initObserve() {

        mRevisePsdViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(tvNextStep, aBoolean);
        });


        mRevisePsdViewModel.reviseLoginPsd.observe(this, s -> {
            getBaseActivity().errorDialog = DialogUtils.createHintDialog(getActivity(), s, SweetAlertDialog.ERROR_TYPE, false, dialog -> {
                SingleLoginService.stopService();
                UserModel.getInstance().cleanUserInfo();
                dialog.dismiss();
                AppManager.getAppManager().killAllActivity();
                LoginActivity.start(getBaseActivity());
            });
        });

    }

    @OnClick({R.id.tv_next_step, R.id.tv_ver_code, R.id.img_verCode, R.id.ll_share_txgp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next_step:
                //点击提交
                if (type == 1) {
                    //修改登录密码
                    if (isNeedVerCode) {
                        //需要验证码


                        if (!et_imgVerCode.getText().toString().toLowerCase().equals(CodeUtils.getInstance().getCode().toLowerCase())) {
                            ToastUtils.showLong(getBaseActivity(), "输入图片验证码不符，请重新输入");
                            return;
                        }

                        mRevisePsdViewModel.getZGW_Users_GetLoginData2();
                    } else {
                        //不需要验证码
                        mRevisePsdViewModel.getZGW_Users_GetLoginData1();
                    }

                } else if (type == 2) {
                    //修改支付密码

                    if (!et_imgVerCode.getText().toString().toLowerCase().equals(CodeUtils.getInstance().getCode().toLowerCase())) {
                        ToastUtils.showLong(getBaseActivity(), "输入图片验证码不符，请重新输入");
                        return;
                    }

                    mRevisePsdViewModel.getZGW_Users_GetPlayData();
                }

                break;
            case R.id.tv_ver_code:

                if (thread == null || !thread.isAlive()) {
                    thread = new Thread(VerifyCountdownUtil.getYzm(tv_ver_code, getActivity()));
                    thread.start();
                }

                break;

            case R.id.img_verCode:
                //设置图片验证码
                setImgVerCode();
                break;

            case R.id.ll_share_txgp:

                ToastUtils.showLong(getBaseActivity(), CodeUtils.getInstance().getCode());

                break;
        }
    }

    //设置图片验证码
    private void setImgVerCode() {
        Bitmap bitmap = CodeUtils.getInstance().createBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

        Glide.with(this).load(bytes)
                .bitmapTransform(new RoundedCornersTransformation(getBaseActivity(), ScreenTool.dip2px(3), 0))
                .into(img_verCode);
    }

    @Override
    public void onDestroy() {
        if (thread != null && thread.isAlive()) {//停止线程
            thread.interrupt();
        }
        super.onDestroy();
    }

}
