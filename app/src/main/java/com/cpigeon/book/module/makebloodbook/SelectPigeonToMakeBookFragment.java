package com.cpigeon.book.module.makebloodbook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.breeding.PairingInfoListFragment;
import com.cpigeon.book.module.breeding.SearchBreedingFootActivity;
import com.cpigeon.book.module.breedpigeon.adpter.BreedPigeonListAdapter;

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
            //搜索
            Bundle bundle = new Bundle();
            bundle.putString(IntentBuilder.KEY_TYPE, "");
            BaseSearchActivity.start(getBaseActivity(), SearchBreedPigeonToMakeBookActivity.class, bundle);
        });

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {

            PigeonEntity mPigeonEntity = mAdapter.getData().get(position);
            PreviewsBookFragment.start(getBaseActivity(), mPigeonEntity.getFootRingNum());
        });

        mAdapter.addHeaderView(initHead());
    }


    private View initHead() {
        View mHeadView = LayoutInflater.from(getBaseActivity()).inflate(R.layout.fragment_select_pigeon_to_make_book, null);

        return mHeadView;
    }

}