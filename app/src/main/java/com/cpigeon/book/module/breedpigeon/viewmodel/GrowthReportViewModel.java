package com.cpigeon.book.module.breedpigeon.viewmodel;

import android.app.Activity;

import com.base.base.BaseViewModel;
import com.base.util.IntentBuilder;

/**
 * Created by Zhu TingYu on 2018/8/31.
 */

public class GrowthReportViewModel extends BaseViewModel {
    public String footNumber;
    public GrowthReportViewModel(Activity activity){
        footNumber = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA);
    }
}
