package com.cpigeon.book.module.menu.smalltools.ullage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.LocationFormatUtils;
import com.base.util.dialog.DialogUtils;
import com.base.util.utility.TimeUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.menu.smalltools.lineweather.presenter.LineWeatherPresenter;
import com.cpigeon.book.module.menu.smalltools.lineweather.view.activity.LineWeatherFragment;
import com.cpigeon.book.module.trainpigeon.SelectTimeHaveHMSDialog;
import com.cpigeon.book.util.MathUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 空距测量小工具
 * Created by Administrator on 2018/1/22.
 */

public class UllageToolFragment extends BaseBookFragment {

    @BindView(R.id.et_ullage1_lo1)
    EditText etUllage1Lo1;
    @BindView(R.id.et_ullage1_lo2)
    EditText etUllage1Lo2;
    @BindView(R.id.et_ullage1_lo3)
    EditText etUllage1Lo3;
    @BindView(R.id.et_ullage1_la1)
    EditText etUllage1La1;
    @BindView(R.id.et_ullage1_la2)
    EditText etUllage1La2;
    @BindView(R.id.et_ullage1_la3)
    EditText etUllage1La3;
    @BindView(R.id.et_ullage2_lo1)
    EditText etUllage2Lo1;
    @BindView(R.id.et_ullage2_lo2)
    EditText etUllage2Lo2;
    @BindView(R.id.et_ullage2_lo3)
    EditText etUllage2Lo3;
    @BindView(R.id.et_ullage2_la1)
    EditText etUllage2La1;
    @BindView(R.id.et_ullage2_la2)
    EditText etUllage2La2;
    @BindView(R.id.et_ullage2_la3)
    EditText etUllage2La3;
    @BindView(R.id.tv_djjs)
    TextView tvDjjs;
    @BindView(R.id.tv_time1)
    TextView tvTime1;
    @BindView(R.id.tv_time2)
    TextView tvTime2;

