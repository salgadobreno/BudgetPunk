package com.br.widgettest.ui.fragments.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.br.widgettest.R;
import com.br.widgettest.core.Entry;
import com.br.widgettest.ui.extensions.CurrencyFormattedText;
import com.br.widgettest.ui.extensions.CategoryTextView;
import com.br.widgettest.ui.fragments.util.EntriesWithSeparatorAndSummaryList;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Breno on 1/14/2016.
 */
public class DailyEntryAdapter extends ArrayAdapter<Object> {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM");
    private EntriesWithSeparatorAndSummaryList entriesWithSeparatorAndSummaryList;

    public DailyEntryAdapter(Context context, EntriesWithSeparatorAndSummaryList entriesWithSeparatorAndSummaryList) {
        super(context, R.layout.daily_entry_row, entriesWithSeparatorAndSummaryList);
        this.entriesWithSeparatorAndSummaryList = entriesWithSeparatorAndSummaryList;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return entriesWithSeparatorAndSummaryList.getEntryViewType(position).getI();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int type = getItemViewType(position);
        int[] layouts = new int[] {R.layout.entries_date_separator, R.layout.daily_entry_row, R.layout.daily_summary};

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layouts[type], null);
        }

        switch (type) {
            case 0: //DATE_SEPARATOR
                Date date = (Date) entriesWithSeparatorAndSummaryList.get(position);

                TextView dateView = (TextView) convertView.findViewById(R.id.date_separator_text);
                dateView.setText(simpleDateFormat.format(date));

                break;
            case 1: //ENTRY
                Entry entry = (Entry) entriesWithSeparatorAndSummaryList.get(position);

                TextView entryDate = (TextView)convertView.findViewById(R.id.daily_entry_date);
                TextView entryVal = (TextView)convertView.findViewById(R.id.daily_entry_val);
                CategoryTextView entryCat = (CategoryTextView)convertView.findViewById(R.id.daily_entry_cat);

                entryDate.setText(simpleDateFormat.format(entry.getStartDate()));
                entryCat.setCategory(entry.getCategory());
                entryVal.setText(new CurrencyFormattedText(entry.getValue()));

                break;
            case 2: //SUMMARY
                Log.d(getClass().getSimpleName(), "SUMMARY CALLED");
                Double[] doubles = (Double[]) entriesWithSeparatorAndSummaryList.get(position);

                TextView summaryTotal = (TextView) convertView.findViewById(R.id.summary_total);
                TextView summaryBalance = (TextView) convertView.findViewById(R.id.summary_balance);

                summaryTotal.setText("Total: " + new CurrencyFormattedText(doubles[0]));
                summaryBalance.setText("Balance: " + new CurrencyFormattedText(doubles[1]));

                break;
        }

        return convertView;
  }

    public void setEntriesWithSeparatorAndSummaryList(EntriesWithSeparatorAndSummaryList entriesWithSeparatorAndSummaryList) {
        this.entriesWithSeparatorAndSummaryList = entriesWithSeparatorAndSummaryList;
    }
}
