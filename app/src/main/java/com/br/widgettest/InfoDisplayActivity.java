package com.br.widgettest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.br.widgettest.ui.fragments.BuyViewFragment;
import com.br.widgettest.ui.fragments.DailyViewFragment;
import com.br.widgettest.ui.fragments.FixedViewFragment;

public class InfoDisplayActivity extends AppCompatActivity {

    class InfoFragmentPagerAdapter extends FragmentPagerAdapter {
        Fragment[] fragments;

        public InfoFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new Fragment[] {
                    new DailyViewFragment(),
                    new FixedViewFragment(),
                    new BuyViewFragment()
            };
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return "Daily Entries";
                case 1: return "Budget";
                case 2: return "Bought";
                default:throw new RuntimeException("Unknown page");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.info_fragment_pager);

        FragmentPagerAdapter fragmentPagerAdapter = new InfoFragmentPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.infoViewPager);
        viewPager.setAdapter(fragmentPagerAdapter);
    }
}

