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

import com.base.util.Lists;
import com.base.util.Utils;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.trainpigeon.adpter.TrainAnalyzeAdapter;

/**
 * Created by Zhu TingYu on 2018/9/7.
 */

public class TrainAnalyzeFragment extends BaseBookFragment{

    XRecyclerView mRecyclerView;
    TrainAnalyzeAdapter mAdapter;
    boolean isScoreOrder = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle(Utils.getString(R.string.text_title_train_analyze, "小朱"));
        setScore();
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.addItemDecorationLine();
        mAdapter = new TrainAnalyzeAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setNewData(Lists.newTestArrayList());
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
