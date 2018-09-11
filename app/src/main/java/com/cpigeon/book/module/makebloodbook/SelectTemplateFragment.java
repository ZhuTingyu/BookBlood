package com.cpigeon.book.module.makebloodbook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/11.
 */

public class SelectTemplateFragment extends BaseBookFragment {

    public static final int TYPE_H = 0;
    public static final int TYPE_V = 1;

    List<View> mSelectViews = Lists.newArrayList();
    List<View> mHookViews = Lists.newArrayList();
    List<Integer> mSelectViewIds = Lists.newArrayList(R.id.rl1, R.id.rl2);
    List<Integer> mmHookViewsIds = Lists.newArrayList(R.id.rlSelect1, R.id.rlSelect2);
    int mChoosePosition = 0;

    public static void start(Activity activity, int type, int code){
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, type)
                .startParentActivity(activity, SelectTemplateFragment.class, code);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_template, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.text_choos_book_template);

        mChoosePosition = getBaseActivity().getIntent().getIntExtra(IntentBuilder.KEY_DATA, 0);

        setToolbarRight(R.string.text_sure,item -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, mChoosePosition)
                    .finishForResult(getBaseActivity());
            return false;
        });

        for (int i = 0, len = mSelectViewIds.size(); i < len; i++) {
            View v = findViewById(mSelectViewIds.get(i));
            int finalI = i;
            v.setOnClickListener(v1 -> {
                hideAllHoke();
                mHookViews.get(finalI).setVisibility(View.VISIBLE);
                mChoosePosition = finalI;
            });
            mSelectViews.add(v);
        }

        for (Integer id : mmHookViewsIds) {
            View v = findViewById(id);
            mHookViews.add(v);
        }

        hideAllHoke();
        mHookViews.get(mChoosePosition).setVisibility(View.VISIBLE);
    }

    void hideAllHoke() {
        for (View view : mHookViews) {
            view.setVisibility(View.GONE);
        }
    }
}
