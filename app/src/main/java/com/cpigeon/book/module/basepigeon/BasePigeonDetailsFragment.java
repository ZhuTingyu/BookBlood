package com.cpigeon.book.module.basepigeon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.base.util.Utils;
import com.base.util.utility.StringUtil;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.event.OpenServiceEvent;
import com.cpigeon.book.model.entity.AssEntity;
import com.cpigeon.book.model.entity.LoftEntity;
import com.cpigeon.book.module.breedpigeon.viewmodel.BookViewModel;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonDetailsViewModel;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonModifyViewModel;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.play.PlayFragment1;
import com.cpigeon.book.module.play.PlayFragment2;
import com.cpigeon.book.module.play.viewmodel.PlayListViewModel;
import com.cpigeon.book.util.PigeonPublicUtil;
import com.cpigeon.book.widget.family.FamilyTreeView;
import com.cpigeon.book.widget.mydialog.AddPlayDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @BindView(R.id.ll_details_other)
    protected LinearLayout ll_details_other;//血统书制作 ， 繁育信息
    @BindView(R.id.img_play_import)
    protected ImageView img_play_import;//导入赛绩
    @BindView(R.id.img_play_add)
    protected ImageView img_play_add;//添加赛绩


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


    protected BreedPigeonDetailsViewModel mBreedPigeonDetailsViewModel;

    protected BreedPigeonModifyViewModel mBreedPigeonModifyViewModel;//种鸽修改ViewModel
    protected SelectTypeViewModel mSelectTypeViewModel;
    protected PlayListViewModel mPlayListViewModel;
    protected BookViewModel mBookViewModel;


    protected AddPlayDialog mAddPlayDialog;
    protected String mType;
    protected int mGenerationCount = 0;
    protected String mFirstFootNumber;


    public static final int CODE_ORGANIZE = 0x123;
    public static final int CODE_LOFT = 0x234;
    public static final String TYPE_MY_SHARE = "TYPE_MY_SHARE";
    public static final String TYPE_HIS_SHARE = "TYPE_HIS_SHARE";
    public static final String TYPE_SHARE_PIGEON = "TYPE_SHARE_PIGEON";
    public static final String TYPE_GOOD_PIGEON = "TYPE_GOOD_PIGEON";
    protected static final String KEY_TITLE_FOOT_NUMBER = "KEY_TITLE_FOOT_NUMBER";


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBreedPigeonDetailsViewModel = new BreedPigeonDetailsViewModel(getBaseActivity());
        mPlayListViewModel = new PlayListViewModel();
        mSelectTypeViewModel = new SelectTypeViewModel();
        mBreedPigeonModifyViewModel = new BreedPigeonModifyViewModel();
        mBookViewModel = new BookViewModel(getBaseActivity());
        initViewModels(mBreedPigeonDetailsViewModel, mPlayListViewModel
                , mSelectTypeViewModel, mBreedPigeonModifyViewModel, mBookViewModel);
        mGenerationCount = getBaseActivity().getIntent().getIntExtra(IntentBuilder.KEY_TITLE, 0);
        mFirstFootNumber = getBaseActivity().getIntent().getStringExtra(KEY_TITLE_FOOT_NUMBER);

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

        if (mGenerationCount != 0) {
            setTitle(Utils.getString(R.string.text_pigeon_generation_title
                    , mFirstFootNumber, String.valueOf(mGenerationCount)));
        }
        mGenerationCount = getBaseActivity().getIntent().getIntExtra(IntentBuilder.KEY_TITLE, 0);

        initInputPlayDialog();
        setProgressVisible(true);


        mPlayListViewModel.footid = mBreedPigeonDetailsViewModel.footId;
        mPlayListViewModel.pigeonid = mBreedPigeonDetailsViewModel.pigeonId;

        mBookViewModel.foodId = mBreedPigeonDetailsViewModel.footId;
        mBookViewModel.pigeonId = mBreedPigeonDetailsViewModel.pigeonId;

