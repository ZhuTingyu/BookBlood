package com.cpigeon.book.module.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;

/**
 * hl 鸽舍 身份认证
 * <p>
 * Created by Administrator on 2018/8/6.
 */

public class IdCertificationFragment extends BaseBookFragment {

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, IdCertificationFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_id_certification, container, false);
    }


}
