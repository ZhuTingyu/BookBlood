package com.cpigeon.book.module;

import android.app.Activity;
import android.os.Bundle;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.PermissionUtil;
import com.base.util.PopWindowBuilder;
import com.base.util.RxUtils;
import com.base.util.system.ScreenTool;
import com.base.util.utility.ToastUtils;
import com.base.widget.CustomViewPager;
import com.cpigeon.book.R;
import com.cpigeon.book.adpter.ContentFragmentAdapter;
import com.cpigeon.book.base.BaseBookActivity;
import com.cpigeon.book.module.breedpigeon.BreedPigeonEntryFragment;
import com.cpigeon.book.module.home.goodpigeon.GoodPigeonListFragment;
import com.cpigeon.book.module.home.home.HomeFragment;
import com.cpigeon.book.module.home.HomeFragment3;
import com.cpigeon.book.module.home.HomeFragment4;
import com.cpigeon.book.module.login.viewmodel.LoginViewModel;
import com.cpigeon.book.module.menu.smalltools.SmallToolsHomeFragment;
import com.cpigeon.book.module.racing.RacingPigeonEntryFragment;
import com.cpigeon.book.widget.BottomAddTabView;
import com.cpigeon.book.widget.SimpleTitleView;

import java.util.List;

public class MainActivity extends BaseBookActivity {

    private CustomViewPager viewPager;
    BottomAddTabView bottomAddTabView;

    private List<String> titles;

    SpringForce spring;


    LoginViewModel mViewModel;

    private PopupWindow mPopupWindow;

    public static void start(Activity activity) {
        IntentBuilder.Builder(activity, MainActivity.class)
                .startActivity();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtil.getAppDetailSettingIntent(this);
        viewPager = findViewById(R.id.viewPager);
        bottomAddTabView = findViewById(R.id.bottomAddView);


        Bundle bundle = new Bundle();
        bundle.putBoolean(IntentBuilder.KEY_BOOLEAN, false);

        HomeFragment homeFragment = new HomeFragment();
        GoodPigeonListFragment goodPigeonListFragment = new GoodPigeonListFragment();
        HomeFragment3 homeFragment3 = new HomeFragment3();
        HomeFragment4 homeFragment4 = new HomeFragment4();
        homeFragment.setArguments(bundle);
        goodPigeonListFragment.setArguments(bundle);
        homeFragment3.setArguments(bundle);
        homeFragment4.setArguments(bundle);

        titles = Lists.newArrayList(getString(R.string.title_home_fragment)
                , getString(R.string.title_home_fragment2)
                , getString(R.string.title_home_fragment3)
                , getString(R.string.title_home_fragment4));

        ContentFragmentAdapter adapter = new ContentFragmentAdapter(getSupportFragmentManager()
                , Lists.newArrayList(homeFragment, goodPigeonListFragment, homeFragment3, homeFragment4));

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setScanScroll(false);

        bottomAddTabView.bindViewPager(viewPager);
        bottomAddTabView.switchTab(0);

        bottomAddTabView.setAddClickListener(() -> {
            mPopupWindow = PopWindowBuilder.builder(this)
                    .setSize(ScreenTool.getScreenWidth(), ScreenTool.getScreenHeight())
                    .setView(initPopView())
                    .setBackgroundColor(R.color.main_home_pop_bg)
                    .setAnimationStyle(R.style.bottom_out_in_anim)
                    .showAtLocation(rootView, 0, 0, Gravity.CENTER);

        });

        //第一次登录  获取鸽币
        mViewModel = new LoginViewModel();
        initViewModel(mViewModel);
        mViewModel.oneStartGetGeBi();//第一次登录
        mViewModel.oneStartHintStr.observe(this, r -> {
            ToastUtils.showLong(this, r);
        });

    }


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    private View initPopView() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_mian_home, null);
        List<Integer> ids = Lists.newArrayList(R.id.simpleText1, R.id.simpleText2, R.id.simpleText3
                , R.id.simpleText4, R.id.simpleText5, R.id.simpleText6);
        List<View> views = Lists.newArrayList();
        for (int i = 0; i < ids.size(); i++) {
            SimpleTitleView simpleTitleView = view.findViewById(ids.get(i));
            views.add(simpleTitleView);
        }

        ImageView close = view.findViewById(R.id.imgClose);
        SimpleTitleView simpleText2 = view.findViewById(R.id.simpleText2);
        SimpleTitleView simpleText3 = view.findViewById(R.id.simpleText3);
        SimpleTitleView simpleText6 = view.findViewById(R.id.simpleText6);
        close.setOnClickListener(v -> {
            mPopupWindow.dismiss();
        });

        simpleText2.setOnClickListener(v -> {
            //种鸽录入
            mPopupWindow.dismiss();
            BreedPigeonEntryFragment.start(getBaseActivity());
        });

        simpleText3.setOnClickListener(v -> {
            //赛鸽录入
            mPopupWindow.dismiss();
            RacingPigeonEntryFragment.start(getBaseActivity());
        });

        simpleText6.setOnClickListener(v -> {
            //赛鸽工具
            mPopupWindow.dismiss();
            SmallToolsHomeFragment.start(getBaseActivity());
        });

        spring = new SpringForce(0)
                .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_MEDIUM);
        for (int i = 0; i < views.size(); i++) {
            addAnimation(views.get(i), 300 + (100 * i));
        }
        return view;
    }

    private void addAnimation(View view, int time) {
        view.setVisibility(View.GONE);
        final SpringAnimation anim = new SpringAnimation(view, SpringAnimation.TRANSLATION_Y).setSpring(spring);
        anim.setStartValue(500);
        bindUi(RxUtils.delayed(time), aLong -> {
            view.setVisibility(View.VISIBLE);
            anim.cancel();
            anim.start();
        });
    }
}

