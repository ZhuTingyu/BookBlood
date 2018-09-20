package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.base.util.IntentBuilder;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFagment;

/**
 * 选择子代信息  列表
 * Created by Administrator on 2018/9/19 0019.
 */

public class OffspringChooseFragment extends BaseFootListFagment {

    public static void start(Activity activity, int requestCode) {
        SearchFragmentParentActivity.
                start(activity, OffspringChooseFragment.class, requestCode, false, null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    protected void initData() {
        super.initData();
        setProgressVisible(false);
        mTvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);

        mActivity.setSearchClickListener(v -> {
            //搜索
            Bundle bundle = new Bundle();
            bundle.putString(IntentBuilder.KEY_TYPE, "");
            BaseSearchActivity.start(getBaseActivity(), OffspringSearchActivity.class, PairingNestAddFragment.requestCode, bundle);
        });

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PigeonEntity mBreedPigeonEntity = mAdapter.getData().get(position);
            Intent intent = new Intent();
            intent.putExtra(IntentBuilder.KEY_DATA, mBreedPigeonEntity);
            getBaseActivity().setResult(PairingNestAddFragment.requestCode, intent);
            getBaseActivity().finish();
        });

        setToolbarRight("添加", item -> {
            OffspringAddFragment.start(getBaseActivity(), PairingNestAddFragment.requestCode);
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