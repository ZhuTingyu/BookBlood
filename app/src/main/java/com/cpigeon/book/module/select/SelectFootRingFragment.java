package com.cpigeon.book.module.select;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.FootEntity;
import com.cpigeon.book.module.select.adpter.SelectFootRingAdapter;
import com.cpigeon.book.module.select.viewmodel.SelectFootRingViewModel;
import com.cpigeon.book.util.RecyclerViewUtils;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/10/13.
 */

public class SelectFootRingFragment extends BaseBookFragment {

    public static final int CODE_SELECT_FOOT = 0x1234;
    public static final int CODE_SELECT_MATHER_FOOT = 0x2345;
    public static final int CODE_SELECT_FATHER_FOOT = 0x3456;
    XRecyclerView mRecyclerView;
    SelectFootRingAdapter mAdapter;
    SelectFootRingViewModel mViewModel;
    SearchFragmentParentActivity mActivity;
    boolean mIsCanSetDeath = false;

    public static void start(Activity activity) {
        SearchFragmentParentActivity.start(activity, SelectFootRingFragment.class, CODE_SELECT_FOOT, false, null);
    }

    public static void start(Activity activity, boolean isCanSetDeath) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IntentBuilder.KEY_BOOLEAN, isCanSetDeath);
        SearchFragmentParentActivity.start(activity, SelectFootRingFragment.class, CODE_SELECT_FOOT, false, bundle);
    }

    public static void start(Activity activity, boolean isCanSetDeath, int requestCode, String... sexId) {
        List<String> sexIds = Lists.newArrayList(sexId);
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_DATA, Lists.appendStringByList(sexIds));
        bundle.putBoolean(IntentBuilder.KEY_BOOLEAN, isCanSetDeath);
        SearchFragmentParentActivity.start(activity, SelectFootRingFragment.class, requestCode, false, bundle);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new SelectFootRingViewModel();
        initViewModel(mViewModel);
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

        String sex = getIntent().getStringExtra(IntentBuilder.KEY_DATA);
        mViewModel.sexId = sex;
        mIsCanSetDeath = getIntent().getBooleanExtra(IntentBuilder.KEY_BOOLEAN, false);

        mActivity.setSearchHint(R.string.text_input_foot_number_search);
        mActivity.setSearchClickListener(v -> {
            SearchFootRingActivity.start(getBaseActivity(), mIsCanSetDeath);
        });
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.addItemDecorationLine();
        mAdapter = new SelectFootRingAdapter(mIsCanSetDeath);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getFootList();
        }, mRecyclerView.getRecyclerView());

        setProgressVisible(true);
        mViewModel.getFootList();
    }

    @Override
    protected void initObserve() {

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

        mViewModel.mDataFootList.observe(this, footEntities -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, footEntities);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            FootEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, entity)
                    .finishForResult(getBaseActivity());
        }
    }
}
