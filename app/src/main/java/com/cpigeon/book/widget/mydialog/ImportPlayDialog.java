package com.cpigeon.book.widget.mydialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cpigeon.book.R;

/**
 * Created by Administrator on 2018/8/31.
 */

public class ImportPlayDialog extends CustomAlertDialog3 {
    public ImportPlayDialog(@NonNull Context context) {
        super(context);
    }

    public ImportPlayDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public ImportPlayDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.dialog_impot_play);
    }

}
