package com.cpigeon.book.module.menu.service;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.base.base.BaseActivity;
import com.base.base.BaseDialogFragment;
import com.base.util.IntentBuilder;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.event.OpenServiceEvent;
import com.cpigeon.book.model.entity.ServiceEntity;
import com.cpigeon.book.module.menu.account_security.ReviseLoginPsdFragment;
import com.cpigeon.book.module.menu.service.viewmodel.PayServiceOrderViewModel;
import com.cpigeon.book.widget.gridpasswordview.GridPasswordView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Zhu TingYu on 2018/9/29.
 */

public class PayServiceOrderDialog extends BaseDialogFragment {

    private TextView mTvPrice;
    private GridPasswordView mPassword;
    private ServiceEntity mServiceEntity;
    PayServiceOrderViewModel mViewModel;
    String mPayWay;
    boolean mIsOpen;
    BaseActivity mBaseActivity;
    boolean mStatusIsPay = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBaseActivity = (BaseActivity) context;
        if (getArguments() != null) {
            mServiceEntity = getArguments().getParcelable(IntentBuilder.KEY_DATA);
            mPayWay = getArguments().getString(IntentBuilder.KEY_TYPE);
            mIsOpen = getArguments().getBoolean(IntentBuilder.KEY_BOOLEAN, false);
            mStatusIsPay = getArguments().getBoolean(IntentBuilder.KEY_BOOLEAN_2, false);
        }
        mViewModel = new PayServiceOrderViewModel();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_pay_service_order;
    }

    @Override
    protected void initView(Dialog dialog) {
        mTvPrice = dialog.findViewById(R.id.tvPrice);
        mPassword = dialog.findViewById(R.id.password);
        mViewModel.mServiceId = mServiceEntity.getSid();
        mViewModel.mPayWay = mPayWay;
        if (mPayWay.equals(PayServiceOrderViewModel.WAY_BALANCE)) {
            mTvPrice.setText(Utils.getString(R.string.text_yuan, mServiceEntity.getPrice()));
        } else if (mPayWay.equals(PayServiceOrderViewModel.WAY_SCORE)) {
            mTvPrice.setText(Utils.getString(R.string.text_pigeon_score_content, mServiceEntity.getScore()));
        }
        mPassword.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                if (psw.length() == 6) {
                    setProgressVisible(true);
                    mViewModel.mPassword = psw;
                    if (mIsOpen) {
                        mViewModel.payOder();
                    } else {
                        mViewModel.renewalPayOder();
                    }
                }
            }
        });

        mViewModel.normalResult.observe(this, s -> {
            mStatusIsPay = true;
            setProgressVisible(false);
            ToastUtils.showLong(getActivity(), s);
            dismiss();
            EventBus.getDefault().post(new OpenServiceEvent());
        });

        mViewModel.getError().observe(this, restErrorInfo -> {
            setProgressVisible(false);

            int errorCode = restErrorInfo.code;
            String msg = null;
            if(errorCode == 998){
                msg = "未找到服务";
            }else if(errorCode == 999){
                msg = "未开通服务";
            }else if(errorCode == 1001){
                msg = "订单不存在";
            }else if(errorCode == 1002){
                msg = "余额不足";
            }else if(errorCode == 1003){
                msg = "余额扣除失败";
            }else if(errorCode == 1004){
                msg = "订单回调失败";
            }

            restErrorInfo.message = msg;

            if (restErrorInfo.code == 1099) {
                DialogUtils.createDialog(getBaseActivity(), Utils.getString(R.string.text_error), restErrorInfo.message
                        , Utils.getString(R.string.text_forget_password)
                        , Utils.getString(R.string.text_reinput)
                        , sweetAlertDialog -> {
                            sweetAlertDialog.dismiss();
                            IntentBuilder.Builder()
                                    .putExtra(IntentBuilder.KEY_DATA, 2)
                                    .startParentActivity(getBaseActivity(), ReviseLoginPsdFragment.class);
                            mPassword.clearPassword();
                        }
                        , sweetAlertDialog -> {
                            sweetAlertDialog.dismiss();
                            mPassword.clearPassword();
                        });
                return;
            }
            DialogUtils.createErrorDialog(mBaseActivity, restErrorInfo.message);
            dismiss();
        });

        dialog.setOnShowListener(dialog1 -> {
            mPassword.forceInputViewGetFocus();
        });
    }

    @Override
    protected void initLayout(Window window, WindowManager.LayoutParams lp) {
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    public static PayServiceOrderDialog show(FragmentManager fragmentManager, ServiceEntity serviceEntity, String payWay, boolean isOpen) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(IntentBuilder.KEY_DATA, serviceEntity);
        bundle.putString(IntentBuilder.KEY_TYPE, payWay);
        bundle.putBoolean(IntentBuilder.KEY_BOOLEAN, isOpen);
        PayServiceOrderDialog payServiceOrderDialog = new PayServiceOrderDialog();
        payServiceOrderDialog.setArguments(bundle);
        payServiceOrderDialog.show(fragmentManager);
        return payServiceOrderDialog;
    }
    public static PayServiceOrderDialog show2(FragmentManager fragmentManager, ServiceEntity serviceEntity, String payWay, boolean isOpen,boolean mStatusIsPay) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(IntentBuilder.KEY_DATA, serviceEntity);
        bundle.putString(IntentBuilder.KEY_TYPE, payWay);
        bundle.putBoolean(IntentBuilder.KEY_BOOLEAN, isOpen);
        bundle.putBoolean(IntentBuilder.KEY_BOOLEAN_2, mStatusIsPay);
        PayServiceOrderDialog payServiceOrderDialog = new PayServiceOrderDialog();
        payServiceOrderDialog.setArguments(bundle);
        payServiceOrderDialog.show(fragmentManager);
        return payServiceOrderDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (!mStatusIsPay) {
            ChoosePayWayDialog.show(mServiceEntity, mIsOpen, getFragmentManager());
        }
        super.onDismiss(dialog);

    }

}
