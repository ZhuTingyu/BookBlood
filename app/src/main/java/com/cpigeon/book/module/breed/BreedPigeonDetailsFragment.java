package com.cpigeon.book.module.breed;

import android.app.Activity;
import android.content.Context;
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

import com.base.base.FragmentAdapter;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.widget.CustomViewPager;
import com.base.widget.magicindicator.MagicIndicator;
import com.base.widget.magicindicator.ViewPagerHelper;
import com.base.widget.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.base.widget.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.base.widget.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.base.widget.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.base.widget.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import com.base.widget.magicindicator.ext.titles.ScaleTransitionPagerTitleView;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntryEntity;
import com.cpigeon.book.module.breed.viewmodel.BreedPigeonDetailsViewModel;
import com.cpigeon.book.module.foot.InputSingleFootDialog;
import com.cpigeon.book.module.photo.PigeonPhotoHomeActivity;
import com.cpigeon.book.module.play.PlayAddFragment;
import com.cpigeon.book.module.play.PlayFragment1;
import com.cpigeon.book.module.play.PlayFragment2;
import com.cpigeon.book.module.play.viewmodel.PlayListViewModel;
import com.cpigeon.book.widget.mydialog.AddPlayDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 种鸽详情
 * Created by Administrator on 2018/8/29.
 */

public class BreedPigeonDetailsFragment extends BaseBookFragment {

