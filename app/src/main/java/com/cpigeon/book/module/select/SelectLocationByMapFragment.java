package com.cpigeon.book.module.select;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.AoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.base.BaseFragment;
import com.base.base.BaseMapFragment;
import com.base.util.IntentBuilder;
import com.base.util.LocationFormatUtils;
import com.base.util.Utils;
import com.base.util.map.LocationLiveData;
import com.base.util.map.PointToAddressManager;
import com.base.util.utility.LogUtil;
import com.cpigeon.book.R;
import com.cpigeon.book.widget.SearchTextView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/7.
 */

public class SelectLocationByMapFragment extends BaseMapFragment {

    private SearchTextView mSearchTextView;
    private TextView mTvLa;
    private TextView mTvLo;
    private TextView mTvOk;
    private TextView mTvLocation;
    private PointToAddressManager mPointToAddressManager;


    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, SelectLocationByMapFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_location_by_map, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPointToAddressManager = PointToAddressManager.build(getBaseActivity())
                .setSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                    @Override
                    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                        if (regeocodeResult == null) {
                            return;
                        }

                        List<AoiItem> aoiItems = regeocodeResult.getRegeocodeAddress().getAois();


                        mTvLocation.setText(regeocodeResult.getRegeocodeAddress().getCity()
                                + regeocodeResult.getRegeocodeAddress().getAois().get(0).getAoiName());

                    }

                    @Override
                    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

                    }
                });

        amapManager.setMoveCenterListener();
        amapManager.mLatLngLiveData.observe(this, latLng -> {
            mPointToAddressManager.setSearchPoint(new LatLonPoint(latLng.latitude, latLng.longitude));
            mPointToAddressManager.search();
        });

        mSearchTextView = findViewById(R.id.searchTextView);
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
    }
}
