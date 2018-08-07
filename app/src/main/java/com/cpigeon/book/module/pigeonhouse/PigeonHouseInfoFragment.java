package com.cpigeon.book.module.pigeonhouse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.LocationFormatUtils;
import com.base.util.PermissionUtil;
import com.base.util.PictureSelectUtil;
import com.base.util.RxUtils;
import com.base.util.Utils;
import com.base.util.utility.ImageUtils;
import com.base.util.utility.ToastUtils;
import com.base.widget.BottomSheetAdapter;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.MainActivity;
import com.cpigeon.book.module.login.viewmodel.LoginViewModel;
import com.cpigeon.book.module.pigeonhouse.viewmodle.PigeonHouseViewModel;
import com.cpigeon.book.module.select.SelectAssActivity;
import com.cpigeon.book.module.select.SelectLocationByMapFragment;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zhu TingYu on 2018/7/31.
 */

public class PigeonHouseInfoFragment extends BaseBookFragment {

    public static int CODE_ORGANIZE = 0x123;

    private CircleImageView mImgHead;
    private TextView mTvName;
    private TextView mTvAuth;

    private LineInputListLayout mLlLineInput;
    private LineInputView mLvHouseName;
    private LineInputView mLvPhone;
    private LineInputView mLvOrganize;
    private LineInputView mLvShedId;
    private LineInputView mLvJoinMatchId;
    private LineInputView mLvHouseLocation;
    private LineInputView mLvCity;
    private LineInputView mLvAddress;

    private boolean isLook = false;
    private String mHeadImagePath;

    PigeonHouseViewModel mViewModel;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, PigeonHouseInfoFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //第一次登录  获取鸽币
        mViewModel = new PigeonHouseViewModel();
        initViewModel(mViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pigon_house_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PermissionUtil.getAppDetailSettingIntent(getBaseActivity());

        mViewModel.oneStartHintStr.observe(this, r -> {
            ToastUtils.showLong(getActivity(), r);
        });

        mViewModel.oneStartGetGeBi();//第一次登录
        mViewModel.oneStartHintStr.observe(this, r -> {
            ToastUtils.showLong(getActivity(), r);
        });

        mImgHead = findViewById(R.id.imgHead);
        mTvName = findViewById(R.id.tvName);
        mTvAuth = findViewById(R.id.tvAuth);


        mLlLineInput = findViewById(R.id.llLineInput);
        mLvHouseName = findViewById(R.id.lvHouseName);
        mLvPhone = findViewById(R.id.lvPhone);
        mLvOrganize = findViewById(R.id.lvOrganize);
        mLvShedId = findViewById(R.id.lvShedId);
        mLvJoinMatchId = findViewById(R.id.lvJoinMatchId);
        mLvHouseLocation = findViewById(R.id.lvHouseLocation);
        mLvCity = findViewById(R.id.lvCity);
        mLvAddress = findViewById(R.id.lvAddress);

        mTvAuth.setOnClickListener(v -> {
            MainActivity.start(getBaseActivity());
        });

        bindUi(RxUtils.textChanges(mLvHouseName.getEditText()),mViewModel.setPigeonHomeName());
        bindUi(RxUtils.textChanges(mLvPhone.getEditText()),mViewModel.setPigeonHomePhone());
        bindUi(RxUtils.textChanges(mLvOrganize.getEditText()),mViewModel.setPigeonISOCID());
        bindUi(RxUtils.textChanges(mLvShedId.getEditText()),mViewModel.setUsePigeonHomeNum());
        bindUi(RxUtils.textChanges(mLvJoinMatchId.getEditText()),mViewModel.setPigeonMatchNum());
        bindUi(RxUtils.textChanges(mLvAddress.getEditText()),mViewModel.setPigeonHomeName());

        composite.add(RxUtils.delayed(50, aLong -> {
            mLlLineInput.getChildViews();
            mLlLineInput.setLineInputViewState(isLook);
        }));

        mLvOrganize.setOnRightClickListener(lineInputView -> {
            IntentBuilder.Builder(getActivity(), SelectAssActivity.class)
                    .startActivity(CODE_ORGANIZE);
        });


        String[] chooseWays = getResources().getStringArray(R.array.array_choose_photo);
        mImgHead.setOnClickListener(v -> {
            BottomSheetAdapter.createBottomSheet(getBaseActivity(), Lists.newArrayList(chooseWays), p -> {
                String way = chooseWays[p];
                if (Utils.getString(R.string.text_open_gallery).equals(way)) {
                    PictureSelectUtil.showChooseHeadImage(getBaseActivity());
                } else if (Utils.getString(R.string.text_open_camera).equals(way)) {
                    PictureSelectUtil.openCamera(getBaseActivity());
                }
            });
        });

        mLvHouseLocation.setOnRightClickListener(lineInputView -> {
            InputLocationFragment inputLocationFragment = new InputLocationFragment();
            inputLocationFragment.setOnSureClickListener(new InputLocationFragment.OnInputLocationClickListener() {
                @Override
                public void sure(String lo, String la) {
                    mLvHouseLocation.setContent(getString(R.string.text_comma_divide, lo, la));
                    mViewModel.mLongitude = LocationFormatUtils.Aj2GPSLocationString(Double.valueOf(lo));
                    mViewModel.mLatitude = LocationFormatUtils.Aj2GPSLocationString(Double.valueOf(la));
                }

                @Override
                public void location() {
                    SelectLocationByMapFragment.start(getBaseActivity());
                }
            });
            inputLocationFragment.show(getBaseActivity().getSupportFragmentManager());
        });

        mTvAuth.setOnClickListener(v -> {
//            //修改密码
//            mLoginViewModel.useroneModifyPsd();
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == PictureMimeType.ofImage()) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            mHeadImagePath = selectList.get(0).getCutPath();
            Bitmap bitmap = ImageUtils.getBitmap(mHeadImagePath);
            mImgHead.setImageBitmap(bitmap);
        }

        if(requestCode == CODE_ORGANIZE){
            String organize = data.getStringExtra(IntentBuilder.KEY_DATA);
            mViewModel.mPigeonISOCID = organize;
            mLvOrganize.setContent(organize);
        }
    }
}
