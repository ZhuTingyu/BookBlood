package com.base.util.picker;

import android.app.Dialog;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.base.base.BaseDialogFragment;
import com.base.http.R;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.system.ScreenTool;
import com.base.util.utility.TimeUtil;

import java.util.List;

import cn.qqtheme.framework.widget.WheelView;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class SelectTimeHaveHMSDialog extends BaseDialogFragment {

    protected float lineSpaceMultiplier = WheelView.LINE_SPACE_MULTIPLIER;
    protected int textPadding = WheelView.TEXT_PADDING;
    protected int textSize = WheelView.TEXT_SIZE;
    protected Typeface typeface = Typeface.DEFAULT;
    protected int textColorNormal = WheelView.TEXT_COLOR_NORMAL;
    protected int textColorFocus = WheelView.TEXT_COLOR_FOCUS;
    protected int offset = WheelView.ITEM_OFF_SET;
    protected boolean cycleDisable = true;
    protected boolean useWeight = true;
    protected boolean textSizeAutoFit = true;
    protected WheelView.DividerConfig dividerConfig = new WheelView.DividerConfig();


    private TextView mTvCancel;
    private TextView mTvSure;
    private WheelView mWheelH;
    private WheelView mWheelM;
    private WheelView mWheelS;

    List<String> mH = Lists.newArrayList();
    List<String> mM = Lists.newArrayList();
    List<String> mS = Lists.newArrayList();


    @Override
    protected int getLayoutRes() {
        return R.layout.dailog_select_time_have_hms;
    }

    @Override
    protected void initView(Dialog dialog) {
        mTvCancel = dialog.findViewById(R.id.tvCancel);
        mTvSure = dialog.findViewById(R.id.tvSure);
        mWheelH = dialog.findViewById(R.id.wheelH);
        mWheelM = dialog.findViewById(R.id.wheelM);
        mWheelS = dialog.findViewById(R.id.wheelS);

        String[] time = TimeUtil.format(System.currentTimeMillis(), TimeUtil.FORMAT_HHMMSS).split(":");

        getH();
        getM();
        getS();

        setWheelView(mWheelH);
        setWheelView(mWheelM);
        setWheelView(mWheelS);

        mWheelH.setItems(mH, time[0]);
        mWheelM.setItems(mM, time[1]);
        mWheelS.setItems(mS, time[2]);


        mTvCancel.setOnClickListener(v -> {
            dismiss();
        });

        mTvSure.setOnClickListener(v -> {
            if (mOnTimeSelectListener != null) {

                String hours = mH.get(mWheelH.getSelectedIndex());
                String minute = mM.get(mWheelM.getSelectedIndex());
                String second = mS.get(mWheelS.getSelectedIndex());
                String times = null;

                StringBuilder sb = new StringBuilder();
                sb.append(TimeUtil.format(System.currentTimeMillis(), TimeUtil.FORMAT_YYYYMMDD));
                sb.append(" ");
                sb.append(Utils.getString(R.string.text_time_h_m_s, hours, minute, second));
                times = sb.toString();

                mOnTimeSelectListener.time(hours, minute, second, times);
                dismiss();
            }
        });
    }

    private void getS() {
        for (int i = 0; i < 60; i++) {
            String s = String.valueOf(i);
            if (s.length() == 1) {
                s = "0" + s;
            }
            mS.add(s);
        }
    }

    private void getM() {
        for (int i = 0; i < 60; i++) {
            String s = String.valueOf(i);
            if (s.length() == 1) {
                s = "0" + s;
            }
            mM.add(s);
        }
    }

    private void getH() {
        for (int i = 0; i < 24; i++) {
            String s = String.valueOf(i);
            if (s.length() == 1) {
                s = "0" + s;
            }
            mH.add(s);
        }
    }

    protected void initLayout(Window window, WindowManager.LayoutParams lp) {
        lp.gravity = Gravity.BOTTOM;
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (ScreenTool.getScreenHeight() / 3);
        window.setAttributes(lp);
    }

    protected void setWheelView(WheelView wheelView) {
        wheelView.setLineSpaceMultiplier(lineSpaceMultiplier);
        wheelView.setTextPadding(textPadding);
        wheelView.setTextSize(textSize);
        wheelView.setTypeface(typeface);
        wheelView.setTextColor(com.base.util.Utils.getColor(R.color.black));
        wheelView.setDividerColor(com.base.util.Utils.getColor(R.color.color_line));
        wheelView.setDividerConfig(dividerConfig);
        wheelView.setOffset(offset);
        wheelView.setCycleDisable(cycleDisable);
        wheelView.setUseWeight(useWeight);
        wheelView.setTextSizeAutoFit(textSizeAutoFit);
        wheelView.setDividerColor(com.base.util.Utils.getColor(R.color.color_line));
//        picker.setTextColor(com.base.util.Utils.getColor(R.color.black));
//        picker.setCancelTextColor(com.base.util.Utils.getColor(R.color.home_bottom_color));
//        picker.setSubmitTextColor(com.base.util.Utils.getColor(R.color.home_bottom_color));
    }

    public interface OnTimeSelectListener {
        void time(String hours, String minute, String second, String time);
    }

    private OnTimeSelectListener mOnTimeSelectListener;

    public void setOnTimeSelectListener(OnTimeSelectListener onTimeSelectListener) {
        mOnTimeSelectListener = onTimeSelectListener;
    }
}
