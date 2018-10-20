package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.os.Bundle;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.module.select.BaseSelectPigeonFragment;

import java.util.List;

/**
 * 选择配对的鸽子
 * Created by Administrator on 2018/10/20 0020.
 */

public class SelectPigeonToPairingFragment extends BaseSelectPigeonFragment {



    public static void start(Activity activity, String sonFootId, String sonPigeonId, int requestCode, String... sexIds) {
        List<String> sexList = Lists.newArrayList(sexIds);
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, BaseSelectPigeonFragment.TYPE_SELECT_PIGEON_TO_ADD_BREED_PIGEON);

        SearchFragmentParentActivity.start(activity, SelectPigeonToPairingFragment.class, requestCode, false, bundle);
    }


    @Override
    protected void setTypeParam() {



    }

    @Override
    public void startSearchActivity() {

    }
}
