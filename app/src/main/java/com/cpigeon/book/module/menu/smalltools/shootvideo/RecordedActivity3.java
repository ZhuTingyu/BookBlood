package com.cpigeon.book.module.menu.smalltools.shootvideo;


import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.base.util.utility.TimeUtil;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.MyApp;
import com.cpigeon.book.R;
import com.cpigeon.book.util.BitmapUtils;
import com.cpigeon.book.video.Constants;
import com.cpigeon.book.video.camera.SensorControler;
import com.cpigeon.book.video.utils.CameraUtil;
import com.cpigeon.book.video.widget.CameraView;
import com.cpigeon.book.video.widget.CircularProgressView;
import com.cpigeon.book.video.widget.FocusImageView;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by cj on 2017/7/25.
 * 鸽运通  训鸽通 拍照，拍摄视频        舍弃（参考）
 */

public class RecordedActivity3 extends Activity implements View.OnTouchListener, SensorControler.CameraFocusListener {

    @BindView(R.id.watermark_gezhu)
    TextView watermarkGeZhu;//鸽主名字
    @BindView(R.id.watermark_time)
    TextView watermarkTime;//水印时间
    @BindView(R.id.watermark_dz)
    TextView watermarkDz;//水印地址
    @BindView(R.id.watermark_lo)
    TextView watermarkLo;//水印经度
    @BindView(R.id.watermark_la)
    TextView watermarkLa;//水印纬度
    @BindView(R.id.watermark_hb)
    TextView watermarkHb;//水印海拔
    @BindView(R.id.watermark_llz)
    RelativeLayout watermarkLlz;//水印总的布局

    @BindView(R.id.watermark_center_img)
    ImageView waterCenImg;//图片中间水印

    @BindView(R.id.imgbtn_ture)
    ImageButton imgbtn_ture;//拍照确定

    @BindView(R.id.imgbtn_false)
    ImageButton imgbtn_false;//拍照取消

    @BindView(R.id.btn_flash_lamp)
    ImageView flash_light;//闪光灯

    @BindView(R.id.btn_paizhao)
    FrameLayout btn_paizhao;//拍照

    @BindView(R.id.layout_water_z)
    RelativeLayout layout_water_z;//全部水印布局

    @BindView(R.id.water_tag)
    TextView water_tag;//标签

    private CameraView mCameraView;
    private CircularProgressView mCapture;
    private FocusImageView mFocus;
    private static int maxTime = 11000;//最长录制11s
    private boolean pausing = false;
    private boolean recordFlag = false;//是否正在录制


    private long timeStep = 50;//进度条刷新的时间
    long timeCount = 0;//用于记录录制时间
    private boolean autoPausing = false;
    ExecutorService executorService;
    private SensorControler mSensorControler;

    private Unbinder mUnbinder;

    private String savePath;//视频保存路径
    private String type;

    private Intent intent1 = null;
    private String img_path;

