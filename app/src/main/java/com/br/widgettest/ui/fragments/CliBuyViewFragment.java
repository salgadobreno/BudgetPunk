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
import com.br.widgettest.core.BuyEntry;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.core.ledger.Ledger;
import com.br.widgettest.core.ledger.decorators.EntryByDateMap;
import com.br.widgettest.ui.fragments.adapters.CliBuyEntryAdapter;
import com.br.widgettest.ui.fragments.util.BuyEntriesWithSeparatorAndMofifierList;

import java.util.List;

/**
 * Created by Breno on 1/14/2016.
 */
//@Trace
public class CliBuyViewFragment extends Fragment implements EntryIndex {
    private ILedger ledger;

    private List<Entry> entries;
    private EntryByDateMap buyEntriesmap;
    private BuyEntriesWithSeparatorAndMofifierList buyEntriesWithSeparatorAndMofifierList; //TODO
    private ListView dateEntryListView;
    private CliBuyEntryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        LinearLayout dateEntryListView = new LinearLayout(getContext());
//        dateEntryListView.setOrientation(LinearLayout.VERTICAL);
//        ViewCompat.setNestedScrollingEnabled(dateEntryListView, true);
//        dateEntryListView.addView(
//                new BentryDateSeparatorViewWidget(getContext(), Instant.parse("2018-10-01").toDate()).getView()
//        );
//        dateEntryListView.addView(
//                new BuyEntryViewWidget(
//                        getContext(),
//                        new BuyEntry(
//                                "Aulas Kite", 1200.00,
//                                Instant.parse("2018-10-01").toDate(),
//                                Instant.parse("2018-12-01").toDate(), null
//                        )
//                ).getView()
//        );
//        dateEntryListView.addView(
//                new BuyEntryViewWidget(
//                        getContext(),
//                        new BuyEntry(
//                                "pau velho", 20.00,
//                                Instant.parse("2018-01-01").toDate(),
//                                Instant.parse("2018-02-01").toDate(), null
//                                )
//                ).getView()
//        );
//        dateEntryListView.addView(
//                new ModifierEntryViewWidget(getContext(), 19.67).getView()
//        );
//        dateEntryListView.addView(
//                new BentryDateSeparatorViewWidget(getContext(), Instant.parse("2018-11-01").toDate()).getView()
//        );

        dateEntryListView = new ListView(getContext());
//        LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                1.0f
//        );
//        dateEntryListView.setLayoutParams(textParam);
        ViewCompat.setNestedScrollingEnabled(dateEntryListView, true);
        dateEntryListView.setClipToPadding(true);
        dateEntryListView.setStackFromBottom(true);
        dateEntryListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        loadData();
        dateEntryListView.setAdapter(adapter);

        return dateEntryListView;
    }

    private void loadData() {
        ledger = new Ledger(new EntryDao(), new CategoryDao(getContext()));
        entries = (List<Entry>) ledger.getEntries(Entry.EntryType.BOUGHT);
        buyEntriesmap = new EntryByDateMap(entries, EntryByDateMap.Granularity.MONTHLY);
        buyEntriesWithSeparatorAndMofifierList = new BuyEntriesWithSeparatorAndMofifierList(buyEntriesmap, ledger);
        adapter = adapter == null ? new CliBuyEntryAdapter(getContext(), buyEntriesWithSeparatorAndMofifierList) : adapter;
        adapter.setBuyEntriesWithSeparatorAndMofifierList(buyEntriesWithSeparatorAndMofifierList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
//        loadData();
//        adapter = new CliBuyEntryAdapter(getContext(), buyEntriesWithSeparatorAndMofifierList);
//        buyEntryAdapter.setBuyEntriesWithSeparatorAndMofifierList(buyEntriesWithSeparatorAndMofifierList);
//        buyEntryAdapter.notifyDataSetChanged();
//        dateEntryListView.setAdapter(adapter);
    }

    @Override
    public void scrollBottom() {
        new BottomScroller(dateEntryListView).run();
        loadData();
    }
}
