package com.cpigeon.book.module.foot;

import android.app.Dialog;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.base.base.BaseDialogFragment;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.system.ScreenTool;
import com.base.util.utility.StringUtil;
import com.base.util.utility.TimeUtil;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.widget.gridpasswordview.GridPasswordView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/28.
 */

public class FootsInfoDialog extends BaseDialogFragment {

    private ImageButton mImgClose;
    private TextView mTvFinish;
    private TextView mTvYear;
    private TextView mTvArea;
    private GridPasswordView mGpSFoot;
    private GridPasswordView mGpEFoot;

    List<String> years = Lists.newArrayList();
    List<String> areas = Lists.newArrayList();

    String year;
    String area;
    String sFoot;
    String eFoot;
    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_foots_info;
    }

    @Override
    protected void initView(Dialog dialog) {

        mImgClose = dialog.findViewById(R.id.imgClose);
        mTvFinish = dialog.findViewById(R.id.tvFinish);
        mTvYear = dialog.findViewById(R.id.tvYear);
        mTvArea = dialog.findViewById(R.id.tvArea);
        mGpSFoot = dialog.findViewById(R.id.gpSFoot);
        mGpEFoot = dialog.findViewById(R.id.gpEFoot);
        getYears();
        getAreas();

        mTvYear.setText(years.get(0));
        mTvArea.setText(areas.get(0));

        if(StringUtil.isStringValid(sFoot)){
            mTvYear.setText(year);
            mTvArea.setText(area);
            mGpSFoot.setPassword(sFoot);
            mGpEFoot.setPassword(eFoot);
        }
        mImgClose.setOnClickListener(v -> {
            hide();
        });


        mTvFinish.setOnClickListener(v -> {
            if(mOnFootStringFinishListener != null){
                if(StringUtil.isStringValid(mGpSFoot.getPassWord())
                        && StringUtil.isStringValid(mGpEFoot.getPassWord())){
                    mOnFootStringFinishListener.sFoot(Utils.getString(R.string.text_standard_foot
                            , mTvYear.getText().toString()
                            , mTvArea.getText().toString()
                            , mGpSFoot.getPassWord()));

                    mOnFootStringFinishListener.eFoot(mGpEFoot.getPassWord());
                    hide();

                }else {
                    ToastUtils.showLong(getActivity(),Utils.getString(R.string.text_pleas_input_foot_number));
                }
            }
        });

        mGpSFoot.forceInputViewGetFocus();

    }

    @Override
    protected void initLayout(Window window, WindowManager.LayoutParams lp) {
        window.setWindowAnimations(R.style.AnimBottomDialog);
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (ScreenTool.getScreenHeight() / 4) * 3;
        window.setAttributes(lp);
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
                areas.add("0" + i);
            } else {
                areas.add(String.valueOf(i));
            }
        }
    }

    public interface OnFootStringFinishListener {
        void sFoot(String foot);
        void eFoot(String foot);
    }

    private OnFootStringFinishListener mOnFootStringFinishListener;

    public void setOnFootStringFinishListener(OnFootStringFinishListener onFootStringFinishListener) {
        mOnFootStringFinishListener = onFootStringFinishListener;
    }

    public void setSFoots(String sFoots) {
        if(StringUtil.isStringValid(sFoots)){
            String[] foots = sFoots.split(Utils.getString(R.string.text_foots_divide));
            year = foots[0];
            area = foots[1];
            sFoot = foots[2];
        }

    }
    public void setEFoots(String eFoots) {
        eFoot = eFoots;
    }


}
