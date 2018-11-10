package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.base.util.IntentBuilder;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PairingInfoEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;

/**
 * 选择子代信息  列表
 * Created by Administrator on 2018/9/19 0019.
 */

public class OffspringChooseFragment extends BaseFootListFragment {

    private PairingInfoEntity mPairingInfoEntity;
    private String mNestTimeId;

    public static void start(Activity activity, String nestTimeId, PairingInfoEntity mPairingInfoEntity, int requestCode) {
        Bundle mBudle = new Bundle();
        mBudle.putSerializable(IntentBuilder.KEY_DATA, mPairingInfoEntity);
        mBudle.putString(IntentBuilder.KEY_DATA_2, nestTimeId);
        SearchFragmentParentActivity
                .start(activity, OffspringChooseFragment.class, requestCode, false, mBudle);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.addItemDecorationLine();
        mPairingInfoEntity = (PairingInfoEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);
        mNestTimeId = getIntent().getStringExtra(IntentBuilder.KEY_DATA_2);
    }

    @Override
    protected void initData() {
        super.initData();
        setProgressVisible(false);
        setStartSearchActvity(OffspringSearchActivity.class);//搜索页面

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            try {
                PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
                Intent intent = new Intent();
                intent.putExtra(IntentBuilder.KEY_DATA, mBreedPigeonEntity);
                getBaseActivity().setResult(PairingNestAddFragment.requestCode, intent);
                getBaseActivity().finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        setToolbarRight("添加", item -> {
            OffspringAddFragment2.start(getBaseActivity(), mNestTimeId, mPairingInfoEntity, PairingNestAddFragment.requestCode);
            return true;
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("hehheheheh", "onActivityResult: 2");
        try {
            if (requestCode == PairingNestAddFragment.requestCode) {
                getBaseActivity().setResult(PairingNestAddFragment.requestCode, data);
                getBaseActivity().finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}