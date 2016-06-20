package com.br.widgettest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.br.widgettest.ui.fragments.BuyViewFragment;
import com.br.widgettest.ui.fragments.DailyViewFragment;
import com.br.widgettest.ui.fragments.FixedViewFragment;

public class InfoDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.info_fragment_pager);

        FragmentStatePagerAdapter fragmentStatePagerAdapter = new InfoFragmentPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.infoViewPager);
        viewPager.setAdapter(fragmentStatePagerAdapter);

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            String action = extras.getString("action");
//            TODO
//            if (action.equals("edit")) {
//                ILedger ledger = new Ledger(new EntryDao(getApplicationContext()), new CategoryDao(getApplicationContext()));
//                Entry.EntryType entryType = Entry.EntryType.valueOf(extras.getString("entryType")); //TODO: constant
//                Entry entry = ledger.getEntries(entryType).get(extras.getInt("entryPos")); //TODO: constant
//                viewPager.setCurrentItem(3); //TODO: constants
//                ((NewEntryFragment) fragmentStatePagerAdapter.getItem(3)).editEntry(entry);
//            }
//        }
    }

    class InfoFragmentPagerAdapter extends FragmentStatePagerAdapter {
        Fragment[] fragments;

        public InfoFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new Fragment[] {
                    new DailyViewFragment(),
                    new FixedViewFragment(),
                    new BuyViewFragment(),
//                    new NewEntryFragment() //TODO: constants
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
//                case 3: return "New Entry";
                default:throw new RuntimeException("Unknown page");
            }
        }
    }
}

