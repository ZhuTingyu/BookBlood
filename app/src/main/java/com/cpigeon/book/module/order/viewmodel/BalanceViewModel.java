package com.cpigeon.book.module.order.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.R;
import com.cpigeon.book.model.BalanceModel;
import com.cpigeon.book.model.entity.AccountBalanceListEntity;
import com.cpigeon.book.util.OrderUtil;
import com.cpigeon.book.util.TextViewUtil;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 余额
 * Created by Administrator on 2018/8/24.
 */

public class BalanceViewModel extends BaseViewModel {


    public int pi = 1;
    public int ps = 50;

    public MutableLiveData<List<AccountBalanceListEntity>> mAccountBalanceListData = new MutableLiveData<>();


    //获取  充值明细列表
    public void getAccountBalanceListData() {
        submitRequestThrowError(BalanceModel.getAccountBalanceList(pi, ps), r -> {
            if (r.isOk()) {
                listEmptyMessage.setValue(r.msg);
                mAccountBalanceListData.setValue(r.data);
            } else throw new HttpErrorException(r);
        });
    }


    public Consumer<String> hintMoney(Context mContext, TextView tv_next_step, TextView tv_hint_money) {
        return s -> {
            double fee;
            ForegroundColorSpan redSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_feedback_handled));
            try {
                fee = Double.valueOf(s);
            } catch (Exception e) {
                e.printStackTrace();
                fee = 0;
            }

            if (fee == 0) {
                TextViewUtil.setEnabled(tv_next_step, false);
            } else {
                TextViewUtil.setEnabled(tv_next_step, true);
            }

            SpannableStringBuilder builder = new SpannableStringBuilder(String.format("微信收取1%%手续费，实际支付金额%.2f元", OrderUtil.getTotalFee(fee, 0.01)));
            builder.setSpan(redSpan, 16,
                    16 + String.format("%.2f", OrderUtil.getTotalFee(fee, 0.01)).length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            tv_hint_money.setText(builder);
        };
    }


}
