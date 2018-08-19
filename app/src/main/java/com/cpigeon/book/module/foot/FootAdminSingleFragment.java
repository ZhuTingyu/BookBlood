package com.cpigeon.book.module.foot;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.RxUtils;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.utility.ToastUtils;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.event.FootUpdateEevnt;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.foot.viewmodel.FootAdminViewModel;
import com.cpigeon.book.module.foot.viewmodel.PigeonPublicViewModel;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.InputBoxView;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 添加 单个足环  fragment
 * Created by Administrator on 2018/8/10.
 */

public class FootAdminSingleFragment extends BaseBookFragment {

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
    private FootAdminViewModel mViewModel;
    private PigeonPublicViewModel mPublicViewModel;

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
        mViewModel = new FootAdminViewModel(getBaseActivity());
        mPublicViewModel = new PigeonPublicViewModel();
        initViewModels(mViewModel, mPublicViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_single_foot, container, false);
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

        mPublicViewModel.setSelectType(PigeonPublicViewModel.TYPE_FOOT_NUMBER);
        mPublicViewModel.getSelectType();

        if (mIsLook) {
            setToolbarRight(R.string.text_delect, item -> {
                DialogUtils.createHintDialog(getBaseActivity()
                        , Utils.getString(R.string.text_is_sure_delect_foot_number)
                        , sweetAlertDialog -> {
                            mViewModel.delectFoot();
                        });
                return false;
            });
            setTitle(R.string.text_foot_details);
            tvOk.setVisibility(View.GONE);
            lvStatus.setVisibility(View.VISIBLE);
            llRoot.setOnInputViewGetFocusListener(() -> {
                tvOk.setVisibility(View.VISIBLE);
            });
            mViewModel.getFootById();
            tvOk.setOnClickListener(v -> {
                mViewModel.modifyFootNumber();
            });
        } else {
            setTitle(R.string.text_foot_number_input);
            lvStatus.setVisibility(View.GONE);
            tvOk.setOnClickListener(v -> {
                mViewModel.addFoot();
            });
        }
    }

    @Override
    protected void initObserve() {
        mViewModel.mFootLiveData.observe(this, footEntity -> {
            if (footEntity != null) {
                lvFoot.setRightText(footEntity.getFootRingNum());//足环号
                lvCategory.setRightText(footEntity.getTypeName());//类别
                lvSource.setRightText(footEntity.getSourceName());//来源
                lvStatus.setRightText(footEntity.getStateName());
                mViewModel.footType = String.valueOf(footEntity.getTypeID());
                lvMoney.setRightText(Utils.getString(R.string.text_yuan, footEntity.getFootRingMoney()));//金额
            }
        });

        mViewModel.normalResult.observe(this, s -> {
            DialogUtils.createHintDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                EventBus.getDefault().post(new FootUpdateEevnt());
                finish();
            });
        });

        mPublicViewModel.mSelectTypeLiveData.observe(this, selectTypeEntities -> {
            mViewModel.mSelectTypes = selectTypeEntities;
        });

        mViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(tvOk, aBoolean);
        });

        mViewModel.mdelectR.observe(this, s -> {
            DialogUtils.createSuccessDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                EventBus.getDefault().post(new FootUpdateEevnt());
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
}
