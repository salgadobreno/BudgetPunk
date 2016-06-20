package com.br.widgettest.ui.fragments.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.br.widgettest.AddEntryActivity;
import com.br.widgettest.InfoDisplayActivity;
import com.br.widgettest.R;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.core.ledger.Ledger;
import com.br.widgettest.ui.extensions.CategoryTextView;
import com.br.widgettest.ui.extensions.CurrencyFormattedText;
import com.br.widgettest.ui.fragments.NewEntryFragment;
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
                final Entry entry = (Entry) entriesWithSeparatorAndSummaryList.get(position);

                LinearLayout entryRow = (LinearLayout) convertView.findViewById(R.id.daily_entry_row);

                TextView entryDate = (TextView)convertView.findViewById(R.id.daily_entry_date);
                TextView entryVal = (TextView)convertView.findViewById(R.id.daily_entry_val);
                CategoryTextView entryCat = (CategoryTextView)convertView.findViewById(R.id.daily_entry_cat);
                Button rmButton = (Button) convertView.findViewById(R.id.daily_entry_remove);

                entryDate.setText(simpleDateFormat.format(entry.getStartDate()));
                entryCat.setCategory(entry.getCategory());
                entryVal.setText(new CurrencyFormattedText(entry.getValue()));
                rmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ILedger ledger = new Ledger(new EntryDao(getContext()), new CategoryDao(getContext()));
                        entriesWithSeparatorAndSummaryList.remove(entry);

                        ledger.rm(entry);
                        notifyDataSetChanged();
                    }
                });

                entryRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ILedger ledger = new Ledger(new EntryDao(getContext()), new CategoryDao(getContext()));
                        Log.d("DEA", "onClick: clicked");
                        Intent intent = new Intent(getContext(), AddEntryActivity.class);
                        intent.putExtra("action", "edit"); //TODO
                        intent.putExtra("entryType", entry.getEntryType().name());
                        intent.putExtra("entryPos", ledger.getEntries(entry.getEntryType()).indexOf(entry)); //TODO
                        //TODO: id
                        getContext().startActivity(intent);
                    }
                });

//                entryRow.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        Log.d("DEA", "onLongClick() called with: " + "v = [" + v + "]");
//                        Intent intent = new Intent(getContext(), InfoDisplayActivity.class);
//                        intent.putExtra("entry", (Serializable) entry);
//                        getContext().startActivity(intent);
//                        return true;
//                    }
//                });

                break;
            case 2: //SUMMARY
                Double[] doubles = (Double[]) entriesWithSeparatorAndSummaryList.get(position);

                TextView summaryTotal = (TextView) convertView.findViewById(R.id.summary_total);
                TextView summaryBalance = (TextView) convertView.findViewById(R.id.summary_balance);

                summaryTotal.setText("Total: " + new CurrencyFormattedText(doubles[0]));
                summaryBalance.setText("Balance: " + new CurrencyFormattedText(doubles[1]));

                break;
        }

        return convertView;
  }

    @Override
    public int getCount() {
        return entriesWithSeparatorAndSummaryList.size();
    }

    public void setEntriesWithSeparatorAndSummaryList(EntriesWithSeparatorAndSummaryList entriesWithSeparatorAndSummaryList) {
        this.entriesWithSeparatorAndSummaryList = entriesWithSeparatorAndSummaryList;
    }
}
