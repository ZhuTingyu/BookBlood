package com.cpigeon.book.module.makebloodbook;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;

/**
 * 血统书制作  足环列表
 * Created by Zhu TingYu on 2018/9/10.
 */

public class SelectPigeonToMakeBookFragment extends BaseFootListFragment {

    public static void start(Activity activity) {
        SearchFragmentParentActivity.
                start(activity, SelectPigeonToMakeBookFragment.class, false, null);
    }

    @Override
    protected void initData() {
        super.initData();

        mTvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);

        mActivity.setSearchClickListener(v -> {
            BaseSearchActivity.start(getBaseActivity(), SearchBreedPigeonToMakeBookActivity.class, null);
        });

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {

            try {
                PigeonEntity mPigeonEntity = mAdapter.getData().get(position);
                PreviewsBookFragment.start(getBaseActivity(), mPigeonEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mAdapter.addHeaderView(initHead());
    }


    private View initHead() {
        View mHeadView = LayoutInflater.from(getBaseActivity()).inflate(R.layout.fragment_select_pigeon_to_make_book, null);
        return mHeadView;
    }

}