package com.cpigeon.book.module.menu;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.util.SharedPreferencesTool;
import com.cpigeon.book.widget.mydialog.TimeRangePickerDialog;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * hl 推送设置
 * Created by Administrator on 2018/8/8.
 */

public class PushSetFragment extends BaseBookFragment {


    public static final String SETTING_KEY_PUSH_ENABLE = "push-enable";
    public static final String SETTING_KEY_PUSH_NOTIFICATION = "push-notification";
    public static final String SETTING_KEY_PUSH_SILENCE_START = "push_silence_start";
    public static final String SETTING_KEY_PUSH_SILENCE_END = "push-silence_end";


    @BindView(R.id.sb_push_info)
    SwitchButton sb_push_info;
    @BindView(R.id.sb_voice_hint)
    SwitchButton sb_voice_hint;//声音提示

    @BindView(R.id.sb_vibration_hint)
    SwitchButton sb_vibration_hint;

    @BindView(R.id.sb_dont_disturb)
    SwitchButton sb_dont_disturb;
    @BindView(R.id.ll_dont_disturb)
    LinearLayout ll_dont_disturb;//

    @BindView(R.id.tv_time_start)
    TextView tv_time_start;//
    @BindView(R.id.tv_time_end)
    TextView tv_time_end;//

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, PushSetFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_push_set, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("推送设置");

