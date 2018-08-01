package com.br.widgettest;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.br.widgettest.ui.IListAndInputInterface;
import com.br.widgettest.ui.fragments.BuyViewFragment;
import com.br.widgettest.ui.fragments.DailyViewFragment;
import com.br.widgettest.ui.fragments.EditBuyEntryFragment;
import com.br.widgettest.ui.fragments.EditDailyEntryFragment;
import com.br.widgettest.ui.fragments.EditFixedEntryFragment;
import com.br.widgettest.ui.fragments.FixedViewFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class InfoDisplayActivity extends AppCompatActivity implements IListAndInputInterface {
    private ViewPager viewPager;
    private FragmentStatePagerAdapter fragmentStatePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.info_fragment_pager2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentStatePagerAdapter = new InfoFragmentPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.infoViewPager);
        final EditDailyEntryFragment editDailyEntryFragment = new EditDailyEntryFragment();
        editDailyEntryFragment.setHolder(this);
        final EditFixedEntryFragment editFixedEntryFragment = new EditFixedEntryFragment();
        editFixedEntryFragment.setHolder(this);
        final EditBuyEntryFragment editBuyEntryFragment = new EditBuyEntryFragment();
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
        viewPager.setAdapter(fragmentStatePagerAdapter);
        editEntryFragmentListener.onPageSelected(0);

        SlidingUpPanelLayout slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_up_panel);
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });

        //TODO: notification channel android 8+
        Intent intent = new Intent(this, InfoDisplayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this, "channel")
                .setSmallIcon(R.drawable.ic_launcher_floating)
                .setContentTitle("Title")
                .setContentText("Text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent);
//                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, nBuilder.build());




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

    @Override
    public void showList(boolean scrollBottom) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        SlidingUpPanelLayout slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_up_panel);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        fragmentStatePagerAdapter.getItem(viewPager.getCurrentItem()).onResume();
    }

    @Override
    public void showInput() {
        Toast vsfToast = Toast.makeText(getApplicationContext(), "vai toma no cu input", Toast.LENGTH_SHORT);
        vsfToast.show();
    }

    class InfoFragmentPagerAdapter extends FragmentStatePagerAdapter {
        Fragment[] fragments;

        public InfoFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new Fragment[] {
                    new DailyViewFragment(),
                    new FixedViewFragment(),
                    new BuyViewFragment(),
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
                default:throw new IllegalArgumentException();
            }
        }
    }
}

