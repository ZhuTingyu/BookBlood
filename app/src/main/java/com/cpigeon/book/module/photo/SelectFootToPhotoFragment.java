package com.cpigeon.book.module.photo;

import android.app.Activity;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cpigeon.book.R;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.model.entity.PigeonPhotoEntity;
import com.cpigeon.book.module.basepigeon.BaseFootListFragment;
import com.cpigeon.book.module.breedpigeon.adpter.LinearLayoutListener;
import com.cpigeon.book.module.homingpigeon.OnDeleteListener;
import com.cpigeon.book.module.homingpigeon.adapter.MyHomingPigeonAdapter;
import com.cpigeon.book.module.photo.viewmodel.PigeonPhotoViewModel;
import com.cpigeon.book.service.EventBusService;

import org.greenrobot.eventbus.Subscribe;

/**
 * 信鸽相册   足环列表
 */
public class SelectFootToPhotoFragment extends BaseFootListFragment {


    PigeonPhotoViewModel mViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new PigeonPhotoViewModel();
        initViewModel(mViewModel);
        mBreedPigeonListModel.stateid = PigeonEntity.ID_ALL_MY_PGIEON;
    }

    public static void start(Activity activity) {
        SearchFragmentParentActivity.
                start(activity, SelectFootToPhotoFragment.class, false, null);
    }


    @Override
    protected void initData() {
        super.initData();

        setStartSearchActvity(SearchFootToPhotoActivity.class);//搜索页面
        setTitle("信鸽相册");
        mAdapter = new MyHomingPigeonAdapter(new OnDeleteListener() {
            @Override
            public void delete(String PigeonId) {
                mBreedPigeonListModel.id = PigeonId;
                mBreedPigeonListModel.deletePigeon();
            }
        }, new LinearLayoutListener() {
            @Override
            public void click(int position) {
                try {
                    PigeonEntity mPigeonEntity = mAdapter.getData().get(position);
                    PigeonPhotoHomeActivity.start(getBaseActivity(), mPigeonEntity);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });


        mViewModel.getTXGP_PigeonPhoto_CountPhotoData();
    }


    @Override
    protected void initObserve() {
        super.initObserve();
        //统计数据
        mViewModel.mPigeonPhotoCount.observe(this, datas -> {

            mAdapter.removeAllHeaderView();
            mAdapter.notifyDataSetChanged();

            mAdapter.addHeaderView(initHead(datas));
        });
    }

    @Subscribe //订阅事件FirstEvent
    public void onEventMainThread(String info) {
        if (info.equals(EventBusService.PIGEON_PHOTO_REFRESH)) {

            mViewModel.getTXGP_PigeonPhoto_CountPhotoData();
            super.initData(true);
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
        SpannableStringBuilder builder = new SpannableStringBuilder(String.format(getString(R.string.text_photo_all_place), datas.getTotalCount() + " "));
        ForegroundColorSpan redSpan = new ForegroundColorSpan(getBaseActivity().getResources().getColor(R.color.black));
        builder.setSpan(redSpan, 6,
                5 + String.format("%1$s", datas.getTotalCount()).length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvAllPlace.setText(builder);

        //相册总容量
        String s = getString(R.string.text_photo_user_and_remain_place);
        s = s.replace("%1%", datas.getUseCount());
        s = s.replace("%2%", String.valueOf(Double.parseDouble(datas.getTotalCount()) - Double.parseDouble(datas.getUseCount())));
        tvUsePlace.setText(s);

        progressBar.setMax((int)Double.parseDouble(datas.getTotalCount()));
        progressBar.setProgress((int)Double.parseDouble(datas.getUseCount()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
