package com.cpigeon.book.module.makebloodbook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.breedpigeon.SearchBreedPigeonActivity;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;


import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/10.
 */

public class SearchBreedPigeonToMakeBookActivity extends SearchBreedPigeonActivity {

    BreedPigeonListAdapter mAdapter;

    @Override
    protected List<DbEntity> getHistory() {
        return AppDatabase.getInstance(getBaseContext()).DbEntityDao()
                .getDataByUserAndType(UserModel.getInstance().getUserId(), AppDatabase.TYPE_SEARCH_BREED_PIGEON);
    }

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new BreedPigeonListAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PreviewsBookFragment.start(getBaseActivity(),"");
        });
        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
