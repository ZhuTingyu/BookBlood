package com.cpigeon.book.module.menu.service;

import android.app.Dialog;
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
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;
import com.cpigeon.book.module.menu.service.adpter.PayOpenServiceAdapter;

/**
 * Created by Zhu TingYu on 2018/9/4.
 */

public class PayOpenServiceDialog extends BaseDialogFragment{

    private ImageView mImgClose;
    private TextView mTvTitle;
    private TextView mTvPrice;
    private RecyclerView mList;
    private TextView mTvOk;

    PayOpenServiceAdapter mAdapter;
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
            mAdapter.setSingleItem(position);
        });
        mList.setAdapter(mAdapter);

        mImgClose.setOnClickListener(v -> {
            hide();
        });


    }

    protected void initLayout(Window window, WindowManager.LayoutParams lp){
        lp.gravity = Gravity.CENTER;
        lp.width = ScreenTool.getScreenWidth() - ScreenTool.dip2px(80);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }


    //传入订单实体类
    public static void show(String s ,FragmentManager fragmentManager){
        Bundle bundle = new Bundle();
        PayOpenServiceDialog dialog = new PayOpenServiceDialog();
        dialog.setArguments(bundle);
        dialog.show(fragmentManager);
    }
}
