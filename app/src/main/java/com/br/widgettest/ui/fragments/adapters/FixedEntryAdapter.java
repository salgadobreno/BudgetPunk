package com.br.widgettest.ui.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.br.widgettest.R;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.FixedEntry;
import com.br.widgettest.ui.extensions.CurrencyFormattedText;
import com.br.widgettest.ui.extensions.CategoryTextView;

import java.util.List;

/**
 * Created by Breno on 1/17/2016.
 */
public class FixedEntryAdapter extends ArrayAdapter<Entry> {
    public FixedEntryAdapter(Context context, List<Entry> entryList) {
        super(context, R.layout.fixed_entry_row, entryList);
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

        FixedEntry entry = (FixedEntry) getItem(position);

        entryName.setText(entry.getName());
        entryCat.setCategory(entry.getCategory());
        entryVal.setText(new CurrencyFormattedText(entry.getMonthlyValue()) + "/d");

        return convertView;
    }
}
