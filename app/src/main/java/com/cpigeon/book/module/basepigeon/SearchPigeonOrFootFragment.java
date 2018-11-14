package com.cpigeon.book.module.basepigeon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.base.util.IntentBuilder;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.breedpigeon.adpter.LinearLayoutListener;
import com.cpigeon.book.module.homingpigeon.OnDeleteListener;
import com.cpigeon.book.module.homingpigeon.adapter.MyHomingPigeonAdapter;

/**
 * 选择鸽子 或  足环返回
 * Created by Administrator on 2018/10/20 0020.
 */

public class SearchPigeonOrFootFragment extends BaseFootListFragment {


    public  static final  int  CODE_SEARCH_PIGEON_ORFOOT = 0x000032;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("选择配对鸽子");
    }

    @Override
    protected void initData() {
        super.initData();
        setStartSearchActvity(SearchPigeonOrFootActivity.class);//搜索页面
setTitle("选择配偶");
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
                    IntentBuilder.Builder()
                            .putExtra(IntentBuilder.KEY_DATA, mPigeonEntity)
                            .finishForResult(getBaseActivity());
                }
               catch (Exception e)
               {
                   e.printStackTrace();
               }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, data.getSerializableExtra(IntentBuilder.KEY_DATA))
                    .finishForResult(getBaseActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
