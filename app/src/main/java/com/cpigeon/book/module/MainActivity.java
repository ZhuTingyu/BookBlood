package com.cpigeon.book.module;

import android.app.Activity;
import android.os.Bundle;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.base.base.BaseActivity;
import com.base.base.FragmentAdapter;
import com.base.util.BarUtils;
import com.base.util.IntentBuilder;
import com.base.util.Lists;
import com.base.util.PermissionUtil;
import com.base.util.PopWindowBuilder;
import com.base.util.RxUtils;
import com.base.util.system.ScreenTool;
import com.base.util.utility.ToastUtils;
import com.base.widget.CustomViewPager;
import com.cpigeon.book.R;
import com.cpigeon.book.module.home.HomeFragment;
import com.cpigeon.book.module.home.HomeFragment2;
import com.cpigeon.book.module.home.HomeFragment3;
import com.cpigeon.book.module.home.HomeFragment4;
import com.cpigeon.book.module.login.viewmodel.LoginViewModel;
import com.cpigeon.book.widget.BottomAddTabView;
import com.cpigeon.book.widget.SimpleTitleView;

import java.util.List;

public class MainActivity extends BaseActivity {

    private CustomViewPager viewPager;
    BottomAddTabView bottomAddTabView;

    private List<String> titles;

    SpringForce spring;

    private DrawerLayout drawerLayout;
    private RelativeLayout llMain;
    private RelativeLayout menuLayoutLeft;

    LoginViewModel mViewModel;

    public static void start(Activity activity) {
        IntentBuilder.Builder(activity, MainActivity.class)
                .startActivity();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtil.getAppDetailSettingIntent(this);
        BarUtils.setStatusBarAllAlpha(this);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        bottomAddTabView = findViewById(R.id.bottomAddView);


        drawerLayout = findViewById(R.id.drawerLayout);
        llMain = findViewById(R.id.llMain);
        menuLayoutLeft = findViewById(R.id.menuLayoutLeft);


        HomeFragment homeFragment = new HomeFragment();
        HomeFragment2 homeFragment2 = new HomeFragment2();
        HomeFragment3 homeFragment3 = new HomeFragment3();
        HomeFragment4 homeFragment4 = new HomeFragment4();

        titles = Lists.newArrayList(getString(R.string.title_home_fragment)
                , getString(R.string.title_home_fragment2)
                , getString(R.string.title_home_fragment3)
                , getString(R.string.title_home_fragment4));

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager()
                , Lists.newArrayList(homeFragment, homeFragment2, homeFragment3, homeFragment4), titles);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setScanScroll(false);

        bottomAddTabView.bindViewPager(viewPager);
        bottomAddTabView.switchTab(0);

        bottomAddTabView.setAddClickListener(() -> {
            PopWindowBuilder.builder(this)
                    .setSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    .setView(initPopView())
                    .setBackgroundColor(R.color.white)
                    .setAnimationStyle(R.style.bottom_out_in_anim)
                    .showAtLocation(rootView, 0, 0, Gravity.CENTER);

        });

        initLeftMenu();


        //第一次登录  获取鸽币
        mViewModel = new LoginViewModel();
        initViewModel(mViewModel);
        mViewModel.oneStartGetGeBi();//第一次登录
        mViewModel.oneStartHintStr.observe(this, r -> {
            ToastUtils.showLong(this, r);
        });
    }

    private void initLeftMenu() {

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                llMain.layout(menuLayoutLeft.getRight(), 0, menuLayoutLeft.getRight() + ScreenTool.getScreenWidth()
                        , ScreenTool.getScreenHeight());
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
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
