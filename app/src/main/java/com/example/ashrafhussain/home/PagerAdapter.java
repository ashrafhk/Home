package com.example.ashrafhussain.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment>fragmentList = new ArrayList<Fragment>();

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList.add(new News1Fragment());
        fragmentList.add(new News2Fragment());
        fragmentList.add(new News3Fragment());
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
