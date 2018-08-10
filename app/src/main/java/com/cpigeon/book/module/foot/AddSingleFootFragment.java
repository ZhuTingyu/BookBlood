package com.cpigeon.book.module.foot;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.foot.viewmodel.FootAdminViewModel;

/**
 * 添加 修改单个足环  fragment
 * Created by Administrator on 2018/8/10.
 */

public class AddSingleFootFragment extends BaseBookFragment {


    private FootAdminViewModel mFootAdminModel;


    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, AddSingleFootFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_single_foot, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFootAdminModel = new FootAdminViewModel();
        initViewModels(mFootAdminModel);



        mFootAdminModel.getTXGP_FootRing_AddData();
    }
}
