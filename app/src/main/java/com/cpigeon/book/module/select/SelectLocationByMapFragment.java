package com.cpigeon.book.module.select;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.geocoder.AoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.RegeocodeRoad;
import com.base.base.BaseMapFragment;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.LocationFormatUtils;
import com.base.util.Utils;
import com.base.util.map.DistrictSearchManager;
import com.base.util.map.LocationLiveData;
import com.base.util.map.PointToAddressManager;
import com.cpigeon.book.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/7.
 */

public class SelectLocationByMapFragment extends BaseMapFragment {

    public static final String KEY_ADDRESS_NAME = "INTENT_ADDRESS_NAME";

    public static int CODE_LOCATION = 0x234;

    private EditText mSearchTextView;
    private TextView mTvLa;
    private TextView mTvLo;
    private TextView mTvOk;
    private TextView mTvLocation;
    private PointToAddressManager mPointToAddressManager;
    private DistrictSearchManager mDistrictSearchManager;
    RegeocodeAddress mAddress;
    RegeocodeResult mRegeocodeResult;

    String addString = null;


    public static void start(Activity activity, int requestCode) {
        IntentBuilder.Builder()
                .startParentActivity(activity, false, SelectLocationByMapFragment.class, requestCode);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_location_by_map_2
                , container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        setTitle(R.string.title_map_location);
        mPointToAddressManager = new PointToAddressManager(getBaseActivity())
                .setSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                    @Override
                    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                        setLocation(regeocodeResult);
                    }

                    @Override
                    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

                    }
                });

        mDistrictSearchManager = DistrictSearchManager.build(getBaseActivity()).setSearchListener(districtResult -> {
            ArrayList<DistrictItem> data = districtResult.getDistrict();
            if (!data.isEmpty()) {
                LatLonPoint point = districtResult.getDistrict().get(0).getCenter();
                amapManager.moveByLatLng(point.getLatitude(), point.getLongitude());
            }
        });

        amapManager.setMoveCenterListener();
        amapManager.mMoveEndLiveData.observe(this, latLng -> {

            mTvLo.setText(Utils.getString(R.string.text_lo
                    , LocationFormatUtils.loLaToDMS(latLng.longitude)));

            mTvLa.setText(Utils.getString(R.string.text_la
                    , LocationFormatUtils.loLaToDMS(latLng.latitude)));

            mPointToAddressManager.setSearchPoint(new LatLonPoint(latLng.latitude, latLng.longitude)).search();
        });

        amapManager.mInMoveLiveData.observe(this, latLng -> {
            mRegeocodeResult = null;
            mAddress = null;
        });

        mSearchTextView = findViewById(R.id.et_input);
        mTvLa = findViewById(R.id.tvLa);
        mTvLo = findViewById(R.id.tvLo);
        mTvOk = findViewById(R.id.tvOk);
        mTvLocation = findViewById(R.id.tvLocation);

        LocationLiveData.get(true).observe(this, aMapLocation -> {

            mTvLo.setText(Utils.getString(R.string.text_lo
                    , LocationFormatUtils.loLaToDMS(aMapLocation.getLongitude())));

            mTvLa.setText(Utils.getString(R.string.text_la
                    , LocationFormatUtils.loLaToDMS(aMapLocation.getLatitude())));

            amapManager.moveByLatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());

        });

//        mSearchTextView.setOnSearchTextClickListener(new SearchTextView.OnSearchTextClickListener() {
//            @Override
//            public void search(String key) {
//
//            }
//
//            @Override
//            public void cancel() {
//
//            }
//        });


        mSearchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mDistrictSearchManager.keyword(v.getText().toString()).search();
                }
                return false;
            }
        });


        mTvOk.setOnClickListener(v -> {
            if (mRegeocodeResult == null) {
                error(R.string.text_error_not_select_location);
                return;
            }
            Intent intent = new Intent();
            intent.putExtra(IntentBuilder.KEY_DATA, mRegeocodeResult.getRegeocodeAddress());
            intent.putExtra(IntentBuilder.KEY_DATA_2, mRegeocodeResult.getRegeocodeQuery().getPoint());
            intent.putExtra(KEY_ADDRESS_NAME, addString);
            getBaseActivity().setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }

    private void setLocation(RegeocodeResult regeocodeResult) {
        if (regeocodeResult == null) {
            return;
        }
        mRegeocodeResult = regeocodeResult;
        mAddress = regeocodeResult.getRegeocodeAddress();

        List<AoiItem> aoiItems = mAddress.getAois();
        List<RegeocodeRoad> roudItems = mAddress.getRoads();

        if (!Lists.isEmpty(aoiItems)) {
            addString = (mAddress.getCity() + aoiItems.get(0).getAoiName());
        } else if (!Lists.isEmpty(roudItems)) {
            addString = (mAddress.getCity() + roudItems.get(0).getName());
        } else {
            addString = (mAddress.getFormatAddress());
        }
        mTvLocation.setText(addString);
    }
}
