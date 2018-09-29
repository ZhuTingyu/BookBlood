package com.cpigeon.book.module.menu.mycurrency.adapter;

import android.widget.TextView;

import com.base.base.BaseViewHolder;
import com.base.base.adpter.BaseQuickAdapter;
import com.base.util.Lists;
import com.base.util.Utils;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.MyApp;
import com.cpigeon.book.R;
import com.cpigeon.book.model.entity.PigeonCurrencyEntity;
import com.cpigeon.book.module.feedpigeon.FeedPigeonRecordListFragment;
import com.cpigeon.book.module.foot.FootAdminAddMultipleFragment;
import com.cpigeon.book.module.foot.FootAdminSingleFragment;
import com.cpigeon.book.module.menu.SignFragment;
import com.cpigeon.book.module.photo.SelectFootToPhotoFragment;
import com.cpigeon.book.module.racing.BreedPigeonEntryFragment2;
import com.cpigeon.book.module.racing.RacingPigeonEntryFragment;

/**
 * Created by Zhu TingYu on 2018/8/24.
 */

public class MyPigeonCurrencyAdapter extends BaseQuickAdapter<PigeonCurrencyEntity.GetgbBean, BaseViewHolder> {
    private String[] chooseWays;

    public MyPigeonCurrencyAdapter() {
        super(R.layout.item_my_pigeon_currency, Lists.newArrayList());
        chooseWays = MyApp.getAppContext().getResources().getStringArray(R.array.array_choose_input_foot_number);
    }

    @Override
    protected void convert(BaseViewHolder helper, PigeonCurrencyEntity.GetgbBean item) {
        helper.setText(R.id.tvTitle, item.getSname());
        helper.setText(R.id.tvRule, item.getSgb());
        TextView status = helper.getView(R.id.tvStatus);
        if (item.getIsdo().equals("0")) {
            status.setTextColor(Utils.getColor(R.color.white));
            status.setText(Utils.getString(R.string.text_go_finish));
            status.setBackgroundResource(R.mipmap.ic_my_currency_go_finish);

            helper.getView(R.id.view_z).setOnClickListener(v -> {
                if (item.getSname().equals(mContext.getString(R.string.str_gb_sign))) {
                    //签到
                    SignFragment.start(getBaseActivity());
                } else if (item.getSname().equals(mContext.getString(R.string.str_gb_entry_foot))) {
                    //录入足环
                    BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                        if (chooseWays[p].equals(Utils.getString(R.string.text_one_foot_input))) {
                            FootAdminSingleFragment.start(getBaseActivity());
                        } else {
                            FootAdminAddMultipleFragment.start(getBaseActivity());
                        }
                    });

                } else if (item.getSname().equals(mContext.getString(R.string.str_gb_entry_breed_pigeon))) {
                    //录入种鸽
//                    BreedPigeonEntryFragment.start(getBaseActivity());
                    BreedPigeonEntryFragment2.start(getBaseActivity());

                } else if (item.getSname().equals(mContext.getString(R.string.str_gb_entry_racing_pigeon))) {
                    //录入赛鸽
                    RacingPigeonEntryFragment.start(getBaseActivity());

                } else if (item.getSname().equals(mContext.getString(R.string.str_gb_feed_pigeon))) {
                    //养鸽记录
                    FeedPigeonRecordListFragment.start(getBaseActivity());

                } else if (item.getSname().equals(mContext.getString(R.string.str_gb_photo_pigeon))) {
                    //信鸽拍照
                    SelectFootToPhotoFragment.start(getBaseActivity());

                }
            });

        } else {
            status.setTextColor(Utils.getColor(R.color.color_text_hint));
            status.setText(Utils.getString(R.string.text_finished));
            status.setBackgroundResource(R.mipmap.ic_my_currency_finish);
        }
    }
}
