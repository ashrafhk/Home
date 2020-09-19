package com.example.ashrafhussain.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.v7.app.AppCompatActivity;

public class Statistics extends AppCompatActivity {

    TabLayout tab;
    ViewPager viewPager;
    PagersAdapter pagersAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);


        viewPager = (ViewPager)findViewById(R.id.pagers);
        pagersAdapter = new com.example.ashrafhussain.home.PagersAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagersAdapter);

        tab = (TabLayout)findViewById(R.id.tabs_layout);
        tab.setupWithViewPager(viewPager);
        setUpWithTab();

    }

    private void setUpWithTab() {
        tab.getTabAt(0).setText("Cricket");
        tab.getTabAt(1).setText("Soccer");
        tab.getTabAt(2).setText("BasketBall");
    }
}
