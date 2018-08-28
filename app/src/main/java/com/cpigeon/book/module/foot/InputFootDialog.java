package com.cpigeon.book.module.foot;

import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.base.base.BaseDialogFragment;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.picker.PickerUtil;
import com.base.util.system.ScreenTool;
import com.base.util.utility.StringUtil;
import com.base.util.utility.TimeUtil;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.widget.gridpasswordview.GridPasswordView;

import java.util.Collections;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;

/**
 * Created by Zhu TingYu on 2018/8/27.
 */

public class InputFootDialog extends BaseDialogFragment {

    private ImageButton mImgClose;
    private TextView mTvFinish;
    private GridPasswordView mGpFoot;
    private TextView mTvYear;
    private TextView mTvArea;
    private TextView mTvSwitch;
    private EditText mEdFoot;
    private boolean isStandard = true;
    List<String> years = Lists.newArrayList();
    List<String> area = Lists.newArrayList();


    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_input_foot;
    }

    @Override
    protected void initView(Dialog dialog) {
        mImgClose = dialog.findViewById(R.id.imgClose);
        mTvFinish = dialog.findViewById(R.id.tvFinish);
        mGpFoot = dialog.findViewById(R.id.gpFoot);
        mTvYear = dialog.findViewById(R.id.tvYear);
        mTvArea = dialog.findViewById(R.id.tvArea);
        mTvSwitch = dialog.findViewById(R.id.tvSwitch);
        mEdFoot = dialog.findViewById(R.id.edFoot);

        getYears();
        getAreas();

        mTvYear.setText(years.get(0));
        mTvArea.setText(area.get(0));

        mTvYear.setOnClickListener(v -> {
            PickerUtil.showItemPicker(getActivity(), years, 0, new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int index, String item) {
                    mTvYear.setText(item);
                }
            });
        });

        mTvArea.setOnClickListener(v -> {
            PickerUtil.showItemPicker(getActivity(), area, 0, new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int index, String item) {
                    mTvArea.setText(item);
                }
            });
        });

        mTvSwitch.setOnClickListener(v -> {
            switchStatus();
        });

        mTvFinish.setOnClickListener(v -> {
            if (mOnFootStringFinishListener != null) {
                if (isStandard) {
                    if(StringUtil.isStringValid(mGpFoot.getPassWord())){
                        mOnFootStringFinishListener.foots(Utils.getString(R.string.text_standard_foot
                                , mTvYear.getText().toString()
                                , mTvArea.getText().toString()
                                , mGpFoot.getPassWord()));
                    }else {
                        ToastUtils.showLong(getActivity(), R.string.text_pleas_input_foot_number);
                    }
                } else {

                    if(StringUtil.isStringValid(mEdFoot.getText().toString())){
                        mOnFootStringFinishListener.foots(mEdFoot.getText().toString());
                    }else {
                        ToastUtils.showLong(getActivity(), R.string.text_pleas_input_foot_number);
                    }

                }
            }
            hide();
        });

        mImgClose.setOnClickListener(v -> {
            hide();
        });

    }

    private void switchStatus() {
        if (isStandard) {
            mTvSwitch.setText(R.string.text_custom_foot_ring_number);
            isStandard = false;
        } else {
            mTvSwitch.setText(R.string.text_standard_foot_ring_number);
            isStandard = true;
        }
        switchInputView();

    }

    private void switchInputView() {
        if (isStandard) {
            mTvYear.setVisibility(View.GONE);
            mTvArea.setVisibility(View.GONE);
            mGpFoot.setVisibility(View.GONE);
            mEdFoot.setVisibility(View.VISIBLE);
        } else {
            mTvYear.setVisibility(View.VISIBLE);
            mTvArea.setVisibility(View.VISIBLE);
            mGpFoot.setVisibility(View.VISIBLE);
            mEdFoot.setVisibility(View.GONE);
        }
    }

    private void getYears() {
        int len = Integer.valueOf(TimeUtil.format(System.currentTimeMillis(), TimeUtil.FORMAT_YYYY));
        for (int i = 2010; i <= len; i++) {
            years.add(String.valueOf(i));
        }
        Collections.reverse(years);
    }

    private void getAreas() {
        for (int i = 1; i <= 33; i++) {
            if (i < 10) {
                area.add("0" + i);
            } else {
                area.add(String.valueOf(i));
            }
        }
    }

    @Override
    protected void initLayout(Window window, WindowManager.LayoutParams lp) {
        window.setWindowAnimations(R.style.AnimBottomDialog);
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (ScreenTool.getScreenHeight() / 4) * 3;
        window.setAttributes(lp);
    }

    public interface OnFootStringFinishListener {
        void foots(String foot);
    }

    private OnFootStringFinishListener mOnFootStringFinishListener;

    public void setOnFootStringFinishListener(OnFootStringFinishListener onFootStringFinishListener) {
        mOnFootStringFinishListener = onFootStringFinishListener;
    }
}
