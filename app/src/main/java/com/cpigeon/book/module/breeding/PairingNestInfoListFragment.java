package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.cpigeon.book.module.basepigeon.BaseListFragment;
import com.cpigeon.book.module.breeding.adapter.PairingNestInfoListAdapter;

/**
 * 窝次信息列表
 * Created by Administrator on 2018/9/10.
 */

public class PairingNestInfoListFragment extends BaseListFragment {

    private PairingNestInfoListAdapter mAdapter;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, PairingNestInfoListFragment.class);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);

        setTitle("窝次信息");
        setToolbarRight("添加窝次", item -> {

            PairingNestAddFragment.start(getBaseActivity());
            return true;
        });

        mAdapter = new PairingNestInfoListAdapter();
        list.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PairingNestAddFragment.start(getBaseActivity());
        });


    }
}
