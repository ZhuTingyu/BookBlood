package com.cpigeon.book.module.foot;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.foot.viewmodel.FootAdminViewModel;
import com.cpigeon.book.widget.LineInputView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 详情 多个足环  fragment
 * Created by Administrator on 2018/8/10.
 */

public class FootAdminDetailsMultipleFragment extends BaseBookFragment {


    @BindView(R.id.lv_city)
    LineInputView lvCity;
    @BindView(R.id.lv_foot)
    LineInputView lvFoot;
    @BindView(R.id.lv_category)
    LineInputView lvCategory;
    @BindView(R.id.lv_source)
    LineInputView lvSource;
    @BindView(R.id.lv_money)
    LineInputView lvMoney;
    @BindView(R.id.tvOk)
    TextView tvOk;
    private FootAdminViewModel mFootAdminModel;


    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, FootAdminDetailsMultipleFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_multiple_foot, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFootAdminModel = new FootAdminViewModel(getBaseActivity());
        initViewModels(mFootAdminModel);

    }

    @OnClick({R.id.lv_city, R.id.lv_foot, R.id.lv_category, R.id.lv_source, R.id.lv_money, R.id.tvOk})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lv_city:
                ToastUtils.showLong(getActivity(), "1");
                break;
            case R.id.lv_foot:
                ToastUtils.showLong(getActivity(), "2");
                break;
            case R.id.lv_category:
                ToastUtils.showLong(getActivity(), "3");
                break;
            case R.id.lv_source:
                ToastUtils.showLong(getActivity(), "4");
                break;
            case R.id.lv_money:
                ToastUtils.showLong(getActivity(), "5");
                break;
            case R.id.tvOk:

               // mFootAdminModel.getTXGP_FootRing_AddData();
                ToastUtils.showLong(getActivity(), "6");
                break;
        }
    }
}
