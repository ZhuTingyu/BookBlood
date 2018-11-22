package com.cpigeon.book.module.findblood;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.module.basepigeon.BaseSearchPigeonActivity;
import com.cpigeon.book.module.homingpigeon.adapter.MyHomingPigeonAdapter;
import com.cpigeon.book.module.pigeonleague.PigeonToLeagueFootListFragment;
import com.cpigeon.book.module.select.BaseSelectPigeonFragment;
import com.cpigeon.book.module.select.SearchPigeonActivity;

import retrofit2.http.PUT;

/**
 * Created by Zhu TingYu on 2018/11/22.
 */

public class SelectPigeonToFindBloodFragment extends BaseSelectPigeonFragment {

    public static void start(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, BaseSelectPigeonFragment.TYPE_SELECT_PIGEON_TO_FIND_BLOOD);
        SearchFragmentParentActivity.start(activity, SelectPigeonToFindBloodFragment.class, true, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.text_blood_find);
    }

    @Override
    protected void setAdapterClick(MyHomingPigeonAdapter adapter, View view, int position) {
        FindPigeonBloodFragment.start(getBaseActivity(), adapter.getItem(position));
    }

    @Override
    protected void setTypeParam() {

    }

    @Override
    public void startSearchActivity() {
        SearchPigeonActivity.start(getBaseActivity(), mType, null);
    }
}
