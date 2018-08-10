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
import com.base.util.Lists;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.module.MainActivity;
import com.cpigeon.book.module.login.LoginActivity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/7/26.
 */

public class GuideFragment extends BaseFragment {

    private ImageView mImageView;
    private int position;
    List<Integer> imgs =Lists.newArrayList(R.mipmap.ic_welcome_1
            ,R.mipmap.ic_welcome_2
            ,R.mipmap.ic_welcome_3
            ,R.mipmap.ic_welcome_4);

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

        Glide.with(getBaseActivity()).load(imgs.get(position))
                .centerCrop()
                .into(mImageView);

        if(position == 3){
            mImageView.setOnClickListener(v -> {
                LoginActivity.start(getBaseActivity());
                finish();
            });
        }
    }
}