/*        mBreedPigeonDetailsViewModel.getPigeonDetails();//获取 鸽子  详情
        mBookViewModel.getBloodBook();// //获取 血统书  四代

        mSelectTypeViewModel.getSelectType_Sex();
        mSelectTypeViewModel.getSelectType_FeatherColor();
        mSelectTypeViewModel.getSelectType_eyeSand();
        mSelectTypeViewModel.getSelectType_lineage();
        mSelectTypeViewModel.getSelectType_State();
        mSelectTypeViewModel.getSelectType_PigeonSource();*/

        initViewPager();

    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mBreedPigeonDetailsViewModel.mBreedPigeonData.observe(this, datas -> {

            if (!StringUtil.isStringValid(mFirstFootNumber)) {
                setTitle(mBreedPigeonDetailsViewModel.mPigeonEntity.getFootRingNum());
                mFirstFootNumber = datas.getFootRingNum();
            }

            setProgressVisible(false);
            mBreedPigeonModifyViewModel.mPigeonEntity = datas;

            tvFoot.setText(String.valueOf(datas.getFootCode() + "-" + datas.getFootRingNum()));//足环号

            //设置性别
            PigeonPublicUtil.setPigeonSexImg(datas.getPigeonSexName(), imgSex);


            if (StringUtil.isStringValid(datas.getFootRingIDToNum())) {
                tvFootVice.setText(datas.getFootRingIDToNum());//副足环
            } else {
                tvFootVice.setText(getString(R.string.str_hint_no));//副足环
            }

            if (StringUtil.isStringValid(datas.getPigeonBloodName())) {
                tvLineage.setText(datas.getPigeonBloodName());//血统
            } else {
                tvLineage.setText(getString(R.string.str_hint_no));//血统
            }

            if (StringUtil.isStringValid(datas.getStateName())) {
                tvState.setText(datas.getStateName());//状态
            } else {
                tvState.setText(getString(R.string.str_hint_no));//状态
            }

            if (StringUtil.isStringValid(datas.getPigeonEyeName())) {
                tvEyeSand.setText(datas.getPigeonEyeName());//眼砂d
            } else {
                tvEyeSand.setText(getString(R.string.str_hint_no));//眼砂d
            }

            if (StringUtil.isStringValid(datas.getPigeonPlumeName())) {
                tvFeatherColor.setText(datas.getPigeonPlumeName());//羽色
            } else {
                tvFeatherColor.setText(getString(R.string.str_hint_no));//羽色
            }

            if (StringUtil.isStringValid(datas.getOutShellTime()) && !datas.getOutShellTime().equals("0001-01-01")) {
                tvTheirShellsDate.setText(datas.getOutShellTime());//出壳日期
            } else {
                tvTheirShellsDate.setText(getString(R.string.str_hint_no));//出壳日期
            }

            if (StringUtil.isStringValid(datas.getSourceName())) {
                tvFootSource.setText(datas.getSourceName());//来源
            } else {
                tvFootSource.setText(getString(R.string.str_hint_no));//来源
            }

            if (StringUtil.isStringValid(datas.getPigeonScore())) {
                tvScore.setText(datas.getPigeonScore());//评分
            } else {
                tvScore.setText(getString(R.string.str_hint_no));//评分
            }

            Glide.with(this)
                    .load(datas.getCoverPhotoUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_img_default2)
                    .error(R.drawable.ic_img_default2)
                    .dontAnimate()
                    .into(img_pigeon);
            if (TYPE_HIS_SHARE.equals(mType)) {
                tvLeft.setText(mBreedPigeonDetailsViewModel.mPigeonEntity.getUserName());
            }
        });


        mBreedPigeonModifyViewModel.mBreedPigeonData.observe(this, pigeonEntryEntity -> {
            mBreedPigeonDetailsViewModel.getPigeonDetails();
        });

        mBookViewModel.mBookLiveData.observe(this, bloodBookEntity -> {
            mFamilyTreeView.setData(bloodBookEntity);
        });

        mSelectTypeViewModel.mSelectType_Sex.observe(this, selectTypeEntities -> {
            mBreedPigeonModifyViewModel.mSelectTypes_Sex = selectTypeEntities;
        });

        mSelectTypeViewModel.mSelectType_FeatherColor.observe(this, selectTypeEntities -> {
            mBreedPigeonModifyViewModel.mSelectTypes_FeatherColor = selectTypeEntities;
        });

        mSelectTypeViewModel.mSelectType_EyeSand.observe(this, selectTypeEntities -> {
            mBreedPigeonModifyViewModel.mSelectTypes_EyeSand = selectTypeEntities;
        });


        mSelectTypeViewModel.mSelectType_Lineage.observe(this, selectTypeEntities -> {
            mBreedPigeonModifyViewModel.mSelectTypes_Lineage = selectTypeEntities;
        });

        mSelectTypeViewModel.mSelectType_State.observe(this, selectTypeEntities -> {
            mBreedPigeonModifyViewModel.mSelectTypes_State = selectTypeEntities;
        });

        mSelectTypeViewModel.mSelectType_Pigeon_Source.observe(this, selectTypeEntities -> {
            setProgressVisible(false);
            mBreedPigeonModifyViewModel.mSelectTypes_Source = selectTypeEntities;
        });
    }

    private void initInputPlayDialog() {
        mAddPlayDialog = new AddPlayDialog();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == CODE_ORGANIZE) {

            //选择协会回调
            AssEntity organize = data.getParcelableExtra(IntentBuilder.KEY_DATA);
            mAddPlayDialog.setllUnitName(organize);

        } else if (requestCode == CODE_LOFT) {

            //选择公棚回调
            LoftEntity organize = data.getParcelableExtra(IntentBuilder.KEY_DATA);
            mAddPlayDialog.setllUnitName(organize);

        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(OpenServiceEvent event){
        mBreedPigeonDetailsViewModel.getPigeonDetails();
    }
}
