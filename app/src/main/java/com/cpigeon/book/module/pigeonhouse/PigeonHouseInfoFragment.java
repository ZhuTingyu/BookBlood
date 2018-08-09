package com.cpigeon.book.module.pigeonhouse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.LocationFormatUtils;
import com.base.util.PermissionUtil;
import com.base.util.PictureSelectUtil;
import com.base.util.RxUtils;
import com.base.util.Utils;
import com.base.util.map.AmapManager;
import com.base.util.map.PointToAddressManager;
import com.base.util.picker.AddressPickTask;
import com.base.util.picker.PickerUtil;
import com.base.util.utility.StringUtil;
import com.base.util.utility.ToastUtils;
import com.base.widget.BottomSheetAdapter;
import com.bumptech.glide.Glide;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.module.MainActivity;
import com.cpigeon.book.module.home.IdCertificationFragment;
import com.cpigeon.book.module.pigeonhouse.viewmodle.PigeonHouseViewModel;
import com.cpigeon.book.module.select.SelectAssActivity;
import com.cpigeon.book.module.select.SelectLocationByMapFragment;
import com.cpigeon.book.util.TextViewUtis;
import com.cpigeon.book.widget.LineInputListLayout;
import com.cpigeon.book.widget.LineInputView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zhu TingYu on 2018/7/31.
 */

public class PigeonHouseInfoFragment extends BaseBookFragment {

    public static int CODE_ORGANIZE = 0x123;
    public static int CODE_LOCATION = 0x234;
    public static int CODE_AUTH = 0x345;

    private CircleImageView mImgHead;
    private TextView mTvName;
    private TextView mTvAuth;
    private TextView mTvOk;

    private LineInputListLayout mLlLineInput;
    private LineInputView mLvHouseName;
    private LineInputView mLvPhone;
    private LineInputView mLvOrganize;
    private LineInputView mLvShedId;
    private LineInputView mLvJoinMatchId;
    private LineInputView mLvHouseLocation;
    private LineInputView mLvCity;
    private LineInputView mLvAddress;
    InputLocationFragment inputLocationFragment;

    private boolean mIsLook;
    private String mHeadImagePath;

    PigeonHouseViewModel mViewModel;
    PointToAddressManager mPointToAddressManager;

