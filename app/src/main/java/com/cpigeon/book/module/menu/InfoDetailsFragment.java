package com.cpigeon.book.module.menu;

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

/**
 * 我的信息
 * Created by Administrator on 2018/8/8.
 */

public class InfoDetailsFragment extends BaseBookFragment {

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, InfoDetailsFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("我的");
        setToolbarRight("签到", item -> {
            //签到
            SignFragment.start(getActivity());
            return true;
        });
    }


}
