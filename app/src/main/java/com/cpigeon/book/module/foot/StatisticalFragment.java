package com.cpigeon.book.module.foot;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.base.util.IntentBuilder;
import com.base.util.RxUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.foot.viewmodel.StatisticalViewModel;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 统计fragment
 * Created by Administrator on 2018/8/9.
 */

public class StatisticalFragment extends BaseBookFragment {


    private StatisticalViewModel mStatisticalViewModel;

    @BindView(R.id.et2)
    EditText et2;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, StatisticalFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistical_home, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStatisticalViewModel = new StatisticalViewModel();
        initViewModels(mStatisticalViewModel);


        bindUi(RxUtils.textChanges(et2), mStatisticalViewModel.setPigeonType());//鸽子类型
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                //得到各种类型的足环个数
                mStatisticalViewModel.getgetTXGP_FootRing_SelectTypeData();

                break;
            case R.id.btn2:
                //查询种鸽（或者赛鸽）的雄鸽雌鸽数量
                mStatisticalViewModel.getPigeonSexCountData();
                break;
            case R.id.btn3:
                //
                break;
            case R.id.btn4:
                //

                break;
            case R.id.btn5:
                //

                break;
            case R.id.btn6:
                //
                break;
            case R.id.btn7:
                //

                break;

            case R.id.btn8:
                //

                break;

            case R.id.btn9:

                break;
        }
    }
}