    public static void start(Activity activity, boolean isLook) {
        IntentBuilder.Builder()
                .putExtra(IntentBuilder.KEY_BOOLEAN, isLook)
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
//        mPointToAddressManager = new PointToAddressManager(context)
//                .setSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
//                    @Override
//                    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
//                        setProgressVisible(false);
//                        if(regeocodeResult != null){
//                            bindAddress(regeocodeResult.getRegeocodeAddress());
//                        }
//                    }
//
//                    @Override
//                    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
//                        setProgressVisible(false);
//                    }
//                });

        mIsLook = getBaseActivity().getIntent().getBooleanExtra(IntentBuilder.KEY_BOOLEAN, false);

        PermissionUtil.getAppDetailSettingIntent(getBaseActivity());

        mViewModel.oneStartGetGeBi();//第一次登录

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
        mTvOk = findViewById(R.id.tvOk);

        mTvAuth.setOnClickListener(v -> {
            MainActivity.start(getBaseActivity());
        });

        bindUi(RxUtils.textChanges(mLvHouseName.getEditText()),mViewModel.setPigeonHomeName());
        bindUi(RxUtils.textChanges(mLvPhone.getEditText()),mViewModel.setPigeonHomePhone());
        bindUi(RxUtils.textChanges(mLvOrganize.getEditText()),mViewModel.setPigeonISOCID());
        bindUi(RxUtils.textChanges(mLvShedId.getEditText()),mViewModel.setUsePigeonHomeNum());
        bindUi(RxUtils.textChanges(mLvJoinMatchId.getEditText()),mViewModel.setPigeonMatchNum());
        bindUi(RxUtils.textChanges(mLvAddress.getEditText()),mViewModel.setPigeonHomeAdds());

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
            inputLocationFragment = new InputLocationFragment();
            inputLocationFragment.setOnSureClickListener(new InputLocationFragment.OnInputLocationClickListener() {
                @Override
                public void sure(String lo, String la) {

                    mLvHouseLocation.setContent(getString(R.string.text_location_lo_la, LocationFormatUtils.strToDMS(lo)
                            , LocationFormatUtils.strToDMS(la)));

                    LatLng latLng = new LatLng(LocationFormatUtils.Aj2GPSLocation(Double.valueOf(lo))
                            , LocationFormatUtils.Aj2GPSLocation(Double.valueOf(la)));
                    LatLng c = AmapManager.converter(getContext(), latLng);

                    mViewModel.mLongitude = String.valueOf(c.longitude);
                    mViewModel.mLatitude = String.valueOf(c.latitude);

//                    mPointToAddressManager.setSearchPoint(new LatLonPoint(Double.valueOf(mViewModel.mLatitude)
//                            , Double.valueOf(mViewModel.mLongitude))).search();

                }

                @Override
                public void location() {
                    SelectLocationByMapFragment.start(getBaseActivity(), CODE_LOCATION);
                }
            });
            inputLocationFragment.show(getBaseActivity().getSupportFragmentManager());
        });

        mLvCity.setOnRightClickListener(lineInputView -> {
            PickerUtil.onAddress3Picker(getBaseActivity(), new AddressPickTask.Callback() {
                @Override
                public void onAddressInitFailed() {

                }

                @Override
                public void onAddressPicked(Province province, City city, County county) {
                    mLvCity.setContent(province.getName() + city.getName());
                }
            });
        });

        if(!mIsLook){
            mTvOk.setVisibility(View.VISIBLE);
            mTvOk.setOnClickListener(v -> {
                mViewModel.addPigeonHouse();
            });
        }else {
            mLlLineInput.setOnInputViewGetFocusListener(() -> {
                mTvOk.setVisibility(View.VISIBLE);
            });
            mViewModel.getPigeonHouse();
            mTvOk.setText(Utils.getString(R.string.text_sure_commit));
            mTvOk.setOnClickListener(v -> {
                mViewModel.modifyPigeonHouse();
            });

        }


    }

    protected void initObserve() {

        mViewModel.isCanCommit.observe(this, aBoolean -> {
            TextViewUtis.setEnabled(mTvOk, aBoolean);
        });

        UserModel.getInstance().mUserLiveData.observe(this, userEntity -> {
            Glide.with(getBaseActivity()).load(userEntity.touxiangurl).into(mImgHead);
        });

        mViewModel.oneStartHintStr.observe(this, r -> {
            ToastUtils.showLong(getActivity(), r);
        });

        mViewModel.setHeadUrlR.observe(this, s -> {

        });

        mViewModel.addR.observe(this, s -> {
            UserModel.getInstance().setIsHaveHouseInfo(true);
            ToastUtils.showLong(getBaseActivity(), s);
            MainActivity.start(getBaseActivity());
        });

        mViewModel.modifyR.observe(this, s -> {

        });

        mViewModel.mHouseEntityInfo.observe(this, r -> {
            if(r == null){
                return;
            }
            mLlLineInput.getChildViews();
            mLlLineInput.setLineInputViewState(mIsLook);
            Glide.with(getBaseActivity()).load(r.getTouxiangurl()).into(mImgHead);
            mTvName.setText(r.getXingming());
            mLvPhone.setContent(r.getPigeonHomePhone());
            mLvHouseName.setContent(r.getPigeonHomeName());
            mLvOrganize.setContent(r.getPigeonISOCID());
            mLvShedId.setContent(r.getUsePigeonHomeNum());
            mLvJoinMatchId.setContent(r.getPigeonMatchNum());
            mLvHouseLocation.setContent(getString(R.string.text_location_lo_la
                    ,LocationFormatUtils.GPS2AjLocation(r.getLongitude())
                    ,LocationFormatUtils.GPS2AjLocation(r.getLatitude())));

            mViewModel.mLongitude = String.valueOf(r.getLongitude());
            mViewModel.mLatitude = String.valueOf(r.getLatitude());

            mLvCity.setContent(r.getProvince());
            mLvCity.setContent(r.getPigeonHomeAdds());

            if(StringUtil.isStringValid(r.getXingming())){
                mTvAuth.setText(getString(R.string.text_yet_auth));
                mTvAuth.setOnClickListener(v -> {
                    IdCertificationFragment.start(getBaseActivity(), false);
                });
            }else {
                mTvAuth.setOnClickListener(v -> {
                    IdCertificationFragment.start(getBaseActivity(), true);
                });
            }

        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == PictureMimeType.ofImage()) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            mViewModel.mHeadUrl = selectList.get(0).getCutPath();
            mViewModel.setUserFace();
        } else if(requestCode == CODE_ORGANIZE){
            String organize = data.getStringExtra(IntentBuilder.KEY_DATA);
            mViewModel.mPigeonISOCID = organize;
            mLvOrganize.setContent(organize);
        } else if(requestCode == CODE_LOCATION){

            RegeocodeAddress address = data.getParcelableExtra(IntentBuilder.KEY_DATA);
            LatLonPoint point = data.getParcelableExtra(IntentBuilder.KEY_DATA_2);

            mLvHouseLocation.setContent(getString(R.string.text_location_lo_la
                    , LocationFormatUtils.loLaToDMS(point.getLongitude())
                    , LocationFormatUtils.loLaToDMS(point.getLatitude())));

            mViewModel.mLongitude = String.valueOf(point.getLongitude());
            mViewModel.mLatitude = String.valueOf(point.getLatitude());

            bindAddress(address);
        }else if(requestCode == CODE_AUTH){
            mViewModel.getPigeonHouse();
        }

    }

    private void bindAddress(RegeocodeAddress mAddress){
        String address = mAddress.getProvince() + mAddress.getCity() + mAddress.getDistrict();
        mLvCity.setContent(address);
        mViewModel.mProvince = mAddress.getProvince();
        mViewModel.mCity = mAddress.getCity();
        mViewModel.mCounty = mAddress.getDistrict();
    }
}