    //闪光灯模式 0:关闭 1: 开启 2: 自动
    private int light_num = 0;

    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //时间
                    if (watermarkTime != null && watermarkLlz != null) {
                        watermarkTime.setText(TimeUtil.format(new Date().getTime(), TimeUtil.FORMAT_YYYYMMDDHHMMSS) + "   " + we + "  " + t + "℃" + "  " + wd + "风");
                        mCameraView.mCameraDrawer.getBitmap.setBitmap(BitmapUtils.getViewBitmap(layout_water_z), cameraTag);
                    }
                }
            });
        }
    };

    private Timer mTimer = new Timer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorde3);
        mUnbinder = ButterKnife.bind(this);

        executorService = Executors.newSingleThreadExecutor();
        mSensorControler = SensorControler.getInstance();
        mSensorControler.setCameraFocusListener(this);
        initView();
    }

    private void initView() {

        mCameraView = (CameraView) findViewById(R.id.camera_view);
        mCapture = (CircularProgressView) findViewById(R.id.mCapture);
        mFocus = (FocusImageView) findViewById(R.id.focusImageView);

        mCameraView.setOnTouchListener(this);

        type = getIntent().getStringExtra("type");

        if (getIntent().getIntExtra("video_time", -1) != -1) {
            maxTime = getIntent().getIntExtra("video_time", -1);
        } else {
            maxTime = 11000;//最长录制11s
        }

        if (getIntent().getStringExtra("label_tag") != null) {
            water_tag.setText(getIntent().getStringExtra("label_tag"));
        }

        if (type.equals("photo")) {
            photoOperation();//拍照
        } else if (type.equals("video")) {
            videoOperation();//拍摄视频
        }

        mTimer.schedule(mTimerTask, 0, 1000);
    }

    //-----------------------------------------------------生命周期（不动）------------------------------------------------------------------------
    @Override
    protected void onResume() {
        super.onResume();
        try {
            mCameraView.onResume();
            cameraTag = 1;

            if (recordFlag && autoPausing) {
                mCameraView.resume(true);
                autoPausing = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            cameraTag = 2;

            if (recordFlag && !pausing) {
                mCameraView.pause(true);
                autoPausing = true;
            }
            mCameraView.onPause();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isStop = false;

    @Override
    protected void onDestroy() {

        try {
            isStop = true;
            if (mLocationClient != null) {
                mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
                mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
            }

            mTimer.cancel();

            if (mCameraView.mCamera != null) {
                mCameraView.mCamera.close();
            }

            mCameraView.destroyDrawingCache();
            mUnbinder.unbind();//解除奶油刀绑定
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    //-----------------------------------------------------事件处理（操作）------------------------------------------------------------------------

    @OnClick({R.id.imgbtn_ture, R.id.imgbtn_false, R.id.btn_flash_lamp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_ture://拍照确定

                if (type.equals("video")) {
                    recordFlag = false;
                    recordComplete(savePath);
                } else {
                    imgbtn_ture();
                }
                break;
            case R.id.imgbtn_false://拍照取消
                imgbtn_false();
                break;
            case R.id.btn_flash_lamp://闪光灯
                setFlashLamp();
                break;
        }
    }

    /**
     * 打开闪光灯
     */
    private void setFlashLamp() {
        Camera.Parameters parameters = mCameraView.mCamera.mCamera.getParameters();

        switch (light_num) {
            case 0:
                //打开
                light_num = 1;
                flash_light.setImageResource(R.drawable.btn_camera_flash_on);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);//开启
                mCameraView.mCamera.mCamera.setParameters(parameters);
                break;
            case 1:
                //自动
                light_num = 2;
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                mCameraView.mCamera.mCamera.setParameters(parameters);
                flash_light.setImageResource(R.drawable.btn_camera_flash_auto);
                break;
            case 2:
                //关闭
                light_num = 0;
                //关闭
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCameraView.mCamera.mCamera.setParameters(parameters);
                flash_light.setImageResource(R.drawable.btn_camera_flash_off);
                break;
        }
    }

    /**
     * 拍照确定
     */
    private void imgbtn_ture() {
        try {
            recordFlag = false;

            switch (light_num) {
                case 0:
                    //关闭
                    CameraUtil.getInstance().turnLightOff(mCameraView.mCamera.mCamera);
                    break;
                case 1:
                    CameraUtil.getInstance().turnLightOn(mCameraView.mCamera.mCamera);
                    break;
                case 2:
                    //自动
                    CameraUtil.getInstance().turnLightAuto(mCameraView.mCamera.mCamera);
                    break;
            }

//            if (type.equals("photo")) {
//                intent1 = new Intent(RecordedActivity3.this, PhotoEditActivity.class);
//            }
//            intent1.putExtra("img_path", img_path);
//            startActivity(intent1);
//
//            RecordedActivity3.this.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void imgbtn_false() {
        btn_paizhao.setVisibility(View.VISIBLE);//拍照显示
        imgbtn_false.setVisibility(View.GONE);//取消按钮隐藏
        imgbtn_ture.setVisibility(View.GONE);//确定按钮隐藏

        mCameraView.onResume();
        mCameraView.resume(true);
        cameraTag = 1;
    }

    //视频录制成功返回
    private void recordComplete(final String path) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(1600);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    ToastUtils.showLong(RecordedActivity3.this, "视频录制成功");
                                    RecordedActivity3.this.finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }

//-----------------------------------------------------线程相关------------------------------------------------------------------------

    /**
     * 视频录制相关线程
     */
    Runnable recordRunnable = new Runnable() {
        @Override
        public void run() {
            recordFlag = true;
            pausing = false;
            autoPausing = false;
            timeCount = 0;
            long time = System.currentTimeMillis();
            savePath = Constants.getPath("record/", time + ".mp4");

            try {
                mCameraView.setSavePath(savePath);
                mCameraView.startRecord();
                while (timeCount <= maxTime && recordFlag) {
                    if (pausing || autoPausing) {
                        continue;
                    }
                    mCapture.setProcess((int) timeCount);
                    Thread.sleep(timeStep);
                    timeCount += timeStep;
                }
                Log.d("xiaohls", "run: 1");
                recordFlag = false;
                mCameraView.stopRecord();
                if (timeCount < 2000) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            imgbtn_false();
                            Toast.makeText(RecordedActivity3.this, "录像时间太短", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RecordedActivity3.this.imgbtn_false.setVisibility(View.VISIBLE);//取消显示
                            RecordedActivity3.this.imgbtn_ture.setVisibility(View.VISIBLE);//确定显示
                            RecordedActivity3.this.btn_paizhao.setVisibility(View.INVISIBLE);//拍照隐藏
                            mCapture.setProcess(0);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private int cameraTag = 1;

    //-----------------------------------------------------定位相关（不动）------------------------------------------------------------------------
    private String TAG = "AtAnyTimeShootingActivity";
    //初始化定位
    AMapLocationClient mLocationClient;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {

            try {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        aMapLocationSucceed(aMapLocation);
                    }
                }
            } catch (Exception e) {

            }
        }
    };
    /**
     * 初始化天气搜索相关
     */
    private WeatherSearchQuery mquery;
    private WeatherSearch mweathersearch;
    private LocalWeatherLive weatherlive;

    /**
     * 天气查询
     *
     * @param city 城市
     */
    public void initWeatherSearch(String city) {

        //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
        mquery = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        mweathersearch = new WeatherSearch(RecordedActivity3.this);

        mweathersearch.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
            @Override
            public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
                try {
                    if (rCode == 1000) {
                        if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                            weatherlive = weatherLiveResult.getLiveResult();
                            weatherSearchSucceed(weatherlive);
                        } else {
                        }
                    } else {
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int rCode) {
            }
        });
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }

//-----------------------------------------------------定位，天气查询成功回调（操作）------------------------------------------------------------------------

    private void aMapLocationSucceed(AMapLocation aMapLocation) {

//        try {
//            //地址
//            watermarkDz.setText(aMapLocation.getProvince() + aMapLocation.getCity() + aMapLocation.getDistrict() + aMapLocation.getStreet() + aMapLocation.getStreetNum());
//            //设置经纬度
//            watermarkLo.setText(GPSFormatUtils.loLaToDMS(aMapLocation.getLongitude()) + "E");
//            watermarkLa.setText(GPSFormatUtils.loLaToDMS(aMapLocation.getLatitude()) + "N");
//
//            watermarkHb.setText("海拔：" + CommonUitls.strTwo(aMapLocation.getAltitude()) + "米");
//
//            lo = CommonUitls.GPS2AjLocation(aMapLocation.getLongitude());//经度
//            la = CommonUitls.GPS2AjLocation(aMapLocation.getLatitude());//纬度
//            initWeatherSearch(aMapLocation.getCity());//天气查询
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


    private String lo = "0", la = "0", we = "暂无", t = "暂无", wp = "暂无", wd = "暂无";//we：天气名称 t：温度   wp：风力 wd：风向


    /**
     * 天气查询成功后执行
     */
    private void weatherSearchSucceed(LocalWeatherLive weatherlive) {
        //we：天气名称 t：温度   wp：风力 wd：风向
        we = weatherlive.getWeather();
        t = weatherlive.getTemperature();
        wd = weatherlive.getWindDirection();
        wp = weatherlive.getWindPower();
    }
//-----------------------------------------------------视频相关（操作）------------------------------------------------------------------------

    /**
     * 当前页面用于拍摄视频
     */
    private void videoOperation() {

        mCapture.setTotal(maxTime);

        mCapture.setOnClickListener(view -> {
            try {
//                            timeCount = 0;
                if (!recordFlag) {//是否正在录制

                    imgbtn_false.setVisibility(View.INVISIBLE);//取消显示
                    imgbtn_ture.setVisibility(View.INVISIBLE);//确定显示
                    btn_paizhao.setVisibility(View.VISIBLE);//拍照隐藏

                    executorService.execute(recordRunnable);
                } else {
                    mCameraView.resume(false);
                    pausing = false;

                    imgbtn_false.setVisibility(View.VISIBLE);//取消显示
                    imgbtn_ture.setVisibility(View.VISIBLE);//确定显示
                    btn_paizhao.setVisibility(View.INVISIBLE);//拍照隐藏

                    mCapture.setProcess(0);
//                        mCameraView.stopRecord();//停止记录
                    recordFlag = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mCameraView.getCameraId() == 1) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                float sRawX = event.getRawX();
                float sRawY = event.getRawY();
                float rawY = sRawY * MyApp.screenWidth / MyApp.screenHeight;
                float temp = sRawX;
                float rawX = rawY;
                rawY = (MyApp.screenWidth - temp) * MyApp.screenHeight / MyApp.screenWidth;

                Point point = new Point((int) rawX, (int) rawY);
                mCameraView.onFocus(point, callback);
                mFocus.startFocus(new Point((int) sRawX, (int) sRawY));
        }
        return true;
    }

    Camera.AutoFocusCallback callback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            //聚焦之后根据结果修改图片
            Log.e("hero", "----onAutoFocus====" + success);
            if (success) {
                mFocus.onFocusSuccess();
            } else {
                //聚焦失败显示的图片
                mFocus.onFocusFailed();
            }
        }
    };

    @Override
    public void onFocus() {
        if (mCameraView.getCameraId() == 1) {
            return;
        }
        Point point = new Point(MyApp.screenWidth / 2, MyApp.screenHeight / 2);
        mCameraView.onFocus(point, callback);
    }


//-----------------------------------------------------图片相关（操作）------------------------------------------------------------------------

    /**
     * 当前页面用于拍照
     */
    private void photoOperation() {
        mCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mCameraView.mCamera.mCamera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {

                            //将data 转换为位图 或者你也可以直接保存为文件使用 FileOutputStream
                            //这里我相信大部分都有其他用处把 比如加个水印 后续再讲解
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    cameraTag = 2;
                                    imgbtn_false.setVisibility(View.VISIBLE);//取消显示
                                    imgbtn_ture.setVisibility(View.VISIBLE);//确定显示
                                    btn_paizhao.setVisibility(View.INVISIBLE);//拍照隐藏

                                    img_path = getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() + File.separator + System.currentTimeMillis() + ".jpeg";
                                    //图片保存
                                    BitmapUtils.saveJPGE_After(RecordedActivity3.this, BitmapUtils.createBitmapCenter(BitmapUtils.createBitmapLowerLeft(BitmapUtils.rotaingImageView(90, BitmapFactory.decodeByteArray(data, 0, data.length)), BitmapUtils.convertViewToBitmap(watermarkLlz)), BitmapUtils.convertViewToBitmap(waterCenImg)), img_path, 85);

                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
