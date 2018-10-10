package com.cpigeon.book.module.menu.smalltools.shootvideo;


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

import com.base.util.IntentBuilder;
import com.base.util.LocationFormatUtils;
import com.base.util.dialog.DialogUtils;
import com.base.util.map.LocationLiveData;
import com.base.util.utility.LogUtil;
import com.base.util.utility.StringUtil;
import com.base.util.utility.TimeUtil;
import com.base.util.utility.ToastUtils;
import com.bumptech.glide.Glide;
import com.cpigeon.book.MyApp;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookActivity;
import com.cpigeon.book.model.entity.ShootInfoEntity;
import com.cpigeon.book.util.BitmapUtils;
import com.cpigeon.book.video.Constants;
import com.cpigeon.book.video.camera.SensorControler;
import com.cpigeon.book.video.widget.CameraView;
import com.cpigeon.book.video.widget.FocusImageView;
import com.cpigeon.book.widget.mydialog.LocalShareDialogFragment;

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
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by cj on 2017/7/25.
 * 鸽运通  训鸽通 拍照，拍摄视频
 */

public class RecordedActivity extends BaseBookActivity implements View.OnTouchListener, SensorControler.CameraFocusListener {

    @BindView(R.id.watermark_z)
    RelativeLayout watermark_z;//水印总布局
    @BindView(R.id.water_tv_name)
    TextView water_tv_name;
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

    @BindView(R.id.btn_video_record)
    FrameLayout btn_video_record;//视频录制

    @BindView(R.id.imgbtn_ture)
    ImageButton imgbtn_ture;//拍照确定]
    @BindView(R.id.btn_click_start)
    ImageView btn_click_start;//
    @BindView(R.id.btn_type_video)
    TextView btn_type_video;//
    @BindView(R.id.btn_type_photo)
    TextView btn_type_photo;

    @BindView(R.id.btn_cen)
    RelativeLayout btn_cen;

    @BindView(R.id.imgbtn_false)
    ImageButton imgbtn_false;//拍照取消

    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.img_logo)
    ImageView img_logo;

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
    private String type = "video";

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

            water_tv_lo.setText("经度：" + LocationFormatUtils.strToDMS(LocationFormatUtils.GPS2AjLocation(aMapLocation.getLongitude())));
            water_tv_la.setText("纬度：" + LocationFormatUtils.strToDMS(LocationFormatUtils.GPS2AjLocation(aMapLocation.getLatitude())));
            water_tv_address.setText(aMapLocation.getPoiName() + aMapLocation.getCity() + aMapLocation.getDistrict());
            water_tv_altitude.setText("海拔：" + aMapLocation.getAltitude() + "m");

            LogUtil.print(aMapLocation);
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUnbinder = ButterKnife.bind(this);
        executorService = Executors.newSingleThreadExecutor();
        mSensorControler = SensorControler.getInstance();
        mSensorControler.setCameraFocusListener(this);
        dialogFragment = new LocalShareDialogFragment();

        ShootInfoEntity mShootInfoEntity = getIntent().getParcelableExtra(IntentBuilder.KEY_DATA);


        if (StringUtil.isStringValid(mShootInfoEntity.getImgurl())) {
            img_logo.setVisibility(View.VISIBLE);
            Glide.with(getBaseActivity()).load(mShootInfoEntity.getImgurl()).into(img_logo);
        } else {
            img_logo.setVisibility(View.GONE);
        }

        if (StringUtil.isStringValid(mShootInfoEntity.getGsname())) {
            water_tv_name.setVisibility(View.VISIBLE);
            water_tv_name.setText("鸽舍：" + mShootInfoEntity.getGsname());
        } else {
            water_tv_name.setVisibility(View.GONE);
        }

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

    @OnClick({R.id.imgbtn_ture, R.id.imgbtn_false, R.id.btn_cen, R.id.btn_type_video, R.id.btn_type_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_ture://确定

                if (type.equals("video")) {
                    recordFlag = false;
                    recordComplete(savePath);
                } else {
                    showShareDialog(2);
                }
                break;
            case R.id.imgbtn_false://取消
                initBtn();
                break;

            case R.id.btn_cen:
                switch (type) {
                    case "video":
                        //拍摄视频
                        videoOperation();
                        break;
                    case "photo":
                        photoOperation();
                        break;
                    default:

                }
                break;
            case R.id.btn_type_video:
                //拍摄视频

                if (recordFlag) {
                    ToastUtils.showLong(getBaseActivity(), "当前正在录制！请结束后继续");
                    return;
                }

                type = "video";
                btn_type_video.setTextColor(getResources().getColor(R.color.colorPrimary));
                btn_type_photo.setTextColor(getResources().getColor(R.color.color_text_cancel));

                initBtn();

                break;
            case R.id.btn_type_photo:
                //拍摄照片
                if (recordFlag) {
                    ToastUtils.showLong(getBaseActivity(), "当前正在录制！请结束后继续");
                    return;
                }

                type = "photo";
                btn_type_video.setTextColor(getResources().getColor(R.color.color_text_cancel));
                btn_type_photo.setTextColor(getResources().getColor(R.color.colorPrimary));

                initBtn();

                break;
        }
    }

    private void initBtn() {
        btn_click_start.setVisibility(View.VISIBLE);//开始按钮显示
        btn_video_record.setVisibility(View.GONE);//录像隐藏
        imgbtn_false.setVisibility(View.GONE);//取消按钮隐藏
        imgbtn_ture.setVisibility(View.GONE);//确定按钮隐藏

        btn_cen.setVisibility(View.VISIBLE);
        isShootComplete = false;
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
                                    showShareDialog(1);
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

    private LocalShareDialogFragment dialogFragment;

    public void showShareDialog(int tag) {
        if (dialogFragment != null && dialogFragment.getDialog() != null && dialogFragment.getDialog().isShowing()) {
            dialogFragment.getDialog().dismiss();
            dialogFragment.dismiss();
        }

        String error = "";
        if (tag == 1) {
            //视频分享
            error = "是否将该视频分享给鸽友";
        } else if (tag == 2) {
            //图片分享
            error = "是否将该图片分享给鸽友";
        }

        //保证界面只有一个错误提示
        if (getBaseActivity().errorDialog == null || !getBaseActivity().errorDialog.isShowing()) {
            getBaseActivity().errorDialog = DialogUtils.createHintDialog2(this, error, SweetAlertDialog.NORMAL_TYPE, false, dialog -> {
                dialog.dismiss();
                initBtn();

                if (dialogFragment != null) {
                    dialogFragment.setLocalFilePath(savePath);
                    dialogFragment.setFileType(type);
                    dialogFragment.show(getBaseActivity().getFragmentManager(), "share");
                }
            }, sweetAlertDialog -> {
                initBtn();
                sweetAlertDialog.dismiss();
            });
        }
    }


    /**
     * 当前页面用于拍照
     */
    private boolean isShootComplete = false;

    private void photoOperation() {
        btn_cen.setVisibility(View.INVISIBLE);
        mCameraView.mCamera.mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                //将data 转换为位图 或者你也可以直接保存为文件使用 FileOutputStream
                //这里我相信大部分都有其他用处把 比如加个水印 后续再讲解
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cameraTag = 2;
                        isShootComplete = true;

                        btn_click_start.setVisibility(View.GONE);//开始按钮
                        btn_video_record.setVisibility(View.GONE);//录像
                        imgbtn_false.setVisibility(View.VISIBLE);//取消按钮
                        imgbtn_ture.setVisibility(View.VISIBLE);//确定按钮

                        savePath = getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() + File.separator + System.currentTimeMillis() + ".jpeg";

                        //图片保存
                        BitmapUtils.saveJPGE_After(RecordedActivity.this,
                                BitmapUtils.createBitmapCenter2(
                                        BitmapUtils.rotaingImageView(90, BitmapFactory.decodeByteArray(data, 0, data.length)),
                                        BitmapUtils.getViewBitmap(watermark_z))
                                , savePath, 100);
                    }
                });
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

