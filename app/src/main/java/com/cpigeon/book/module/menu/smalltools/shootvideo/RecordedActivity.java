package com.cpigeon.book.module.menu.smalltools.shootvideo;


import android.content.Intent;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.util.map.LocationLiveData;
import com.base.util.utility.LogUtil;
import com.base.util.utility.TimeUtil;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.MyApp;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookActivity;
import com.cpigeon.book.util.BitmapUtils;
import com.cpigeon.book.video.Constants;
import com.cpigeon.book.video.camera.SensorControler;
import com.cpigeon.book.video.utils.CameraUtil;
import com.cpigeon.book.video.widget.CameraView;
import com.cpigeon.book.video.widget.FocusImageView;

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
 * 鸽运通  训鸽通 拍照，拍摄视频
 */

public class RecordedActivity extends BaseBookActivity implements View.OnTouchListener, SensorControler.CameraFocusListener {


    @BindView(R.id.watermark_z)
    RelativeLayout watermark_z;//谁有总布局
    @BindView(R.id.water_tv_time)
    TextView watermarkTime;//水印时间
    @BindView(R.id.water_tv_lo)
    TextView water_tv_lo;//经度
    @BindView(R.id.water_tv_la)
    TextView water_tv_la;//纬度
    @BindView(R.id.water_tv_address)
    TextView water_tv_address;//地址
    @BindView(R.id.water_tv_altitude)
    TextView water_tv_altitude;//海拔

    @BindView(R.id.btn_paizhao)
    FrameLayout btn_paizhao;//拍照

    @BindView(R.id.imgbtn_ture)
    ImageButton imgbtn_ture;//拍照确定

    @BindView(R.id.imgbtn_false)
    ImageButton imgbtn_false;//拍照取消

    private CameraView mCameraView;
    private View mCapture;
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

    private Timer mTimer = new Timer();

    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //时间
                    if (watermarkTime != null && watermark_z != null) {
                        watermarkTime.setText(TimeUtil.format(new Date().getTime(), TimeUtil.FORMAT_YYYYMMDDHHMMSS));
                        mCameraView.mCameraDrawer.getBitmap.setBitmap(BitmapUtils.getViewBitmap(watermark_z), cameraTag);
                    }
                }
            });
        }
    };

    @Override
    protected void initObserve() {
        super.initObserve();

        LocationLiveData.get(true).observe(this, aMapLocation -> {
            Log.d("dingwei", "initObserve: 城市--》" + aMapLocation.getCity());

            water_tv_lo.setText("经度：" + aMapLocation.getLongitude());
            water_tv_la.setText("纬度：" + aMapLocation.getLatitude());
            water_tv_address.setText(aMapLocation.getAddress());
            water_tv_altitude.setText("海拔：" + aMapLocation.getAltitude() + "m");

            LogUtil.print(aMapLocation);
//            WeatherLiveData.get(aMapLocation.getCity()).observe(this, localWeatherLive -> {
//            Log.d("dingwei", "initObserve: 天气" + localWeatherLive.getWeather());
//                mPairingNestAddViewModel.weather = localWeatherLive.getWeather();//天气
//                mPairingNestAddViewModel.temper = localWeatherLive.getTemperature();//气温
//                mPairingNestAddViewModel.hum = localWeatherLive.getHumidity();//湿度
//                mPairingNestAddViewModel.dir = localWeatherLive.getWindDirection();//风向
//            });
        });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUnbinder = ButterKnife.bind(this);
        executorService = Executors.newSingleThreadExecutor();
        mSensorControler = SensorControler.getInstance();
        mSensorControler.setCameraFocusListener(this);
        initView();
        initObserve();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_recorde;
    }

    private void initView() {

        mCameraView = findViewById(R.id.camera_view);
        mCapture = findViewById(R.id.mCapture);
        mFocus = findViewById(R.id.focusImageView);

        mCameraView.setOnTouchListener(this);

        type = getIntent().getStringExtra("type");

        if (type.equals("photo")) {

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

    @OnClick({R.id.imgbtn_ture, R.id.imgbtn_false})
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

                                    ToastUtils.showLong(RecordedActivity.this, "视频录制成功");
                                    RecordedActivity.this.finish();
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
                            Toast.makeText(RecordedActivity.this, "录像时间太短", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RecordedActivity.this.imgbtn_false.setVisibility(View.VISIBLE);//取消显示
                            RecordedActivity.this.imgbtn_ture.setVisibility(View.VISIBLE);//确定显示
                            RecordedActivity.this.btn_paizhao.setVisibility(View.INVISIBLE);//拍照隐藏
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
    private String TAG = "RecordedActivity";


//-----------------------------------------------------视频相关（操作）------------------------------------------------------------------------

    /**
     * 当前页面用于拍摄视频
     */
    private void videoOperation() {


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

}
