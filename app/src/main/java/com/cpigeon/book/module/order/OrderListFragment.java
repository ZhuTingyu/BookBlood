package com.cpigeon.book.module.order;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;

/**
 * Created by Administrator on 2018/8/19.
 */

public class OrderListFragment extends BaseBookFragment {

    public static final String TYPE_ALL = "TYPE_ALL";
    public static final String TYPE_UNPAID = "TYPE_UNPAID";
    public static final String TYPE_FINISH = "TYPE_FINISH";
    public static final String TYPE_OUT_OF_DATE = "TYPE_OUT_OF_DATE";

    XRecyclerView mRecyclerView;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, OrderListFragment.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xrecyclerview_no_padding_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = findViewById(R.id.list);
    }
}
