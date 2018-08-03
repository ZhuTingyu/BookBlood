package com.cpigeon.book.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.BaseFragment;
import com.base.entity.ExpendEntity;
import com.base.entity.RaceEntity;
import com.base.util.Lists;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.adpter.ExpandAdapter;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/3/5.
 */

public class ExpandFragment extends BaseFragment {

    XRecyclerView recyclerView;
    ExpandAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = findViewById(R.id.list);

        List<ExpendEntity> data = Lists.newArrayList();
        for(int i = 0; i < 4; i++){
            TestExpandEntity entity = new TestExpandEntity();
            entity.a = "父" + i;
            List<RaceEntity> race = Lists.newArrayList();
            for (int j = 0; j < 4; j++) {
                TestExpand2Entity expand2Entity = new TestExpand2Entity();
                expand2Entity.name  = String.valueOf(j);
                race.add(expand2Entity);
            }
            entity.race = race;
            data.add(entity);
        }


        adapter = new ExpandAdapter();

        recyclerView.setAdapter(adapter);

        adapter.setNewData(adapter.get(data));

    }
}
