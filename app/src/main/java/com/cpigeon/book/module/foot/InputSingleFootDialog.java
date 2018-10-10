package com.cpigeon.book.module.foot;

import android.app.Dialog;
import android.content.DialogInterface;
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
import com.base.util.utility.KeyboardUtils;
import com.base.util.utility.StringUtil;
import com.base.util.utility.TimeUtil;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.widget.gridpasswordview.GridPasswordView;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.util.Collections;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;

/**
 * Created by Zhu TingYu on 2018/8/27.
 */

public class InputSingleFootDialog extends BaseDialogFragment {

    private ImageButton mImgClose;
    private TextView mTvFinish;
    private GridPasswordView mGpFoot;
    private TextView mTvYear;
    private TextView mTvArea;
    private TextView mTvSwitch;
    private EditText mEdFoot;
    private boolean isStandard = true;
    private boolean isHaveStandard = true;
    List<String> years = Lists.newArrayList();
    List<String> area = Lists.newArrayList();
    List<String> foots;
    private boolean mKeyboardIsOpen;


    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_input_foot;
    }

    @Override
    protected void initView(Dialog dialog) {

        KeyboardUtils.registerSoftInputChangedListener(getActivity(),isOpen -> {
            mKeyboardIsOpen = isOpen;
        });

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
        String[] address = getResources().getStringArray(R.array.srt_arr_address);

        //初始化显示省份
        mTvArea.setText(area.get(0));
        for (int i = 1; i <= address.length; i++) {
            if (address[i - 1].contains(UserModel.getInstance().getProvince())) {
                mTvArea.setText(String.valueOf(i));
                break;
            }
        }

        if (foots.size() == 3) {
            isStandard = true;
            if (!Lists.isEmpty(foots)) {
                mTvYear.setText(foots.get(0));
                mTvArea.setText(foots.get(1));
                mGpFoot.setPassword(foots.get(2));
            }
        } else if (foots.size() == 1 && StringUtil.isStringValid(foots.get(0))) {
            isStandard = false;
            if (!Lists.isEmpty(foots)) {
                mEdFoot.setText(foots.get(0));
            }
        }

        if (isStandard) {
            mTvSwitch.setText(R.string.text_custom_foot_ring_number);
            mTvYear.setVisibility(View.VISIBLE);
            mTvArea.setVisibility(View.VISIBLE);
            mGpFoot.setVisibility(View.VISIBLE);
            mEdFoot.setVisibility(View.GONE);
            mEdFoot.clearFocus();
            mGpFoot.forceInputViewGetFocus();
        } else {
            mTvSwitch.setText(R.string.text_standard_foot_ring_number);
            mTvYear.setVisibility(View.GONE);
            mTvArea.setVisibility(View.GONE);
            mGpFoot.setVisibility(View.GONE);
            mEdFoot.setVisibility(View.VISIBLE);
            mGpFoot.clearFocus();
            mEdFoot.requestFocus();
            KeyboardUtils.showSoftInput(getActivity());
        }

        KeyboardUtils.toggleSoftInput();


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
                    if (StringUtil.isStringValid(mGpFoot.getPassWord())) {
                        mOnFootStringFinishListener.foots(Utils.getString(R.string.text_standard_foot
                                , mTvYear.getText().toString()
                                , mTvArea.getText().toString()
                                , mGpFoot.getPassWord()));
                        hide();
                    } else {
                        ToastUtils.showLong(getActivity(), R.string.text_pleas_input_foot_number);
                    }
                } else {

                    if (StringUtil.isStringValid(mEdFoot.getText().toString())) {
                        mOnFootStringFinishListener.foots(mEdFoot.getText().toString());
                        hide();
                    } else {
                        ToastUtils.showLong(getActivity(), R.string.text_pleas_input_foot_number);
                    }

                }
            }

        });

        mImgClose.setOnClickListener(v -> {
            hide();
        });

        if (!isHaveStandard) {
            mTvSwitch.setVisibility(View.GONE);
        }
    }

    private void switchStatus() {
        if (isStandard) {
            isStandard = false;
            mTvSwitch.setText(R.string.text_custom_foot_ring_number);
            mTvYear.setVisibility(View.GONE);
            mTvArea.setVisibility(View.GONE);
            mGpFoot.setVisibility(View.GONE);
            mEdFoot.setVisibility(View.VISIBLE);
            mGpFoot.clearFocus();
            mEdFoot.requestFocus();
            KeyboardUtils.showSoftInput(getActivity());
        } else {
            isStandard = true;
            mTvSwitch.setText(R.string.text_standard_foot_ring_number);
            mTvYear.setVisibility(View.VISIBLE);
            mTvArea.setVisibility(View.VISIBLE);
            mGpFoot.setVisibility(View.VISIBLE);
            mEdFoot.setVisibility(View.GONE);
            mEdFoot.clearFocus();
            mGpFoot.forceInputViewGetFocus();
        }

    }

    private void getYears() {
        int len = Integer.valueOf(TimeUtil.format(System.currentTimeMillis(), TimeUtil.FORMAT_YYYY));
        for (int i = len - 10; i <= len; i++) {
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
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    public interface OnFootStringFinishListener {
        void foots(String foot);
    }

    private OnFootStringFinishListener mOnFootStringFinishListener;

    public void setOnFootStringFinishListener(OnFootStringFinishListener onFootStringFinishListener) {
        mOnFootStringFinishListener = onFootStringFinishListener;
    }

    public void setFoots(List<String> foots) {
        this.foots = foots;
    }

    public void setFootNumber(String footNumber) {
        List<String> foot = Lists.newArrayList();
        if (StringUtil.isStringValid(footNumber)) {
            String[] foots = footNumber.split(Utils.getString(R.string.text_foots_divide));
            foot = Lists.newArrayList(foots);
        }
        this.foots = foot;
    }

    public void setHaveStandard(boolean haveStandard) {
        isHaveStandard = haveStandard;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(mKeyboardIsOpen){
            KeyboardUtils.toggleSoftInput();
        }
    }
}
