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
import com.cpigeon.book.module.breedpigeon.adpter.LinearLayoutListener;
import com.cpigeon.book.module.homingpigeon.OnDeleteListener;
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
    protected int getLayoutRes() {
        return R.layout.fragment_select_pgeion_to_make_book;
    }

    @Override
    protected void initData() {
        super.initData();

        setStartSearchActvity(SearchBreedPigeonToMakeBookActivity.class);//搜索页面
        mAdapter=new MyHomingPigeonAdapter(new OnDeleteListener() {
            @Override
            public void delete(String PigeonId) {
                setProgressVisible(true);
                mBreedPigeonListModel.id = PigeonId;
                mBreedPigeonListModel.deletePigeon();
            }
        }, new LinearLayoutListener() {
            @Override
            public void click(int position) {
                try {
                    PigeonEntity mPigeonEntity = mAdapter.getData().get(position);
                    PreviewsBookActivity.start(getBaseActivity(), mPigeonEntity);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        initHead();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bloodUserViewModel = new BloodUserViewModel();
        initViewModels(bloodUserViewModel);
    }

    @Override
    protected void initParameter() {
        mBreedPigeonListModel.stateid = PigeonEntity.ID_ALL_MY_PGIEON;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.text_blood_book_made);
        bloodUserViewModel.getBloodNum();
        setNotMyPigeon();
    }

    private void setNotMyPigeon(){
        setToolbarRight("非本棚鸽", item -> {
            mBreedPigeonListModel.stateid = PigeonEntity.ID_NOT_MY_PIGEON;
            initData(true);
            setMyPigeon();
            return false;
        });
    }

    private void setMyPigeon(){
        setToolbarRight("我的信鸽", item -> {
            mBreedPigeonListModel.stateid = PigeonEntity.ID_ALL_MY_PGIEON;
            initData(true);
            setNotMyPigeon();
            return false;
        });
    }



    @Override
    protected void initObserve() {
        super.initObserve();

        bloodUserViewModel.count.observe(this, s -> {
            tv.setText(s.getCount());
        });
    }

    private void initHead() {
        mHeadView = findViewById(R.id.llHead);
        tv = mHeadView.findViewById(R.id.tvUserCount);
    }
}