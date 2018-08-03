package com.cpigeon.book.module.launch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.base.BaseFragment;
import com.base.util.IntentBuilder;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.module.MainActivity;

/**
 * Created by Zhu TingYu on 2018/7/26.
 */

public class GuideFragment extends BaseFragment {

    private ImageView mImageView;
    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mImageView = new ImageView(getContext());
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return mImageView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null){
            position = getArguments().getInt(IntentBuilder.KEY_DATA);
        }
        Glide.with(getBaseActivity()).load(R.mipmap.ic_launcher)
                .into(mImageView);

        if(position == 3){
            mImageView.setOnClickListener(v -> {
                MainActivity.start(getBaseActivity());
                finish();
            });
        }
    }
}
