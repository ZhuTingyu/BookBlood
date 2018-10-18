package com.cpigeon.book.module.home.sharehall;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.event.ShareHallEvent;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonListModel;
import com.cpigeon.book.module.select.BaseSelectPigeonFragment;
import com.cpigeon.book.module.select.SearchPigeonActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Zhu TingYu on 2018/10/18.
 */

public class SelectPigeonToShareFragment extends BaseSelectPigeonFragment {
    //共享厅选择
    public static void start(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, BaseSelectPigeonFragment.TYPE_SHARE_PIGEON_TO_SHARE);
        SearchFragmentParentActivity.start(activity, SelectPigeonToShareFragment.class, false, bundle);
    }

    @Override
    protected void setTypeParam() {
        mViewModel.bitshare = BreedPigeonListModel.CODE_IN_NOT_SHARE_HALL;
    }

    @Override
    public void startSearchActivity() {
        SearchPigeonActivity.start(getBaseActivity(), mType, null);
    }

    @Override
    protected void setAdapterClick(BaseQuickAdapter adapter, View view, int position) {
        PigeonEntity pigeonEntity = mAdapter.getItem(position);
        BreedPigeonDetailsFragment.start(getBaseActivity(), pigeonEntity.getPigeonID()
                , pigeonEntity.getFootRingID(), BreedPigeonDetailsFragment.TYPE_SHARE_PIGEON, pigeonEntity.getUserID());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(ShareHallEvent event) {
        mAdapter.cleanList();
        mViewModel.pi = 1;
        mViewModel.getPigeonList();
    }

}
