package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.db.DbEntity;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.module.trainpigeon.adpter.SearchFootRingAdapter;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */
public class SearchFootRingActivity extends BaseSearchActivity {

    SearchFootRingAdapter mAdapter;

    public static void start(Activity activity, int code) {
        IntentBuilder.Builder(activity, SearchFootRingActivity.class)
                .startActivity(code);
    }

    @Override
    protected List<DbEntity> getHistory() {
        return null;
    }

    @Override
    protected BaseQuickAdapter getResultAdapter() {
        mAdapter = new SearchFootRingAdapter();
        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSearchHint(R.string.text_input_foot_number_search);

        mRlHistory.setVisibility(View.GONE);
        mAdapter.setNewData(Lists.newTestArrayList());
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, "2018-11-12345667")
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

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
    }
}
