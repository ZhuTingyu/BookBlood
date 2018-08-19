package com.cpigeon.book.module.foot;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.BaseFragment;
import com.base.base.pinyin.LetterSortModel;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.AssEntity;
import com.gjiazhe.wavesidebar.WaveSideBar;

/**
 * Created by Zhu TingYu on 2018/8/19.
 */

public class SelectCountyFragment extends BaseFragment {

    XRecyclerView mRecyclerView;
    WaveSideBar mWaveSideBar;
    //LetterSortModel<> mModel = new LetterSortModel<>();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_with_wave_bar, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = findViewById(R.id.list);
        mWaveSideBar = findViewById(R.id.side_bar);
    }
}
