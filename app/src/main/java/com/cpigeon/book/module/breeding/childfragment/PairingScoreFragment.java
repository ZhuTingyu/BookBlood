package com.cpigeon.book.module.breeding.childfragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.cpigeon.book.module.basepigeon.BaseListFragment;
import com.cpigeon.book.module.breeding.PairingNestInfoListFragment;
import com.cpigeon.book.module.breeding.adapter.PairingScoreAdapter;

/**
 * 推荐配对--- 》按评分
 * Created by Administrator on 2018/9/11.
 */

public class PairingScoreFragment extends BaseListFragment {

    private PairingScoreAdapter mAdapter;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, PairingNestInfoListFragment.class);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);

        mAdapter = new PairingScoreAdapter();
        list.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            getBaseActivity().finish();
        });


    }
}
