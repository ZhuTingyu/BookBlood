package com.cpigeon.book.module.play;

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
import com.base.util.Lists;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.PlayInportListEntity;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.play.adapter.PlayInportAdapter;
import com.cpigeon.book.module.play.viewmodel.PlayViewModel;

import butterknife.BindView;

/**
 * 比赛导入
 * Created by Administrator on 2018/9/3.
 */

public class PlayInportFragment extends BaseBookFragment {

    private SelectTypeViewModel mSelectTypeViewModel;
    private PlayViewModel mPlayViewModel;

    @BindView(R.id.list)
    XRecyclerView mRecyclerView;
    @BindView(R.id.tvOk)
    TextView tvOk;

    private boolean isChooseAll = false;

    private PlayInportAdapter mAdapter;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, PlayInportFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        mViewModel = new FootAddMultiViewModel(getBaseActivity());
        mSelectTypeViewModel = new SelectTypeViewModel();
        mPlayViewModel = new PlayViewModel();
        initViewModels(mSelectTypeViewModel, mPlayViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xreclyview_with_bottom_btn, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("选择赛绩");

        setToolbarRight("全选", item -> {

            if (isChooseAll) {
                setToolbarRight("全选");
                isChooseAll = false;
                mAdapter.isChooseAll(isChooseAll);
            } else {
                setToolbarRight("取消全选");
                isChooseAll = true;
                mAdapter.isChooseAll(isChooseAll);
            }

            return true;
        });

        tvOk.setText("确定导入");

        mAdapter = new PlayInportAdapter();

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setNewData(Lists.newArrayList(new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity(),
                new PlayInportListEntity()));

        mAdapter.setChooseVisible(true);

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            mAdapter.setMultiSelectItem(position);
        });

    }

}
