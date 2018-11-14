package com.cpigeon.book.module.breeding.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.breeding.PairingInfoAddFragment;
import com.cpigeon.book.module.breeding.SearchPigeonToAddBreedingActivity;
import com.cpigeon.book.module.breedpigeon.adpter.LinearLayoutListener;
import com.cpigeon.book.module.homingpigeon.OnDeleteListener;
import com.cpigeon.book.module.homingpigeon.adapter.MyHomingPigeonAdapter;

/**
 * Created by Administrator on 2018/11/3 0003.
 */

public class AddBreedingFragment extends BaseFootListFragment {

    public static final int CODE_ADD_BREEDING = 0x123;

    public static void start(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString(BaseFootListFragment.STATEID, PigeonEntity.ID_ALL_MY_PGIEON);
        SearchFragmentParentActivity.
                start(activity, AddBreedingFragment.class, CODE_ADD_BREEDING, true, bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBreedPigeonListModel.bitTogether = PigeonEntity.STATUS_NOT_TOGETHER;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitle("选择配对信鸽");
        setStartSearchActvity(SearchPigeonToAddBreedingActivity.class);//搜索页面
        MyHomingPigeonAdapter myHomingPigeonAdapter = new MyHomingPigeonAdapter(new OnDeleteListener() {
            @Override
            public void delete(String PigeonId) {
                mBreedPigeonListModel.id = PigeonId;
                mBreedPigeonListModel.deletePigeon();
            }
        }, new LinearLayoutListener() {
            @Override
            public void click(int position) {
                try {
                    PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);

                    PairingInfoAddFragment.start(getBaseActivity(), mBreedPigeonEntity, null, CODE_ADD_BREEDING);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        myHomingPigeonAdapter.setInBreed(true);
        mAdapter = myHomingPigeonAdapter;
    }

    @Override
    protected void initObserve() {
        super.initObserve();
        mBreedPigeonListModel.mLivePigeonSexCount.observe(this, pigeonSexCountEntity -> {

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(Activity.RESULT_OK == resultCode){
            IntentBuilder.Builder().finishForResult(getBaseActivity());
        }
    }
}
