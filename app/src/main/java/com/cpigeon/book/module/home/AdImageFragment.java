package com.cpigeon.book.module.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.base.util.IntentBuilder;
import com.base.util.glide.GlideUtil;
import com.base.util.system.ScreenTool;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;

import retrofit2.http.PUT;

/**
 * Created by Zhu TingYu on 2018/11/9.
 */

public class AdImageFragment extends BaseBookFragment {

    public static final String TYPE_AI_PIGEON_HOUSE = "TYPE_AI_PIGEON_HOUSE";
    public static final String TYPE_FIND_BLOOD = "TYPE_FIND_BLOOD";
    public static final String TYPE_MATCH_PIGEON_ANALYSE = "TYPE_MATCH_PIGEON_ANALYSE";
    private String mType;
    private ImageView mImageView;

    public static void start(Activity activity, String type) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_TYPE, type)
                .startParentActivity(activity, AdImageFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mType = getBaseActivity().getIntent().getStringExtra(IntentBuilder.KEY_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_al_image_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageView = findViewById(R.id.img);
        int w = ScreenTool.getScreenWidth();
        mImageView.setLayoutParams(new RelativeLayout.LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT));
        if (TYPE_AI_PIGEON_HOUSE.equals(mType)) {
            setTitle(R.string.text_ai_pigeon_house);
            GlideUtil.setGlideImageView(getBaseActivity(), R.mipmap.ic_bg_ai_pigeon_house, mImageView);
        } else if (TYPE_FIND_BLOOD.equals(mType)) {
            setTitle(R.string.text_blood_find);
            GlideUtil.setGlideImageView(getBaseActivity(), R.mipmap.ic_bg_find_blood, mImageView);
        } else {
            setTitle(R.string.text_train_analyze);
            GlideUtil.setGlideImageView(getBaseActivity(), R.mipmap.ic_bg_match_fen, mImageView);
        }
    }
}
