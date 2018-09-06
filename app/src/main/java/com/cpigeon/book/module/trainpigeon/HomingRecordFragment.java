package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.base.adpter.BaseExpandAdapter;
import com.base.entity.ExpendEntity;
import com.base.entity.RaceEntity;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.HomingRecordEntity;
import com.cpigeon.book.model.entity.HomingRecordExpandEntity;
import com.cpigeon.book.module.trainpigeon.adpter.HomingRecordAdapter;
import com.cpigeon.book.test.TestExpand2Entity;
import com.cpigeon.book.test.TestExpandEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/6.
 * //归巢记录
 */

public class HomingRecordFragment extends BaseBookFragment {

    boolean isEnd = false;

    XRecyclerView mRecyclerView;
    HomingRecordAdapter mAdapter;
    TextView mTvOk;

    public static void start(Activity activity, boolean isEnd) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_BOOLEAN, isEnd)
                .startParentActivity(activity, HomingRecordFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xreclyview_with_bottom_btn, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("项目名称");

        isEnd = getBaseActivity().getIntent().getBooleanExtra(IntentBuilder.KEY_BOOLEAN, false);
        mRecyclerView = findViewById(R.id.list);
        mTvOk = findViewById(R.id.tvOk);
        mAdapter = new HomingRecordAdapter();
        mRecyclerView.setAdapter(mAdapter);

        if(isEnd){
            mTvOk.setVisibility(View.GONE);
        }else {
            setToolbarRight(R.string.text_add, item -> {
                AddHomingRecordFragment.start(getBaseActivity());
                return false;
            });
            mTvOk.setOnClickListener(v -> {

            });
        }

        List<ExpendEntity> data = Lists.newArrayList();
        for (int i = 0; i < 4; i++) {
            HomingRecordEntity homingRecordEntity = new HomingRecordEntity();
            homingRecordEntity.order = i;
            HomingRecordExpandEntity entity = new HomingRecordExpandEntity();
            homingRecordEntity.mHomingRecordExpandEntity = Lists.newArrayList(entity);
            data.add(homingRecordEntity);
        }

        mAdapter.setNewData(mAdapter.get(data));

    }
}
