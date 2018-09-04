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
import com.base.util.RxUtils;
import com.base.util.dialog.DialogUtils;
import com.base.util.picker.PickerUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseInputDialog;
import com.cpigeon.book.model.entity.PigeonEntryEntity;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.module.play.viewmodel.PlayViewModel;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加比赛
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
    private int type = 1;//1  标准   2  附加信息


    private SelectTypeViewModel mSelectTypeViewModel;
    private PlayViewModel mPlayViewModel;
    private PigeonEntryEntity mPigeonEntryEntity;

    public static void start(Activity activity, PigeonEntryEntity mPigeonEntryEntity) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_TYPE, mPigeonEntryEntity)
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
        mPlayViewModel.isCanCommit();

        if (type == 1) {
            //修改登录密码
            setTitle("赛绩录入");
            //不需要验证码
            setToolbarRight("附加信息", item -> {
                if (isStandard) {
                    setTitle("附加信息");
                    setToolbarRight("赛绩录入");
                    isStandard = false;
                    llz.setVisibility(View.GONE);
                    rlz_input.setVisibility(View.VISIBLE);

                } else {
                    //需要验证码
                    setTitle("赛绩录入");
                    setToolbarRight("附加信息");
                    isStandard = true;
                    llz.setVisibility(View.VISIBLE);
                    rlz_input.setVisibility(View.GONE);
                }
                return true;
            });
        }

        try {
            mPigeonEntryEntity = (PigeonEntryEntity) getBaseActivity().getIntent().getSerializableExtra(IntentBuilder.KEY_TYPE);

            mPlayViewModel.pigeonid = mPigeonEntryEntity.getPigeonID();
            mPlayViewModel.footid = mPigeonEntryEntity.getFootRingID();

            llFoot.setContent(mPigeonEntryEntity.getFootRingNum());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mPlayViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(tv_next_step, aBoolean);
        });

        mPlayViewModel.addPigeonPlayData.observe(this, o -> {

            getBaseActivity().errorDialog = DialogUtils.createDialogReturn(getBaseActivity(), "您已成功录入赛绩，是否继续录入", sweetAlertDialog -> {
                //确定
                sweetAlertDialog.dismiss();
                initView();
            }, sweetAlertDialog -> {
                //取消
                sweetAlertDialog.dismiss();
                getBaseActivity().finish();
            });

        });
    }

    private void initView() {
        mPlayViewModel.playOrg = "";
        llPlayOrg.setRightText("");


        mPlayViewModel.projectName = "";
        llProjectName.setRightText("");


        mPlayViewModel.palyScale = "";
        llPalyScale.setRightText("");


        mPlayViewModel.palyRank = "";
        llPalyRank.setRightText("");


        mPlayViewModel.plyPlace = "";
        llFlyPlace.setRightText("");

        mPlayViewModel.plyUllage = "";
        llFlyUllage.setRightText("");

        llPlayTime.setContent("");
        mPlayViewModel.playTime = "";

        mPlayViewModel.isCanCommit();
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
                        , R.string.tv_play_org, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                            mPlayViewModel.playOrg = content;
                            llPlayOrg.setRightText(content);
                            mInputDialog.hide();
                            mPlayViewModel.isCanCommit();
                        }, null);

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
