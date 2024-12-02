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
import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.core.ledger.Ledger;
import com.br.widgettest.core.ledger.decorators.EntryByDateMap;
import com.br.widgettest.ui.fragments.adapters.BuyEntryAdapter;
import com.br.widgettest.ui.fragments.util.BuyEntriesWithSeparatorAndMofifierList;

import java.util.List;

/**
 * Created by Breno on 1/14/2016.
 */
//@Trace
public class BuyViewFragment extends Fragment {
    private ILedger ledger;

    private List<Entry> entries;
    private EntryByDateMap buyEntriesmap;
    private BuyEntriesWithSeparatorAndMofifierList buyEntriesWithSeparatorAndMofifierList; //TODO
    private ListView dateEntryListView;
    private BuyEntryAdapter buyEntryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loadData();

//        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.list_view_with_button, null);

        dateEntryListView = new ListView(getContext());
//        ListView dateEntryListView = (ListView) linearLayout.findViewById(R.id.fragment_list_view);
        dateEntryListView.setStackFromBottom(true);
        buyEntryAdapter = new BuyEntryAdapter(getContext(), buyEntriesWithSeparatorAndMofifierList);
        dateEntryListView.setAdapter(buyEntryAdapter);

//        Button addEntryButton = (Button) linearLayout.findViewById(R.id.fragment_button);
//        addEntryButton.setText("Add Entry");
//        addEntryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), AddEntryActivity.class);
//                intent.putExtra("entryType", Entry.EntryType.BOUGHT.name());
//                getContext().startActivity(intent);
//            }
//        });

        return dateEntryListView;
//        return linearLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
        buyEntryAdapter = new BuyEntryAdapter(getContext(), buyEntriesWithSeparatorAndMofifierList);
//        buyEntryAdapter.setBuyEntriesWithSeparatorAndMofifierList(buyEntriesWithSeparatorAndMofifierList);
//        buyEntryAdapter.notifyDataSetChanged();
        dateEntryListView.setAdapter(buyEntryAdapter);
    }

    private void loadData() {
        ledger = new Ledger(new EntryDao(), new CategoryDao(getContext()));
        entries = (List<Entry>) ledger.getEntries(Entry.EntryType.BOUGHT);
        buyEntriesmap = new EntryByDateMap(entries, EntryByDateMap.Granularity.MONTHLY);
        buyEntriesWithSeparatorAndMofifierList = new BuyEntriesWithSeparatorAndMofifierList(buyEntriesmap, ledger);
    }
}
