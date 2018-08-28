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
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.event.FootUpdateEvent;
import com.cpigeon.book.model.entity.SelectTypeEntity;
import com.cpigeon.book.module.foot.viewmodel.FootAddMultiViewModel;
import com.cpigeon.book.module.foot.viewmodel.FootDetailsMultiViewModel;
import com.cpigeon.book.module.foot.viewmodel.SelectTypeViewModel;
import com.cpigeon.book.util.TextViewUtil;
import com.cpigeon.book.widget.InputBoxView;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;

import org.greenrobot.eventbus.EventBus;


/**
 * 详情 多个足环  fragment
 * Created by Administrator on 2018/8/10.
 */

public class FootAdminDetailsMultipleFragment extends BaseBookFragment {

    FootDetailsMultiViewModel mViewModel;
    SelectTypeViewModel mPublicViewModel;

    private LineInputListLayout mLlRoot;
    private LineInputView mLvCity;
    private LineInputView mLvFoot;
    private LineInputView mLvCategory;
    private LineInputView mLvStatus;
    private LineInputView mLvSource;
    private LineInputView mLvMoney;
    private InputBoxView mBoxViewRemark;
    private TextView mTvOk;


    public static void start(Activity activity, String id) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_DATA, id)
                .startParentActivity(activity, FootAdminDetailsMultipleFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new FootDetailsMultiViewModel(getBaseActivity());
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
        setTitle(R.string.text_foots_details);
        setToolbarRight(R.string.text_delete, item -> {
            DialogUtils.createDialogWithLeft(getBaseActivity(), Utils.getString(R.string.text_is_delete_foot_numbers)
                    ,sweetAlertDialog -> {
                        setProgressVisible(true);
                        sweetAlertDialog.dismiss();
                        mViewModel.deleteMultiFoot();
                    });
            return false;
        });

        mLlRoot = findViewById(R.id.llRoot);
        mLvCity = findViewById(R.id.lv_city);
        mLvFoot = findViewById(R.id.lv_foot);
        mLvCategory = findViewById(R.id.lv_category);
        mLvStatus = findViewById(R.id.lv_status);
        mLvSource = findViewById(R.id.lv_source);
        mLvMoney = findViewById(R.id.lv_money);
        mBoxViewRemark = findViewById(R.id.boxViewRemark);
        mTvOk = findViewById(R.id.tvOk);

        mLvCity.setRightImageVisible(false);
        mLvMoney.setTitle(R.string.text_all_money);
        mTvOk.setVisibility(View.GONE);

        composite.add(RxUtils.delayed(50, aLong -> {
            mLlRoot.setLineInputViewState(true);
        }));

        bindUi(RxUtils.textChanges(mLvSource.getEditText()), mViewModel.setSource());
        bindUi(RxUtils.textChanges(mLvMoney.getEditText()), mViewModel.setMoney());
        bindUi(RxUtils.textChanges(mBoxViewRemark.getEditText()), mViewModel.setRemark());

        mLvFoot.setOnRightClickListener(lineInputView -> {
            FootsInfoDialog dialog = new FootsInfoDialog();
            dialog.setSFoots(mViewModel.sFootNumber);
            dialog.setEFoots(mViewModel.eFootNumber);
            dialog.setOnFootStringFinishListener(new FootsInfoDialog.OnFootStringFinishListener() {
                @Override
                public void sFoot(String foot) {
                    mViewModel.sFootNumber = foot;
                }

                @Override
                public void eFoot(String foot) {
                    mViewModel.eFootNumber = foot;
                    mLvFoot.setRightText(Utils.getString(R.string.text_foots
                            ,mViewModel.sFootNumber,mViewModel.eFootNumber));//足环号
                }
            });
            dialog.show(getBaseActivity().getSupportFragmentManager());
        });

        mLvCategory.setOnClickListener(v -> {
            if (!Lists.isEmpty(mViewModel.mSelectTypes)) {
                BottomSheetAdapter.createBottomSheet(getBaseActivity()
                        , SelectTypeEntity.getTypeNames(mViewModel.mSelectTypes), p -> {
                            mViewModel.typeId = mViewModel.mSelectTypes.get(p).getTypeID();
                            mLvCategory.setContent(mViewModel.mSelectTypes.get(p).getTypeName());
                            mViewModel.isCanCommit();
                        });
            }
        });

        mTvOk.setOnClickListener(v -> {
            setProgressVisible(true);
            mViewModel.modifyFoots();
        });

        setProgressVisible(true);
        mPublicViewModel.setSelectType(SelectTypeViewModel.TYPE_FOOT_RING);
        mPublicViewModel.getSelectType();
        mViewModel.getFootInfo();

        mLlRoot.setOnInputViewGetFocusListener(() -> {
            mTvOk.setVisibility(View.VISIBLE);
        });

        mBoxViewRemark.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                mTvOk.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    protected void initObserve() {

        mViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtil.setEnabled(mTvOk, aBoolean);
        });

        mPublicViewModel.mSelectTypeLiveData.observe(this, selectTypeEntities -> {
            mViewModel.mSelectTypes = selectTypeEntities;
        });

        mViewModel.mFootEntityLiveData.observe(this, footEntity -> {
            setProgressVisible(false);
            if (footEntity != null) {
                mLvFoot.setRightText(Utils.getString(R.string.text_foots
                        ,footEntity.getFootRingNum(),footEntity.getEndFootRingNum()));//足环号
                mLvCategory.setRightText(footEntity.getTypeName());//类别
                mLvSource.setRightText(footEntity.getSourceName());//来源
                mViewModel.typeId = String.valueOf(footEntity.getTypeID());
                mLvMoney.setRightText(Utils.getString(R.string.text_yuan, footEntity.getFootRingMoney()));//金额
            }
        });

        mViewModel.deleteR.observe(this, s -> {
            setProgressVisible(false);
            DialogUtils.createSuccessDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                EventBus.getDefault().post(new FootUpdateEvent());
                finish();
            });
        });

    }

}
