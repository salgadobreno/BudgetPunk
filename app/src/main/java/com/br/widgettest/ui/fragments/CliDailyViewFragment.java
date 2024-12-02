package com.br.widgettest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.br.widgettest.BottomScroller;
import com.br.widgettest.EntryIndex;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.core.ledger.Ledger;
import com.br.widgettest.ui.fragments.adapters.CliDailyEntryAdapter;
import com.br.widgettest.ui.fragments.util.EntriesWithSeparatorAndSummaryList;

import java.util.Date;
import java.util.List;

//TODO: common interface
public class CliDailyViewFragment extends Fragment implements EntryIndex {
    private ILedger ledger;

    private List<Entry> entries;
    private EntriesWithSeparatorAndSummaryList entriesWithSeparatorAndSummaryList;
    private CliDailyEntryAdapter adapter;
    private ListView dateEntryListView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dateEntryListView = new ListView(getContext());
        ViewCompat.setNestedScrollingEnabled(dateEntryListView, true);
        dateEntryListView.setClipToPadding(true);
        dateEntryListView.setStackFromBottom(true);
        dateEntryListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
//        LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                1.0f
//        );
//        dateEntryListView.setLayoutParams(textParam);
//        adapter = new CliDailyEntryAdapter(getContext(), entriesWithSeparatorAndSummaryList);

//        int paddingInDp = (int) (24 * getResources().getDisplayMetrics().density + 0.5f);
//        dateEntryListView.setPadding(0, paddingInDp, 0, paddingInDp);
//        dateEntryListView.setPadding(0, 25, 0, 25);

        loadData();
        dateEntryListView.setAdapter(adapter);

        return dateEntryListView;
    }

    private void loadData() {
//        ledger = ledger == null ? new Ledger(new EntryDao(), new CategoryDao(getContext())) : ledger;
        ledger = new Ledger(new EntryDao(), new CategoryDao(getContext()));
        entries = (List<Entry>) ledger.getEntries(Entry.EntryType.DAILY);
        entriesWithSeparatorAndSummaryList = new EntriesWithSeparatorAndSummaryList(
                entries,
                ledger.calcDailyAvailable(new Date()).getAmount().doubleValue()
        );
        adapter = adapter == null ? new CliDailyEntryAdapter(getContext(), entriesWithSeparatorAndSummaryList) : adapter;
        adapter.setEntriesWithSeparatorAndSummaryList(entriesWithSeparatorAndSummaryList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
        scrollBottom();
    }

    @Override
    public void scrollBottom() {
        new BottomScroller(dateEntryListView).run(); //TODO: crap
        loadData();
    }
}

