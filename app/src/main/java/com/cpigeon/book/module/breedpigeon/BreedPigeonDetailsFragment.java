package com.cpigeon.book.module.breedpigeon;

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
import com.base.util.picker.PickerUtil;
import com.base.util.utility.StringUtil;
import com.base.widget.BottomSheetAdapter;
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
import com.cpigeon.book.base.BaseInputDialog;
import com.cpigeon.book.event.PigeonAddEvent;
import com.cpigeon.book.model.entity.BreedPigeonEntity;
import com.cpigeon.book.model.entity.PigeonEntryEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.breedpigeon.viewmodel.BookViewModel;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonDetailsViewModel;
import com.cpigeon.book.module.breedpigeon.viewmodel.BreedPigeonModifyViewModel;
import com.cpigeon.book.module.foot.InputSingleFootDialog;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.photo.PigeonPhotoHomeActivity;
import com.cpigeon.book.module.play.PlayAddFragment;
import com.cpigeon.book.module.play.PlayFragment1;
import com.cpigeon.book.module.play.PlayFragment2;
import com.cpigeon.book.module.play.viewmodel.PlayListViewModel;
import com.cpigeon.book.widget.family.FamilyTreeView;
import com.cpigeon.book.widget.mydialog.AddPlayDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.qqtheme.framework.picker.OptionPicker;
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
    @BindView(R.id.familyTreeView)
    FamilyTreeView mFamilyTreeView;

    @BindView(R.id.img_pigeon)
    CircleImageView img_pigeon;


    @BindView(R.id.indicator)
    MagicIndicator mIndicator;
    @BindView(R.id.viewPager)
    CustomViewPager mViewPager;
    protected List<Fragment> mFragments = Lists.newArrayList();
    protected List<String> mTitles = Lists.newArrayList();
    @BindView(R.id.tvLeft)
    TextView tvLeft;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.llButton)
    LinearLayout llButton;
    Unbinder unbinder;

    private BreedPigeonDetailsViewModel mBreedPigeonDetailsViewModel;

    private BreedPigeonModifyViewModel mBreedPigeonModifyViewModel;//种鸽修改ViewModel
    private SelectTypeViewModel mSelectTypeViewModel;
    private PlayListViewModel mPlayListViewModel;
    private BookViewModel mBookViewModel;

    private AddPlayDialog mAddPlayDialog;

    public static final int CODE_ORGANIZE = 0x123;
    public static final int CODE_LOFT = 0x234;
    public static final String TYPE_MY_SHARE = "TYPE_MY_SHARE";
    public static final String TYPE_HIS_SHARE = "TYPE_HIS_SHARE";


    private String mType;

    public static void start(Activity activity, String pigeonId, String footId) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, pigeonId)
                .putExtra(IntentBuilder.KEY_DATA_2, footId)
                .startParentActivity(activity, BreedPigeonDetailsFragment.class);
    }

    public static void start(Activity activity, String pigeonId, String footId, String type) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, pigeonId)
                .putExtra(IntentBuilder.KEY_DATA_2, footId)
                .putExtra(IntentBuilder.KEY_TYPE, type)
                .startParentActivity(activity, BreedPigeonDetailsFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
        mBreedPigeonDetailsViewModel = new BreedPigeonDetailsViewModel(getBaseActivity());
        mPlayListViewModel = new PlayListViewModel();
        mSelectTypeViewModel = new SelectTypeViewModel();
        mBreedPigeonModifyViewModel = new BreedPigeonModifyViewModel();
        mBookViewModel = new BookViewModel(getBaseActivity());
        initViewModels(mBreedPigeonDetailsViewModel, mPlayListViewModel
                , mSelectTypeViewModel, mBreedPigeonModifyViewModel, mBookViewModel);
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
        mType = getBaseActivity().getIntent().getStringExtra(IntentBuilder.KEY_TYPE);
        setTitle("详情");

        setToolbarRight("成长记录", item -> {
            GrowthReportFragment.start(getBaseActivity(), "");
            return true;
        });

        initInputPlayDialog();
        setProgressVisible(true);
        mBreedPigeonDetailsViewModel.getPigeonDetails();

        mPlayListViewModel.footid = mBreedPigeonDetailsViewModel.footId;
        mPlayListViewModel.pigeonid = mBreedPigeonDetailsViewModel.pigeonId;

        mBookViewModel.foodId = mBreedPigeonDetailsViewModel.footId;
        mBookViewModel.pigeonId = mBreedPigeonDetailsViewModel.pigeonId;

        initViewPager();

        mSelectTypeViewModel.getSelectType_Sex();
        mSelectTypeViewModel.getSelectType_FeatherColor();
        mSelectTypeViewModel.getSelectType_eyeSand();
        mSelectTypeViewModel.getSelectType_lineage();
        mSelectTypeViewModel.getSelectType_State();
        mSelectTypeViewModel.getSelectType_PigeonSource();

        mBookViewModel.getBloodBook();

        mFamilyTreeView.setOnFamilyClickListener(new FamilyTreeView.OnFamilyClickListener() {
            @Override
            public void add(int x, int y) {

                if (x == mFamilyTreeView.getStartGeneration()) {
                    BreedPigeonEntryFragment.start(getBaseActivity()
                            , StringUtil.emptyString()
                            , mBookViewModel.foodId
                            , mBookViewModel.pigeonId
                            , FamilyTreeView.isMale(y) ? BreedPigeonEntryFragment.TYPE_SEX_MALE : BreedPigeonEntryFragment.TYPE_SEX_FEMALE
                            , 0);
                } else {

                    BreedPigeonEntity breedPigeonEntity = null;
                    if (mFamilyTreeView.getSon(x, y) != null) {
                        breedPigeonEntity = mFamilyTreeView.getSon(x, y).getData();
                    }

                    BreedPigeonEntryFragment.start(getBaseActivity()
                            , StringUtil.emptyString()
                            , breedPigeonEntity == null ? StringUtil.emptyString() : breedPigeonEntity.getFootRingID()
                            , breedPigeonEntity == null ? StringUtil.emptyString() : breedPigeonEntity.getPigeonID()
                            , FamilyTreeView.isMale(y) ? BreedPigeonEntryFragment.TYPE_SEX_MALE : BreedPigeonEntryFragment.TYPE_SEX_FEMALE
                            , 0);

                }
            }

            @Override
            public void showInfo(BreedPigeonEntity breedPigeonEntity) {
                BreedPigeonDetailsFragment.start(getBaseActivity(), breedPigeonEntity.getPigeonID()
                        , breedPigeonEntity.getPigeonID());
            }
        });

        if (StringUtil.isStringValid(mType)) {
            llButton.setVisibility(View.VISIBLE);
            if (TYPE_MY_SHARE.equals(mType)) {
                tvLeft.setVisibility(View.GONE);
                tvRight.setText(R.string.text_sure_share);
                tvRight.setOnClickListener(v -> {

                });
            } else if (TYPE_HIS_SHARE.equals(mType)) {
                tvLeft.setOnClickListener(v -> {

                });

                tvRight.setOnClickListener(v -> {

                });
            }
        }


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
            setProgressVisible(false);
            mBreedPigeonModifyViewModel.mBreedPigeonEntity = datas;

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

        mBreedPigeonModifyViewModel.mBreedPigeonData.observe(this, pigeonEntryEntity -> {

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


    private BaseInputDialog mInputDialog;

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
                    mBreedPigeonModifyViewModel.mBreedPigeonEntity.setFootRingNum(foot);
                    mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
                });
                dialog.show(getBaseActivity().getSupportFragmentManager());

                break;
            case R.id.img_sex:
                //性别
                if (!Lists.isEmpty(mBreedPigeonModifyViewModel.mSelectTypes_Sex)) {
                    BottomSheetAdapter.createBottomSheet(getBaseActivity()
                            , SelectTypeEntity.getTypeNames(mBreedPigeonModifyViewModel.mSelectTypes_Sex), p -> {
                                mBreedPigeonModifyViewModel.sexId = mBreedPigeonModifyViewModel.mSelectTypes_Sex.get(p).getTypeID();
//                                llSex.setContent(mBreedPigeonModifyViewModel.mSelectTypes_Sex.get(p).getTypeName());


                                mBreedPigeonModifyViewModel.mBreedPigeonEntity.setPigeonSexID(mBreedPigeonModifyViewModel.mSelectTypes_Sex.get(p).getTypeID());
                                mBreedPigeonModifyViewModel.mBreedPigeonEntity.setPigeonSexName(mBreedPigeonModifyViewModel.mSelectTypes_Sex.get(p).getTypeName());

                                mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
                            });
                } else {
                    mSelectTypeViewModel.getSelectType_Sex();
                }

                break;
            case R.id.ll_foot_vice:
                //副环
                List<String> foots2 = new ArrayList<>();
                InputSingleFootDialog dialog2 = new InputSingleFootDialog();
                dialog2.setFoots(foots2);
                dialog2.setOnFootStringFinishListener(foot -> {
                    tvFootVice.setText(foot);
                    mBreedPigeonModifyViewModel.mBreedPigeonEntity.setFootRingIDToNum(foot);
                    mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
                });
                dialog2.show(getBaseActivity().getSupportFragmentManager());
                break;
            case R.id.ll_lineage:
                //血统
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.text_pigeon_lineage, 0, content -> {
                            mInputDialog.hide();
                            tvLineage.setText(content);
                            mBreedPigeonModifyViewModel.mBreedPigeonEntity.setPigeonBloodID("");
                            mBreedPigeonModifyViewModel.mBreedPigeonEntity.setPigeonBloodName(content);
                            mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
                        }, () -> {
                            mInputDialog.hide();
                            if (!Lists.isEmpty(mBreedPigeonModifyViewModel.mSelectTypes_Lineage)) {
                                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBreedPigeonModifyViewModel.mSelectTypes_Lineage), 0, new OptionPicker.OnOptionPickListener() {
                                    @Override
                                    public void onOptionPicked(int index, String item) {
                                        tvLineage.setText(mBreedPigeonModifyViewModel.mSelectTypes_Lineage.get(index).getTypeName());
                                        mBreedPigeonModifyViewModel.mBreedPigeonEntity.setPigeonBloodID(mBreedPigeonModifyViewModel.mSelectTypes_Lineage.get(index).getTypeID());
                                        mBreedPigeonModifyViewModel.mBreedPigeonEntity.setPigeonBloodName(mBreedPigeonModifyViewModel.mSelectTypes_Lineage.get(index).getTypeName());
                                        mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();

                                    }
                                });
                            } else {
                                mSelectTypeViewModel.getSelectType_lineage();
                            }
                        });
                break;
            case R.id.ll_state:
                //状态
                if (!Lists.isEmpty(mBreedPigeonModifyViewModel.mSelectTypes_State)) {
                    PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBreedPigeonModifyViewModel.mSelectTypes_State), 0, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            tvState.setText(mBreedPigeonModifyViewModel.mSelectTypes_State.get(index).getTypeID());

                            mBreedPigeonModifyViewModel.mBreedPigeonEntity.setStateID(mBreedPigeonModifyViewModel.mSelectTypes_State.get(index).getTypeID());
                            mBreedPigeonModifyViewModel.mBreedPigeonEntity.setStateName(mBreedPigeonModifyViewModel.mSelectTypes_State.get(index).getTypeName());
                            mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();

                        }
                    });
                } else {
                    mSelectTypeViewModel.getSelectType_State();
                }
                break;
            case R.id.ll_eye_sand:
                //眼砂
                if (!Lists.isEmpty(mBreedPigeonModifyViewModel.mSelectTypes_EyeSand)) {
                    PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBreedPigeonModifyViewModel.mSelectTypes_EyeSand), 0, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            tvEyeSand.setText(mBreedPigeonModifyViewModel.mSelectTypes_EyeSand.get(index).getTypeName());

                            mBreedPigeonModifyViewModel.mBreedPigeonEntity.setPigeonEyeID(mBreedPigeonModifyViewModel.mSelectTypes_EyeSand.get(index).getTypeID());
                            mBreedPigeonModifyViewModel.mBreedPigeonEntity.setPigeonEyeName(mBreedPigeonModifyViewModel.mSelectTypes_EyeSand.get(index).getTypeName());
                            mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
                        }
                    });
                } else {
                    mSelectTypeViewModel.getSelectType_eyeSand();
                }
                break;
            case R.id.ll_feather_color:
                //羽色
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.text_feather_color, 0, content -> {
                            mInputDialog.hide();
                            tvFeatherColor.setText(content);

                            mBreedPigeonModifyViewModel.mBreedPigeonEntity.setPigeonPlumeID("");
                            mBreedPigeonModifyViewModel.mBreedPigeonEntity.setPigeonPlumeName(content);
                            mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();

                        }, () -> {
                            mInputDialog.hide();

                            if (!Lists.isEmpty(mBreedPigeonModifyViewModel.mSelectTypes_FeatherColor)) {
                                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mBreedPigeonModifyViewModel.mSelectTypes_FeatherColor), 0, new OptionPicker.OnOptionPickListener() {
                                    @Override
                                    public void onOptionPicked(int index, String item) {
                                        tvFeatherColor.setText(mBreedPigeonModifyViewModel.mSelectTypes_FeatherColor.get(index).getTypeName());
                                        mBreedPigeonModifyViewModel.mBreedPigeonEntity.setPigeonPlumeID(mBreedPigeonModifyViewModel.mSelectTypes_FeatherColor.get(index).getTypeID());
                                        mBreedPigeonModifyViewModel.mBreedPigeonEntity.setPigeonPlumeName(mBreedPigeonModifyViewModel.mSelectTypes_FeatherColor.get(index).getTypeName());
                                        mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();

                                    }
                                });
                            } else {
                                mSelectTypeViewModel.getSelectType_FeatherColor();
                            }
                        });
                break;
            case R.id.ll_their_shells_date:
                //出壳日期
                PickerUtil.showTimePicker(getActivity(), new Date().getTime(), (view1, year, monthOfYear, dayOfMonth) -> {
                    tvTheirShellsDate.setText(year + "-" + monthOfYear + "-" + dayOfMonth);

                    mBreedPigeonModifyViewModel.mBreedPigeonEntity.setOutShellTime(year + "-" + monthOfYear + "-" + dayOfMonth);
                    mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
                });
                break;
            case R.id.ll_foot_source:
                //来源
                if (!Lists.isEmpty(mBreedPigeonModifyViewModel.mSelectTypes_Source)) {
                    BottomSheetAdapter.createBottomSheet(getBaseActivity()
                            , SelectTypeEntity.getTypeNames(mBreedPigeonModifyViewModel.mSelectTypes_Source), p -> {
                                tvFootSource.setText(mBreedPigeonModifyViewModel.mSelectTypes_Source.get(p).getTypeName());

                                mBreedPigeonModifyViewModel.mBreedPigeonEntity.setSourceID(mBreedPigeonModifyViewModel.mSelectTypes_Source.get(p).getTypeID());
                                mBreedPigeonModifyViewModel.mBreedPigeonEntity.setSourceName(mBreedPigeonModifyViewModel.mSelectTypes_Source.get(p).getTypeName());
                                mBreedPigeonModifyViewModel.modifyBreedPigeonEntry();
                            });
                } else {
                    mSelectTypeViewModel.getSelectType_PigeonSource();
                }

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
                                    .build(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(PigeonAddEvent event) {
        mBookViewModel.getBloodBook();
    }
}
