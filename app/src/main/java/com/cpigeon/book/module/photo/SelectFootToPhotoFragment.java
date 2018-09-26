package com.cpigeon.book.module.photo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseSearchActivity;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonPhotoEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.photo.adpter.SelectFootToPhotoAdapter;
import com.cpigeon.book.module.photo.viewmodel.PigeonPhotoViewModel;
import com.cpigeon.book.service.EventBusService;
import com.cpigeon.book.util.OrderUtil;

import org.greenrobot.eventbus.Subscribe;

/**
 * 信鸽赛绩   足环列表
 * Created by Zhu TingYu on 2018/9/11.
 */

public class SelectFootToPhotoFragment extends BaseFootListFragment {


    PigeonPhotoViewModel mViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new PigeonPhotoViewModel();
        initViewModel(mViewModel);
    }

    public static void start(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentBuilder.KEY_TYPE, "");
        SearchFragmentParentActivity.
                start(activity, SelectFootToPhotoFragment.class, false, bundle);
    }


    @Override
    protected void initData() {
        super.initData();

        mTvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);

        mAdapter = new SelectFootToPhotoAdapter();

        mActivity.setSearchClickListener(v -> {
            //搜索
            BaseSearchActivity.start(getBaseActivity(), SearchFootToPhotoActivity.class, null);
        });

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {

            PigeonEntity mPigeonEntity = mAdapter.getData().get(position);
            PigeonPhotoHomeActivity.start(getBaseActivity(), mPigeonEntity);

        });

        mViewModel.getTXGP_PigeonPhoto_CountPhotoData();
    }


    @Override
    protected void initObserve() {
        super.initObserve();
        //统计数据
        mViewModel.mPigeonPhotoCount.observe(this, datas -> {
            mAdapter.removeAllHeaderView();
            mAdapter.addHeaderView(initHead(datas));
        });
    }

    @Subscribe //订阅事件FirstEvent
    public void onEventMainThread(String info) {
        if (info.equals(EventBusService.PIGEON_PHOTO_DEL_REFRESH)) {
            mViewModel.getTXGP_PigeonPhoto_CountPhotoData();
        }
    }


    private View initHead(PigeonPhotoEntity datas) {
        View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.include_select_foot_to_photo_head, null);
        ProgressBar progressBar = view.findViewById(R.id.progressPlace);
        TextView tvCount = view.findViewById(R.id.tvCount);//总图片张数
        TextView tvAllPlace = view.findViewById(R.id.tvAllPlace);//相册总容量
        TextView tvUsePlace = view.findViewById(R.id.tvUsePlace);//相册使用

        //总图片张数
        tvCount.setText(datas.getPhotoCount());

        //相册总容量
        SpannableStringBuilder builder = new SpannableStringBuilder(String.format(getString(R.string.text_photo_all_place), datas.getTotalCount()));
        ForegroundColorSpan redSpan = new ForegroundColorSpan(getBaseActivity().getResources().getColor(R.color.black));
        builder.setSpan(redSpan, 5,
                5 + String.format("%1$s", datas.getTotalCount()).length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvAllPlace.setText(builder);

        //相册总容量
        String s = getString(R.string.text_photo_user_and_remain_place);
        s = s.replace("%1%", datas.getUseCount());
        s = s.replace("%2%", String.valueOf(Integer.parseInt(datas.getTotalCount()) - Integer.parseInt(datas.getUseCount())));
        tvUsePlace.setText(s);

        progressBar.setMax(Integer.parseInt(datas.getTotalCount()));
        progressBar.setProgress(Integer.parseInt(datas.getUseCount()));
        return view;
    }
}
