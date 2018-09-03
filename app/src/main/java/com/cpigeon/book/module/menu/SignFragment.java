package com.cpigeon.book.module.menu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.RxUtils;
import com.base.util.utility.TimeUtil;
import com.base.util.utility.ToastUtils;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.model.entity.SignInfoEntity;
import com.cpigeon.book.module.menu.adapter.SignRuleAdapter;
import com.cpigeon.book.module.menu.animation.SignAnimation;
import com.cpigeon.book.module.menu.viewmodel.SignViewModel;
import com.cpigeon.book.widget.mydialog.HintDialog;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 签到
 * Created by Administrator on 2018/8/9.
 */

public class SignFragment extends BaseBookFragment {

    @BindView(R.id.calendarView)
    CalendarView mCalendarView;

    @BindView(R.id.fl_sign)
    FrameLayout fl_sign;//需要旋转

    @BindView(R.id.rv_guize)
    RecyclerView rv_guize;//签到规则
    SignRuleAdapter mSignRuleAdapter;

    @BindView(R.id.img_sign)
    ImageView img_sign;
    @BindView(R.id.tv_gebi_add)
    TextView tv_gebi_add;
    @BindView(R.id.tv_sign_hint)
    TextView tv_sign_hint;

    private SignViewModel mSignViewModel;

