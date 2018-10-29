package com.cpigeon.book.module.makebloodbook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cpigeon.book.R;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.homingpigeon.adapter.MyHomingPigeonAdapter;

/**
 * 血统书制作  足环列表
 * Created by Zhu TingYu on 2018/9/10.
 */

public class SelectPigeonToMakeBookFragment extends BaseFootListFragment {
    BloodUserViewModel bloodUserViewModel;
    private View mHeadView;
    private TextView tv;

    public static void start(Activity activity) {
        SearchFragmentParentActivity.
                start(activity, SelectPigeonToMakeBookFragment.class, false, null);
    }

    @Override
    protected void initData() {
        super.initData();

        setStartSearchActvity(SearchBreedPigeonToMakeBookActivity.class);//搜索页面
        mAdapter=new MyHomingPigeonAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {

            try {
                PigeonEntity mPigeonEntity = mAdapter.getData().get(position);
                PreviewsBookActivity.start(getBaseActivity(), mPigeonEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mAdapter.addHeaderView(initHead());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        bloodUserViewModel = new BloodUserViewModel();
        initViewModels(bloodUserViewModel);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bloodUserViewModel.getBloodNum();

    }



    @Override
    protected void initObserve() {
        super.initObserve();

        bloodUserViewModel.count.observe(this, s -> {
            tv.setText(s.getCount());

        });
    }

    private View initHead() {
        View mHeadView = LayoutInflater.from(getBaseActivity()).inflate(R.layout.fragment_select_pigeon_to_make_book, null);
        tv = mHeadView.findViewById(R.id.tvUserCount);
        return mHeadView;
    }

    @Override
    public void onResume() {
        super.onResume();
        bloodUserViewModel.getBloodNum();

    }
}