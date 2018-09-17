package com.cpigeon.book.module.trainpigeon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.base.base.BaseMapFragment;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.LocationFormatUtils;
import com.base.util.Utils;
import com.base.util.dialog.DialogUtils;
import com.base.util.map.AmapManager;
import com.base.util.map.MapMarkerManager;
import com.base.util.system.AppManager;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.login.LoginActivity;
import com.cpigeon.book.module.pigeonhouse.InputLocationFragment;
import com.cpigeon.book.module.select.SelectLocationByMapFragment;
import com.cpigeon.book.module.trainpigeon.adpter.NewTrainPigeonListAdapter;
import com.cpigeon.book.service.SingleLoginService;
import com.cpigeon.book.widget.LineInputView;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Zhu TingYu on 2018/9/3.
 */

public class NewTrainPigeonFragment extends BaseMapFragment {

    private static final int CODE_SELECT_PIGEONS = 0x123;

    private LineInputView mLvName;
    private LineInputView mLvFlyLocation;
    private LineInputView mLvFlyPoint;
    private LineInputView mLvPigeonHousePoint;
    private MapView mMap;
    private ImageView mImgAdd;
    private RecyclerView mList;
    private NewTrainPigeonListAdapter mAdapter;
    private TextView mTvOk;
    private TextView mTvDis;
    private CardView mCardView;
    private MapMarkerManager mMapMarkerManager;

    private LatLng mPigeonHousePosition;



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

        amapManager.setZoomControlsVisible(false);
        mMapMarkerManager = new MapMarkerManager(aMap, getBaseActivity());

        mLvName = findViewById(R.id.lvName);
        mLvFlyLocation = findViewById(R.id.lvFlyLocation);
        mLvFlyPoint = findViewById(R.id.lvFlyPoint);
        mLvPigeonHousePoint = findViewById(R.id.lvPigeonHousePoint);
        mMap = findViewById(R.id.map);
        mImgAdd = findViewById(R.id.imgAdd);
        mList = findViewById(R.id.list);
        mTvOk = findViewById(R.id.tvOk);
        mTvDis = findViewById(R.id.tvDis);
        mCardView = findViewById(R.id.card);


        mPigeonHousePosition = new LatLng(Double.valueOf(UserModel.getInstance()
                .getUserData().pigeonHouseEntity.getLatitude())
                ,Double.valueOf(UserModel.getInstance().getUserData().pigeonHouseEntity.getLongitude()));


        amapManager.moveByLatLng(mPigeonHousePosition);

        mMapMarkerManager.addCustomCenterMarker(mPigeonHousePosition,"",R.mipmap.ic_blue_point);
        mMapMarkerManager.addMap();

        amapManager.setMapZoomLevel(11f);

        mLvPigeonHousePoint.setContent(getString(R.string.text_location_lo_la
                , LocationFormatUtils.strToDMS(String.valueOf(mPigeonHousePosition.longitude))
                , LocationFormatUtils.strToDMS(String.valueOf(mPigeonHousePosition.latitude))));

        mLvFlyLocation.setOnClickListener(v -> {
            InputLocationFragment inputLocationFragment = new InputLocationFragment();
            inputLocationFragment.setOnSureClickListener(new InputLocationFragment.OnInputLocationClickListener() {
                @Override
                public void sure(String lo, String la) {
                    mLvFlyPoint.setContent(getString(R.string.text_location_lo_la, LocationFormatUtils.strToDMS(lo)
                            , LocationFormatUtils.strToDMS(la)));

                    LatLng latLng = new LatLng(LocationFormatUtils.Aj2GPSLocation(Double.valueOf(lo))
                            , LocationFormatUtils.Aj2GPSLocation(Double.valueOf(la)));
                    LatLng c = AmapManager.converter(getContext(), latLng);

                }

                @Override
                public void location() {
                    SelectLocationByMapFragment.start(getBaseActivity(), SelectLocationByMapFragment.CODE_LOCATION);
                }
            });
            inputLocationFragment.show(getBaseActivity().getSupportFragmentManager());
        });

        mImgAdd.setOnClickListener(v -> {
            NewTrainAddPigeonFragment.start(getBaseActivity()
                    , (ArrayList<PigeonEntity>) mAdapter.getData(),CODE_SELECT_PIGEONS);
        });

        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        addItemDecorationLine(mList);
        mAdapter = new NewTrainPigeonListAdapter();
        mAdapter.setEmptyText(Utils.getString(R.string.text_not_choose_pigeon));
        mAdapter.setOnDeleteListener(position -> {
            mAdapter.remove(position);
        });
        mList.setAdapter(mAdapter);
        mAdapter.setNewData(Lists.newArrayList());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == SelectLocationByMapFragment.CODE_LOCATION) {

            RegeocodeAddress address = data.getParcelableExtra(IntentBuilder.KEY_DATA);
            LatLonPoint point = data.getParcelableExtra(IntentBuilder.KEY_DATA_2);

            mLvFlyPoint.setContent(getString(R.string.text_location_lo_la
                    , LocationFormatUtils.loLaToDMS(point.getLongitude())
                    , LocationFormatUtils.loLaToDMS(point.getLatitude())));

            /*mViewModel.mLongitude = String.valueOf(point.getLongitude());
            mViewModel.mLatitude = String.valueOf(point.getLatitude());

            bindAddress(address);*/

            mLvFlyLocation.setContent(data.getStringExtra(SelectLocationByMapFragment.KEY_ADDRESS_NAME));

            mMapMarkerManager.clean();

            mMapMarkerManager.addCustomCenterMarker(mPigeonHousePosition,"",R.mipmap.ic_blue_point);
            LatLng selectPoint = new LatLng(point.getLatitude(),point.getLongitude());
            mMapMarkerManager.addCustomCenterMarker(selectPoint,"",R.mipmap.ic_red_point);
            mMapMarkerManager.addMap();

            int d = (int) AMapUtils.calculateLineDistance(mPigeonHousePosition,new LatLng(point.getLatitude(),point.getLongitude())) / 1000;
            mCardView.setVisibility(View.VISIBLE);
            mTvDis.setText(Utils.getString(R.string.text_KM, String.valueOf(d)));

            addLine(Lists.newArrayList(mPigeonHousePosition, selectPoint), R.color.colorPrimary);
        }else if(requestCode == CODE_SELECT_PIGEONS){
            List<PigeonEntity> pigeonEntities = (List<PigeonEntity>) data.getSerializableExtra(IntentBuilder.KEY_DATA);
            mAdapter.setNewData(pigeonEntities);
        }
    }

    private void bindAddress(RegeocodeAddress mAddress) {
        String address = mAddress.getProvince() + mAddress.getCity() + mAddress.getDistrict();

        /*mViewModel.mProvince = mAddress.getProvince();
        mViewModel.mCity = mAddress.getCity();
        mViewModel.mCounty = mAddress.getDistrict();*/
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
