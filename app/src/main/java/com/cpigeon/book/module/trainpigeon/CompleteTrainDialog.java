package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.base.base.BaseDialogFragment;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.LocationFormatUtils;
import com.base.util.Utils;
import com.base.util.picker.SelectTimeHaveHMSDialog;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.select.SelectLocationByMapFragment;
import com.cpigeon.book.util.MathUtil;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/10/9.
 */

public class CompleteTrainDialog extends BaseDialogFragment {
    private ImageView mImgClose;
    private RelativeLayout mLlChooseTime;
    private ImageView mImgLocation;
    private EditText mEdLoDegree;
    private EditText mEdLoMinute;
    private EditText mEdLoSecond;
    private EditText mEdLaDegree;
    private EditText mEdLaMinute;
    private EditText mEdLaSecond;
    private EditText mEdDis;
    private TextView mEdTime;
    private TextView mTvOk;
    List<EditText> mEditTexts;

    String mTime;
    float mDis;
    String mLocation;
    LatLng mFlyLocation;

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_complete_train;
    }

    @Override
    protected void initView(Dialog dialog) {
        mImgClose = dialog.findViewById(R.id.imgClose);
        mLlChooseTime = dialog.findViewById(R.id.llChooseTime);
        mImgLocation = dialog.findViewById(R.id.imgLocation);
        mEdLoDegree = dialog.findViewById(R.id.edLoDegree);
        mEdLoMinute = dialog.findViewById(R.id.edLoMinute);
        mEdLoSecond = dialog.findViewById(R.id.edLoSecond);
        mEdLaDegree = dialog.findViewById(R.id.edLaDegree);
        mEdLaMinute = dialog.findViewById(R.id.edLaMinute);
        mEdLaSecond = dialog.findViewById(R.id.edLaSecond);
        mEdTime = dialog.findViewById(R.id.edTime);
        mEdDis = dialog.findViewById(R.id.edDis);
        mTvOk = dialog.findViewById(R.id.tvOk);

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

                    if(isDataNotNull()){
                        getDis();
                    }

                }
            });
        }

        mImgLocation.setOnClickListener(v -> {
            SelectLocationByMapFragment.start(getBaseActivity(), SelectLocationByMapFragment.CODE_LOCATION);
        });

        mLlChooseTime.setOnClickListener(v -> {
            SelectTimeHaveHMSDialog selectTimeHaveHMSDialog = new SelectTimeHaveHMSDialog();
            selectTimeHaveHMSDialog.setOnTimeSelectListener((hours, minute, second, time) -> {
                mTime = time;
                mEdTime.setText(mTime);
            });
            selectTimeHaveHMSDialog.show(getActivity().getSupportFragmentManager());
        });

        mImgClose.setOnClickListener(v -> {
            dismiss();
        });

        if (mOnSureClickListener != null) {
            mTvOk.setOnClickListener(v -> {
                if (!isDataNotNull()) {
                    getBaseActivity().error(Utils.getString(R.string.text_pleas_complete_train_info));
                    return;
                }
                mOnSureClickListener.click(mTime, mFlyLocation, mDis, mLocation);
            });
        }
    }

    private boolean isDataNotNull() {
        return StringUtil.isStringValid(getString(mEdLoDegree))
                && StringUtil.isStringValid(getString(mEdLoMinute))
                && StringUtil.isStringValid(getString(mEdLoSecond))
                && StringUtil.isStringValid(getString(mEdLaDegree))
                && StringUtil.isStringValid(getString(mEdLaMinute))
                && StringUtil.isStringValid(getString(mEdLaSecond))
                && StringUtil.isStringValid(mTime);
    }

    private void getDis(){
        String lo = LocationFormatUtils.getDMS(getString(mEdLoDegree), getString(mEdLoMinute), getString(mEdLoSecond));
        String la = LocationFormatUtils.getDMS(getString(mEdLaDegree), getString(mEdLaMinute), getString(mEdLaSecond));

        mFlyLocation = new LatLng(LocationFormatUtils.Aj2GPSLocation(Double.valueOf(la))
                , LocationFormatUtils.Aj2GPSLocation(Double.valueOf(lo)));

        LatLng houseP = new LatLng(UserModel.getInstance().getUserData().pigeonHouseEntity.getLatitude()
                , UserModel.getInstance().getUserData().pigeonHouseEntity.getLongitude());

        mDis = AMapUtils.calculateLineDistance(mFlyLocation, houseP);

        mEdDis.setText(Utils.getString(R.string.text_KM, MathUtil.doubleformat(mDis / 1000,2)));
    }

    private void textMax(EditText editText, int maxNumber, EditText nextEd) {
        String text = editText.getText().toString();

        if (!StringUtil.isStringValid(text)) {
            return;
        }

        String maxNumberString = String.valueOf(maxNumber);
        int number = Integer.valueOf(text);
        if (text.length() >= maxNumberString.length()) {
            if (number > maxNumber) {
                editText.setText(maxNumberString);
            }
            editText.clearFocus();
            if (nextEd != null) {
                nextEd.requestFocus();
            }
        }

    }

    private void textMax(EditText thisEd, float maxNumber, EditText nextEd) {
        String text = thisEd.getText().toString();
        if (!StringUtil.isStringValid(text)) {
            return;
        }
        String maxNumberString = String.valueOf(maxNumber);

        float number = Float.valueOf(text);

        if (number > maxNumber) {
            thisEd.setText(maxNumberString);
            thisEd.clearFocus();
            if (nextEd != null) {
                nextEd.requestFocus();
            }
        } else {
            if (text.length() >= 5) {
                thisEd.clearFocus();
                if (nextEd != null) {
                    nextEd.requestFocus();
                }
            }
        }
    }

    private String getString(EditText editText) {
        return editText.getText().toString();
    }


    public interface OnSureClickListener {
        void click(String time, LatLng latLng, float dis, String location);
    }

    private OnSureClickListener mOnSureClickListener;

    public void setOnSureClickListener(OnSureClickListener onSureClickListener) {
        mOnSureClickListener = onSureClickListener;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            RegeocodeAddress address = data.getParcelableExtra(IntentBuilder.KEY_DATA);
            LatLonPoint point = data.getParcelableExtra(IntentBuilder.KEY_DATA_2);
            String loStr = LocationFormatUtils.GPS2AjLocation(point.getLongitude());
            String laStr = LocationFormatUtils.GPS2AjLocation(point.getLatitude());

            mEdLoDegree.setText(LocationFormatUtils.strToD(loStr));
            mEdLoMinute.setText(LocationFormatUtils.strToM(loStr));
            mEdLoSecond.setText(LocationFormatUtils.strToS(loStr));

            mEdLaDegree.setText(LocationFormatUtils.strToD(laStr));
            mEdLaMinute.setText(LocationFormatUtils.strToM(laStr));
            mEdLaSecond.setText(LocationFormatUtils.strToS(laStr));
            mLocation = address.getProvince() + address.getCity() + address.getDistrict();

            getDis();
        }
    }
}
