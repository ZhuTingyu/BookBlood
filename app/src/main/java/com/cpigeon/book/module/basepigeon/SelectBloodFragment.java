package com.cpigeon.book.module.basepigeon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseLetterSelectAdapter;
import com.base.base.pinyin.LetterSortModel;
import com.base.util.IntentBuilder;
import com.base.widget.recyclerview.XRecyclerView;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.gjiazhe.wavesidebar.WaveSideBar;

/**
 * Created by Zhu TingYu on 2018/10/12.
 */

public class SelectBloodFragment extends BaseBookFragment {

    public static final int CODE_SELECT_BLOOD = 0x987;

    XRecyclerView mRecyclerView;
    WaveSideBar mWaveSideBar;
    LetterSortModel<SelectTypeEntity> mModel = new LetterSortModel<>();
    SelectTypeViewModel mViewModel;
    SelectBloodAdapter mAdapter;

    public static void start(Activity activity){
        IntentBuilder.Builder().startParentActivity(activity, SelectBloodFragment.class, CODE_SELECT_BLOOD);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new SelectTypeViewModel();
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_with_wave_bar, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.text_select_blood);
        mRecyclerView = findViewById(R.id.list);
        mWaveSideBar = findViewById(R.id.side_bar);

        mAdapter = new SelectBloodAdapter();
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            try {
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_DATA, mAdapter.getItem(position))
                        .finishForResult(getBaseActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        mAdapter.bindToRecyclerView(mRecyclerView.getRecyclerView());

        setProgressVisible(true);
        mViewModel.getSelectType_lineage();
    }

    @Override
    protected void initObserve() {
        mViewModel.mSelectType_Lineage.observe(this, assEntities -> {
            setProgressVisible(false);
            mModel.setData(assEntities);
            mAdapter.initWave(mModel, mWaveSideBar);
            mAdapter.initHead(getBaseActivity());
            mRecyclerView.setRefreshing(false);
            mAdapter.setNewData(mModel.getData());
        });
    }
}
class SelectBloodAdapter extends BaseLetterSelectAdapter<SelectTypeEntity, BaseViewHolder>{

    public SelectBloodAdapter() {
        super(R.layout.item_select_blood, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectTypeEntity item) {
        helper.setText(R.id.tvBlood, item.getContent());

        Glide.with(mContext)
                .load("")
                .error( R.drawable.ic_img_default)
                .placeholder(R.drawable.ic_img_default)
                .fallback( R.drawable.ic_img_default)
                .into((ImageView) helper.getView(R.id.imgHead));
    }
}

