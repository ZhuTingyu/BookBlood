package com.cpigeon.book.module.home.goodpigeon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.module.select.BaseSelectPigeonFragment;
import com.cpigeon.book.module.select.SearchPigeonActivity;

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("选择申请铭鸽库的信鸽");
    }

    @Override
    protected void setTypeParam() {
    }

    @Override
    public void startSearchActivity() {
        SearchPigeonActivity.start(getBaseActivity(), mType, null);
    }
}
