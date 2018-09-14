package com.cpigeon.book.module.menu.smalltools;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.util.IntentBuilder;
import com.cpigeon.book.R;
import com.cpigeon.book.base.BaseBookFragment;
import com.cpigeon.book.module.menu.smalltools.lineweather.view.activity.LineWeatherFragment;
import com.cpigeon.book.module.menu.smalltools.ullage.UllageToolFragment;

import butterknife.OnClick;

/**
 * 小工具
 * Created by Administrator on 2018/9/12.
 */

public class SmallToolsHomeFragment extends BaseBookFragment {


    public static void start(Activity activity) {
        IntentBuilder.Builder()
                .startParentActivity(activity, SmallToolsHomeFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_small_tools_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("小工具");
    }

    @OnClick({R.id.ll_line_water, R.id.ll_ullage_calculation, R.id.ll_cpigeon, R.id.ll_cpigeonhelp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_line_water:
                //赛线天气
                LineWeatherFragment.start(getBaseActivity());
                break;
            case R.id.ll_ullage_calculation:
                //空距计算
                UllageToolFragment.start(getBaseActivity());
                break;
            case R.id.ll_cpigeon:
                //中鸽网
                startApp(getString(R.string.package_cpigeon), getString(R.string.app_share_zgw));
                break;
            case R.id.ll_cpigeonhelp:
                //中鸽助手
                startApp(getString(R.string.package_cpigeonhelp), getString(R.string.app_share_zgzs));
                break;
        }
    }

    //跳转中鸽网  中鸽助手
    private void startApp(String packageStr, String urlStr) {
        Intent intent = new Intent();
        try {
            PackageManager packageManager = getActivity().getPackageManager();
            intent = packageManager.getLaunchIntentForPackage(packageStr);
            startActivity(intent);

        } catch (Exception e) {
            Log.d("xiaohlsta", "startCpigeon: 异常" + e.getLocalizedMessage());
            Uri uri = Uri.parse(urlStr);
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}
