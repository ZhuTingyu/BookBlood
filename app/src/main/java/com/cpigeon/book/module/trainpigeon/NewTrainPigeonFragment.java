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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.base.base.BaseMapFragment;
import com.base.util.FragmentUtils;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.LocationFormatUtils;
import com.base.util.Utils;
import com.base.util.db.AppDatabase;
import com.base.util.dialog.DialogUtils;
import com.base.util.map.MapMarkerManager;
import com.base.util.system.AppManager;
import com.base.util.utility.StringUtil;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.event.UpdateTrainEvent;
import com.cpigeon.book.model.UserModel;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.cpigeon.book.module.login.LoginActivity;
import com.cpigeon.book.module.pigeonhouse.InputLocationFragment;
import com.cpigeon.book.module.select.SelectLocationByMapFragment;
import com.cpigeon.book.module.trainpigeon.adpter.NewTrainPigeonListAdapter;
import com.cpigeon.book.module.trainpigeon.viewmodel.NewTrainPigeonViewModel;
import com.cpigeon.book.service.SingleLoginService;
import com.cpigeon.book.widget.LineInputView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    FrameLayout mFrameLayout;


    private LatLng mPigeonHousePosition;
    NewTrainPigeonViewModel mViewModel;
    private LineWeatherFragment mLineWeatherFragment;


    public static void start(Activity activity) {
        IntentBuilder.Builder().startParentActivity(activity, NewTrainPigeonFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = new NewTrainPigeonViewModel();
        initViewModel(mViewModel);
        EventBus.getDefault().register(this);
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
        mFrameLayout = findViewById(R.id.frame);


        mPigeonHousePosition = new LatLng(Double.valueOf(UserModel.getInstance()
                .getUserData().pigeonHouseEntity.getLatitude())
                , Double.valueOf(UserModel.getInstance().getUserData().pigeonHouseEntity.getLongitude()));

        mViewModel.fromLa = mPigeonHousePosition.latitude;
        mViewModel.fromLo = mPigeonHousePosition.longitude;


        amapManager.moveByLatLng(mPigeonHousePosition);

        mMapMarkerManager.addCustomCenterMarker(mPigeonHousePosition, "", R.mipmap.ic_blue_point);
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
                    mViewModel.endLa = latLng.latitude;
                    mViewModel.endLo = latLng.longitude;
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
                    , (ArrayList<PigeonEntity>) mAdapter.getData(), CODE_SELECT_PIGEONS);
        });

        mTvOk.setOnClickListener(v -> {
            mViewModel.name = mLvName.getContent();
            mViewModel.fromLocation = mLvFlyLocation.getContent();
            setProgressVisible(true);
            mViewModel.newTrainPigeon();
        });

        mFrameLayout.setOnClickListener(v -> {

            if (mViewModel.endLa == 0) {
                ToastUtils.showLong(getBaseActivity(), "请选择司放地！");
                return;
            }

            mapView.setVisibility(View.GONE);


            if (FragmentUtils.getAllFragments(getFragmentManager()).size() == 2) {
                FragmentUtils.show(mLineWeatherFragment);
            } else {
                mLineWeatherFragment = new LineWeatherFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(IntentBuilder.KEY_DATA, new LatLng(mViewModel.fromLa, mViewModel.fromLo));
                bundle.putParcelable(IntentBuilder.KEY_DATA_2, new LatLng(mViewModel.endLa, mViewModel.endLo));
                bundle.putFloat(IntentBuilder.KEY_DATA_3, mViewModel.dis);
                mLineWeatherFragment.setArguments(bundle);
                FragmentUtils.add(getFragmentManager(), mLineWeatherFragment, R.id.rlMap);
            }
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
        getBaseActivity().setOnActivityFinishListener(() -> {
            AppDatabase.getInstance(getBaseActivity()).delete(AppDatabase.getInstance(getBaseActivity()).DbEntityDao()
                    .getDataByUserAndType(UserModel.getInstance().getUserId()
                            , AppDatabase.TYPE_SELECT_PIGEON_TO_TRAINING));
        });


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

            //bindAddress(address);

            mLvFlyLocation.setContent(data.getStringExtra(SelectLocationByMapFragment.KEY_ADDRESS_NAME));

            mMapMarkerManager.clean();

            mMapMarkerManager.addCustomCenterMarker(mPigeonHousePosition, "", R.mipmap.ic_blue_point);
            LatLng selectPoint = new LatLng(point.getLatitude(), point.getLongitude());
            mMapMarkerManager.addCustomCenterMarker(selectPoint, "", R.mipmap.ic_red_point);
            mMapMarkerManager.addMap();

            float dis = AMapUtils.calculateLineDistance(mPigeonHousePosition, new LatLng(point.getLatitude(), point.getLongitude()));
            mCardView.setVisibility(View.VISIBLE);
            mTvDis.setText(Utils.getString(R.string.text_KM, String.valueOf((int) (dis / 1000))));
            addLine(Lists.newArrayList(mPigeonHousePosition, selectPoint), R.color.colorPrimary);
            mViewModel.endLo = point.getLongitude();
            mViewModel.endLa = point.getLatitude();
            mViewModel.dis = dis;
        } else if (requestCode == CODE_SELECT_PIGEONS) {
            List<PigeonEntity> pigeonEntities = (List<PigeonEntity>) data.getSerializableExtra(IntentBuilder.KEY_DATA);
            mAdapter.setNewData(pigeonEntities);
            mViewModel.mPigeonEntities = pigeonEntities;
        }
    }

    private void bindAddress(RegeocodeAddress mAddress) {
        String address = mAddress.getProvince() + mAddress.getCity() + mAddress.getDistrict();

        /*mViewModel.mProvince = mAddress.getProvince();
        mViewModel.mCity = mAddress.getCity();
        mViewModel.mCounty = mAddress.getDistrict();*/
    }


    @Override
    protected void initObserve() {
        mViewModel.normalResult.observe(this, s -> {
            setProgressVisible(false);
            DialogUtils.createHintDialog(getBaseActivity(), s, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                EventBus.getDefault().post(new UpdateTrainEvent());
                finish();
            });
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(CloseMapEvent event) {
        hideLine();
        mapView.setVisibility(View.VISIBLE);
    }

    public void hideLine() {
        FragmentUtils.hide(mLineWeatherFragment);
    }

    @Override
    public boolean OnBackClick() {
        if (FragmentUtils.getTopShow(getBaseActivity().getSupportFragmentManager()).getClass()
                .equals(LineWeatherFragment.class)) {
            hideLine();
            mapView.setVisibility(View.VISIBLE);
            return true;
        }

        return false;
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