    private LineWeatherPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ullage_tool, container, false);
        return view;
    }


    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, UllageToolFragment.class);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = new LineWeatherPresenter();
        initViewModels(mPresenter);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("空距计算");
        initViews(savedInstanceState);
    }

    protected void initViews(Bundle savedInstanceState) {

        etUllage1Lo1.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getContext(), etUllage1Lo1, 1, etUllage1Lo2));
        etUllage1Lo2.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getContext(), etUllage1Lo2, 3, etUllage1Lo3));
        etUllage1Lo3.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getContext(), etUllage1Lo3, 4, etUllage1La1));

        etUllage1La1.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getContext(), etUllage1La1, 2, etUllage1La2));
        etUllage1La2.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getContext(), etUllage1La2, 3, etUllage1La3));
        etUllage1La3.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getContext(), etUllage1La3, 4, etUllage2Lo1));

        etUllage2Lo1.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getContext(), etUllage2Lo1, 1, etUllage2Lo2));
        etUllage2Lo2.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getContext(), etUllage2Lo2, 3, etUllage2Lo3));
        etUllage2Lo3.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getContext(), etUllage2Lo3, 4, etUllage2La1));

        etUllage2La1.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getContext(), etUllage2La1, 2, etUllage2La2));
        etUllage2La2.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getContext(), etUllage2La2, 3, etUllage2La3));
        etUllage2La3.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getContext(), etUllage2La3, 4, etUllage2La3));
    }

    private SelectTimeHaveHMSDialog mSelectTimeHaveHMSDialog = new SelectTimeHaveHMSDialog();

    @OnClick({R.id.ll_xzsj1, R.id.ll_xzsj2, R.id.tv_djjs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_xzsj1://司放地经纬度选择时间


                mSelectTimeHaveHMSDialog.setOnTimeSelectListener((hours, minute, second, time) -> {
                    tvTime1.setText(hours + "时" + minute + "分" + second + "秒");
                });
                mSelectTimeHaveHMSDialog.show(getBaseActivity().getSupportFragmentManager());
                break;
            case R.id.ll_xzsj2://鸽舍经纬度选择时间
                mSelectTimeHaveHMSDialog.setOnTimeSelectListener((hours, minute, second, time) -> {
                    tvTime2.setText(hours + "时" + minute + "分" + second + "秒");
                });
                mSelectTimeHaveHMSDialog.show(getBaseActivity().getSupportFragmentManager());
                break;
            case R.id.tv_djjs://点击计算
                uploadIdCardInfo();
                break;
        }
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mPresenter.mUllageToolData.observe(this, data -> {
            double result = data.getResult();

            IntentBuilder mIntentBuilder = IntentBuilder.Builder();

            mIntentBuilder.putExtra("sfd_lo", LocationFormatUtils.Aj2GPSLocation(UlageToolPresenter.strPj(etUllage1Lo1, etUllage1Lo2, etUllage1Lo3)));
            mIntentBuilder.putExtra("sfd_la", LocationFormatUtils.Aj2GPSLocation(UlageToolPresenter.strPj(etUllage1La1, etUllage1La2, etUllage1La3)));
            mIntentBuilder.putExtra("gc_lo", LocationFormatUtils.Aj2GPSLocation(UlageToolPresenter.strPj(etUllage2Lo1, etUllage2Lo2, etUllage2Lo3)));
            mIntentBuilder.putExtra("gc_la", LocationFormatUtils.Aj2GPSLocation(UlageToolPresenter.strPj(etUllage2La1, etUllage2La2, etUllage2La3)));
            mIntentBuilder.putExtra("result", String.valueOf(result));


            if (!tvTime1.getText().toString().equals("请选择时间") && !tvTime2.getText().toString().equals("请选择时间")) {
                double time1 = TimeUtil.parse(tvTime1.getText().toString(), TimeUtil.FORMAT_HHMMSS);
                double time2 = TimeUtil.parse(tvTime2.getText().toString(), TimeUtil.FORMAT_HHMMSS);
                if (time2 < time1) {
                    getBaseActivity().errorDialog = DialogUtils.createErrorDialog(getBaseActivity(), "归巢时间小于司放时间");
                    return;
                }
                mIntentBuilder.putExtra("time", String.valueOf(MathUtil.doubleformat(result * 1000 / (time2 - time1), 2) + "米/秒"));
            }

            mIntentBuilder.startParentActivity(getBaseActivity(), UllageToolDetailsFragment.class);

        });
    }

    public void uploadIdCardInfo() {

        mPresenter.body = new HashMap<>();//存放参数
        if (errotToast(etUllage1Lo1)) return;
        if (errotToast(etUllage1Lo2)) return;
        if (errotToast(etUllage1Lo3)) return;
        if (errotToast(etUllage1La1)) return;
        if (errotToast(etUllage1La2)) return;
        if (errotToast(etUllage1La3)) return;

        if (errotToast(etUllage2Lo1)) return;
        if (errotToast(etUllage2Lo2)) return;
        if (errotToast(etUllage2Lo3)) return;
        if (errotToast(etUllage2La1)) return;
        if (errotToast(etUllage2La2)) return;
        if (errotToast(etUllage2La3)) return;

        mPresenter.body.clear();//清除集合中之前的数据
        mPresenter.body.put("uid", UserModel.getInstance().getUserId());//用户id
        mPresenter.body.put("fangfeijingdu_du", etUllage1Lo1.getText().toString());//
        mPresenter.body.put("fangfeijingdu_fen", etUllage1Lo2.getText().toString());//
        mPresenter.body.put("fangfeijingdu_miao", etUllage1Lo3.getText().toString());//
        mPresenter.body.put("fangfeiweidu_du", etUllage1La1.getText().toString());//
        mPresenter.body.put("fangfeiweidu_fen", etUllage1La2.getText().toString());//
        mPresenter.body.put("fangfeiweidu_miao", etUllage1La3.getText().toString());//

        mPresenter.body.put("guichaojingdu_du", etUllage2Lo1.getText().toString());//
        mPresenter.body.put("guichaojingdu_fen", etUllage2Lo2.getText().toString());//
        mPresenter.body.put("guichaojingdu_miao", etUllage2Lo3.getText().toString());//
        mPresenter.body.put("guichaoweidu_du", etUllage2La1.getText().toString());//
        mPresenter.body.put("guichaoweidu_fen", etUllage2La2.getText().toString());//
        mPresenter.body.put("guichaoweidu_miao", etUllage2La3.getText().toString());//

        mPresenter.getKongJuData();


//        if (!tvTime1.getText().toString().equals("请选择时间")) {
//            Log.d(TAG, "时间: " + DateUtils.hmsTolong(tvTime1.getText().toString()));
//        }
//
//        if (!tvTime2.getText().toString().equals("请选择时间")) {
//            Log.d(TAG, "时间: " + DateUtils.hmsTolong(tvTime2.getText().toString()));
//        }

//        RetrofitHelper.getApi()
//                .getKongju(AssociationData.getUserToken(), postParams, timestamp, CommonUitls.getApiSign(timestamp, postParams))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(data -> {
//
//                    try {
//                        if (data.getErrorCode() == 0) {
//                            if (data.getData() != null) {
//                                double result = data.getData().getResult();
//
//                                Intent intent = new Intent(UllageToolFragment.this, UllageToolDetailsFragment.class);
//                                intent.putExtra("sfd_lo", CommonUitls.Aj2GPSLocation(UlageToolPresenter.strPj(etUllage1Lo1, etUllage1Lo2, etUllage1Lo3)));
//                                intent.putExtra("sfd_la", CommonUitls.Aj2GPSLocation(UlageToolPresenter.strPj(etUllage1La1, etUllage1La2, etUllage1La3)));
//                                intent.putExtra("gc_lo", CommonUitls.Aj2GPSLocation(UlageToolPresenter.strPj(etUllage2Lo1, etUllage2Lo2, etUllage2Lo3)));
//                                intent.putExtra("gc_la", CommonUitls.Aj2GPSLocation(UlageToolPresenter.strPj(etUllage2La1, etUllage2La2, etUllage2La3)));
//                                intent.putExtra("result", String.valueOf(result));
//
//                                if (!tvTime1.getText().toString().equals("请选择时间") && !tvTime2.getText().toString().equals("请选择时间")) {
//                                    double time1 = DateUtils.hmsTolong(tvTime1.getText().toString());
//                                    double time2 = DateUtils.hmsTolong(tvTime2.getText().toString());
//                                    if (time2 < time1) {
//                                        CommonUitls.showSweetDialog(UllageToolFragment.this, "归巢时间小于司放时间");
//                                        return;
//                                    }
//                                    intent.putExtra("time", String.valueOf(CommonUitls.doubleformat(result * 1000 / (time2 - time1), 2) + "米/秒"));
//                                }
//
//                                startActivity(intent);
//                            }
//                        } else {
//                            CommonUitls.showSweetDialog(UllageToolFragment.this, "输入经纬度有误");
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }, throwable -> {
//                    try {
//                        CommonUitls.showSweetDialog(UllageToolFragment.this, throwable.getLocalizedMessage());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
    }

    //判读输入文本输入是否为空
    public boolean errotToast(EditText et) {
        if (et.getText().toString().equals("")) {
            getBaseActivity().errorDialog = DialogUtils.createHintDialog(getContext(), "输入内容不能为空", SweetAlertDialog.ERROR_TYPE, true, dialog -> {
                dialog.dismiss();
            });
            return true;
        } else {
            return false;
        }
    }
}
