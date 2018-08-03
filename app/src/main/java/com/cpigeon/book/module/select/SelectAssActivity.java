package com.cpigeon.book.module.select;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.base.base.BaseActivity;
import com.base.base.pinyin.LetterSortModel;
import com.base.util.FragmentUtils;
import com.base.util.Lists;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.AssEntity;
import com.cpigeon.book.module.select.adpter.SelectAssAdapter;
import com.gjiazhe.wavesidebar.WaveSideBar;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/3.
 */

public class SelectAssActivity extends BaseActivity {

    XRecyclerView mRecyclerView;
    SelectAssAdapter mAdapter;
    WaveSideBar mWaveSideBar;
    LetterSortModel<AssEntity> mModel = new LetterSortModel<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ass);

        mRecyclerView = findViewById(R.id.list);
        mWaveSideBar = findViewById(R.id.side_bar);


        List<AssEntity> assEntityLists = Lists.newArrayList();

        assEntityLists.add(new AssEntity("朱"));
        assEntityLists.add(new AssEntity("朱"));
        assEntityLists.add(new AssEntity("江"));
        assEntityLists.add(new AssEntity("王"));
        assEntityLists.add(new AssEntity("许"));
        assEntityLists.add(new AssEntity("胡"));
        assEntityLists.add(new AssEntity("陈"));

        mModel.setData(assEntityLists);

        mWaveSideBar.setIndexItems(mModel.getLetters().toArray(new String[mModel.getLetters().size()]));
        mWaveSideBar.setOnSelectIndexItemListener(index -> {
            for (int i = 0; i < mModel.getData().size(); i++) {
                if (index.equals(mModel.getData().get(i).getLetter())) {
                    ((LinearLayoutManager) mRecyclerView.getRecyclerView().getLayoutManager()).scrollToPositionWithOffset(i, 0);
                    return;
                }
            }
        });

        mAdapter  = new SelectAssAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView.getRecyclerView());
        mAdapter.initHead(getBaseActivity());
        mAdapter.setNewData(mModel.getData());
    }
}
