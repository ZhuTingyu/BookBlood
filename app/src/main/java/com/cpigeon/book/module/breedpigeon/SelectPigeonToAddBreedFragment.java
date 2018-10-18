package com.cpigeon.book.module.breedpigeon;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.utility.StringUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.InputPigeonFragment;
import com.cpigeon.book.module.home.sharehall.SelectPigeonToShareFragment;
import com.cpigeon.book.module.select.BaseSelectPigeonFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhu TingYu on 2018/10/18.
 */

public class SelectPigeonToAddBreedFragment extends BaseSelectPigeonFragment {

    public static final int CODE_ADD_PIGEON = 0x123;

    public String mSonFootId;
    public String mSonPigeonId;
    public String mSexType = StringUtil.emptyString();

    public static void start(Activity activity, @Nullable String pigeonId, String sonFootId, String sonPigeonId, int requestCode, String... sexIds) {
        List<String> sexList = Lists.newArrayList(sexIds);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(IntentBuilder.KEY_DATA, (ArrayList<String>) sexList);
        bundle.putString(IntentBuilder.KEY_TYPE, BaseSelectPigeonFragment.TYPE_SHARE_PIGEON_TO_SHARE);
        SearchFragmentParentActivity.start(activity, SelectPigeonToAddBreedFragment.class, requestCode, false, bundle);
    }

    @Override
    protected void setTypeParam() {
        List<String> sexIds = getIntent().getStringArrayListExtra(IntentBuilder.KEY_DATA);
        mViewModel.sexid = Lists.appendStringByList(sexIds);

        if (sexIds.contains(PigeonEntity.ID_MALE) && sexIds.contains(PigeonEntity.ID_MALE)) {
            return;
        }

        if (sexIds.contains(PigeonEntity.ID_MALE)) {
            mSexType = InputPigeonFragment.TYPE_SEX_MALE;
        } else if (sexIds.contains(PigeonEntity.ID_MALE)) {
            mSexType = InputPigeonFragment.TYPE_SEX_FEMALE;
        }
    }

    @Override
    protected void setAdapterClick(BaseQuickAdapter adapter, View view, int position) {
        PigeonEntity pigeonEntity = mAdapter.getItem(position);
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, pigeonEntity)
                .finishForResult(getBaseActivity());
    }

    @Override
    public void startSearchActivity() {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarRight(R.string.text_add, item -> {
            InputPigeonFragment.start(getBaseActivity()
                    , StringUtil.emptyString()
                    , mSonFootId
                    , mSonPigeonId
                    , mSexType
                    , CODE_ADD_PIGEON);
            return false;
        });
    }
}
