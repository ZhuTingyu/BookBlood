package com.cpigeon.book.module.foot;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.base.util.IntentBuilder;
import com.base.util.RxUtils;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.foot.viewmodel.FootAdminViewModel;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/9.
 */

public class FootAdminHomeFragment extends BaseBookFragment {

    @BindView(R.id.et1)
    EditText et1;
    @BindView(R.id.et2)
    EditText et2;
    @BindView(R.id.et3)
    EditText et3;
    @BindView(R.id.et4)
    EditText et4;
    @BindView(R.id.et5)
    EditText et5;
    @BindView(R.id.et6)
    EditText et6;
    @BindView(R.id.et8)
    EditText et8;
    @BindView(R.id.et8_1)
    EditText et8_1;

    //    private BreedPigeonViewModel mViewModel;
    private FootAdminViewModel mFootAdminModel;
    private String TAG = "bufffm";


    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, FootAdminHomeFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foot_admin_home, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mViewModel = new BreedPigeonViewModel();
//        initViewModel(mViewModel);


        mFootAdminModel = new FootAdminViewModel();
        initViewModels(mFootAdminModel);

        bindUi(RxUtils.textChanges(et1), mFootAdminModel.setAddFootNum());
        bindUi(RxUtils.textChanges(et3), mFootAdminModel.setEditFootId());
        bindUi(RxUtils.textChanges(et4), mFootAdminModel.setDelFootId());
        bindUi(RxUtils.textChanges(et6), mFootAdminModel.setDetailsFootId());


        bindUi(RxUtils.textChanges(et8), mFootAdminModel.setSegmentStartFoot());
        bindUi(RxUtils.textChanges(et8_1), mFootAdminModel.setSegmentEndFoot());

    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                //添加足环
                Log.d(TAG, "onViewClicked: a");
                ToastUtils.showLong(getActivity(), "添加足环1");
                mFootAdminModel.getTXGP_FootRing_AddData();
                break;
            case R.id.btn2:
                //

                break;
            case R.id.btn3:
                //修改足环
                Log.d(TAG, "onViewClicked: c");
                mFootAdminModel.getTXGP_FootRing_EditData();
                break;
            case R.id.btn4:
                //删除足环
                Log.d(TAG, "onViewClicked: d");
                mFootAdminModel.getTXGP_FootRing_DeleteData();
                break;
            case R.id.btn5:
                //

                break;
            case R.id.btn6:
                //获取单个足环详细
                Log.d(TAG, "onViewClicked: f");
                mFootAdminModel.getTXGP_FootRing_SelectData();
                break;
            case R.id.btn7:
                //

                break;

            case R.id.btn8:
                //添加足环号段
                mFootAdminModel.getTXGP_FootRing_AddSectionData();
                break;

            case R.id.btn9:
                mFootAdminModel.getTXGP_FootRing_SelectKeyAllData();
                break;
        }
    }
}
