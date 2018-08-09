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
import com.cpigeon.book.module.foot.viewmodel.BreedPigeonViewModel;
import com.cpigeon.book.module.foot.viewmodel.PigeonPublicViewModel;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * hl 种鸽
 * Created by Administrator on 2018/8/6.
 */

public class BreedPigeonFragment extends BaseBookFragment {

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
    @BindView(R.id.et9)
    EditText et9;

    private BreedPigeonViewModel mBreedPigeonViewModel;
    private PigeonPublicViewModel mPigeonPublicViewModel;
    private String TAG = "bufffm";


    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, BreedPigeonFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breed_pigeon, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPigeonPublicViewModel = new PigeonPublicViewModel();
        initViewModels(mPigeonPublicViewModel);

        mBreedPigeonViewModel = new BreedPigeonViewModel();
        initViewModels(mBreedPigeonViewModel);

        bindUi(RxUtils.textChanges(et9), mBreedPigeonViewModel.setDetailsFootId());
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                //获取鸽子性别
                mPigeonPublicViewModel.getTXGP_PigeonSexType_SelectData();
                break;
            case R.id.btn2:
                //鸽子血统选择
                mPigeonPublicViewModel.getTXGP_PigeonBloodType_SelectData();
                break;
            case R.id.btn3:
                //选择  眼砂
                mPigeonPublicViewModel.getTXGP_PigeonEyeType_SelectData();
                break;
            case R.id.btn4:
                //选择  羽色
                mPigeonPublicViewModel.getTXGP_PigeonPlumeType_SelectData();

                break;
            case R.id.btn5:
                //获取足环来源
                mPigeonPublicViewModel.getTXGP_FootRingSource_SelectData();

                break;
            case R.id.btn6:
                //足环类型选择
                mPigeonPublicViewModel.getTXGP_FootRingType_SelectData();

                break;
            case R.id.btn7:
                //种鸽来源
                mPigeonPublicViewModel.getTXGP_PigeonSource_SelectData();
                break;

            case R.id.btn8:
                //

                break;

            case R.id.btn9:
                //获取种鸽详细
                mBreedPigeonViewModel.getTXGP_Pigeon_GetInfoData();

                break;
        }
    }

}
