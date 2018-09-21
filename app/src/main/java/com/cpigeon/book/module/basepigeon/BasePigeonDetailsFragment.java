package com.cpigeon.book.module.basepigeon;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.util.Lists;
import com.base.widget.CustomViewPager;
import com.base.widget.magicindicator.MagicIndicator;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.widget.family.FamilyTreeView;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/9/21 0021.
 */

public class BasePigeonDetailsFragment extends BaseBookFragment {

    @BindView(R.id.tv_foot)
    protected TextView tvFoot;
    @BindView(R.id.img_sex)
    protected ImageView imgSex;
    @BindView(R.id.tv_foot_vice)
    protected TextView tvFootVice;
    @BindView(R.id.ll_foot_vice)
    protected LinearLayout llFootVice;
    @BindView(R.id.tv_lineage)
    protected TextView tvLineage;
    @BindView(R.id.ll_lineage)
    protected LinearLayout llLineage;
    @BindView(R.id.tv_state)
    protected TextView tvState;
    @BindView(R.id.ll_state)
    protected LinearLayout llState;
    @BindView(R.id.tv_eye_sand)
    protected TextView tvEyeSand;
    @BindView(R.id.ll_eye_sand)
    protected LinearLayout llEyeSand;
    @BindView(R.id.tv_feather_color)
    protected TextView tvFeatherColor;
    @BindView(R.id.ll_feather_color)
    protected LinearLayout llFeatherColor;
    @BindView(R.id.ll_info1)
    protected LinearLayout llInfo1;
    @BindView(R.id.tv_their_shells_date)
    protected TextView tvTheirShellsDate;
    @BindView(R.id.ll_their_shells_date)
    protected LinearLayout llTheirShellsDate;
    @BindView(R.id.tv_foot_source)
    protected TextView tvFootSource;
    @BindView(R.id.ll_foot_source)
    protected LinearLayout llFootSource;
    @BindView(R.id.tv_score)
    protected TextView tvScore;
    @BindView(R.id.ll_score)
    protected LinearLayout llScore;
    @BindView(R.id.familyTreeView)
    protected FamilyTreeView mFamilyTreeView;

    @BindView(R.id.img_pigeon)
    protected CircleImageView img_pigeon;


    @BindView(R.id.indicator)
    protected MagicIndicator mIndicator;
    @BindView(R.id.viewPager)
    protected CustomViewPager mViewPager;
    @BindView(R.id.tvLeft)
    protected TextView tvLeft;
    @BindView(R.id.tvRight)
    protected TextView tvRight;
    @BindView(R.id.llButton)
    protected LinearLayout llButton;


    protected List<Fragment> mFragments = Lists.newArrayList();
    protected List<String> mTitles = Lists.newArrayList();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breed_pigeon_details, container, false);
        return view;
    }


}
