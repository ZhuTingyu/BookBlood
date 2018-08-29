package com.cpigeon.book.module.foot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.base.base.BaseDialogFragment;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.RxUtils;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.picker.PickerUtil;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseInputDialog;
import com.cpigeon.book.base.SearchFragmentParentActivity;
import com.cpigeon.book.event.FootUpdateEvent;
import com.cpigeon.book.model.entity.CountyAreaEntity;
import com.cpigeon.book.model.entity.CountyEntity;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.foot.viewmodel.FootAdminSingleViewModel;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.InputBoxView;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 添加 单个足环  fragment
 * Created by Administrator on 2018/8/10.
 */

public class FootAdminSingleFragment extends BaseBookFragment {

    public static final int CODE_SELECT_COUNTY = 0x123;

    @BindView(R.id.lv_city)
    LineInputView lvCity;
    @BindView(R.id.lv_foot)
    LineInputView lvFoot;
    @BindView(R.id.lv_category)
    LineInputView lvCategory;
    @BindView(R.id.lv_source)
    LineInputView lvSource;
    @BindView(R.id.lv_money)
    LineInputView lvMoney;
    @BindView(R.id.tvOk)
    TextView tvOk;
    @BindView(R.id.llRoot)
    LineInputListLayout llRoot;
    @BindView(R.id.boxViewRemark)
    InputBoxView boxViewRemark;
    @BindView(R.id.lv_status)
    LineInputView lvStatus;
    private FootAdminSingleViewModel mViewModel;
    private SelectTypeViewModel mPublicViewModel;
    private int selectSourcePosition = 0;
    private BaseInputDialog mDialogSource;
    private BaseInputDialog mDialogMoney;


