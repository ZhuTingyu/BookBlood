package com.cpigeon.book.module.menu.smalltools.shootvideo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.PictureSelectUtil;
import com.base.util.Utils;
import com.base.util.utility.StringUtil;
import com.base.widget.BottomSheetAdapter;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.base.BaseInputDialog;
import com.cpigeon.book.module.menu.smalltools.shootvideo.viewmodel.ShootViewModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 拍摄视频  设置页面
 * Created by Administrator on 2018/9/30 0030.
 */

public class ShootVideoSettingFragment extends BaseBookFragment {


    @BindView(R.id.tv_name)
    TextView tv_name;//
    @BindView(R.id.img_logo)
    ImageView img_logo;//
    @BindView(R.id.ll_upload_logo)
    LinearLayout ll_upload_logo;//

    private ShootViewModel mShootViewModel;
    private String[] chooseWays;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, ShootVideoSettingFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mShootViewModel = new ShootViewModel();
        initViewModels(mShootViewModel);
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

        chooseWays = getResources().getStringArray(R.array.array_choose_photo);
        setProgressVisible(true);
        mShootViewModel.getTXGP_GetTouXiangGeSheMingChengData();
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        mShootViewModel.mShootInfo.observe(this, datas -> {
            setProgressVisible(false);
            mShootViewModel.mShootInfoEntity = datas;

            if (StringUtil.isStringValid(mShootViewModel.mShootInfoEntity.getImgurl())) {
                ll_upload_logo.setVisibility(View.GONE);
                img_logo.setVisibility(View.VISIBLE);
                Glide.with(getBaseActivity()).load(mShootViewModel.mShootInfoEntity.getImgurl()).into(img_logo);
            } else {
                img_logo.setVisibility(View.GONE);
                ll_upload_logo.setVisibility(View.VISIBLE);
            }

            tv_name.setText(mShootViewModel.mShootInfoEntity.getGsname());
        });
    }

    private BaseInputDialog mDialogMoney;

    @OnClick({R.id.btn_shoot_video, R.id.rl_upload_logo, R.id.ll_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_shoot_video:
                //拍摄视频
                Intent intent = new Intent(getBaseActivity(), RecordedActivity.class);
                intent.putExtra(IntentBuilder.KEY_DATA, mShootViewModel.mShootInfoEntity);
                intent.putExtra("type", "video");
                startActivity(intent);
                break;
            case R.id.rl_upload_logo:
                //上传logo
                BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                    String way = chooseWays[p];
                    if (Utils.getString(R.string.text_open_gallery).equals(way)) {
                        PictureSelectUtil.showChooseHeadImage(getBaseActivity());
                    } else if (Utils.getString(R.string.text_open_camera).equals(way)) {
                        PictureSelectUtil.openCamera(getBaseActivity(), true);
                    }
                });

                break;
            case R.id.ll_name:
                //鸽舍名称
                mDialogMoney = BaseInputDialog.show(getBaseActivity().getSupportFragmentManager()
                        , R.string.text_pigeon_loft_name, InputType.TYPE_NUMBER_FLAG_DECIMAL, content -> {
                            tv_name.setText(content);
                            mDialogMoney.hide();
                            mShootViewModel.mShootInfoEntity.setGsname(content);
                            setProgressVisible(true);
                            mShootViewModel.getTXGP_SetTouXiangGeSheMingChengData();
                        }, null);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == PictureMimeType.ofImage()) {

            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            mShootViewModel.mShootInfoEntity.setImgurl(selectList.get(0).getCutPath());
            setProgressVisible(true);
            mShootViewModel.getTXGP_SetTouXiangGeSheMingChengData();
        }
    }
}
