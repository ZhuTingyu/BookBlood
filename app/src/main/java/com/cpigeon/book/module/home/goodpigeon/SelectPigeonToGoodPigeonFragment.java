package com.cpigeon.book.module.home.goodpigeon;

import android.app.Activity;
import android.os.Bundle;

import com.base.util.IntentBuilder;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.module.select.BaseSelectPigeonFragment;
import com.cpigeon.book.module.select.BaseSearchPigeonActivity;

/**
 * Created by Zhu TingYu on 2018/10/18.
 */

public class SelectPigeonToGoodPigeonFragment extends BaseSelectPigeonFragment {
    public static void start(Activity activity, int requestCode){
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, BaseSelectPigeonFragment.TYPE_SELECT_PIGEON_TO_GOOD_PIGEON);
        SearchFragmentParentActivity.start(activity, SelectPigeonToGoodPigeonFragment.class ,requestCode, false, bundle);
    }

    @Override
    protected void setTypeParam() {
    }

    @Override
    public void startSearchActivity() {
        BaseSearchPigeonActivity.start(getBaseActivity(), mType, null);
    }
}