    @BindView(R.id.tv_foot)
    TextView tvFoot;
    @BindView(R.id.img_sex)
    ImageView imgSex;
    @BindView(R.id.tv_foot_vice)
    TextView tvFootVice;
    @BindView(R.id.ll_foot_vice)
    LinearLayout llFootVice;
    @BindView(R.id.tv_lineage)
    TextView tvLineage;
    @BindView(R.id.ll_lineage)
    LinearLayout llLineage;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.ll_state)
    LinearLayout llState;
    @BindView(R.id.tv_eye_sand)
    TextView tvEyeSand;
    @BindView(R.id.ll_eye_sand)
    LinearLayout llEyeSand;
    @BindView(R.id.tv_feather_color)
    TextView tvFeatherColor;
    @BindView(R.id.ll_feather_color)
    LinearLayout llFeatherColor;
    @BindView(R.id.ll_info1)
    LinearLayout llInfo1;
    @BindView(R.id.tv_their_shells_date)
    TextView tvTheirShellsDate;
    @BindView(R.id.ll_their_shells_date)
    LinearLayout llTheirShellsDate;
    @BindView(R.id.tv_foot_source)
    TextView tvFootSource;
    @BindView(R.id.ll_foot_source)
    LinearLayout llFootSource;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.ll_score)
    LinearLayout llScore;

    @BindView(R.id.img_pigeon)
    CircleImageView img_pigeon;


    @BindView(R.id.indicator)
    MagicIndicator mIndicator;
    @BindView(R.id.viewPager)
    CustomViewPager mViewPager;
    protected List<Fragment> mFragments = Lists.newArrayList();
    protected List<String> mTitles = Lists.newArrayList();

    private BreedPigeonDetailsViewModel mBreedPigeonDetailsViewModel;

    private PlayListViewModel mPlayListViewModel;

    private AddPlayDialog mAddPlayDialog;

    public static int CODE_ORGANIZE = 0x123;
    public static int CODE_LOFT = 0x234;

    public static void start(Activity activity, String foodId) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, foodId)
                .startParentActivity(activity, BreedPigeonDetailsFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mBreedPigeonDetailsViewModel = new BreedPigeonDetailsViewModel(getBaseActivity());
        mPlayListViewModel = new PlayListViewModel();
        initViewModels(mBreedPigeonDetailsViewModel, mPlayListViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breed_pigeon_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("详情");

        setToolbarRight("成长记录", item -> {
            GrowthReportFragment.start(getBaseActivity(), "");
            return true;
        });

        initInputPlayDialog();


        mBreedPigeonDetailsViewModel.getPigeonDetails();


        mPlayListViewModel.pigeonid = mBreedPigeonDetailsViewModel.footId;


        initViewPager();
    }

    private void initViewPager() {

        PlayFragment1 mPlayFragment1 = new PlayFragment1();
        mFragments.add(mPlayFragment1);

        PlayFragment2 mPlayFragment2 = new PlayFragment2();
        mFragments.add(mPlayFragment2);

        String[] titles = {"赛绩", "附加信息"};
        mTitles = Lists.newArrayList(titles);

        FragmentAdapter adapter = new FragmentAdapter(getBaseActivity().getSupportFragmentManager()
                , mFragments, mTitles);

        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(5);

        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdjustMode(false);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitles == null ? 0 : mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mTitles.get(index));
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(getBaseActivity().getResources().getColor(R.color.color_4c4c4c));
                simplePagerTitleView.setSelectedColor(getBaseActivity().getResources().getColor(R.color.colorPrimary));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                return 1.0f;
            }
        });
        mIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicator, mViewPager);

    }


    private void initInputPlayDialog() {
        mAddPlayDialog = new AddPlayDialog();
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mBreedPigeonDetailsViewModel.mBreedPigeonData.observe(this, datas -> {

            tvFoot.setText(datas.getFootRingNum());//足环号

            if (datas.getPigeonSexName().equals("雌")) {
                imgSex.setImageResource(R.mipmap.ic_female);
            } else if (datas.getPigeonSexName().equals("雄")) {
                imgSex.setImageResource(R.mipmap.ic_male);
            } else {
                imgSex.setImageResource(R.mipmap.ic_sex_no);
            }

            tvFootVice.setText(datas.getFootRingIDToNum());//副足环
            tvLineage.setText(datas.getPigeonBloodName());//血统
            tvState.setText(datas.getStateName());//状态
            tvEyeSand.setText(datas.getPigeonEyeName());//眼砂d

            tvFeatherColor.setText(datas.getPigeonPlumeName());//羽色
            tvTheirShellsDate.setText(datas.getOutShellTime());//出壳日期
            tvFootSource.setText(datas.getSourceName());//来源

            tvScore.setText(datas.getPigeonScore());//评分

            Glide.with(this)
                    .load(datas.getCoverPhotoUrl())
                    .placeholder(R.drawable.ic_img_default)
                    .into(img_pigeon);//鸽子照片
        });


    }

    @OnClick({R.id.img_pigeon, R.id.tv_foot, R.id.img_sex, R.id.ll_foot_vice, R.id.ll_lineage, R.id.ll_state, R.id.ll_eye_sand, R.id.ll_feather_color, R.id.ll_their_shells_date, R.id.ll_foot_source, R.id.ll_score
            , R.id.tv_make_book, R.id.tv_lineage_analysis, R.id.tv_lineage_roots, R.id.tv_breed_info
            , R.id.img_play_import, R.id.img_play_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.img_pigeon:
                //信鸽相册
                PigeonPhotoHomeActivity.start(getBaseActivity());
                break;

            case R.id.tv_foot:
                //足环号
                List<String> foots = new ArrayList<>();
                InputSingleFootDialog dialog = new InputSingleFootDialog();
                dialog.setFoots(foots);
                dialog.setOnFootStringFinishListener(foot -> {
                    tvFoot.setText(foot);
                });
                dialog.show(getBaseActivity().getSupportFragmentManager());

                break;
            case R.id.img_sex:
                //性别

                break;
            case R.id.ll_foot_vice:
                //副环
                List<String> foots2 = new ArrayList<>();
                InputSingleFootDialog dialog2 = new InputSingleFootDialog();
                dialog2.setFoots(foots2);
                dialog2.setOnFootStringFinishListener(foot -> {
                    tvFootVice.setText(foot);
                });
                dialog2.show(getBaseActivity().getSupportFragmentManager());
                break;
            case R.id.ll_lineage:
                //血统

                break;
            case R.id.ll_state:
                //状态

                break;
            case R.id.ll_eye_sand:
                //眼砂

                break;
            case R.id.ll_feather_color:
                //羽色

                break;
            case R.id.ll_their_shells_date:
                //出壳日期

                break;
            case R.id.ll_foot_source:
                //来源

                break;
            case R.id.ll_score:
                //评分

                break;

            case R.id.tv_make_book:
                //血统书制作

                break;

            case R.id.tv_lineage_analysis:
                //血统分析

                break;
            case R.id.tv_lineage_roots:
                //血统寻根

                break;

            case R.id.tv_breed_info:
                //繁育信息

                break;
            case R.id.img_play_import:
                //赛绩导入

//                ImportPlayDialog mImportPlayDialog = new ImportPlayDialog(getBaseActivity());
//                mImportPlayDialog.show();

                mAddPlayDialog.show(getBaseActivity().getFragmentManager(), "");
                break;
            case R.id.img_play_add:
                //手动添加赛绩
                try {
                    BreedPigeonEntity mBreedPigeonEntity = mBreedPigeonDetailsViewModel.mBreedPigeonData.getValue();
                    PlayAddFragment.start(getBaseActivity(),
                            new PigeonEntryEntity.Builder()
                                    .FootRingID(mBreedPigeonEntity.getFootRingID())
                                    .FootRingNum(mBreedPigeonEntity.getFootRingNum())
                                    .PigeonID(mBreedPigeonEntity.getPigeonID())
                                    .build());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
