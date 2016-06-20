package com.br.widgettest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.aop.annotations.Trace;
import com.br.widgettest.R;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.core.ledger.Ledger;
import com.br.widgettest.core.ledger.decorators.EntryByDateMap;
import com.br.widgettest.ui.fragments.adapters.DailyEntryAdapter;
import com.br.widgettest.ui.fragments.util.EntriesWithSeparatorAndSummaryList;

import java.util.Date;
import java.util.List;

/**
 * Created by Breno on 1/14/2016.
 */
@Trace
public class DailyViewFragment extends Fragment {
    private ILedger ledger;

    private List<Entry> entries;
    private EntriesWithSeparatorAndSummaryList entriesWithSeparatorAndSummaryList;
    private DailyEntryAdapter dailyEntryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadData();
        ListView dateEntryListView = new ListView(getContext());
        dateEntryListView.setStackFromBottom(true);
        dailyEntryAdapter = new DailyEntryAdapter(getContext(), entriesWithSeparatorAndSummaryList);
        dateEntryListView.setAdapter(dailyEntryAdapter);

        return dateEntryListView;
    }

    private void loadData() {
        ledger = new Ledger(new EntryDao(getContext()), new CategoryDao(getContext()));
        entries = (List<Entry>) ledger.getEntries(Entry.EntryType.DAILY);
        entriesWithSeparatorAndSummaryList = new EntriesWithSeparatorAndSummaryList(
                entries,
                ledger.calcDailyAvailable(new Date()).getAmount().doubleValue()
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
        dailyEntryAdapter.setEntriesWithSeparatorAndSummaryList(entriesWithSeparatorAndSummaryList);
        dailyEntryAdapter.notifyDataSetChanged();
    }
}
