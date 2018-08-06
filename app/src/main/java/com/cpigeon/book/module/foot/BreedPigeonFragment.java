package com.cpigeon.book.module.foot;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.util.RxUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.foot.viewmodel.BreedPigeonViewModel;

/**
 * Created by Administrator on 2018/8/6.
 */

public class BreedPigeonFragment extends BaseBookFragment {

    private BreedPigeonViewModel mViewModel;


    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, BreedPigeonFragment.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_breed_pigeon, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new BreedPigeonViewModel();
        initViewModel(mViewModel);

        mViewModel.getTXGP_FootRingType_SelectData();


        RxUtils.delayed(1000,aLong -> {
            mViewModel.getTXGP_FootRing_AddData();
        });


        RxUtils.delayed(3000,aLong -> {
            mViewModel.getTXGP_FootRing_DeleteData();
        });

    }
}
