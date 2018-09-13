package com.cpigeon.book.module.menu.smalltools.lineweather.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.base.util.IntentBuilder;
import com.base.util.LocationFormatUtils;
import com.base.util.RxUtils;
import com.base.util.http.GsonUtil;
import com.base.util.map.MapMarkerManager;
import com.base.util.map.WeatherManager;
import com.base.util.utility.ImageUtils;
import com.base.util.utility.LogUtil;
import com.base.util.utility.StringUtil;
import com.base.util.utility.ToastUtils;
import com.base.widget.guideview.Component;
import com.base.widget.guideview.GuideBuilder;
import com.base.widget.guideview.GuideManager;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.menu.smalltools.lineweather.model.bean.ContactModel;
import com.cpigeon.book.module.menu.smalltools.lineweather.presenter.LineWeatherPresenter;
import com.cpigeon.book.module.menu.smalltools.ullage.UlageToolPresenter;
import com.cpigeon.book.util.BitmapUtils;
import com.cpigeon.book.util.MapUtil;
import com.cpigeon.book.util.SharedPreferencesTool;
import com.cpigeon.book.widget.mydialog.CustomAlertDialog;
import com.cpigeon.book.widget.mydialog.ShareDialogFragment;
import com.cpigeon.book.widget.mydialog.ViewControlShare;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.UMShareAPI;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;


/**
 * 赛线天气小工具
 * Created by Administrator on 2018/5/7.
 */
public class LineWeatherFragment extends BaseBookFragment {

    private static final String SP_GUIDE_LIBERATION = "SP_GUIDE_LIBERATION";
    private static final String SP_GUIDE_CHOOSE_LIBERATION = "SP_GUIDE_CHOOSE_LIBERATION";
    private static final String SP_GUIDE_CHOOSE_RETURN_NEAT = "SP_GUIDE_CHOOSE_RETURN_NEAT";
    private static final String SP_GUIDE_RESET_LOCATION = "SP_GUIDE_RESET_LOCATION";
    private static final String SP_GUIDE_SHOW_SELF_LOCATION = "SP_GUIDE_SHOW_SELF_LOCATION";


    @BindView(R.id.map)
    MapView mMapView;

    @BindView(R.id.rlz_sfd_gcd)
    RelativeLayout rlz_sfd_gcd;//确定司放地归巢地
    @BindView(R.id.rlz_sfd)
    RelativeLayout rlz_sfd;//司放地总弹出
    @BindView(R.id.rlz_gcd)
    RelativeLayout rlz_gcd;//归巢地总弹出
    @BindView(R.id.z_et_fly_place)
    TextView z_et_fly_place;//总司放点点
    @BindView(R.id.z_et_homing_place)
    TextView z_et_homing_place;//总归巢地点

    @BindView(R.id.et_input_sfd)
    EditText et_input_sfd;//输入司放地
    @BindView(R.id.et_input_gcd)
    EditText et_input_gcd;//输入归巢地

    @BindView(R.id.et_sfd_lo1)
    EditText etSfdLo1;
    @BindView(R.id.et_sfd_lo2)
    EditText etSfdLo2;
    @BindView(R.id.et_sfd_lo3)
    EditText etSfdLo3;
    @BindView(R.id.et_sfd_la1)
    EditText etSfdLa1;
    @BindView(R.id.et_sfd_la2)
    EditText etSfdLa2;
    @BindView(R.id.et_sfd_la3)
    EditText etSfdLa3;

    //归巢地坐标
    @BindView(R.id.et_gcd_lo1)
    EditText etGcdLo1;
    @BindView(R.id.et_gcd_lo2)
    EditText etGcdLo2;
    @BindView(R.id.et_gcd_lo3)
    EditText etGcdLo3;
    @BindView(R.id.et_gcd_la1)
    EditText etGcdLa1;
    @BindView(R.id.et_gcd_la2)
    EditText etGcdLa2;
    @BindView(R.id.et_gcd_la3)
    EditText etGcdLa3;

    @BindView(R.id.tv_copyright_information)
    TextView tv_copyright_information;//版权提示 分享

    @BindView(R.id.img_locate)
    ImageView img_locate;

    private AMap aMap;
    private MapMarkerManager markerManager;

    private double sureSfdLo = -1, sureSfdLa = -1;//确定归巢地
    private double sureGcdLo = -1, sureGcdLa = -1;//确定司放地
    private Handler handler;


