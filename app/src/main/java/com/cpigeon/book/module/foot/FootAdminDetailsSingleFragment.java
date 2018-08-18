package com.cpigeon.book.module.foot;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.RxUtils;
import com.base.util.dialog.DialogUtils;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.FootAdminListEntity;
import com.cpigeon.book.module.foot.viewmodel.FootAdminViewModel;
import com.cpigeon.book.widget.InputBoxView;
import com.cpigeon.book.widget.LineInputView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 详情 单个足环  fragment
 * Created by Administrator on 2018/8/10.
 */

public class FootAdminDetailsSingleFragment extends BaseBookFragment {


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

    @BindView(R.id.et_remark)
    InputBoxView etRemark;//备注

    private FootAdminViewModel mFootAdminModel;


    private FootAdminListEntity mFootAdminListEntity;


    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, FootAdminDetailsSingleFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_single_foot, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("足环号详情");

        setToolbarRight("删除", item -> {

            getBaseActivity().errorDialog = DialogUtils.createDialogWithLeft2(getActivity(), "确定删除该条足环数据！", false, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
            }, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();

                mFootAdminModel.getTXGP_FootRing_DeleteData(String.valueOf(mFootAdminListEntity.getFootRingID()));
            });

            return true;
        });

        mFootAdminModel = new FootAdminViewModel();
        initViewModels(mFootAdminModel);

        bindUi(RxUtils.textChanges(lvFoot.getEditText()), mFootAdminModel.editFootName_single());//足环号
        bindUi(RxUtils.textChanges(lvMoney.getEditText()), mFootAdminModel.editFootMoney_single());//金额
        bindUi(RxUtils.textChanges(lvSource.getEditText()), mFootAdminModel.footSourceEdit_single());//来源
        bindUi(RxUtils.textChanges(etRemark.getEditText()), mFootAdminModel.remarkEdit_single());//备注

        mFootAdminListEntity = getActivity().getIntent().getParcelableExtra(IntentBuilder.KEY_DATA);

        initViewData(mFootAdminListEntity);
    }

    //初始化视图数据
    private void initViewData(FootAdminListEntity mFootAdminListEntity) {

        lvCity.setRightText("");//国家
        lvFoot.setRightText(mFootAdminListEntity.getFootRingNum());//足环号
        lvCategory.setRightText(mFootAdminListEntity.getTypeName());//类别
        lvSource.setRightText(mFootAdminListEntity.getSourceName());//来源
        lvMoney.setRightText(mFootAdminListEntity.getFootRingMoney() + "元");//金额


        mFootAdminModel.footDetailsData.observe(getActivity(), data -> {

            lvCity.setRightText("");//国家
            lvFoot.setRightText(mFootAdminListEntity.getFootRingNum());//足环号
            lvCategory.setRightText(mFootAdminListEntity.getTypeName());//类别
            lvSource.setRightText(mFootAdminListEntity.getSourceName());//来源
            lvMoney.setRightText(mFootAdminListEntity.getFootRingMoney() + "元");//金额

        });

        //获取单个足环详细
        mFootAdminModel.getTXGP_FootRing_SelectData(String.valueOf(mFootAdminListEntity.getFootRingID()));

    }

    @OnClick({R.id.lv_city, R.id.lv_foot, R.id.lv_category, R.id.lv_source, R.id.lv_money, R.id.tvOk})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lv_city:
                ToastUtils.showLong(getActivity(), "1");
                break;
            case R.id.lv_foot:
                ToastUtils.showLong(getActivity(), "2");
                break;
            case R.id.lv_category:
                ToastUtils.showLong(getActivity(), "3");
                break;
            case R.id.lv_source:
                ToastUtils.showLong(getActivity(), "4");
                break;
            case R.id.lv_money:
                ToastUtils.showLong(getActivity(), "5");
                break;
            case R.id.tvOk:
                mFootAdminModel.getTXGP_FootRing_EditData(String.valueOf(mFootAdminListEntity.getFootRingID()));
                break;
        }
    }
}
