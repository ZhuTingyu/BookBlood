package com.cpigeon.book.module.select;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.util.utility.StringUtil;
import com.base.widget.recyclerview.XRecyclerView;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.event.ShareHallEvent;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.breedpigeon.BreedPigeonDetailsFragment;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonListModel;
import com.cpigeon.book.module.select.adpter.SelectPigeonAdapter;
import com.cpigeon.book.util.RecyclerViewUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Zhu TingYu on 2018/9/26.
 */

public class SelectPigeonFragment extends BaseBookFragment {

    public static String REQUEST_CODE = "REQUEST_CODE";
    public static String TYPE_SHARE_PIGEON_TO_SHARE = "TYPE_SHARE_PIGEON_TO_SHARE";
    public static String TYPE_SELECT_PIGEON_TO_ADD_FLY_BACK = "TYPE_SELECT_PIGEON_TO_ADD_FLY_BACK";
    public static int CODE_SEARCH = 0x321;
    public static int CODE_SELECT = 0x1234;

    XRecyclerView mRecyclerView;
    SelectPigeonAdapter mAdapter;
    BreedPigeonListModel mViewModel;
    SearchFragmentParentActivity mActivity;
    int mRequestCode;
    String mType;

    public static void start(Activity activity, int code) {
        Bundle bundle = new Bundle();
        bundle.putInt(REQUEST_CODE, code);
        SearchFragmentParentActivity.start(activity, SelectPigeonFragment.class, code, false, bundle);
    }

    public static void start(Activity activity, String type, int code) {
        Bundle bundle = new Bundle();
        bundle.putInt(REQUEST_CODE, code);
        bundle.putString(IntentBuilder.KEY_TYPE, type);
        SearchFragmentParentActivity.start(activity, SelectPigeonFragment.class, code, false, bundle);
    }

    public static void start(Activity activity, String type) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, type);
        SearchFragmentParentActivity.start(activity, SelectPigeonFragment.class, false, bundle);
  }

      @Override

  public void onAttach(Context context) {

      super.onAttach(context);

      mViewModel = new BreedPigeonListModel();

      initViewModel(mViewModel);

      mActivity = (SearchFragmentParentActivity) context;
        mViewModel.typeid = StringUtil.emptyString();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xrecyclerview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRequestCode = getBaseActivity().getIntent().getIntExtra(REQUEST_CODE, 0);
        mType = getBaseActivity().getIntent().getStringExtra(IntentBuilder.KEY_TYPE);
        mActivity.setSearchHint(R.string.text_input_foot_number_search);
        mActivity.setSearchClickListener(v -> {
            if (mRequestCode == 0) {
                SearchPigeonActivity.start(getBaseActivity(), mType);
            } else {
                SearchPigeonActivity.start(getBaseActivity(), mType, mRequestCode);
            }
        });
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.addItemDecorationLine();
        mAdapter = new SelectPigeonAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setRefreshListener(() -> {
            mAdapter.cleanList();
            mViewModel.pi = 1;
            mViewModel.getPigeonList();
        });

        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.pi++;
            mViewModel.getPigeonList();
        }, mRecyclerView.getRecyclerView());

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {

            try {
                PigeonEntity pigeonEntity = mAdapter.getItem(position);
                if (mRequestCode != 0) {
                    IntentBuilder.Builder()
                            .putExtra(IntentBuilder.KEY_DATA, pigeonEntity)
                            .finishForResult(getBaseActivity());
                } else {
                    if (TYPE_SHARE_PIGEON_TO_SHARE.equals(mType)) {
                        BreedPigeonDetailsFragment.start(getBaseActivity(), pigeonEntity.getPigeonID()
                                , pigeonEntity.getFootRingID(), BreedPigeonDetailsFragment.TYPE_SHARE_PIGEON, pigeonEntity.getUserID());
                    }if(TYPE_SELECT_PIGEON_TO_ADD_FLY_BACK.equals(mType)){

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        setProgressVisible(true);

        //搜索鸽子去共享
        if (TYPE_SHARE_PIGEON_TO_SHARE.equals(mType)) {
            mViewModel.bitshare = BreedPigeonListModel.CODE_IN_NOT_SHARE_HALL;
        }
        mViewModel.getPigeonList();
    }


    @Override
    protected void initObserve() {
        mViewModel.mPigeonListData.observe(this, pigeonEntities -> {
            setProgressVisible(false);
            RecyclerViewUtils.setLoadMoreCallBack(mRecyclerView, mAdapter, pigeonEntities);
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(ShareHallEvent event) {
        mAdapter.cleanList();
        mViewModel.pi = 1;
        mViewModel.getPigeonList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK != resultCode) return;
        PigeonEntity pigeonEntity = (PigeonEntity) data.getSerializableExtra(IntentBuilder.KEY_DATA);
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, pigeonEntity)
                .finishForResult(getBaseActivity());

    }
}
