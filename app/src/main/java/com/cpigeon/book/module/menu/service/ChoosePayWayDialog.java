package com.cpigeon.book.module.menu.service;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseDialogFragment;
import com.base.util.IntentBuilder;
import com.base.util.Utils;
import com.base.util.system.ScreenTool;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.event.OpenServiceEvent;
import com.cpigeon.book.event.WXPayEvent;
import com.cpigeon.book.model.entity.ServiceEntity;
import com.cpigeon.book.module.menu.service.adpter.PayOpenServiceAdapter;
import com.cpigeon.book.module.menu.service.viewmodel.PayServiceOrderViewModel;
import com.cpigeon.book.util.MathUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Zhu TingYu on 2018/9/4.
 */

public class ChoosePayWayDialog extends BaseDialogFragment {

    private ImageView mImgClose;
    private TextView mTvTitle;
    private TextView mTvPrice;
    private RecyclerView mList;
    private TextView mTvOk;
    private boolean mIsOpen;


    PayOpenServiceAdapter mAdapter;
    ServiceEntity mServiceEntity;
    double mScore;
    double mBlance;
    private String mPayWay;

    PayServiceOrderViewModel mPayServiceOrderViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            mServiceEntity = getArguments().getParcelable(IntentBuilder.KEY_DATA);
            mScore = getArguments().getDouble(IntentBuilder.KEY_DATA_2);
            mBlance = getArguments().getDouble(IntentBuilder.KEY_DATA_3);
            mIsOpen = getArguments().getBoolean(IntentBuilder.KEY_BOOLEAN);
        }
        mPayServiceOrderViewModel = new PayServiceOrderViewModel();
        initViewModel(mPayServiceOrderViewModel);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_pay_open_service;
    }

    @Override
    protected void initView(Dialog dialog) {
        mImgClose = dialog.findViewById(R.id.imgClose);
        mTvTitle = dialog.findViewById(R.id.tvTitle);
        mTvPrice = dialog.findViewById(R.id.tvPrice);
        mList = dialog.findViewById(R.id.list);
        mTvOk = dialog.findViewById(R.id.tvOk);

        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new PayOpenServiceAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            setPayWay(position);
        });
        mList.setAdapter(mAdapter);
        setPayWay(0);

        if (mServiceEntity != null) {
            mTvTitle.setText(mServiceEntity.getSname());
        }

        mImgClose.setOnClickListener(v -> {
            hide();
        });
if(!mIsOpen)
{
    mTvOk.setText("立即续费");
}
        mTvOk.setOnClickListener(v -> {
            if (mPayWay.equals(PayServiceOrderViewModel.WAY_WX)) {
                setProgressVisible(true);
                mPayServiceOrderViewModel.mPayWay = PayServiceOrderViewModel.WAY_WX;
                mPayServiceOrderViewModel.mServiceId = mServiceEntity.getSid();
                if (mIsOpen) {
                    mPayServiceOrderViewModel.payOder();
                } else {
                    mPayServiceOrderViewModel.renewalPayOder();
                }
            } else {

                if (mPayWay.equals(PayServiceOrderViewModel.WAY_SCORE)) {
                    if (mScore < Double.valueOf(mServiceEntity.getScore())) {
                        error(R.string.text_score_not_lack);
                        return;
                    }
                } else {
                    if (mBlance < Double.valueOf(mServiceEntity.getPrice())) {
                        error(R.string.text_blance_not_lack);
                        return;
                    }
                }

                PayServiceOrderDialog.show(getFragmentManager(), mServiceEntity, mScore, mBlance, mPayWay, mIsOpen);
                dismiss();
            }
        });

        mPayServiceOrderViewModel.mDataWXOrder.observe(this, s -> {
            ToastUtils.showLong(getBaseActivity(), Utils.getString(R.string.text_to_wx));
            mPayServiceOrderViewModel.mOrderId = s.getOid();
            mPayServiceOrderViewModel.getWXOrder();
        });

    }

    private void setPayWay(int position) {
        try {
            mAdapter.setSingleItem(position);
            StringBuilder sb = new StringBuilder();
            if (position == 0) {
                try {
                    sb.append(Utils.getString(R.string.text_pigeon_score_content, mServiceEntity.getScore()));
                    sb.append(mServiceEntity.getNum());
                    sb.append(mServiceEntity.getDanwei());
                    mPayWay = PayServiceOrderViewModel.WAY_SCORE;
                } catch (Exception e) {
                    ToastUtils.showLong(getBaseActivity(), "当前鸽币订单生成有误，请稍后再试！");
                }
            } else if (position == 1) {
                try {
                    sb.append(Utils.getString(R.string.text_yuan, mServiceEntity.getPrice()));
                    sb.append(mServiceEntity.getNum());
                    sb.append(mServiceEntity.getDanwei());
                    mPayWay = PayServiceOrderViewModel.WAY_BALANCE;
                } catch (Exception e) {
                    ToastUtils.showLong(getBaseActivity(), "当前余额订单生成有误，请稍后再试！");
                }
            } else if (position == 2) {
                try {
                    mPayWay = PayServiceOrderViewModel.WAY_WX;
                    float price = Float.valueOf(mServiceEntity.getPrice()) / 100f;
                    sb.append(Utils.getString(R.string.text_yuan, mServiceEntity.getPrice()));
                    sb.append(mServiceEntity.getNum());
                    sb.append(mServiceEntity.getDanwei());
                    sb.append(Utils.getString(R.string.text_weixing_charge, String.valueOf(MathUtil.doubleformat(price, 2))));
                } catch (Exception e) {
                    ToastUtils.showLong(getBaseActivity(), "当前微信订单生成有误，请稍后再试！");
                }
            }
            mTvPrice.setText(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void initLayout(Window window, WindowManager.LayoutParams lp) {
        lp.gravity = Gravity.CENTER;
        lp.width = ScreenTool.getScreenWidth() - ScreenTool.dip2px(80);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }


    //传入订单实体类
    public static void show(ServiceEntity s, boolean isOpen, double score, double banlace, FragmentManager fragmentManager) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(IntentBuilder.KEY_DATA, s);
        bundle.putDouble(IntentBuilder.KEY_DATA_2, score);
        bundle.putDouble(IntentBuilder.KEY_DATA_3, banlace);
        bundle.putBoolean(IntentBuilder.KEY_BOOLEAN, isOpen);
        ChoosePayWayDialog dialog = new ChoosePayWayDialog();
        dialog.setArguments(bundle);
        dialog.show(fragmentManager);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(WXPayEvent event) {
        setProgressVisible(false);
        EventBus.getDefault().post(new OpenServiceEvent());
        dismiss();
    }
}
