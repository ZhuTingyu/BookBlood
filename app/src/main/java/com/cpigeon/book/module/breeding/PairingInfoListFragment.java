package com.cpigeon.book.module.breeding;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.module.basepigeon.BaseListFragment;
import com.cpigeon.book.module.breeding.adapter.PairingInfoListAdapter;

/**
 * 配对信息
 * Created by Administrator on 2018/9/10.
 */

public class PairingInfoListFragment extends BaseListFragment {

    private PairingInfoListAdapter mPairingInfoListAdapter;

    public static void start(Activity activity, String pigeonId, String footId) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, pigeonId)
                .putExtra(IntentBuilder.KEY_DATA_2, footId)
                .startParentActivity(activity, PairingInfoListFragment.class);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvOk.setVisibility(View.GONE);
        view_placeholder.setVisibility(View.GONE);

        setTitle("配对信息");


        String[] chooseWays = getResources().getStringArray(R.array.text_breeding_info);
        setToolbarRightImage(R.drawable.svg_filtrate, item -> {
            BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                if (chooseWays[p].equals(Utils.getString(R.string.array_pairing_add))) {
                    //添加配对
                    PairingInfoAddFragment.start(getBaseActivity());
                } else if (chooseWays[p].equals(Utils.getString(R.string.array_pairing_recommend))) {
                    //推荐配对
                    PairingInfoRecommendFragment.start(getBaseActivity());
                } else if (chooseWays[p].equals(Utils.getString(R.string.array_blind_date))) {
                    //相亲配对

                }
            });

            return false;
        });

        mPairingInfoListAdapter = new PairingInfoListAdapter();

        CardView mHeadView = (CardView) LayoutInflater.from(getContext()).inflate(R.layout.layout_pairing_info_head, (ViewGroup) view, false);

        mPairingInfoListAdapter.addHeaderView(mHeadView);
        list.setAdapter(mPairingInfoListAdapter);


        mPairingInfoListAdapter.setOnItemClickListener((adapter, view1, position) -> {
            PairingNestInfoListFragment.start(getBaseActivity());
        });
    }
}
