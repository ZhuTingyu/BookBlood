package com.cpigeon.book.module.home.sharehall;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.Lists;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.module.home.sharehall.adpter.ShareHallHomeAdapter;
import com.cpigeon.book.module.select.SearchFootRingActivity;

/**
 * Created by Zhu TingYu on 2018/9/15.
 */

public class MySharePigeonFragment extends BaseBookFragment{

    XRecyclerView mRecyclerView;
    TextView mTvOk;
    ShareHallHomeAdapter mAdapter;
    SearchFragmentParentActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SearchFragmentParentActivity) getBaseActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xreclyview_with_bottom_btn_not_white, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.setSearchHint(R.string.text_input_foot_number_search);
        mRecyclerView = findViewById(R.id.list);
        mTvOk = findViewById(R.id.tvOk);
        mAdapter = new ShareHallHomeAdapter(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newTestArrayList());
        mTvOk.setText(R.string.text_add_share_pigeon);
        mTvOk.setOnClickListener(v -> {
            SearchFootRingActivity.start(getBaseActivity(), SearchFootRingActivity.CODE_SEARCH_FOOT_RING);
        });
    }
}
