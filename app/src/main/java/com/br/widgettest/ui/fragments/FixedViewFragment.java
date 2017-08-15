package com.br.widgettest.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aop.annotations.Trace;
import com.br.widgettest.AddEntryActivity;
import com.br.widgettest.R;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.ui.extensions.CurrencyFormattedText;
import com.br.widgettest.core.ledger.Ledger;
import com.br.widgettest.ui.fragments.adapters.FixedEntryAdapter;

import java.util.Date;
import java.util.List;

/**
 * Created by Breno on 1/14/2016.
 */
@Trace
public class FixedViewFragment extends Fragment {
    private ILedger ledger;
    private List<Entry> entries;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ledger = new Ledger(new EntryDao(), new CategoryDao(getContext()));

        entries = (List<Entry>) ledger.getEntries(Entry.EntryType.FIXED);

        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.list_view_with_button, null);

//        ListView fixedEntriesListView = new ListView(getContext());
        ListView fixedEntriesListView = (ListView) linearLayout.findViewById(R.id.fragment_list_view);
        fixedEntriesListView.setAdapter(new FixedEntryAdapter(getContext(), entries));

        LinearLayout summary = (LinearLayout) inflater.inflate(R.layout.fixed_entries_summary, null);
        TextView summaryTotal = (TextView) summary.findViewById(R.id.fixed_entries_total);
        TextView summaryAdditive = (TextView) summary.findViewById(R.id.fixed_entries_summary_additive);

        summaryTotal.setText(
                new CurrencyFormattedText(ledger.calcAvailableFromFixed())
                + " (" + new CurrencyFormattedText(ledger.calcMonthModifier(new Date())) + ")"
                );
        summaryAdditive.setText(""); //TODO

        fixedEntriesListView.addFooterView(summary);

        Button addEntryButton = (Button) linearLayout.findViewById(R.id.fragment_button);
        addEntryButton.setText("Add Entry");
        addEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddEntryActivity.class);
                intent.putExtra("entryType", Entry.EntryType.FIXED.name());
                getContext().startActivity(intent);
            }}
        );

//        return fixedEntriesListView;
        return linearLayout;
    }
}
