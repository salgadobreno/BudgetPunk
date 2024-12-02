package com.br.widgettest;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.core.ledger.Ledger;
import com.br.widgettest.ui.MainUI;
import com.br.widgettest.ui.fragments.*;

public class MainActivity extends AppCompatActivity implements MainUI {
    private ViewPager viewPager;
    private FragmentStatePagerAdapter fragmentStatePagerAdapter;
    private ILedger ledger;
    private LinearLayout bottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ledger = new Ledger(new EntryDao(), new CategoryDao(getApplicationContext()));
        fragmentStatePagerAdapter = new InfoFragmentPagerAdapter(getSupportFragmentManager());

        //init views
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        bottomSheet = (LinearLayout) findViewById(R.id.bottomSheet);
        //grab bottomsheet behavior
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
        bottomSheetBehavior = (BottomSheetBehavior) params.getBehavior();

        //setup toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //setup viewpager/tablayout
        viewPager.setAdapter(fragmentStatePagerAdapter);
        //setup edit entry fragment stuff
        final EditDailyEntryFragment editDailyEntryFragment = new EditDailyEntryFragment();
        final EditFixedEntryFragment editFixedEntryFragment = new EditFixedEntryFragment();
        final EditBuyEntryFragment editBuyEntryFragment = new EditBuyEntryFragment();
        editDailyEntryFragment.setHolder(this);
        editFixedEntryFragment.setHolder(this);
        editBuyEntryFragment.setHolder(this);
        ViewPager.SimpleOnPageChangeListener editEntryFragmentListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                final Fragment fragment;
                switch (position) {
                    case 0:
                        fragment = editDailyEntryFragment;
                        break;
                    case 1:
                        fragment = editFixedEntryFragment;
                        break;
                    case 2:
                        fragment = editBuyEntryFragment;
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.slide_panel_inner, fragment);
                fragmentTransaction.commit();
            }
        };
        viewPager.addOnPageChangeListener(editEntryFragmentListener);
        editEntryFragmentListener.onPageSelected(0);

        // Setup notification stuff
        //TODO: notification channel android 8+
//    Intent intent = new Intent(this, InfoDisplayActivity.class);
//    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//    NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this, "channel")
//        .setSmallIcon(R.drawable.ic_vimlogo)
//                .setContentTitle("Title")
//        .setContentText("-> BALANCE: " + ledger.calcDailyAvailable(new Date()))
//        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        // Set the intent that will fire when the user taps the notification
//        .setContentIntent(pendingIntent);
//                .setAutoCancel(true);
//    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//    notificationManager.notify(0, nBuilder.build());
    }

    @Override
    public void showEditor() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void hideEditor() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void scrollBottom() {
        getCurrentEntryIndex().scrollBottom();
    }

    private EntryIndex getCurrentEntryIndex() {
        return (EntryIndex) fragmentStatePagerAdapter.getItem(viewPager.getCurrentItem());
//        return null;
    }

    class InfoFragmentPagerAdapter extends FragmentStatePagerAdapter {
        Fragment[] fragments;

        public InfoFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new Fragment[]{
                    new CliDailyViewFragment(),
                    new CliFixedViewFragment(),
                    new CliBuyViewFragment(),
            };
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CliDaily";
                case 1:
                    return "CliBudget";
                case 2:
                    return "CliBought";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
}