    boolean mIsLook;


    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, FootAdminSingleFragment.class);
    }

    public static void start(Activity activity, String foodId) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, foodId)
                .putExtra(IntentBuilder.KEY_BOOLEAN, true)
                .startParentActivity(activity, FootAdminSingleFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new FootAdminSingleViewModel(getBaseActivity());
        mPublicViewModel = new SelectTypeViewModel();
        initViewModels(mViewModel, mPublicViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_single_foot, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsLook = getBaseActivity().getIntent().getBooleanExtra(IntentBuilder.KEY_BOOLEAN, false);
        composite.add(RxUtils.delayed(50, aLong -> {
            llRoot.setLineInputViewState(mIsLook);
        }));

        bindUi(RxUtils.textChanges(lvFoot.getEditText()), mViewModel.setFootNumber());//足环号
        bindUi(RxUtils.textChanges(lvMoney.getEditText()), mViewModel.setMoney());//金额
        bindUi(RxUtils.textChanges(lvSource.getEditText()), mViewModel.setFootSource());//来源
        bindUi(RxUtils.textChanges(boxViewRemark.getEditText()), mViewModel.setRemark());//备注

        mPublicViewModel.setSelectType(SelectTypeViewModel.TYPE_FOOT_RING);
        mPublicViewModel.getSelectType();

        lvCity.setOnRightClickListener(v -> {
            SearchFragmentParentActivity.start(getBaseActivity(), SelectCountyFragment.class, CODE_SELECT_COUNTY);
        });

        lvFoot.setOnClickListener(v -> {
            List<String> foots = mViewModel.getFoots();
            InputSingleFootDialog dialog = new InputSingleFootDialog();
            dialog.setFoots(foots);
            dialog.setOnFootStringFinishListener(foot -> {
                lvFoot.setRightText(foot);
            });
            dialog.show(getBaseActivity().getSupportFragmentManager());
        });

        lvSource.setOnRightClickListener(lineInputView -> {
            mDialogSource = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                    , R.string.text_foot_source, 0,content -> {
                        lvSource.setRightText(content);
                    }, () -> {
                        setProgressVisible(true);
                        mPublicViewModel.getSelectType_Source();
                    });
        });

        lvMoney.setOnRightClickListener(lineInputView -> {
           mDialogMoney = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                    , R.string.text_foot_input_price, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                        lvSource.setRightText(content);
                        mDialogMoney.hide();
                    }, null);
        });

        if (mIsLook) {
            setToolbarRight(R.string.text_delete, item -> {
                DialogUtils.createDialogWithLeft(getBaseActivity()
                        , Utils.getString(R.string.text_is_sure_delete_foot_number)
                        , sweetAlertDialog -> {
                            setProgressVisible(true);
                            mViewModel.deleteFoot();
                        });
                return false;
            });
            setTitle(R.string.text_foot_details);
            tvOk.setVisibility(View.GONE);
            lvStatus.setVisibility(View.VISIBLE);
            llRoot.setOnInputViewGetFocusListener(() -> {
                tvOk.setVisibility(View.VISIBLE);
            });
            boxViewRemark.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    tvOk.setVisibility(View.VISIBLE);
                }
            });
            setProgressVisible(true);
            mViewModel.getFootById();
            tvOk.setOnClickListener(v -> {
                setProgressVisible(true);
                mViewModel.modifyFootNumber();
            });
        } else {
            setTitle(R.string.text_foot_number_input);
            lvStatus.setVisibility(View.GONE);
            tvOk.setOnClickListener(v -> {
                setProgressVisible(true);
                mViewModel.addFoot();
            });
        }
    }

    @Override
    protected void initObserve() {
        mViewModel.mFootLiveData.observe(this, footEntity -> {
            setProgressVisible(false);
            if (footEntity != null) {
                lvCity.setRightText(footEntity.getFootCodeName());
                mViewModel.countryId = String.valueOf(footEntity.getFootCodeID());
                lvFoot.setRightText(footEntity.getFootRingNum());//足环号
                lvCategory.setRightText(footEntity.getTypeName());//类别
                lvSource.setRightText(footEntity.getSourceName());//来源
                lvStatus.setRightText(footEntity.getStateName());
                mViewModel.footType = String.valueOf(footEntity.getTypeID());
                lvMoney.setRightText(Utils.getString(R.string.text_yuan, footEntity.getFootRingMoney()));//金额
            }
        });

        mViewModel.normalResult.observe(this, s -> {
            setProgressVisible(false);
            DialogUtils.createHintDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                EventBus.getDefault().post(new FootUpdateEvent());
                finish();
            });
        });

        mPublicViewModel.mSelectTypeLiveData.observe(this, selectTypeEntities -> {
            mViewModel.mSelectTypes = selectTypeEntities;
        });

        mPublicViewModel.mSelectType_Foot_Source.observe(this, selectTypeEntities -> {
            setProgressVisible(false);
            PickerUtil.showItemPicker(getBaseActivity(), SelectTypeEntity.getTypeNames(selectTypeEntities)
                    , selectSourcePosition, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            selectSourcePosition = index;
                            lvSource.setRightText(item);
                            mDialogSource.hide();
                        }
                    });
        });

        mViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(tvOk, aBoolean);
        });

        mViewModel.mdelectR.observe(this, s -> {
            setProgressVisible(false);
            DialogUtils.createSuccessDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                EventBus.getDefault().post(new FootUpdateEvent());
                finish();
            });
        });
    }

    @OnClick({R.id.lv_city, R.id.lv_foot, R.id.lv_category, R.id.lv_source, R.id.lv_money, R.id.tvOk})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lv_category:
                if (!Lists.isEmpty(mViewModel.mSelectTypes)) {
                    BottomSheetAdapter.createBottomSheet(getBaseActivity()
                            , SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes), p -> {
                                mViewModel.footType = mViewModel.mSelectTypes.get(p).getTypeID();
                                lvCategory.setContent(mViewModel.mSelectTypes.get(p).getTypeName());
                                mViewModel.isCanCommit();
                            });
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODE_SELECT_COUNTY) {
                try {
                    CountyEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                    mViewModel.countryId = entity.getSort();
                    lvCity.setRightText(entity.getCode());
                } catch (Exception e) {
                    CountyAreaEntity entity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                    mViewModel.countryId = entity.getFootCodeID();
                    lvCity.setRightText(entity.getCode());
                }
            }
        }

    }
}
