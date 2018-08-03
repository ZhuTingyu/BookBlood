package com.base.base;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.base.BaseFragment;

import java.util.List;

/**
 * Created by Zhu TingYu
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments, @Nullable List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @NonNull
    public List<Fragment> getFragments() {
        return mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments.get(position) instanceof BaseFragment) {
            BaseFragment fragment = (BaseFragment) mFragments.get(position);
            return fragment;
        } else {
            return mFragments.get(position);
        }
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles == null)
            return "";
        else
            return mTitles.get(position);
    }
}
