package com.cpigeon.book.module.menu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.menu.viewmodel.FeedbackDetailViewModel;

import butterknife.BindView;

/**
 * 提交意见反馈
 * Created by Administrator on 2018/8/9.
 */

public class FeedbackDetailsFragment extends BaseBookFragment {

    private FeedbackDetailViewModel mViewModel;
    private XRecyclerView recyclerView;

    public static void start(Activity activity, String id) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, id)
                .startParentActivity(activity, FeedbackDetailsFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mViewModel = new FeedbackDetailViewModel(getBaseActivity());
        initViewModel(mViewModel);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_no_padding_layout, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("意见反馈详情");
        recyclerView = findViewById(R.id.list);


        mViewModel.getFeedbackDetail();

    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mViewModel.mFeedbackDetaisLiveData.observe(getActivity(), feedbackDetails -> {
        });
    }
}
