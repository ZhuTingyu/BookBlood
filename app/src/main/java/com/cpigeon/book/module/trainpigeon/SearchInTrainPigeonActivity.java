package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.db.AppDatabase;
import com.base.util.db.DbEntity;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.TrainEntity;
import com.cpigeon.book.module.trainpigeon.adpter.SearchFootRingAdapter;
import com.cpigeon.book.module.trainpigeon.viewmodel.SearchFootRingViewModel;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */
public class SearchInTrainPigeonActivity extends BaseSearchActivity {

    SearchFootRingAdapter mAdapter;

    SearchFootRingViewModel mViewModel;

    public static void start(Activity activity, TrainEntity trainEntity, int code) {
        IntentBuilder.Builder(activity, SearchInTrainPigeonActivity.class)
                .putExtra(IntentBuilder.KEY_DATA, trainEntity)
                .startActivity(code);
    }

    @Override
    protected List<DbEntity> getHistory() {
        return AppDatabase.getInstance(getBaseActivity())
                .DbEntityDao().getDataByUserAndType(UserModel.getInstance().getUserId(), AppDatabase.TYPE_SEARCH_IN_TRAIN_PIGEON);
    }

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new SearchFootRingAdapter();
        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new SearchFootRingViewModel(getBaseActivity());
        initViewModel(mViewModel);
        setSearchHint(R.string.text_input_foot_number_search);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, mAdapter.getItem(position))
                    .finishForResult(getBaseActivity());
        });

        mSearchTextView.setOnSearchTextClickListener(new SearchTextView.OnSearchTextClickListener() {
            @Override
            public void search(String key) {

            }

            @Override
            public void cancel() {
                finish();
            }
        });
        setProgressVisible(true);
        mViewModel.getFootRingToFlyBack();

        mViewModel.listEmptyMessage.observe(this, s -> {
            mAdapter.setEmptyText(s);
        });

        mViewModel.mDataPigeon.observe(this, pigeonEntities -> {
            setProgressVisible(false);
            mAdapter.setNewData(pigeonEntities);
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
    }
}
