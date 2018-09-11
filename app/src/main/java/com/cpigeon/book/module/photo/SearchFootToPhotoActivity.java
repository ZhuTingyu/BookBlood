package com.cpigeon.book.module.photo;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.photo.adpter.SelectFootToPhotoAdapter;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/11.
 */

public class SearchFootToPhotoActivity extends BaseSearchActivity {

    SelectFootToPhotoAdapter mAdapter;

    @Override
    protected List<DbEntity> getHistory() {
        return AppDatabase.getInstance(getBaseActivity())
                .DbEntityDao().getDataByUserAndType(UserModel.getInstance().getUserId()
                        , AppDatabase.TYPE_SEARCH_FOOT_TO_PHOTO_HISTORY);
    }

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new SelectFootToPhotoAdapter();
        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchTextView.setOnSearchTextClickListener(new SearchTextView.OnSearchTextClickListener() {
            @Override
            public void search(String key) {
                saveHistory(key,AppDatabase.TYPE_SEARCH_FOOT_TO_PHOTO_HISTORY);
                mAdapter.setNewData(Lists.newTestArrayList());
            }

            @Override
            public void cancel() {
                finish();
            }
        });
    }
}
