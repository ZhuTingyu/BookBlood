package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.content.Context;
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
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.event.FlyBackAddRecordEvent;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.module.trainpigeon.adpter.SearchFootRingAdapter;
import com.cpigeon.book.module.trainpigeon.viewmodel.SearchFootRingViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Zhu TingYu on 2018/10/10.
 */

public class SelectPigeonToFlyBackFragment extends BaseBookFragment{

    XRecyclerView mRecyclerView;
    SearchFootRingAdapter mAdapter;
    SearchFootRingViewModel mViewModel;
    SearchFragmentParentActivity mActivity;


    public static void start(Activity activity, TrainEntity entity){
        Bundle bundle = new Bundle();
        bundle.putParcelable(IntentBuilder.KEY_DATA, entity);
        SearchFragmentParentActivity.start(activity, SelectPigeonToFlyBackFragment.class, bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new SearchFootRingViewModel(getBaseActivity());
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
        setTitle("选择你要归巢的信鸽");
        mActivity.setSearchHint(R.string.text_input_foot_number_search);
        mActivity.setSearchClickListener(v -> {
            SearchPigeonToFlyBackActivity.start(getBaseActivity(), mViewModel.mTrainEntity);
        });
        mRecyclerView = findViewById(R.id.list);
        mAdapter = new SearchFootRingAdapter(mViewModel.mTrainEntity);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecorationLine();
        setProgressVisible(true);
        mViewModel.getFootRingToFlyBack();
    }

    @Override
    protected void initObserve() {
        mViewModel.mDataPigeon.observe(this, pigeonEntities -> {
            setProgressVisible(false);
            mAdapter.setNewData(pigeonEntities);
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(FlyBackAddRecordEvent event){
        mViewModel.getFootRingToFlyBack();
    }
}
