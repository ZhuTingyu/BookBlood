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
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.menu.viewmodel.FeedbackViewModel;

import butterknife.BindView;

/**
 * 提交意见反馈
 * Created by Administrator on 2018/8/9.
 */

public class FeedbackDetailsFragment extends BaseBookFragment {

    private FeedbackViewModel mViewModel;

    @BindView(R.id.tv_content)
    TextView tv_content;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, FeedbackDetailsFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mViewModel = new FeedbackViewModel();
        initViewModel(mViewModel);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback_submit, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("意见反馈详情");

        String id = getBaseActivity().getIntent().getStringExtra(IntentBuilder.KEY_DATA);

        mViewModel.getZGW_Users_Feedback_DetailData(id);

    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mViewModel.feedbackDetailsData.observe(getActivity(), feedbackDetails -> {
            tv_content.setText(feedbackDetails.toJsonString());
        });
    }
}
