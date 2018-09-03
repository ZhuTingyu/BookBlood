package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.base.base.BaseMapFragment;
import com.base.util.IntentBuilder;
import com.base.util.dialog.DialogUtils;
import com.base.util.system.AppManager;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.login.LoginActivity;
import com.cpigeon.book.service.SingleLoginService;
import com.cpigeon.book.widget.LineInputView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Zhu TingYu on 2018/9/3.
 */

public class NewTrainPigeonFragment extends BaseMapFragment {
    private LineInputView mLvName;
    private LineInputView mLvFlyLocation;
    private LineInputView mLvFlyPoint;
    private LineInputView mLvPigeonHousePoint;
    private MapView mMap;
    private ImageView mImgAdd;
    private RecyclerView mList;
    private TextView mTvOk;


    public static void start(Activity activity) {
        IntentBuilder.Builder().startParentActivity(activity, NewTrainPigeonFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_train_pigeon, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.text_new_train_pigeon);

    }

    @Override
    public void error(int code, String error) {

        if (code == 90102) {

            if (!StringUtil.isStringValid(error)) {
                return;
            }

            //保证界面只有一个错误提示
            if (getBaseActivity().errorDialog == null || !getBaseActivity().errorDialog.isShowing()) {
                getBaseActivity().errorDialog = DialogUtils.createHintDialog(getActivity(), error, SweetAlertDialog.ERROR_TYPE, false, dialog -> {
                    SingleLoginService.stopService();
                    UserModel.getInstance().cleanUserInfo();
                    dialog.dismiss();
                    AppManager.getAppManager().killAllActivity();
                    LoginActivity.start(getBaseActivity());
                });
            }

        } else {
            super.error(code, error);
        }
    }
}