        initData();
    }

    private void initSwitchButton(SwitchButton mSwitchButton) {
        if (mSwitchButton.isChecked()) {
            mSwitchButton.setBackColorRes(R.color.colorPrimary);
        } else {
            mSwitchButton.setBackColorRes(R.color.gray);
        }
    }

    public void initData() {
        boolean pushArgs = SharedPreferencesTool.Get(getBaseActivity(), SETTING_KEY_PUSH_ENABLE, true, SharedPreferencesTool.SP_FILE_APPSETTING);
        int pushNotification = SharedPreferencesTool.Get(getBaseActivity(), SETTING_KEY_PUSH_NOTIFICATION, ~0, SharedPreferencesTool.SP_FILE_APPSETTING);

        sb_push_info.setChecked(pushArgs);
        sb_voice_hint.setChecked(pushNotification == ~0 || (pushNotification & Notification.DEFAULT_VIBRATE) == Notification.DEFAULT_VIBRATE);
        sb_vibration_hint.setChecked(pushNotification == ~0 || (pushNotification & Notification.DEFAULT_SOUND) == Notification.DEFAULT_SOUND);

        initSwitchButton(sb_push_info);
        initSwitchButton(sb_voice_hint);
        initSwitchButton(sb_vibration_hint);


        sb_push_info.setOnCheckedChangeListener(onCheckedChangeListener);
        sb_voice_hint.setOnCheckedChangeListener(onCheckedChangeListener);
        sb_vibration_hint.setOnCheckedChangeListener(onCheckedChangeListener);
        sb_dont_disturb.setOnCheckedChangeListener(onCheckedChangeListener);

        int startTime = SharedPreferencesTool.Get(getBaseActivity(), SETTING_KEY_PUSH_SILENCE_START, 0, SharedPreferencesTool.SP_FILE_APPSETTING);
        int endTime = SharedPreferencesTool.Get(getBaseActivity(), SETTING_KEY_PUSH_SILENCE_END, 0, SharedPreferencesTool.SP_FILE_APPSETTING);
        startHour = startTime / 60;
        startMinute = startTime % 60;
        endHour = endTime / 60;
        endMinute = endTime % 60;

        refreshPushSilence(false);
    }


    @OnClick({R.id.ll_time_start, R.id.ll_time_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_time_start:
                //起始时间
                showDialog();

                break;
            case R.id.ll_time_end:
                //结束时间
                showDialog();
                break;
        }
    }


    int startHour, startMinute, endHour, endMinute;

    private void showDialog() {
        //时间选择器
        final TimeRangePickerDialog timePickerDialog = TimeRangePickerDialog.newInstance(new TimeRangePickerDialog.OnTimeRangeSelectedListener() {
            @Override
            public void onTimeRangeSelected(int startHour, int startMin, int endHour, int endMin) {
                PushSetFragment.this.startHour = startHour;
                PushSetFragment.this.startMinute = startMin;
                PushSetFragment.this.endHour = endHour;
                PushSetFragment.this.endMinute = endMin;
                refreshPushSilence(false);
        }
        });

        timePickerDialog.setStartHour(startHour);
        timePickerDialog.setStartMinute(startMinute);
        timePickerDialog.setEndHour(endHour);
        timePickerDialog.setEndMinute(endMinute);
        timePickerDialog.show(getBaseActivity().getFragmentManager(), "123");
    }


    private void refreshPushSilence(boolean isShowDialog) {

        Log.d("xiaohll", "refreshPushSilence: 1");
        tv_time_start.setText(String.valueOf(startHour + ":" + startMinute));
        tv_time_end.setText(String.valueOf(endHour + ":" + endMinute));

        if (startHour == 0 && startMinute == 0 && endHour == 0 && endMinute == 0) {
            sb_dont_disturb.setChecked(false);
            ll_dont_disturb.setVisibility(View.GONE);
        } else {
            if (isShowDialog) {
                showDialog();
            }

            sb_dont_disturb.setChecked(true);
            ll_dont_disturb.setVisibility(View.VISIBLE);
        }

        JPushInterface.setSilenceTime(getBaseActivity().getApplicationContext(), startHour, startMinute, endHour, endMinute);

        SharedPreferencesTool.Save(getBaseActivity(), SETTING_KEY_PUSH_SILENCE_START, startHour * 60 + startMinute, SharedPreferencesTool.SP_FILE_APPSETTING);
        SharedPreferencesTool.Save(getBaseActivity(), SETTING_KEY_PUSH_SILENCE_END, endHour * 60 + endMinute, SharedPreferencesTool.SP_FILE_APPSETTING);

        initSwitchButton(sb_dont_disturb);
    }


    private boolean showTag = false;

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.sb_push_info:
                    SharedPreferencesTool.Save(getBaseActivity(), SETTING_KEY_PUSH_ENABLE, isChecked, SharedPreferencesTool.SP_FILE_APPSETTING);
                    if (isChecked) {
                        JPushInterface.resumePush(getBaseActivity().getApplicationContext());
                    } else {
                        JPushInterface.stopPush(getBaseActivity().getApplicationContext());
                    }

                    initSwitchButton(sb_push_info);

                    if (sb_push_info.isChecked()) {

                    } else {
                        sb_voice_hint.setChecked(false);
                        sb_vibration_hint.setChecked(false);
                        sb_dont_disturb.setChecked(false);
                    }
                    break;
                case R.id.sb_voice_hint:
                case R.id.sb_vibration_hint:

                    initSwitchButton(sb_voice_hint);
                    initSwitchButton(sb_vibration_hint);

                    BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(getBaseActivity());
                    //builder.statusBarDrawable = R.drawable.jpush_notification_icon;
                    //builder.notificationFlags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
                    builder.notificationDefaults = 0;

                    if (sb_voice_hint.isChecked()) {
                        builder.notificationDefaults |= Notification.DEFAULT_VIBRATE;
                    }
                    if (sb_vibration_hint.isChecked()) {
                        builder.notificationDefaults |= Notification.DEFAULT_SOUND;
                    }

                    SharedPreferencesTool.Save(getBaseActivity(), SETTING_KEY_PUSH_NOTIFICATION, builder.notificationDefaults, SharedPreferencesTool.SP_FILE_APPSETTING);
                    JPushInterface.setDefaultPushNotificationBuilder(builder);
                    break;

                case R.id.sb_dont_disturb:
                    if (sb_dont_disturb.isChecked()) {
                        ll_dont_disturb.setVisibility(View.VISIBLE);
                        initSwitchButton(sb_dont_disturb);
                    } else {
                        startHour = 0;
                        startMinute = 0;
                        endHour = 0;
                        endMinute = 0;
                        ll_dont_disturb.setVisibility(View.GONE);
                        refreshPushSilence(false);
                    }
                    break;
            }
        }
    };

}
