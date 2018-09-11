package com.cpigeon.book.module.photo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.base.base.BaseActivity;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.module.photo.adpter.SelectFootToPhotoAdapter;

/**
 * Created by Zhu TingYu on 2018/9/11.
 */

public class SelectFootToPhotoFragment extends BaseBookFragment {

    XRecyclerView mRecyclerView;
    SelectFootToPhotoAdapter mAdapter;
    SearchFragmentParentActivity mActivity;

    public static void start(Activity activity) {
        SearchFragmentParentActivity.start(activity, SelectFootToPhotoFragment.class, null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SearchFragmentParentActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.setSearchHint(R.string.text_input_foot_number_search);
        mActivity.setSearchClickListener(v -> {
            BaseSearchActivity.start(getBaseActivity(), SearchFootToPhotoActivity.class, null);
        });
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.addItemDecorationLine();
        mAdapter = new SelectFootToPhotoAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newTestArrayList());
        mAdapter.addHeaderView(initHead());
    }

    private View initHead() {
        View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.include_select_foot_to_photo_head, null);
        ProgressBar progressBar = view.findViewById(R.id.progressPlace);
        progressBar.setMax(100);
        progressBar.setProgress(50);
        return view;
    }
}
