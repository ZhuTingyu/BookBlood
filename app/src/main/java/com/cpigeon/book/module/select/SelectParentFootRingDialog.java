package com.cpigeon.book.module.select;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.base.util.Lists;
import com.base.util.system.ScreenTool;
import com.base.widget.LoadingView;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.select.adpter.SelectPigeonAdapter;
import com.cpigeon.book.module.select.viewmodel.SelectParentFootRingViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhu TingYu on 2018/10/20.
 */

public class SelectParentFootRingDialog extends BaseDialogFragment {
    private TextView mTvTitle;
    private RecyclerView mList;
    private SelectPigeonAdapter mAdapter;
    private String mFootNumber;
    private String mSexId;
    private List<PigeonEntity> mPigeonEntities;
    private ImageView mImgClose;


    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_select_parent_foot_ring;
    }


    @Override
    protected void initView(Dialog dialog) {

        mPigeonEntities = (List<PigeonEntity>) getArguments().getSerializable(IntentBuilder.KEY_DATA);
        mSexId = getArguments().getString(IntentBuilder.KEY_DATA_2);

        mTvTitle = dialog.findViewById(R.id.tvTitle);
        mList = dialog.findViewById(R.id.list);
        mImgClose = dialog.findViewById(R.id.imgClose);

        mList.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        RecyclerViewUtils.addItemDecorationLine(mList);
        mAdapter = new SelectPigeonAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mOnPigeonChooseListener.choosePigeon(mAdapter.getItem(position));
            dismiss();
        });
        mList.setAdapter(mAdapter);
        if (PigeonEntity.ID_MALE.equals(mSexId)) {
            mTvTitle.setText(R.string.text_select_father_pigeon);
        } else {
            mTvTitle.setText(R.string.text_select_mother_pigeon);
        }

        mImgClose.setOnClickListener(v -> {
            dismiss();
        });


        mAdapter.setNewData(mPigeonEntities);

    }

    public static void show(FragmentManager fragmentManager, List<PigeonEntity> pigeonEntities
            , String sexId, OnPigeonChooseListener listener) {
        if(Lists.isEmpty(pigeonEntities)){
            return;
        }
        SelectParentFootRingDialog selectParentFootRingDialog = new SelectParentFootRingDialog();
        selectParentFootRingDialog.setOnPigeonChooseListener(listener);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentBuilder.KEY_DATA, (Serializable) pigeonEntities);
        bundle.putString(IntentBuilder.KEY_DATA_2, sexId);
        selectParentFootRingDialog.setArguments(bundle);
        selectParentFootRingDialog.show(fragmentManager);

    }

    @Override
    protected void initLayout(Window window, WindowManager.LayoutParams lp) {
        lp.gravity = Gravity.CENTER;
        lp.width = (ScreenTool.getScreenWidth() / 4) * 3;
        lp.height = (ScreenTool.getScreenHeight() / 4) * 3;
        window.setAttributes(lp);
    }

    public interface OnPigeonChooseListener{
        void choosePigeon(PigeonEntity pigeonEntity);
    }

    private OnPigeonChooseListener mOnPigeonChooseListener;

    public void setOnPigeonChooseListener(OnPigeonChooseListener onPigeonChooseListener) {
        mOnPigeonChooseListener = onPigeonChooseListener;
    }
}
