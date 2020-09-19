package com.example.ashrafhussain.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.v7.app.AppCompatActivity;

public class News extends AppCompatActivity {

    TabLayout tab;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        viewPager = (ViewPager)findViewById(R.id.pager);
        pagerAdapter = new com.example.ashrafhussain.home.PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tab = (TabLayout)findViewById(R.id.tab_layout);
        tab.setupWithViewPager(viewPager);
        setUpWithTab();

    }

    private void setUpWithTab() {
        tab.getTabAt(0).setText("Cricket");
        tab.getTabAt(1).setText("Soccer");
        tab.getTabAt(2).setText("BasketBall");
    }
}
