package com.br.widgettest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.br.widgettest.core.Entry;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.ledger.Ledger;
import com.br.widgettest.core.ledger.decorators.EntryByDateMap;
import com.br.widgettest.ui.fragments.adapters.BuyEntryAdapter;
import com.br.widgettest.ui.fragments.util.FixedEntriesWithSeparatorAndMofifierList;

import java.util.List;

/**
 * Created by Breno on 1/14/2016.
 */
public class BuyViewFragment extends Fragment {
    private ILedger ledger;

    private List<Entry> entries;
    private EntryByDateMap buyEntriesmap;
    private FixedEntriesWithSeparatorAndMofifierList fixedEntriesWithSeparatorAndMofifierList;
    private BuyEntryAdapter buyEntryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loadData();

        ListView dateEntryListView = new ListView(getContext());
        dateEntryListView.setStackFromBottom(true);
        buyEntryAdapter = new BuyEntryAdapter(getContext(), fixedEntriesWithSeparatorAndMofifierList);
        dateEntryListView.setAdapter(buyEntryAdapter);

        return dateEntryListView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
        buyEntryAdapter.setFixedEntriesWithSeparatorAndMofifierList(fixedEntriesWithSeparatorAndMofifierList);
        buyEntryAdapter.notifyDataSetChanged();
    }

    private void loadData() {
        ledger = new Ledger(getContext());
        entries = ledger.getEntries(Entry.EntryType.BOUGHT);
        buyEntriesmap = new EntryByDateMap(entries, EntryByDateMap.Granularity.MONTHLY);
        fixedEntriesWithSeparatorAndMofifierList = new FixedEntriesWithSeparatorAndMofifierList(buyEntriesmap, ledger);
    }
}