    private LineWeatherPresenter mPresenter = new LineWeatherPresenter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_line_weather, container, false);
        return view;
    }


    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, LineWeatherFragment.class);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        dialogFragment = new ShareDialogFragment();

        icMap1 = MapUtil.initIcMap1();
        icMap2 = MapUtil.initIcMap2();
        Log.d("aaas", "initView: 111333----ss");


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.getMenu().add("").setIcon(R.drawable.ic_share_line_weather).setOnMenuItemClickListener(item -> {
            showLoading();
            getImageByMap();
            return false;

        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        setTitle("赛线天气");
        toolbar.setNavigationOnClickListener(v -> finish());

        mMapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
            aMap.getUiSettings().setZoomControlsEnabled(false);
            markerManager = new MapMarkerManager(aMap, getBaseActivity());
        }

        showLocate();//查看当前位置

        presentLocate(2);//定位当前位置（）归巢地

        manager = new WeatherManager(getBaseActivity());

        selectAnchorPoint(2);//点击选择司放地

        et_input_sfd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    InputMethodManager imm = (InputMethodManager) getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                    //输入司放地确定
                    startGeocodeSearch(et_input_sfd.getText().toString(), 1);
                }
                return false;
            }
        });

        etSfdLo1.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getBaseActivity(), etSfdLo1, 1, etSfdLo2));
        etSfdLo2.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getBaseActivity(), etSfdLo2, 3, etSfdLo3));
        etSfdLo3.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getBaseActivity(), etSfdLo3, 4, etSfdLa1));

        etSfdLa1.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getBaseActivity(), etSfdLa1, 2, etSfdLa2));
        etSfdLa2.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getBaseActivity(), etSfdLa2, 3, etSfdLa3));
        etSfdLa3.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getBaseActivity(), etSfdLa3, 4, etSfdLa3));

        etGcdLo1.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getBaseActivity(), etGcdLo1, 1, etGcdLo2));
        etGcdLo2.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getBaseActivity(), etGcdLo2, 3, etGcdLo3));
        etGcdLo3.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getBaseActivity(), etGcdLo3, 4, etGcdLa1));

        etGcdLa1.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getBaseActivity(), etGcdLa1, 2, etGcdLa2));
        etGcdLa2.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getBaseActivity(), etGcdLa2, 3, etGcdLa3));
        etGcdLa3.addTextChangedListener(UlageToolPresenter.setLoLaSListener(getBaseActivity(), etGcdLa3, 4, etGcdLa3));


        composite.add(RxUtils.delayed(100, aLong -> {

            String isShow = SharedPreferencesTool.Get(getBaseActivity(), SP_GUIDE_LIBERATION, "", SharedPreferencesTool.SP_FILE_GUIDE);

            if (!StringUtil.isStringValid(isShow)) {
                GuideManager.get()
                        .setHintText("点击设置司放地哦~~")
                        .setTagViewId(R.id.llz_sfd)
                        .setGuideLocation(Component.ANCHOR_TOP)
                        .setVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
                            @Override
                            public void onShown() {

                            }

                            @Override
                            public void onDismiss() {
                                showSelfLocationGuideView();
                            }
                        })
                        .show(getBaseActivity());
                SharedPreferencesTool.Save(getBaseActivity(), SP_GUIDE_LIBERATION, SP_GUIDE_LIBERATION, SharedPreferencesTool.SP_FILE_GUIDE);
            }

            //showGuideView();
        }));

    }


    public void showChooseReturnNestLocationGuideView() {

        String isShow = SharedPreferencesTool.Get(getBaseActivity(), SP_GUIDE_CHOOSE_RETURN_NEAT, "", SharedPreferencesTool.SP_FILE_GUIDE);

        if (!StringUtil.isStringValid(isShow)) {
            GuideManager.get()
                    .setHintText("点击选择公棚哦~~")
                    .setTagViewId(R.id.tv_select_gp)
                    .setGuideLocation(Component.ANCHOR_TOP)
                    .setViewLocation(Component.FIT_END)
                    .show(getBaseActivity());
            SharedPreferencesTool.Save(getBaseActivity(), SP_GUIDE_CHOOSE_RETURN_NEAT, SP_GUIDE_CHOOSE_RETURN_NEAT, SharedPreferencesTool.SP_FILE_GUIDE);
        }
    }

    public void showChooseLiberationGuideView() {

        String isShow = SharedPreferencesTool.Get(getBaseActivity(), SP_GUIDE_CHOOSE_LIBERATION, "", SharedPreferencesTool.SP_FILE_GUIDE);

        if (!StringUtil.isStringValid(isShow)) {
            GuideManager.get()
                    .setHintText("点击选择参考司放地哦~~")
                    .setTagViewId(R.id.tv_select_sfd)
                    .setGuideLocation(Component.ANCHOR_TOP)
                    .setViewLocation(Component.FIT_END)
                    .show(getBaseActivity());
            SharedPreferencesTool.Save(getBaseActivity(), SP_GUIDE_CHOOSE_LIBERATION, SP_GUIDE_CHOOSE_LIBERATION, SharedPreferencesTool.SP_FILE_GUIDE);
        }

    }

    public void showResetLocationGuideView() {

        String isShow = SharedPreferencesTool.Get(getBaseActivity(), SP_GUIDE_RESET_LOCATION, "", SharedPreferencesTool.SP_FILE_GUIDE);

        if (!StringUtil.isStringValid(isShow)) {
            GuideManager.get()
                    .setHintText("点击这里重置位置")
                    .setTagView(ll_arrow)
                    .setGuideLocation(Component.ANCHOR_TOP)
                    .setViewLocation(Component.FIT_CENTER)
                    .setVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
                        @Override
                        public void onShown() {

                        }

                        @Override
                        public void onDismiss() {
                        }
                    })
                    .show(getBaseActivity());
            SharedPreferencesTool.Save(getBaseActivity(), SP_GUIDE_RESET_LOCATION, SP_GUIDE_RESET_LOCATION, SharedPreferencesTool.SP_FILE_GUIDE);
        }

    }

    public void showSelfLocationGuideView() {
        GuideManager.get()
                .setHintText("点击这里查看当前位置")
                .setTagViewId(R.id.img_locate)
                .setGuideLocation(Component.ANCHOR_BOTTOM)
                .setViewLocation(Component.FIT_END)
                .show(getBaseActivity());
    }



    @OnClick({R.id.llz_sfd, R.id.llz_gcd, R.id.tv_sure, R.id.img_close1, R.id.img_close2,
            R.id.input_sfd_sure, R.id.input_sfd_coordinate_sure, R.id.ll_select_shed, R.id.tv_input_gcd,
            R.id.tv_coordinate_gcd, R.id.ll_click_sfd_sure, R.id.ll_click_gcd_sure, R.id.tv_locate_current_position_gcd,
            R.id.ll_select_sfd, R.id.ll_arrow, R.id.img_locate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llz_sfd:
                //司放地
                rlz_sfd_gcd.setVisibility(View.GONE);
                rlz_sfd.setVisibility(View.VISIBLE);
                selectAnchorPoint(1);

                composite.add(RxUtils.delayed(100, aLong -> {
                    showChooseLiberationGuideView();
                }));
                break;
            case R.id.llz_gcd:
                //归巢地
                rlz_sfd_gcd.setVisibility(View.GONE);
                rlz_gcd.setVisibility(View.VISIBLE);

                selectAnchorPoint(2);

                composite.add(RxUtils.delayed(100, aLong -> {
                    showChooseReturnNestLocationGuideView();
                }));
                break;
            case R.id.tv_sure:
                if (sureSfdLa == -1 || sureSfdLo == -1) {
                    ToastUtils.showLong(getBaseActivity(), "请选择司放地点");
                    return;
                }
                if (sureGcdLa == -1 || sureGcdLo == -1) {
                    ToastUtils.showLong(getBaseActivity(), "请选择归巢地点");
                    return;
                }

                hintLine();

                String sfdLo = LocationFormatUtils.GPS2AjLocation(sureSfdLo);
                String sfdLa = LocationFormatUtils.GPS2AjLocation(sureSfdLa);


                String gcdLo = LocationFormatUtils.GPS2AjLocation(sureGcdLo);
                String gcdLa = LocationFormatUtils.GPS2AjLocation(sureGcdLa);

                Map<String, String> map = new HashMap<>();
                map.put("fangfeijingdu_du", LocationFormatUtils.strToD(sfdLo));
                map.put("fangfeijingdu_fen", LocationFormatUtils.strToM(sfdLo));
                map.put("fangfeijingdu_miao", LocationFormatUtils.strToS(sfdLo));

                map.put("fangfeiweidu_du", LocationFormatUtils.strToD(sfdLa));
                map.put("fangfeiweidu_fen", LocationFormatUtils.strToM(sfdLa));
                map.put("fangfeiweidu_miao", LocationFormatUtils.strToS(sfdLa));

                map.put("guichaojingdu_du", LocationFormatUtils.strToD(gcdLo));
                map.put("guichaojingdu_fen", LocationFormatUtils.strToM(gcdLo));
                map.put("guichaojingdu_miao", LocationFormatUtils.strToS(gcdLo));

                map.put("guichaoweidu_du", LocationFormatUtils.strToD(gcdLa));
                map.put("guichaoweidu_fen", LocationFormatUtils.strToM(gcdLa));
                map.put("guichaoweidu_miao", LocationFormatUtils.strToS(gcdLa));

                mPresenter.getKongJuData(map, data -> {
                    try {
                        Log.d("sousuo", "onViewClicked: " + data.getResult());
                        showLoading();
                        addLinePoint(data.getResult() * 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                break;
            case R.id.img_close1:
                rlz_sfd_gcd.setVisibility(View.VISIBLE);
                rlz_sfd.setVisibility(View.GONE);
                break;
            case R.id.img_close2:
                rlz_sfd_gcd.setVisibility(View.VISIBLE);
                rlz_gcd.setVisibility(View.GONE);
                break;
            case R.id.input_sfd_sure:
                //输入司放地确定
                startGeocodeSearch(et_input_sfd.getText().toString(), 1);
                break;
            case R.id.input_sfd_coordinate_sure:
                //输入司放地坐标确定
                startCoordinateLocate(etSfdLo1, etSfdLo2, etSfdLo3, etSfdLa1, etSfdLa2, etSfdLa3, 1);
                break;
            case R.id.ll_select_shed:
                //选择公棚
                SelectShedFragment.start(getBaseActivity());
                break;
            case R.id.tv_input_gcd:
                //输入归巢地
                startGeocodeSearch(et_input_gcd.getText().toString(), 2);
                break;
            case R.id.tv_coordinate_gcd:
                //输入归巢地坐标，确定定位
                startCoordinateLocate(etGcdLo1, etGcdLo2, etGcdLo3, etGcdLa1, etGcdLa2, etGcdLa3, 2);
                break;
            case R.id.ll_click_sfd_sure:
                rlz_sfd_gcd.setVisibility(View.VISIBLE);
                rlz_sfd.setVisibility(View.GONE);

                ToastUtils.showLong(getBaseActivity(), "选择地图定位 （司放地）");
                selectAnchorPoint(1);
                break;

            case R.id.ll_click_gcd_sure:
                rlz_sfd_gcd.setVisibility(View.VISIBLE);
                rlz_gcd.setVisibility(View.GONE);
                ToastUtils.showLong(getBaseActivity(), "选择地图定位（归巢地）");
                selectAnchorPoint(2);
                break;

            case R.id.tv_locate_current_position_gcd:
                //定位当前位置，归巢地
                presentLocate(2);//定位当前位置（）归巢地
                return;
            case R.id.ll_select_sfd:
                //选择司放地
                SelectFlyFragment.start(getBaseActivity());
                break;

            case R.id.ll_arrow:
                //控件下移上升
                startAnimator();
                break;
            case R.id.img_locate:
                dialog.show();
                break;
        }
    }

    private LinearLayout dialogLayout;
    private TextView presentLo, presentLa;
    private CustomAlertDialog dialog;

    //初始化当前定位点坐标
    private void showLocate() {
        //layout_dialog_present_locate
        dialogLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog_present_locate, null);

        TextView dialogDetermine = (TextView) dialogLayout.findViewById(R.id.dialog_determine);
        presentLo = (TextView) dialogLayout.findViewById(R.id.tv_present_lo);
        presentLa = (TextView) dialogLayout.findViewById(R.id.tv_present_la);

        dialogDetermine.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog = new CustomAlertDialog(getActivity());
        dialog.setContentView(dialogLayout);
        //调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
        dialog.setCanceledOnTouchOutside(false);
    }


    @BindView(R.id.img_l_arrow)
    ImageView img_l_arrow;
    @BindView(R.id.ll_arrow)
    LinearLayout ll_arrow;

    private int tag = 1;//点击图片设置tag，   1：向下   2：向上
    private ValueAnimator animator;//属性动画值
    private float curTranslationY;//动画Y轴移动距离

    /**
     * 开启动画
     */
    private void startAnimator() {
        if (tag == 1) {//向下移动
            img_l_arrow.setRotation(0);
            curTranslationY = rlz_sfd_gcd.getTranslationY();//获取当前空间Y方向上的值
            animator = ValueAnimator.ofFloat(curTranslationY, rlz_sfd_gcd.getHeight() - ll_arrow.getHeight());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    rlz_sfd_gcd.setTranslationY(value);
                }
            });//设置监听

            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    showResetLocationGuideView();
                }
            });
            animator.setDuration(700);//设置动画执行时间
            if (!animator.isRunning()) {
                animator.start();//开始动画
                tag = 2;
            }
        } else {//向上移动
            img_l_arrow.setRotation(180);
            curTranslationY = rlz_sfd_gcd.getTranslationY();
            animator = ValueAnimator.ofFloat(curTranslationY, 0);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    rlz_sfd_gcd.setTranslationY(value);
                }
            });
            animator.setDuration(700);
            if (!animator.isRunning()) {
                animator.start();
                tag = 1;
            }
        }
    }


    //选择定位点
    private void selectAnchorPoint(int tag) {

        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                hintLine(tag, latLng);
            }
        });
    }

    //隐藏线，显示点
    private void hintLine(int tag, LatLng latLng) {
        switch (tag) {
            case 1://选择司放地
                getAddressByLatlng(latLng.longitude, latLng.latitude, tag);
                initStartStop(latLng, tag, true, false);

                break;
            case 2:
                getAddressByLatlng(latLng.longitude, latLng.latitude, tag);
                initStartStop(latLng, tag, true, false);

                break;
        }
    }

    //隐藏弹出View  显示总
    private void showHintView() {
        rlz_sfd_gcd.setVisibility(View.VISIBLE);
        rlz_sfd.setVisibility(View.GONE);
        rlz_gcd.setVisibility(View.GONE);
    }

    private void hintLine() {
        try {
            aMap.clear();
            aMap.getMapScreenMarkers().clear();
            markerManager.clean();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //显示起点，终点  isMoveView:是否移动视图到正中间
    private void initStartStop(LatLng latLng, int tag, boolean isAnimation, boolean isMoveView) {

        showHintView();
        hintLine();

        switch (tag) {
            case 1:

                showSfd(latLng, isAnimation, isMoveView);
                try {
                    if (sureGcdLa != -1 && sureGcdLo != -1) {
                        showGcd(new LatLng(sureGcdLa, sureGcdLo), false, false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case 2:
                showGcd(latLng, isAnimation, isMoveView);

                try {
                    if (sureSfdLa != -1 && sureSfdLo != -1) {
                        showSfd(new LatLng(sureSfdLa, sureSfdLo), false, false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    //显示司放地
    private void showSfd(LatLng latLng, boolean isAnimation, boolean isMoveView) {
        sureSfdLo = latLng.longitude;
        sureSfdLa = latLng.latitude;

        Log.d("sousuo", "司放地: la->" + latLng.latitude + "   lo->" + latLng.longitude);

        if (isMoveView) {
            //是否移动视图
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        }

        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(ImageUtils.setImgSize(BitmapFactory.decodeResource(getResources(), R.drawable.ic_line_ewather_sfd), 125, 125)));


        if (mMarkerStart != null) {
            mMarkerStart.remove();
        }

        mMarkerStart = aMap.addMarker(markerOption);
        mMarkerStart.setTitle("司放地");
        mMarkerStart.setAnchor(0f, 1f);

        if (isAnimation) {
            jumpPoint(mMarkerStart);
        }
    }

    //显示归巢地
    private void showGcd(LatLng latLng, boolean isAnimation, boolean isMoveView) {
        sureGcdLo = latLng.longitude;
        sureGcdLa = latLng.latitude;
        Log.d("sousuo", "归巢地: la->" + latLng.latitude + "   lo->" + latLng.longitude);

        if (isMoveView) {
            //是否移动视图
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        }
        MarkerOptions markerOption2 = new MarkerOptions();
        markerOption2.position(latLng);

        markerOption2.icon(BitmapDescriptorFactory.fromBitmap(ImageUtils.setImgSize(BitmapFactory.decodeResource(getResources(), R.drawable.ic_line_ewather_gcd), 125, 125)));

        if (mMarkerEnd != null) {
            mMarkerEnd.remove();
        }

        mMarkerEnd = aMap.addMarker(markerOption2);
        mMarkerEnd.setAnchor(0f, 1f);
        mMarkerEnd.setTitle("归巢地");

        if (isAnimation) {
            jumpPoint(mMarkerEnd);
        }
    }

    /**
     * marker点击时跳动一下
     */
    public void jumpPoint(Marker marker) {
        handler = new Handler();
        long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        LatLng markerLatlng = marker.getPosition();
        Point markerPoint = proj.toScreenLocation(markerLatlng);
        markerPoint.offset(0, -100);
        LatLng startLatLng = proj.fromScreenLocation(markerPoint);

        long duration = 1500;

        Interpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * markerLatlng.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * markerLatlng.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    private Marker mMarkerStart;
    private Marker mMarkerEnd;

    private GeocodeSearch geocoderSearch;

    //开始查询
    private void startGeocodeSearch(String name, int tag) {

        if (name.isEmpty()) {
            ToastUtils.showLong(getBaseActivity(), "输入地址不能为空");
            return;
        }

        geocoderSearch = new GeocodeSearch(getBaseActivity());
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

                try {
                    double la = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLatitude();
                    double lo = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLongitude();

                    z_et_fly_place.setText(geocodeResult.getGeocodeQuery().getLocationName());
                    initStartStop(new LatLng(la, lo), tag, true, true);

                } catch (Exception e) {
                    Log.d("sousuo2", "异常：" + e.getLocalizedMessage());
                }
            }
        });

        // name表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode
        GeocodeQuery query = new GeocodeQuery(name, "");

        geocoderSearch.getFromLocationNameAsyn(query);
    }

    //开始坐标定位  tag =1 :司放地  tag =2：归巢地
    private void startCoordinateLocate(EditText etLo1, EditText etLo2, EditText etLo3,
                                       EditText etLa1, EditText etLa2, EditText etLa3, int tag) {

        String strEtLo1 = etLo1.getText().toString();
        String strEtLo2 = etLo2.getText().toString();
        String strEtLo3 = etLo3.getText().toString();

        String strEtLa1 = etLa1.getText().toString();
        String strEtLa2 = etLa2.getText().toString();
        String strEtLa3 = etLa3.getText().toString();

        if (strEtLo1.isEmpty() || strEtLo2.isEmpty() || strEtLo3.isEmpty() ||
                strEtLa1.isEmpty() || strEtLa2.isEmpty() || strEtLa3.isEmpty()) {
            ToastUtils.showLong(getBaseActivity(), "输入经纬度内容不能为空");
            return;
        }

        //获取输入的经度
        String strLo = strEtLo1 + ".";
        if (strEtLo2.length() == 1) {
            strLo += "0" + strEtLo2;
        } else {
            strLo += strEtLo2;
        }

        strLo += strEtLo3.replace(".", "");

        //获取输入的纬度
        String strLa = strEtLa1 + ".";
        if (strEtLa2.length() == 1) {
            strLa += "0" + strEtLa2;
        } else {
            strLa += strEtLa2;
        }

        strLa += strEtLa3.replace(".", "");

        try {
            double la = LocationFormatUtils.Aj2GPSLocation(Double.valueOf(strLa));
            double lo = LocationFormatUtils.Aj2GPSLocation(Double.valueOf(strLo));

            Log.d("sousuo", "onRegeocodeSearched2: la-->" + la + "    lo-->" + lo);
            aMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(la, lo)));

            initStartStop(new LatLng(la, lo), tag, true, true);
            getAddressByLatlng(lo, la, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param lo 经度  逆地理搜索
     * @param la 纬度
     */
    private void getAddressByLatlng(double lo, double la, int tag) {
        //地理搜索类
        GeocodeSearch geocodeSearch = new GeocodeSearch(getActivity());
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

                try {
                    showHintView();
                    switch (tag) {
                        case 1://司放地点
                            sureSfdLo = lo;
                            sureSfdLa = la;
                            z_et_fly_place.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
                            break;
                        case 2://归巢地点
                            sureGcdLo = lo;
                            sureGcdLa = la;
                            z_et_homing_place.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
                            break;
                    }
                } catch (Exception e) {
                    switch (tag) {
                        case 1://司放地点
                            z_et_fly_place.setText("");
                            break;
                        case 2://归巢地点
                            z_et_homing_place.setText("");
                            break;
                    }
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });

        //逆地理编码查询条件：逆地理编码查询的地理坐标点、查询范围、坐标类型。
        LatLonPoint latLonPoint = new LatLonPoint(la, lo);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 500f, GeocodeSearch.AMAP);
        //异步查询
        geocodeSearch.getFromLocationAsyn(query);
    }

    private List<LatLng> afterPoints = new ArrayList<>();//沿途路线的点

    //添加线路的点
    private void addLinePoint(double juli) {
        /**
         *  加点公式，可参考
         */
        afterPoints.clear();

        int distance = (int) juli;
        int point = 1;
        double distanceOne = 0;//一条距离

        if (distance < 150 * 1000) {
            point = 2;
            distanceOne = (juli / 2);
        } else if (150 * 1000 <= distance && distance < 300 * 1000) {
            point = 3;
            distanceOne = (juli / 3);
        } else if (300 * 1000 <= distance && distance < 450 * 1000) {
            point = 4;
            distanceOne = (juli / 4);
        } else if (450 * 1000 <= distance && distance < 600 * 1000) {
            point = 5;
            distanceOne = (juli / 5);
        } else if (distance >= 600 * 1000) {
            point = 6;
            distanceOne = (juli / 6);
        }

        double x = (sureSfdLo - sureGcdLo) * (1d / point);
        double y = (sureSfdLa - sureGcdLa) * (1d / point);
        Log.d("fdf", "斜度:" + x);

        afterPoints.add(new LatLng(sureGcdLa, sureGcdLo));
        for (int i = 1; i < point; i++) {
            double x1 = sureGcdLa + (y * i);//第一个点La
            double x2 = sureGcdLo + (x * i);//第一个点Long
            LatLng latLng = new LatLng(x1, x2);
            afterPoints.add(latLng);
        }
        afterPoints.add(new LatLng(sureSfdLa, sureSfdLo));

        Log.d("sousuo", "沿途点: " + afterPoints.size());

        Collections.reverse(afterPoints); // 倒序排列

        weatherList.clear();
        i = 0;
        searchCityByPoint(distanceOne);
    }

    private WeatherManager manager;
    private ArrayList<RegeocodeAddress> addressList;
    private ArrayList<LocalWeatherLive> weatherList = new ArrayList<>();
    int i = 0;
    protected final CompositeDisposable composite = new CompositeDisposable();

    private void searchCityByPoint(double distanceOne) {
        try {
            Log.d("sousuo", "onRegeocodeSearched: 0");
            composite.add(manager.searchCityByLatLng(afterPoints.get(i), r -> {
                composite.add(manager.requestWeatherByCityName(r.data.getCity(), response -> {
                    if (response.isOk()) {
                        weatherList.add(response.data);

                        Log.d("sousuo", "1---->: " + response.getData().getWeather());
                        Log.d("sousuo", "1---->: " + weatherList.size());
                        if (weatherList.size() == afterPoints.size()) {

                            initMap(distanceOne);
                            hideLoading();
                            LogUtil.print("debug" + "hideLoading");
                        } else {
                            searchCityByPoint(distanceOne);
                        }
                    } else {
                        Log.d("sousuo", "2---->: " + weatherList.size());

                        LocalWeatherLive mData = new LocalWeatherLive();
                        mData.setCity("未知");
                        mData.setAdCode("未知");
                        mData.setHumidity("未知");
                        mData.setProvince("未知");
                        mData.setReportTime("未知");
                        mData.setWeather("未知");
                        mData.setWindDirection("未知");
                        mData.setWindPower("未知");
                        weatherList.add(mData);

                        if (weatherList.size() == afterPoints.size()) {

                            initMap(distanceOne);
                            hideLoading();
                            LogUtil.print("debug" + "hideLoading");
                        } else {
                            searchCityByPoint(distanceOne);
                        }
                    }
                }));
                i++;
            }));
        } catch (Exception e) {
            Log.d("sousuo", "3---->: 异常" + e.getLocalizedMessage());
        }
    }

    private boolean isShowEnd = true;

    private void initMap(double distanceOne) {
        aMap.addPolyline(new PolylineOptions().
                addAll(afterPoints).width(10).color(Color.argb(255, 61, 188, 196)));

        markerManager.addCustomMarker2(afterPoints.get(0), null, BitmapUtils.getViewBitmap(getInfoWindow(GsonUtil.toJson(weatherList.get(0)), MARKER_START, 0, distanceOne)));

        for (int i = 1, len = afterPoints.size() - 1; i < len; i++) {
            markerManager.addCustomMarker2(afterPoints.get(i), null, BitmapUtils.getViewBitmap(getInfoWindow(GsonUtil.toJson(weatherList.get(i)), MARKER_NORMAL, i, distanceOne)));
        }

        markerManager.addCustomMarker2(afterPoints.get(afterPoints.size() - 1), null,
                BitmapUtils.getViewBitmap(getInfoWindow(GsonUtil.toJson(weatherList.get(afterPoints.size() - 1)), MARKER_END, afterPoints.size() - 1, distanceOne)));


        List<Marker> markerList = markerManager.addMap();
        if (isShowEnd) {
            markerList.get(markerList.size() - 1).showInfoWindow();
        } else {
            markerList.get(0).showInfoWindow();
        }

        RxUtils.delayed(200, aLong -> {
            aMap.moveCamera(CameraUpdateFactory.zoomTo(aMap.getCameraPosition().zoom - 1));
        });

        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Log.d("sousuo", "onMarkerClick: " + marker.getPosition());

                int siez = markerList.size();
                for (int i = 0; i < siez; i++) {
                    if (markerList.get(i).getPosition().latitude == marker.getPosition().latitude &&
                            markerList.get(i).getPosition().longitude == marker.getPosition().longitude) {
                        AWeekWeatherFragment.start(getBaseActivity(),markerList.get(i).getPosition());
                        return true;
                    }
                }
                return false;
            }
        });

        ll_arrow.setVisibility(View.VISIBLE);

        startAnimator();

        selectAnchorPoint(-1);
    }

    private LinearLayout llz_weather;//点标记
    private LinearLayout llz_place;//总的地点
    private ImageView img_weather;//天气图标
    private TextView tv_place_name;//地名
    private TextView tv_distance;//距离


    private LinearLayout ll_weather;//一周天气
    private TextView tv_weather1;//一周
    private TextView tv_weather2;//天气

    private TextView tv_triangle;//三角

    private ImageView img_start_stop;//定位点起点终点图标

    public View getInfoWindow(String info, int type, int position, double distanceOne) {

        Log.d("xiaohl", "getInfoWindow: 111-->" + type);

        if (llz_weather == null) {
            llz_weather = (LinearLayout) findViewById(R.id.llz_weather);
            llz_place = (LinearLayout) llz_weather.findViewById(R.id.llz_place);
            img_weather = (ImageView) llz_weather.findViewById(R.id.img_weather);
            tv_place_name = (TextView) llz_weather.findViewById(R.id.tv_place_name);
            tv_distance = (TextView) llz_weather.findViewById(R.id.tv_distance);

            ll_weather = (LinearLayout) llz_weather.findViewById(R.id.ll_weather);
            tv_weather1 = (TextView) llz_weather.findViewById(R.id.tv_weather1);
            tv_weather2 = (TextView) llz_weather.findViewById(R.id.tv_weather2);

            tv_triangle = (TextView) llz_weather.findViewById(R.id.tv_triangle);

            img_start_stop = (ImageView) llz_weather.findViewById(R.id.img_start_stop);
        }

        if (type == MARKER_END) {
            img_start_stop.setImageResource(R.drawable.line_weather_stop);
        } else if (type == MARKER_START) {
            img_start_stop.setImageResource(R.drawable.line_weather_start);
        } else {
            img_start_stop.setImageResource(R.drawable.line_weather_start);
        }

        render(info, type, position, distanceOne);
        return llz_weather;
    }


    private Map<String, Integer> icMap1;
    private Map<String, Integer> icMap2;
    public static final int MARKER_NORMAL = -1;
    public static final int MARKER_START = 0;
    public static final int MARKER_END = 1;

    private void render(String info, int type, int position, double distanceOne) {
        LocalWeatherLive weatherLive = GsonUtil.fromJson(info, new TypeToken<LocalWeatherLive>() {
        }.getType());

        try {
            tv_place_name.setText(weatherLive.getCity());
        } catch (Exception e) {
            tv_place_name.setText("暂无该地区名称");
        }

        int integer1 = -1;
        int integer2 = -1;
        try {
            integer1 = icMap1.get(weatherLive.getWeather());
            integer2 = icMap2.get(weatherLive.getWeather());
        } catch (Exception e) {
            integer1 = -1;
            integer2 = -1;
        }

        if (type == MARKER_START) {
            tv_distance.setText("司放地");
        } else if (type == MARKER_END) {
            double jl = distanceOne * position;
            tv_distance.setText("归巢地：约" + new DecimalFormat("0.00").format(jl / 1000) + "KM");
        } else {
            double jl = distanceOne * position;
            tv_distance.setText("约" + new DecimalFormat("0.00").format(jl / 1000) + "KM");
        }

        if (type == MARKER_START || type == MARKER_END) {
            //起点 或者终点
            tv_triangle.setBackgroundResource(R.color.colorWhite);
            llz_place.setBackgroundResource(R.color.colorWhite);
            ll_weather.setBackgroundResource(R.color.bg_line_weather_l);
            tv_weather1.setTextColor(getResources().getColor(R.color.colorWhite));
            tv_weather2.setTextColor(getResources().getColor(R.color.colorWhite));

            tv_place_name.setTextColor(getResources().getColor(R.color.bg_line_weather_l));
            tv_distance.setTextColor(getResources().getColor(R.color.bg_line_weather_l));

            try {
                if (integer2 == -1) {
                    img_weather.setImageResource(icMap2.get("未知"));
                } else {
                    img_weather.setImageResource(icMap2.get(weatherLive.getWeather()));
                }
            } catch (Exception e) {

            }

        } else {
            //中间点
            tv_triangle.setBackgroundResource(R.color.bg_line_weather_l);
            llz_place.setBackgroundResource(R.color.bg_line_weather_l);
            ll_weather.setBackgroundResource(R.color.colorWhite);
            tv_weather1.setTextColor(getResources().getColor(R.color.bg_line_weather_l));
            tv_weather2.setTextColor(getResources().getColor(R.color.bg_line_weather_l));

            tv_place_name.setTextColor(getResources().getColor(R.color.colorWhite));
            tv_distance.setTextColor(getResources().getColor(R.color.colorWhite));

            try {
                if (integer1 == -1) {
                    img_weather.setImageResource(icMap1.get("未知"));
                } else {
                    img_weather.setImageResource(icMap1.get(weatherLive.getWeather()));
                }
            } catch (Exception e) {

            }
        }
    }


    //分享图片
    public void getImageByMap() {

        /**
         * 对地图进行截屏
         */
        aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
            @Override
            public void onMapScreenShot(Bitmap bitmap) {

            }

            @Override
            public void onMapScreenShot(Bitmap bitmap, int status) {

                try {
                    //弹出分享框
                    if (dialogFragment != null && dialogFragment.getDialog() != null && dialogFragment.getDialog().isShowing()) {
                        dialogFragment.dismiss();
                    }

                    if (dialogFragment != null) {
                        dialogFragment.setShareContent(BitmapUtils.createBitmapBottom(bitmap, BitmapUtils.getViewBitmap(tv_copyright_information)));
                        dialogFragment.setShareListener(ViewControlShare.getShareResultsDown(getBaseActivity(), dialogFragment, "tp"));
                        dialogFragment.setShareType(3);
                        dialogFragment.show(getBaseActivity().getFragmentManager(), "share");
                    }

                    hideLoading();
                } catch (Exception e) {
                    hideLoading();
                    e.printStackTrace();
                }
            }
        });
    }

    private void showLoading() {
        setProgressVisible(true);
    }

    private void hideLoading() {
        setProgressVisible(false);
    }

    private ShareDialogFragment dialogFragment;

    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    //定位当前位置
    public void presentLocate(int tag) {
        hintLine();

        mlocationClient = new AMapLocationClient(getBaseActivity());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //获取一次定位结果：该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        initStartStop(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()), tag, false, true);
                        showHintView();

                        switch (tag) {
                            case 1://司放地点
                                z_et_fly_place.setText(amapLocation.getAddress());
                                break;
                            case 2://归巢地点
                                z_et_homing_place.setText(amapLocation.getAddress());
                                break;
                        }

                        String preLo = LocationFormatUtils.GPS2AjLocation(amapLocation.getLongitude());
                        String preLa = LocationFormatUtils.GPS2AjLocation(amapLocation.getLatitude());


                        presentLo.setText("经度：" + LocationFormatUtils.strToD(preLo) + "度" + LocationFormatUtils.strToM(preLo) + "分" + LocationFormatUtils.strToS(preLo) + "秒");
                        presentLa.setText("纬度：" + LocationFormatUtils.strToD(preLa) + "度" + LocationFormatUtils.strToM(preLa) + "分" + LocationFormatUtils.strToS(preLa) + "秒");

                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            }
        });
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            UMShareAPI.get(getBaseActivity()).onActivityResult(requestCode, resultCode, data);

            ContactModel.MembersEntity mData = (ContactModel.MembersEntity) data.getSerializableExtra("data");
            if (resultCode == 0x0033) {
                //选择公棚地址()归巢地点
                Log.d("sousuo", "onActivityResult:1 " + mData.getLo() + "   la-->" + mData.getLa());
                sureGcdLo = Double.valueOf(mData.getLo());
                sureGcdLa = Double.valueOf(mData.getLa());

                z_et_homing_place.setText(mData.getUsername());
                initStartStop(new LatLng(sureGcdLa, sureGcdLo), 2, false, true);
                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                aMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(sureGcdLa, sureGcdLo)));
            } else if (resultCode == 0x0034) {
                //选择司放地位置
                Log.d("sousuo", "onActivityResult:2 " + mData.getLo() + "   la-->" + mData.getLa());

                sureSfdLo = LocationFormatUtils.Aj2GPSLocation(Double.valueOf(mData.getLo()));
                sureSfdLa = LocationFormatUtils.Aj2GPSLocation(Double.valueOf(mData.getLa()));

                z_et_fly_place.setText(mData.getUsername());
                initStartStop(new LatLng(sureSfdLa, sureSfdLo), 1, false, true);
                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                aMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(sureSfdLa, sureSfdLo)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
