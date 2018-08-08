package com.cpigeon.book.module.pigeonhouse;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.base.BaseDialogFragment;
import com.base.util.Lists;
import com.base.util.LocationFormatUtils;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/6.
 */

public class InputLocationFragment extends BaseDialogFragment {

    private ImageView mImgLocation;
    private EditText mEdLoDegree;
    private EditText mEdLoMinute;
    private EditText mEdLoSecond;
    private EditText mEdLaDegree;
    private EditText mEdLaMinute;
    private EditText mEdLaSecond;
    private TextView mTvCancel;
    private TextView mTvSure;

    List<EditText> mEditTexts;

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_inoput_location;
    }

    @Override
    protected void initView(Dialog dialog) {
        mImgLocation = dialog.findViewById(R.id.imgLocation);
        mEdLoDegree = dialog.findViewById(R.id.edLoDegree);
        mEdLoMinute = dialog.findViewById(R.id.edLoMinute);
        mEdLoSecond = dialog.findViewById(R.id.edLoSecond);
        mEdLaDegree = dialog.findViewById(R.id.edLaDegree);
        mEdLaMinute = dialog.findViewById(R.id.edLaMinute);
        mEdLaSecond = dialog.findViewById(R.id.edLaSecond);
        mTvCancel = dialog.findViewById(R.id.tvCancel);
        mTvSure = dialog.findViewById(R.id.tvSure);

        mEditTexts = Lists.newArrayList(mEdLoDegree, mEdLoMinute, mEdLoSecond, mEdLaDegree, mEdLaMinute, mEdLaSecond);

        for (EditText editText : mEditTexts) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int id = editText.getId();
                    if (id == mEdLoDegree.getId()) {
                        textMax(editText, 180, mEdLoMinute);
                    } else if (id == mEdLoMinute.getId()) {
                        textMax(editText, 60, mEdLoSecond);
                    } else if (id == mEdLoSecond.getId()) {
                        textMax(editText, 60.00f, mEdLaDegree);
                    } else if (id == mEdLaDegree.getId()) {
                        textMax(editText, 90, mEdLaMinute);
                    } else if (id == mEdLaMinute.getId()) {
                        textMax(editText, 60, mEdLaSecond);
                    } else if (id == mEdLaSecond.getId()) {
                        textMax(editText, 60.00f, null);
                    }
                }
            });
        }

        mImgLocation.setOnClickListener(v -> {
            if(mOnSureClickListener != null){
                mOnSureClickListener.location();
                dismiss();
            }
        });

        mTvSure.setOnClickListener(v -> {
            if(mOnSureClickListener != null){

                String lo = LocationFormatUtils.getDMS(getString(mEdLoDegree),getString(mEdLoMinute),getString(mEdLoSecond));
                String la = LocationFormatUtils.getDMS(getString(mEdLaDegree),getString(mEdLaMinute),getString(mEdLaSecond));

                if(StringUtil.isStringValid(lo) && StringUtil.isStringValid(la)){
                    mOnSureClickListener.sure(lo, la);
                }
            }
            dismiss();
        });

        mTvCancel.setOnClickListener(v -> {
            dismiss();
        });
    }

    private void textMax(EditText editText, int maxNumber, EditText nextEd) {
        String text = editText.getText().toString();

        if(!StringUtil.isStringValid(text)){
            return;
        }

        String maxNumberString = String.valueOf(maxNumber);
        int number = Integer.valueOf(text);
        if(text.length() >= maxNumberString.length()){
            if(number > maxNumber){
                editText.setText(maxNumberString);
            }
            editText.clearFocus();
            if(nextEd != null){
                nextEd.requestFocus();
            }
        }

    }

    private void textMax(EditText thisEd, float maxNumber, EditText nextEd) {
        String text = thisEd.getText().toString();
        if(!StringUtil.isStringValid(text)){
            return;
        }
        String maxNumberString = String.valueOf(maxNumber);

        float number = Float.valueOf(text);

        if(number > maxNumber){
            thisEd.setText(maxNumberString);
            thisEd.clearFocus();
            if(nextEd != null){
                nextEd.requestFocus();
            }
        }else {
            if(text.length() >= 5){
                thisEd.clearFocus();
                if(nextEd != null){
                    nextEd.requestFocus();
                }
            }
        }

    }

    private String getString(EditText editText){
        return editText.getText().toString();
    }

    private OnInputLocationClickListener mOnSureClickListener;

    public interface OnInputLocationClickListener{
        void sure(String lo, String la);
        void location();
    }

    public void setOnSureClickListener(OnInputLocationClickListener onSureClickListener) {
        mOnSureClickListener = onSureClickListener;
    }
}
