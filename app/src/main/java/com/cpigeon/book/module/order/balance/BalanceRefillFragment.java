package com.cpigeon.book.module.order.balance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.base.base.BaseWebViewActivity;
import com.base.util.IntentBuilder;
import com.base.util.RxUtils;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.order.viewmodel.BalanceViewModel;
import com.cpigeon.book.util.TextViewUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 余额充值页面
 * Created by Administrator on 2018/8/23.
 */

public class BalanceRefillFragment extends BaseBookFragment {


    @BindView(R.id.et_input_money)
    EditText et_input_money;
    @BindView(R.id.tv_hint_money)
    TextView tv_hint_money;//
    @BindView(R.id.checkBoxb)
    CheckBox checkBoxb;

    @BindView(R.id.tv_next_step)
    TextView tv_next_step;//

    private BalanceViewModel mBalanceViewModel;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, BalanceRefillFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBalanceViewModel = new BalanceViewModel();
        initViewModels(mBalanceViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_balance_refill, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("余额充值");
        CashierInputFilter cashierInputFilter=new CashierInputFilter();
        et_input_money.setFilters(new InputFilter[]{cashierInputFilter});
        et_input_money.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_CLASS_NUMBER);
        bindUi(RxUtils.textChanges(et_input_money), mBalanceViewModel.hintMoney(getBaseActivity(), tv_next_step, tv_hint_money));

        TextViewUtil.setEnabled(tv_next_step, false);


    }


    @OnClick({R.id.ll_cb, R.id.tv_paly_agreement, R.id.tv_next_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_cb:
                //同意协议
                if (checkBoxb.isChecked()) {
                    checkBoxb.setChecked(false);
                } else {
                    checkBoxb.setChecked(true);
                }
                break;

            case R.id.tv_paly_agreement:
                //支付协议
                Intent intent1 = new Intent(getActivity(), BaseWebViewActivity.class);
                intent1.putExtra(IntentBuilder.KEY_DATA, String.valueOf(getString(R.string.baseUr_j) + getString(R.string.txgp_play_protocol)));
                intent1.putExtra(IntentBuilder.KEY_TITLE, getString(R.string.web_title_play_agreement));
                startActivity(intent1);
                break;
            case R.id.tv_next_step:
                //下一步
                ToastUtils.showLong(getActivity(), "下一步");

                if (checkBoxb.isChecked()) {

                } else {
                    ToastUtils.showLong(getActivity(), "请先阅读并同意支付协议!");
                }

                break;
        }
    }
}

