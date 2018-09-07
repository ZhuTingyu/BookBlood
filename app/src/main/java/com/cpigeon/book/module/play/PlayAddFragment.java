package com.cpigeon.book.module.play;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.RxUtils;
import com.base.util.dialog.DialogUtils;
import com.base.util.picker.PickerUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseInputDialog;
import com.cpigeon.book.model.entity.PigeonEntryEntity;
import com.cpigeon.book.model.entity.PigeonPlayEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.play.viewmodel.PlayViewModel;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 添加 修改  删除 比赛 （附加信息）
 * Created by Administrator on 2018/8/31.
 */

public class PlayAddFragment extends BaseBookFragment {

    @BindView(R.id.ll_foot)
    LineInputView llFoot;
    @BindView(R.id.ll_play_org)
    LineInputView llPlayOrg;
    @BindView(R.id.ll_project_name)
    LineInputView llProjectName;
    @BindView(R.id.ll_paly_scale)
    LineInputView llPalyScale;
    @BindView(R.id.ll_paly_rank)
    LineInputView llPalyRank;
    @BindView(R.id.ll_fly_place)
    LineInputView llFlyPlace;
    @BindView(R.id.ll_fly_ullage)
    LineInputView llFlyUllage;
    @BindView(R.id.ll_play_time)
    LineInputView llPlayTime;
    @BindView(R.id.llz)
    LineInputListLayout llz;
    @BindView(R.id.tv_next_step)
    TextView tv_next_step;


    @BindView(R.id.input_box_editText)
    EditText input_box_editText;

    @BindView(R.id.rlz_input)
    RelativeLayout rlz_input;
    private boolean isStandard = true;//是否是标准的赛绩
    private int type = 0;//0 添加赛绩，可切换标准，附加信息   1  标准 修改删除  2 附加信息修改删除


    private SelectTypeViewModel mSelectTypeViewModel;
    private PlayViewModel mPlayViewModel;
    private PigeonEntryEntity mPigeonEntryEntity;

