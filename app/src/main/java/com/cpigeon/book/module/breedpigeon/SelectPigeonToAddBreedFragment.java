package com.cpigeon.book.module.breedpigeon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.utility.StringUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.event.PigeonAddEvent;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.basepigeon.InputPigeonFragment;
import com.cpigeon.book.module.breedpigeon.viewmodel.BookViewModel;
import com.cpigeon.book.module.select.BaseSelectPigeonFragment;
import com.cpigeon.book.module.select.SearchPigeonActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhu TingYu on 2018/10/18.
 */

public class SelectPigeonToAddBreedFragment extends BaseSelectPigeonFragment {

    public static final int CODE_ADD_PIGEON = 0x123;

    public static final String KEY_SON_FOOT_ID = "KEY_SON_FOOT_ID";
    public static final String KEY_SON_PIGEON_ID = "KEY_SON_PIGEON_ID";
    public static final String KEY_SEX = "KEY_SEX";

    public String mSonFootId;
    public String mSonPigeonId;
    public String mSexType = StringUtil.emptyString();
    protected BookViewModel mBookViewModel;
    private PigeonEntity mPigeonEntity;

    public static void start(Activity activity, String sonFootId, String sonPigeonId, int requestCode, String... sexIds) {
        List<String> sexList = Lists.newArrayList(sexIds);
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, BaseSelectPigeonFragment.TYPE_SELECT_PIGEON_TO_ADD_BREED_PIGEON);
        bundle.putString(KEY_SON_FOOT_ID, sonFootId);
        bundle.putString(KEY_SON_PIGEON_ID, sonPigeonId);
        bundle.putStringArrayList(KEY_SEX, (ArrayList<String>) sexList);
        SearchFragmentParentActivity.start(activity, SelectPigeonToAddBreedFragment.class, requestCode, false, bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBookViewModel = new BookViewModel();
        initViewModel(mBookViewModel);
        mSonFootId = getIntent().getStringExtra(KEY_SON_FOOT_ID);
        mSonPigeonId = getIntent().getStringExtra(KEY_SON_PIGEON_ID);
        mBookViewModel.sonFootId = mSonFootId;
        mBookViewModel.sonPigeonId = mSonPigeonId;
    }

    @Override
    protected void setTypeParam() {
        List<String> sexIds = getIntent().getStringArrayListExtra(KEY_SEX);
        mViewModel.sexid = Lists.appendStringByList(sexIds);
        mViewModel.pigeonidStr = mSonPigeonId;

        if (sexIds.contains(PigeonEntity.ID_MALE) && sexIds.contains(PigeonEntity.ID_FEMALE)) {
            return;
        }

        if (sexIds.contains(PigeonEntity.ID_MALE)) {
            mSexType = InputPigeonFragment.TYPE_SEX_MALE;
            mViewModel.sexid = PigeonEntity.ID_MALE;
        } else if (sexIds.contains(PigeonEntity.ID_FEMALE)) {
            mSexType = InputPigeonFragment.TYPE_SEX_FEMALE;
            mViewModel.sexid = PigeonEntity.ID_FEMALE;
        }
    }

    @Override
    protected void setAdapterClick(BaseQuickAdapter adapter, View view, int position) {
        mPigeonEntity = mAdapter.getItem(position);
        if (StringUtil.isStringValid(mSonPigeonId)) {
            setProgressVisible(true);
            mBookViewModel.pigeonId = mPigeonEntity.getPigeonID();
            mBookViewModel.foodId = mPigeonEntity.getFootRingID();
            mBookViewModel.sexId = mPigeonEntity.getPigeonSexID();
            mBookViewModel.addParent();
        } else {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, mPigeonEntity)
                    .finishForResult(getBaseActivity());
        }
    }

    @Override
    public void startSearchActivity() {
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_DATA, mViewModel.sexid);
        SearchPigeonActivity.start(getBaseActivity(), mType, bundle);
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
                    , PigeonEntity.ID_BREED_PIGEON
                    , CODE_ADD_PIGEON);

          return false;
       });
    }

    @Override
    protected void initObserve() {
        super.initObserve();
        mBookViewModel.mDataAddParentR.observe(this, s -> {
            setProgressVisible(false);
            EventBus.getDefault().post(new PigeonAddEvent());
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, mPigeonEntity)
                    .finishForResult(getBaseActivity());
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK != resultCode) return;
        mPigeonEntity = (PigeonEntity) data.getSerializableExtra(IntentBuilder.KEY_DATA);
        if (requestCode == BaseSelectPigeonFragment.CODE_SEARCH) {
            if (StringUtil.isStringValid(mSonPigeonId)) {
                setProgressVisible(true);
                mBookViewModel.pigeonId = mPigeonEntity.getPigeonID();
                mBookViewModel.foodId = mPigeonEntity.getFootRingID();
                mBookViewModel.sexId = mPigeonEntity.getPigeonSexID();
                mBookViewModel.addParent();
            }else {
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_DATA, mPigeonEntity)
                        .finishForResult(getBaseActivity());
            }
        } else if (requestCode == CODE_ADD_PIGEON) {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, mPigeonEntity)
                    .finishForResult(getBaseActivity());
        }


    }


}
