package com.cpigeon.book.module.menu.smalltools.shootvideo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseInputDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 拍摄视频  设置页面
 * Created by Administrator on 2018/9/30 0030.
 */

public class ShootVideoSettingFragment extends BaseBookFragment {


    @BindView(R.id.tv_name)
    TextView tv_name;//

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, ShootVideoSettingFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoot_video_setting, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(getString(R.string.str_small_tools));

    }

    private BaseInputDialog mDialogMoney;

    @OnClick({R.id.btn_shoot_video, R.id.ll_upload_logo, R.id.ll_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_shoot_video:
                //拍摄视频
                Intent intent = new Intent(getBaseActivity(), RecordedActivity.class);
                intent.putExtra("type", "video");
                startActivity(intent);
                break;
            case R.id.ll_upload_logo:
                //上传logo

                break;
            case R.id.ll_name:
                //鸽舍名称
                mDialogMoney = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.text_pigeon_loft_name, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                            tv_name.setText(content);
                            mDialogMoney.hide();
                        }, null);

                break;
        }
    }
}