//            savePath = Constants.getPath("record/", time + ".mp4");
            savePath = Constants.getPath(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "record/", time + ".mp4");

            try {
                mCameraView.setSavePath(savePath);
                mCameraView.startRecord();
//                while (timeCount <= maxTime && recordFlag) {
                while (recordFlag) {
                    if (pausing || autoPausing) {
                        continue;
                    }
                    Thread.sleep(timeStep);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_time.setText(TimeUtil.format(timeCount, TimeUtil.FORMAT_MMSS));
                        }
                    });
                    timeCount += timeStep;
                }
                Log.d("xiaohls", "run: 1");
                recordFlag = false;
                mCameraView.stopRecord();
                if (timeCount < 2000) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initBtn();
                            Toast.makeText(RecordedActivity.this, "录像时间太短", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btn_click_start.setVisibility(View.GONE);//开始按钮
                            btn_video_record.setVisibility(View.GONE);//录像
                            imgbtn_false.setVisibility(View.VISIBLE);//取消按钮
                            imgbtn_ture.setVisibility(View.VISIBLE);//确定按钮

                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private int cameraTag = 1;

//-----------------------------------------------------视频相关（操作）------------------------------------------------------------------------

    /**
     * 当前页面用于拍摄视频
     */
    private void videoOperation() {

        try {
            if (recordFlag) {//是否正在录制

                mCameraView.resume(false);
                pausing = false;
                recordFlag = false;

                btn_click_start.setVisibility(View.GONE);//开始按钮
                btn_video_record.setVisibility(View.GONE);//录像
                imgbtn_false.setVisibility(View.VISIBLE);//取消按钮
                imgbtn_ture.setVisibility(View.VISIBLE);//确定按钮

            } else {
                btn_click_start.setVisibility(View.GONE);//开始按钮
                btn_video_record.setVisibility(View.VISIBLE);//录像
                imgbtn_false.setVisibility(View.GONE);//取消按钮
                imgbtn_ture.setVisibility(View.GONE);//确定按钮

                executorService.execute(recordRunnable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
