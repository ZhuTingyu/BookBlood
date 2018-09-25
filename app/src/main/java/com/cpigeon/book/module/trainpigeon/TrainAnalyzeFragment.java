package com.cpigeon.book.module.trainpigeon;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Utils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.trainpigeon.adpter.TrainAnalyzeAdapter;
import com.cpigeon.book.module.trainpigeon.viewmodel.TrainAnalyzeViewModel;

/**
 * Created by Zhu TingYu on 2018/9/7.
 */

public class TrainAnalyzeFragment extends BaseBookFragment{

    XRecyclerView mRecyclerView;
    TrainAnalyzeAdapter mAdapter;
    boolean isScoreOrder = true;
    TrainAnalyzeViewModel mViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new TrainAnalyzeViewModel(getBaseActivity());
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle(Utils.getString(R.string.text_title_train_analyze
                ,mViewModel.mTrainEntity.getPigeonTrainName()));
        setScore();
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.addItemDecorationLine();
        mAdapter = new TrainAnalyzeAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setProgressVisible(true);
        mViewModel.getAnalyze();

    }

    @Override
    protected void initObserve() {
        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

        mViewModel.mDataTrainAnalyse.observe(this, trainAnalyseEntities -> {
            setProgressVisible(false);
            mAdapter.setNewData(trainAnalyseEntities);
        });
    }

    private void setScore(){
        toolbar.getMenu().clear();
        toolbar.getMenu().add("")
                .setActionView(R.layout.menu_train_analyze_order)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

         View view = toolbar.getMenu().getItem(0).getActionView();
         TextView textView = view.findViewById(R.id.tvTitle);
         textView.setText(R.string.text_order_by_score);

         textView.setOnClickListener(v -> {
            if(isScoreOrder){
                textView.setText(R.string.text_order_by_speed);
                isScoreOrder = false;
            }else {
                textView.setText(R.string.text_order_by_score);
                isScoreOrder = true;
            }
         });
    }
}
