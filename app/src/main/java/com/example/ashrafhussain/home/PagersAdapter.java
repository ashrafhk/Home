package com.example.ashrafhussain.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PagersAdapter extends FragmentStatePagerAdapter {

    List<Fragment>fragmentList = new ArrayList<Fragment>();

    public PagersAdapter(FragmentManager fm) {
        super(fm);
        fragmentList.add(new Stats1Fragment());
        fragmentList.add(new Stats2Fragment());
        fragmentList.add(new Stats3Fragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