    private boolean isSign = false;//今天是否已签到
    private SignAnimation myYAnimation;
    private String[] wGift;

    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, SignFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSignViewModel = new SignViewModel();
        initViewModel(mSignViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("签到");

        myYAnimation = new SignAnimation();
        myYAnimation.setRepeatCount(Animation.INFINITE); //旋转的次数（无数次）


        //当前的年月
        year = mCalendarView.getCurYear();
        month = mCalendarView.getCurMonth();

        //只能当前月份
        mCalendarView.setRange(year, month, year, month);

        mSignRuleAdapter = new SignRuleAdapter(null);
        rv_guize.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_guize.setAdapter(mSignRuleAdapter);

        //获取签到规则
        mSignViewModel.getZGW_Users_SignGuiZeData();

        //获取签到信息
        mSignViewModel.getUserSignInfoData();

        tv_gebi_add.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "SF-PRO-TEXT_REGULAR.TTF"));


        mCalendarView.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Calendar calendar, boolean isClick) {
                try {
                    if (isClick) {
                        if (Arrays.asList(wGift).contains(String.valueOf(calendar.getDay()))) {
                            ToastUtils.showLong(getBaseActivity(),"暂未达到领取条件，请再接再厉！");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick({R.id.btn_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign:
                //点击签到
                if (!isSign) {
                    mSignViewModel.getZGW_Users_SignIn();
                    fl_sign.startAnimation(myYAnimation);
                }
                break;
        }
    }


    @Override
    protected void initObserve() {
        super.initObserve();

        //签到规则
        mSignViewModel.pigeonFriendMsgListData.observe(this, signRuleListEntities -> {
            mSignRuleAdapter.setNewData(signRuleListEntities);
            mSignRuleAdapter.notifyDataSetChanged();
        });

        //获取签到信息
        mSignViewModel.mSignInfoEntity.observe(this, mSignInfoEntity -> {
            initView(mSignInfoEntity);
        });

        //签到返回
        mSignViewModel.mSignClickData.observe(this, signClickEntity -> {
            RxUtils.delayed(2000, aLong -> {
                try {
                    myYAnimation.reset();
                    myYAnimation.cancel();

                    if (signClickEntity != null) {


                        img_sign.setVisibility(View.GONE);
                        tv_gebi_add.setVisibility(View.VISIBLE);
                        tv_gebi_add.setText("+" + signClickEntity.getGb());
                        tv_sign_hint.setText("已签到");

                        if (Integer.valueOf(signClickEntity.getLbgb()) > 0) {
                            HintDialog.shootHintDialog(getActivity(), "已成功获取礼包：鸽币+" + signClickEntity.getLbgb() + "!");
                        }

                        mSignViewModel.getUserSignInfoData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private int mMax = TimeUtil.getDayOfMonth();
    //当前的年月
    private int year;
    private int month;

    private void initView(SignInfoEntity mSignInfoEntity) {

        isSign = mSignInfoEntity.isSigned();//今天是否已签到

        if (isSign) {
            img_sign.setVisibility(View.GONE);
            tv_gebi_add.setVisibility(View.VISIBLE);
            tv_gebi_add.setText("+" + mSignInfoEntity.getSignSetting());
            tv_sign_hint.setText("已签到");
        }

        Log.d("xiaoooos", "initView: " + mMax);
        //已签到的日期
        String[] ySgin = mSignInfoEntity.getSignDays().split(",");

        //已领取的礼包
        List<SignInfoEntity.GiftdataBean> ylq = mSignInfoEntity.getGiftdata();

        ArrayList<String> yGiftArr = new ArrayList<>();

        for (SignInfoEntity.GiftdataBean item : ylq) {
            String[] s = item.getDate().split("-");
            try {
                yGiftArr.add(String.valueOf(Integer.valueOf(s[2])));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String[] yGift = Lists.toArray(String.class, yGiftArr);

        //未领取的礼包
        List<SignInfoEntity.GiftSettingsBean> wlq = mSignInfoEntity.getGiftSettings();
        ArrayList<String> wGiftArr = new ArrayList<>();

        for (SignInfoEntity.GiftSettingsBean item : wlq) {
            String[] s = item.getDate().split("-");
            try {
                wGiftArr.add(String.valueOf(Integer.valueOf(s[2])));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        wGift = Lists.toArray(String.class, wGiftArr);


//        //已签到的日期
////        String[] ySgin = {"2", "4", "5", "6", "8", "10", "12", "13", "14", "15", "16", "17", "18", "19"};
//        String[] ySgin = {"4", "5"};
//        //未领取的礼包日期
//        String[] wGift = {"20", "23", "28"};
//        //已领取礼包的日期
//        String[] yGift = {"5", "15"};

        List<Calendar> schemes = new ArrayList<>();

        for (int x = 1; x <= mMax; x++) {

            //未领取的礼包
            for (int i = 0; i < wGift.length; i++) {
                if (wGift[i].equals(String.valueOf(x))) {
                    //未领取的礼包
                    schemes.add(getSchemeCalendar(x, 5));
                }
            }

            //已领取的礼包
            for (int i = 0; i < yGift.length; i++) {
                if (yGift[i].equals(String.valueOf(x))) {
                    //已领取的礼包
                    schemes.add(getSchemeCalendar(x, 6));
                }
            }

            //已签到
            for (int i = 0; i < ySgin.length; i++) {
                if (ySgin[i].equals(String.valueOf(x))) {
                    if (Arrays.asList(yGift).contains(ySgin[i])) {
                        //含有礼包
                    } else {
                        //未包含礼包
                        if (ySgin.length == 1) {
                            schemes.add(getSchemeCalendar(x, 0));
                        } else if (ySgin.length == 2) {

                            if (Integer.valueOf(ySgin[1]) == Integer.valueOf(ySgin[0]) + 1) {
                                //只签到两天 且连在一起
                                String sss1 = year + "-" + month + "-" + Integer.valueOf(ySgin[0]);

                                if (TimeUtil.getWeekOfDate(sss1) == 6 || TimeUtil.getWeekOfDate(sss1) == 0) {
                                    schemes.add(getSchemeCalendar(Integer.valueOf(ySgin[0]), 0));
                                    schemes.add(getSchemeCalendar(Integer.valueOf(ySgin[1]), 0));
                                } else {
                                    schemes.add(getSchemeCalendar(Integer.valueOf(ySgin[0]), 2));
                                    schemes.add(getSchemeCalendar(Integer.valueOf(ySgin[1]), 1));
                                }

                            } else {
                                //签到 没有连在一起
                                schemes.add(getSchemeCalendar(Integer.valueOf(ySgin[0]), 0));
                                schemes.add(getSchemeCalendar(Integer.valueOf(ySgin[1]), 0));
                            }
                        } else if (ySgin.length >= 3) {
                            //已签到的天数大于三天

                            //签到第一天
                            if (x == Integer.valueOf(ySgin[0]) && !Arrays.asList(yGift).contains(ySgin[0]) && !Arrays.asList(wGift).contains(ySgin[0])) {
                                //  今天没有礼包
                                if (Integer.valueOf(ySgin[0]) + 1 == Integer.valueOf(ySgin[1])) {
                                    //签到 跟后一天连在一起
                                    schemes.add(getSchemeCalendar(x, 2));
                                } else {
                                    schemes.add(getSchemeCalendar(x, 0));
                                }
                            }

                            //签到最后一天
                            if (x == Integer.valueOf(ySgin[ySgin.length - 1]) && !Arrays.asList(yGift).contains(ySgin[ySgin.length - 1]) && !Arrays.asList(wGift).contains(ySgin[ySgin.length - 1])) {
                                //  今天没有礼包
                                String sss = year + "-" + month + "-" + Integer.valueOf(ySgin[ySgin.length - 1]);

                                if (Arrays.asList(wGift).contains(String.valueOf(Integer.valueOf(ySgin[ySgin.length - 1]) + 1))) {
                                    //最后一天的  下一天 有礼包
                                    if (TimeUtil.getWeekOfDate(sss) == 6) {
                                        //今天是周六
                                        if (Integer.valueOf(ySgin[ySgin.length - 2]) + 1 == Integer.valueOf(ySgin[ySgin.length - 1])) {
                                            //签到 跟前一天连在一起
                                            schemes.add(getSchemeCalendar(x, 1));
                                        } else {
                                            schemes.add(getSchemeCalendar(x, 0));
                                        }

                                    } else if (TimeUtil.getWeekOfDate(sss) == 0) {
                                        //今天是周天
                                        schemes.add(getSchemeCalendar(x, 0));
                                    } else {
                                        // 不是周末周天
                                        if (Integer.valueOf(ySgin[ySgin.length - 2]) + 1 == Integer.valueOf(ySgin[ySgin.length - 1])) {
                                            //签到 跟前一天连在一起
                                            schemes.add(getSchemeCalendar(x, 1));
                                        } else {
                                            schemes.add(getSchemeCalendar(x, 0));
                                        }
                                    }
                                } else {
                                    if (TimeUtil.getWeekOfDate(sss) == 6) {
                                        //今天是周六
                                        if (Integer.valueOf(ySgin[ySgin.length - 2]) + 1 == Integer.valueOf(ySgin[ySgin.length - 1])) {
                                            //签到 跟前一天连在一起
                                            schemes.add(getSchemeCalendar(x, 1));
                                        } else {
                                            schemes.add(getSchemeCalendar(x, 0));
                                        }

                                    } else if (TimeUtil.getWeekOfDate(sss) == 0) {
                                        //今天是周天
                                        schemes.add(getSchemeCalendar(x, 0));
                                    } else {
                                        // 不是周末周天
                                        if (Integer.valueOf(ySgin[ySgin.length - 2]) + 1 == Integer.valueOf(ySgin[ySgin.length - 1])) {
                                            //签到 跟前一天连在一起

                                            if (Arrays.asList(yGift).contains(ySgin[ySgin.length - 2]) || Arrays.asList(wGift).contains(ySgin[ySgin.length - 2])){
                                                schemes.add(getSchemeCalendar(x, 0));
                                            }else {
                                                schemes.add(getSchemeCalendar(x, 1));
                                            }

                                        } else {
                                            schemes.add(getSchemeCalendar(x, 0));
                                        }
                                    }
                                }
                            }

                            if (i != 0 && i != ySgin.length - 1) {
                                for (int ms = 1; ms < ySgin.length - 1; ms++) {

                                    if (x == Integer.valueOf(ySgin[ms]) && !Arrays.asList(yGift).contains(ySgin[ms]) && !Arrays.asList(wGift).contains(ySgin[ms])) {
                                        //  今天没有礼包
                                        String sss1 = year + "-" + month + "-" + Integer.valueOf(ySgin[ms]);

                                        if (Integer.valueOf(ySgin[ms - 1]) + 1 == Integer.valueOf(ySgin[ms]) && Integer.valueOf(ySgin[ms]) == Integer.valueOf(ySgin[ms + 1]) - 1) {
                                            //已签到的三天连在一起
                                            if (TimeUtil.getWeekOfDate(sss1) == 6) {
                                                //今天是周六

                                                if (Arrays.asList(yGift).contains(ySgin[ms - 1]) || Arrays.asList(wGift).contains(ySgin[ms - 1])) {
                                                    //上一天 有已领取礼包  或者上一天有 未领取礼包
                                                    schemes.add(getSchemeCalendar(x, 0));
                                                } else {
                                                    schemes.add(getSchemeCalendar(x, 1));
                                                }

                                            } else if (TimeUtil.getWeekOfDate(sss1) == 0) {
                                                //今天是周天
                                                if (Arrays.asList(yGift).contains(ySgin[ms + 1]) || Arrays.asList(wGift).contains(ySgin[ms + 1])) {
                                                    //下一天 有已领取礼包  或者上一天有 未领取礼包
                                                    schemes.add(getSchemeCalendar(x, 0));
                                                } else {
                                                    schemes.add(getSchemeCalendar(x, 2));
                                                }
                                            } else {
                                                // 不是周末周天
                                                //今天没有礼包
                                                if (!Arrays.asList(yGift).contains(ySgin[ms]) && !Arrays.asList(wGift).contains(ySgin[ms])) {

                                                    if ((Arrays.asList(yGift).contains(ySgin[ms - 1]) || Arrays.asList(wGift).contains(ySgin[ms - 1]))
                                                            && (Arrays.asList(yGift).contains(ySgin[ms + 1]) || Arrays.asList(wGift).contains(ySgin[ms + 1]))
                                                            ) {
                                                        //上一天 有礼包   并且   下一天有 礼包

                                                        schemes.add(getSchemeCalendar(x, 0));
                                                    } else if ((Arrays.asList(yGift).contains(ySgin[ms - 1]) || Arrays.asList(wGift).contains(ySgin[ms - 1]))
                                                            && !(Arrays.asList(yGift).contains(ySgin[ms + 1]) || Arrays.asList(wGift).contains(ySgin[ms + 1]))
                                                            ) {
                                                        //上一天 有礼包   并且   下一天 没有 礼包

                                                        schemes.add(getSchemeCalendar(x, 2));
                                                    } else if (!(Arrays.asList(yGift).contains(ySgin[ms - 1]) || Arrays.asList(wGift).contains(ySgin[ms - 1]))
                                                            && (Arrays.asList(yGift).contains(ySgin[ms + 1]) || Arrays.asList(wGift).contains(ySgin[ms + 1]))
                                                            ) {
                                                        //上一天 没有礼包   并且   下一天 有 礼包
                                                        schemes.add(getSchemeCalendar(x, 1));
                                                    } else {
                                                        schemes.add(getSchemeCalendar(x, 3));
                                                    }
                                                }
                                            }


                                        } else if (Integer.valueOf(ySgin[ms - 1]) + 1 == Integer.valueOf(ySgin[ms]) && Integer.valueOf(ySgin[ms]) != Integer.valueOf(ySgin[ms + 1]) - 1) {
                                            //前两天 连在一起
                                            if (TimeUtil.getWeekOfDate(sss1) == 6) {
                                                //今天是周六

                                                if (Arrays.asList(yGift).contains(ySgin[ms - 1]) || Arrays.asList(wGift).contains(ySgin[ms - 1])
                                                        ) {
                                                    //上一天 有礼包
                                                    schemes.add(getSchemeCalendar(x, 0));
                                                } else {
                                                    //上一天 没有礼包
                                                    schemes.add(getSchemeCalendar(x, 1));
                                                }

                                            } else if (TimeUtil.getWeekOfDate(sss1) == 0) {
                                                //今天是周天  不管上天有没有礼包
                                                schemes.add(getSchemeCalendar(x, 0));

                                            } else {
                                                // 不是周末周天
                                                if (Arrays.asList(yGift).contains(ySgin[ms - 1]) || Arrays.asList(wGift).contains(ySgin[ms - 1])
                                                        ) {
                                                    //上一天 有礼包
                                                    schemes.add(getSchemeCalendar(x, 0));
                                                } else {
                                                    //上一天 没有礼包
                                                    schemes.add(getSchemeCalendar(x, 1));
                                                }
                                            }
                                        } else if (Integer.valueOf(ySgin[ms - 1]) + 1 != Integer.valueOf(ySgin[ms]) && Integer.valueOf(ySgin[ms]) == Integer.valueOf(ySgin[ms + 1]) - 1) {
                                            //后两天连在一起

                                            if (TimeUtil.getWeekOfDate(sss1) == 6) {
                                                //今天是周六
                                                schemes.add(getSchemeCalendar(x, 0));

                                            } else if (TimeUtil.getWeekOfDate(sss1) == 0) {
                                                //今天是周天
                                                if (Arrays.asList(yGift).contains(ySgin[ms + 1]) || Arrays.asList(wGift).contains(ySgin[ms + 1])
                                                        ) {
                                                    //下一天 有礼包
                                                    schemes.add(getSchemeCalendar(x, 0));
                                                } else {
                                                    //下一天 没有礼包
                                                    schemes.add(getSchemeCalendar(x, 2));
                                                }

                                            } else {
                                                // 不是周末周天
                                                if (Arrays.asList(yGift).contains(ySgin[ms + 1]) || Arrays.asList(wGift).contains(ySgin[ms + 1])
                                                        ) {
                                                    //下一天 有礼包
                                                    schemes.add(getSchemeCalendar(x, 0));
                                                } else {
                                                    //下一天 没有礼包
                                                    schemes.add(getSchemeCalendar(x, 2));
                                                }

                                            }
                                        } else {
                                            //三天都不在一起
                                            schemes.add(getSchemeCalendar(x, 0));
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        mCalendarView.setSchemeDate(schemes);
    }

    private Calendar getSchemeCalendar(int day, int type) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        if (type == 0) {
            calendar.setSchemeColor(Color.parseColor("#F9B855"));//如果单独标记颜色、则会使用这个颜色
        } else {
            calendar.setSchemeColor(Color.parseColor("#FFFFFF"));
        }


        calendar.addScheme(type, -1, "", "");

        return calendar;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myYAnimation.reset();
        myYAnimation.cancel();
    }
}
