package com.br.widgettest.ui.fragments.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.br.widgettest.AddEntryActivity;
import com.br.widgettest.R;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.FixedEntry;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.core.ledger.Ledger;
import com.br.widgettest.ui.extensions.CategoryTextView;
import com.br.widgettest.ui.extensions.CurrencyFormattedText;

import java.util.List;

/**
 * Created by Breno on 1/17/2016.
 */
public class FixedEntryAdapter extends ArrayAdapter<Entry> implements FixedEntryUI {
    private List<Entry> entryList;
    private ILedger ledger;

    public FixedEntryAdapter(Context context, List<Entry> entryList) {
        super(context, R.layout.fixed_entry_row, entryList);
        this.entryList = entryList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fixed_entry_row, null);
        }

        TextView entryName = (TextView)convertView.findViewById(R.id.fixed_entry_name);
        CategoryTextView entryCat = (CategoryTextView)convertView.findViewById(R.id.fixed_entry_cat);
        TextView entryVal = (TextView)convertView.findViewById(R.id.fixed_entry_val);
        Button popupButton = (Button)convertView.findViewById(R.id.fixed_entry_popup);

        final FixedEntry entry = (FixedEntry) getItem(position);

        entryName.setText(entry.getName());
        entryCat.setCategory(entry.getCategory());
        entryVal.setText(new CurrencyFormattedText(entry.getMonthlyValue()) + "/d");
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.fixed_popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.fmenu_edit:
                                edit(entry);
                            case R.id.fmenu_delete:
                                delete(entry);
                            default:
                                throw new IllegalArgumentException("unexpected param");
                        }
                    }
                });
                popupMenu.show();
            }
        });

        return convertView;
    }

    @Override
    public boolean delete(Entry entry) {
        entryList.remove(entry);

        ledger.rm(entry);
        notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean edit(Entry entry) {
        Intent intent = new Intent(getContext(), AddEntryActivity.class);
        intent.putExtra("action", "edit"); //TODO
        intent.putExtra("entryType", entry.getEntryType().name());
        intent.putExtra("entryPos", getLedger().getEntries(entry.getEntryType()).indexOf(entry)); //TODO
        //TODO: id
        getContext().startActivity(intent);
        return true;
    }

    private ILedger getLedger() {
        //TODO: factory?
        if (ledger == null) {
            ledger = new Ledger(new EntryDao(getContext()), new CategoryDao(getContext()));
        }
        return ledger;
    }
}