    public static void start(Activity activity, PigeonEntryEntity mPigeonEntryEntity, int type) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, mPigeonEntryEntity)
                .putExtra(IntentBuilder.KEY_TYPE, type)//类型
                .startParentActivity(activity, PlayAddFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        mViewModel = new FootAddMultiViewModel(getBaseActivity());
        mSelectTypeViewModel = new SelectTypeViewModel();
        mPlayViewModel = new PlayViewModel();
        initViewModels(mSelectTypeViewModel, mPlayViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_play, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setTitle("");

        composite.add(RxUtils.delayed(50, aLong -> {
            llz.setLineInputViewState(false);
        }));

        type = getBaseActivity().getIntent().getIntExtra(IntentBuilder.KEY_TYPE, 0);

        try {
            mPigeonEntryEntity = (PigeonEntryEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);
            mPlayViewModel.pigeonid = mPigeonEntryEntity.getPigeonID();
            mPlayViewModel.footid = mPigeonEntryEntity.getFootRingID();
            llFoot.setContent(mPigeonEntryEntity.getFootRingNum());

            if (type == 1) {
                mPlayViewModel.matchid = mPigeonEntryEntity.getPigeonMatchID();
                setProgressVisible(true);//加载框
                mPlayViewModel.getStandardPlayDatails();//获取标准比赛的详情
            } else if (type == 2) {
                mPlayViewModel.infoid = mPigeonEntryEntity.getMatchInfoID();
                input_box_editText.setText(mPigeonEntryEntity.getMatchInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (type == 0) {
            mPlayViewModel.isAdd = true;
            mPlayViewModel.isCanCommit();
            setTitle("赛绩录入");
            setToolbarRight("附加信息", item -> {
                if (isStandard) {
                    setTitle("附加信息");
                    setToolbarRight("赛绩录入");
                    isStandard = false;
                    llz.setVisibility(View.GONE);
                    rlz_input.setVisibility(View.VISIBLE);

                    mPlayViewModel.isStandardPlay = false;
                    mPlayViewModel.isCanCommit2();
                } else {
                    //需要验证码
                    setTitle("赛绩录入");
                    setToolbarRight("附加信息");
                    isStandard = true;
                    llz.setVisibility(View.VISIBLE);
                    rlz_input.setVisibility(View.GONE);

                    mPlayViewModel.isStandardPlay = true;
                    mPlayViewModel.isCanCommit();
                }
                return true;
            });
        } else if (type == 1) {
            //修改删除标准赛绩
            mPlayViewModel.isAdd = false;
            tv_next_step.setVisibility(View.GONE);
            isStandard = true;
            llz.setVisibility(View.VISIBLE);
            rlz_input.setVisibility(View.GONE);

            mPlayViewModel.isStandardPlay = true;

            setTitle("详情");
            setToolbarRight("删除", item -> {
                mPlayViewModel.delStandardPlay();
                return true;
            });

        } else if (type == 2) {
            //修改删除附加信息
            mPlayViewModel.isAdd = false;
            tv_next_step.setVisibility(View.GONE);
            isStandard = false;
            llz.setVisibility(View.GONE);
            rlz_input.setVisibility(View.VISIBLE);

            mPlayViewModel.isStandardPlay = false;

            setTitle("详情");
            setToolbarRight("删除", item -> {
                mPlayViewModel.delAdditionalPlay();
                return true;
            });
        }

        bindUi(RxUtils.textChanges(input_box_editText), mPlayViewModel.setPlayAdditionalInfo());//附加信息


        mSelectTypeViewModel.getSelectType_PigeonPlay_Org();//赛事组织
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mPlayViewModel.isCanCommit.observe(this, aBoolean -> {

            if (aBoolean) {
                tv_next_step.setVisibility(View.VISIBLE);
            }

            TextViewUtil.setEnabled(tv_next_step, aBoolean);
        });

        mPlayViewModel.addPigeonPlayData.observe(this, o -> {
            setProgressVisible(false);//加载框
            getBaseActivity().errorDialog = DialogUtils.createDialogReturn(getBaseActivity(), "您已成功录入赛绩，是否继续录入", sweetAlertDialog -> {
                //确定
                sweetAlertDialog.dismiss();
                initView(new PigeonPlayEntity.Builder().build());
                mPlayViewModel.isCanCommit();
            }, sweetAlertDialog -> {
                //取消
                sweetAlertDialog.dismiss();
                getBaseActivity().finish();
            });
        });

        mSelectTypeViewModel.mSelectType_Play_Org.observe(this, selectTypeEntities -> {
            mPlayViewModel.mPlayOrgData = selectTypeEntities;
        });

        mPlayViewModel.getPigeonPlayDatails.observe(this, pigeonPlayEntity -> {
            setProgressVisible(false);//加载框
            initView(pigeonPlayEntity);
        });


    }

    private void initView(PigeonPlayEntity mPigeonPlayEntity) {
        //组织名称
        mPlayViewModel.playOrg = mPigeonPlayEntity.getMatchISOCName();
        llPlayOrg.setRightText(mPigeonPlayEntity.getMatchISOCName());
        //项目名称
        mPlayViewModel.projectName = mPigeonPlayEntity.getMatchName();
        llProjectName.setRightText(mPigeonPlayEntity.getMatchName());
        //比赛规模
        mPlayViewModel.palyScale = mPigeonPlayEntity.getMatchCount();
        llPalyScale.setRightText(mPigeonPlayEntity.getMatchCount());
        //比赛名次
        mPlayViewModel.palyRank = mPigeonPlayEntity.getMatchNumber();
        llPalyRank.setRightText(mPigeonPlayEntity.getMatchNumber());
        //司放地点
        mPlayViewModel.plyPlace = mPigeonPlayEntity.getMatchAdds();
        llFlyPlace.setRightText(mPigeonPlayEntity.getMatchAdds());
        //司放空距
        mPlayViewModel.plyUllage = mPigeonPlayEntity.getMatchInterval();
        llFlyUllage.setRightText(mPigeonPlayEntity.getMatchInterval());
        //比赛时间
        llPlayTime.setContent(mPigeonPlayEntity.getMatchTime());
        mPlayViewModel.playTime = mPigeonPlayEntity.getMatchTime();
        //
        mPlayViewModel.playAdditionalInfo = "";
        input_box_editText.setText("");

    }

    private BaseInputDialog mInputDialog;

    @OnClick({R.id.ll_foot, R.id.ll_play_org, R.id.ll_project_name, R.id.ll_paly_scale, R.id.ll_paly_rank, R.id.ll_fly_place,
            R.id.ll_fly_ullage, R.id.ll_play_time, R.id.llz, R.id.tv_next_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_foot:
                break;
            case R.id.ll_play_org:
                //赛事组织
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_play_org, 0, content -> {
                            mPlayViewModel.playOrg = content;
                            llPlayOrg.setRightText(content);
                            mInputDialog.hide();
                            mPlayViewModel.isCanCommit();
                        }, () -> {
                            mInputDialog.hide();
                            if (!Lists.isEmpty(mPlayViewModel.mPlayOrgData)) {
                                PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(mPlayViewModel.mPlayOrgData), 0, new OptionPicker.OnOptionPickListener() {
                                    @Override
                                    public void onOptionPicked(int index, String item) {
                                        mPlayViewModel.playOrg = mPlayViewModel.mPlayOrgData.get(index).getTypeName();
                                        llPlayOrg.setRightText(mPlayViewModel.mPlayOrgData.get(index).getTypeName());
                                        mInputDialog.hide();
                                        mPlayViewModel.isCanCommit();
                                    }
                                });
                            } else {
                                mSelectTypeViewModel.getSelectType_PigeonPlay_Org();//赛事组织
                            }

                        });

                break;
            case R.id.ll_project_name:
                //项目名称
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_project_name, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                            mPlayViewModel.projectName = content;
                            llProjectName.setRightText(content);
                            mInputDialog.hide();
                            mPlayViewModel.isCanCommit();
                        }, null);


                break;
            case R.id.ll_paly_scale:
                //比赛规模
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_paly_scale, InputType.TYPE_CLASS_NUMBER, content -> {
                            mPlayViewModel.palyScale = content;
                            llPalyScale.setRightText(content);
                            mInputDialog.hide();
                            mPlayViewModel.isCanCommit();
                        }, null);
                break;
            case R.id.ll_paly_rank:
                //比赛名次
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_paly_rank, InputType.TYPE_CLASS_NUMBER, content -> {
                            mPlayViewModel.palyRank = content;
                            llPalyRank.setRightText(content);
                            mInputDialog.hide();
                            mPlayViewModel.isCanCommit();
                        }, null);
                break;
            case R.id.ll_fly_place:
                //司放地点
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_fly_place, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                            mPlayViewModel.plyPlace = content;
                            llFlyPlace.setRightText(content);
                            mInputDialog.hide();
                            mPlayViewModel.isCanCommit();
                        }, null);

                break;
            case R.id.ll_fly_ullage:
                //司放空距
                mInputDialog = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.tv_fly_ullage, InputType.TYPE_CLASS_NUMBER, content -> {
                            mPlayViewModel.plyUllage = content;
                            llFlyUllage.setRightText(content);
                            mInputDialog.hide();
                            mPlayViewModel.isCanCommit();
                        }, null);

                break;
            case R.id.ll_play_time:
                //比赛日期
                PickerUtil.showTimePicker(getActivity(), new Date().getTime(), (view1, year, monthOfYear, dayOfMonth) -> {
                    llPlayTime.setContent(year + "-" + monthOfYear + "-" + dayOfMonth);
                    mPlayViewModel.playTime = year + "-" + monthOfYear + "-" + dayOfMonth;
                    mPlayViewModel.isCanCommit();
                });
                break;
            case R.id.llz:

                break;
            case R.id.tv_next_step:
                //点击按钮添加赛绩
                mPlayViewModel.addPigeonPlay();
                break;
        }
    }
}
