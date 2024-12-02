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
import com.br.widgettest.ui.fragments.adapters.CliFixedEntryAdapter;
import com.br.widgettest.ui.fragments.util.EntryByPositiveMap;

public class CliFixedViewFragment extends Fragment implements EntryIndex {
    private ILedger ledger;

    private CliFixedEntryAdapter.FixedEntriesWithSeparatorAndSummary fixedEntriesWithSeparatorAndSummary;
    private CliFixedEntryAdapter cliFixedEntryAdapter;
    private ListView fixedEntriesListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        ledger = new Ledger(new EntryDao(), new CategoryDao(getContext()));

//        LinearLayout rootView = new LinearLayout(getContext());
//        ViewCompat.setNestedScrollingEnabled(rootView, true);
//        rootView.setClipToPadding(true);
//        ScrollView rootView = new ScrollView(getContext());
//        rootView.setPadding(20, 20, 20, 20);
//        rootView.setFillViewport(true);
//        rootView.setOrientation(LinearLayout.VERTICAL);
//        rootView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

//        rootView.addView(new CliTextViewWidget.V(getContext(),"Fixed"));
//        rootView.addView(new CliEmptyLine(getContext()));

        fixedEntriesListView = new ListView(getContext());
//        fixedEntriesListView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
        ViewCompat.setNestedScrollingEnabled(fixedEntriesListView, true);
        fixedEntriesListView.setClipToPadding(true);
        fixedEntriesListView.setStackFromBottom(true);
        fixedEntriesListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        loadData();

//        fixedEntriesListView.setAdapter(new CliFixedEntryAdapter(getContext(),
//                new FixedEntriesWithSeparatorAndSummary(
//                        new EntryByPositiveMap(ledger.getEntries(Entry.EntryType.FIXED)), ledger))
//        );
//        rootView.addView(fixedEntriesListView);

//        rootView.addView(new CliTitle(getContext(), "IN"));
//        rootView.addView(new CliFixedEntryView(getContext(), new FixedEntry("Salario", 4300.00, null)));
//
//        rootView.addView(new CliEmptyLine(getContext()));
//        rootView.addView(new CliTitle(getContext(), "OUT"));
//        rootView.addView(new CliFixedEntryView(getContext(), new FixedEntry("Academia", 99.00, null)));
//
//        rootView.addView(new CliEmptyLine(getContext()));
//        rootView.addView(new CliSeparator(getContext()));
//        rootView.addView(new CliFinancialEntryView(getContext(), "V/D:", "R$50.00/d(-R$10.31)"));
//        rootView.addView(new CliSeparator(getContext()));

//        ScrollView scrollView = new ScrollView(getContext());
//        scrollView.addView(rootView);
//        return scrollView;
//        NestedScrollView nestedScrollView = new NestedScrollView(getContext());
//        nestedScrollView.addView(rootView);
        return fixedEntriesListView;
//        return rootView;
    }

    private void loadData() {
        ledger = new Ledger(new EntryDao(), new CategoryDao(getContext()));
        fixedEntriesWithSeparatorAndSummary = new CliFixedEntryAdapter.FixedEntriesWithSeparatorAndSummary(
                new EntryByPositiveMap(ledger.getEntries(Entry.EntryType.FIXED)),
                ledger
        );
        cliFixedEntryAdapter = new CliFixedEntryAdapter(getContext(), fixedEntriesWithSeparatorAndSummary);
        fixedEntriesListView.setAdapter(cliFixedEntryAdapter);
        cliFixedEntryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
        scrollBottom();
    }

    @Override
    public void scrollBottom() {
        new BottomScroller(fixedEntriesListView).run();
        loadData();
    }
}

