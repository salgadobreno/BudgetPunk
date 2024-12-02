package com.br.widgettest.ui.fragments.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.br.widgettest.AddEntryActivity;
import com.br.widgettest.R;
import com.br.widgettest.core.DailyEntry;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.core.ledger.Ledger;
import com.br.widgettest.ui.extensions.cli.widgets.DailyEntryViewWidget;
import com.br.widgettest.ui.extensions.cli.widgets.DateSeparatorViewWidget;
import com.br.widgettest.ui.extensions.cli.widgets.SummaryWithBalanceViewWidget;
import com.br.widgettest.ui.fragments.util.EntriesWithSeparatorAndSummaryList;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Breno on  08/2018.
 * TODO: Change from ArrayAdapter to Adapter
 */
public class CliDailyEntryAdapter extends ArrayAdapter<Object> implements DailyEntryUI {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM");
    private EntriesWithSeparatorAndSummaryList entriesWithSeparatorAndSummaryList;
    private ILedger ledger;

    public CliDailyEntryAdapter(Context context, EntriesWithSeparatorAndSummaryList entriesWithSeparatorAndSummaryList) {
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
        final EntriesWithSeparatorAndSummaryList.EntryViewType type = EntriesWithSeparatorAndSummaryList.EntryViewType.values()[getItemViewType(position)];

        switch (type) {
            case DATE_SEPARATOR:
                final Date date = (Date) entriesWithSeparatorAndSummaryList.get(position);

//                return new CliTitle(getContext(), simpleDateFormat.format(date));
                return new DateSeparatorViewWidget(getContext(), date).getView();
            case ENTRY:
                final DailyEntry entry = (DailyEntry) entriesWithSeparatorAndSummaryList.get(position);

                return new DailyEntryViewWidget(getContext(), entry).getView();
            case SUMMARY:
                Double[] doubles = (Double[]) entriesWithSeparatorAndSummaryList.get(position);

//                return new CliFinancialEntryView(getContext(), String.format("TOTAL: %s", doubles[0]), String.format("BALANCE: %s", doubles[1]));
                return new SummaryWithBalanceViewWidget(getContext(), doubles[0], doubles[1]).getView();
            default:
                throw new IllegalArgumentException(String.format("I don't know what %s is", type));
        }
  }

    @Override
    public int getCount() {
        return entriesWithSeparatorAndSummaryList.size();
    }

    public void setEntriesWithSeparatorAndSummaryList(EntriesWithSeparatorAndSummaryList entriesWithSeparatorAndSummaryList) {
        this.entriesWithSeparatorAndSummaryList = entriesWithSeparatorAndSummaryList;
    }

    @Override
    public boolean delete(Entry entry) {
        entriesWithSeparatorAndSummaryList.remove(entry);

        getLedger().rm(entry);
        notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean edit(Entry entry) {
        Intent intent = new Intent(getContext(), AddEntryActivity.class);
        intent.putExtra("id", entry.toEntity().getId());
        intent.putExtra("entryType", entry.getEntryType().name());
        getContext().startActivity(intent);
        return true;
    }

    @Override
    public boolean turnMonthly(Entry entry) {
        return false;
    }

    private ILedger getLedger() {
        // TODO: make factory?
        if (ledger == null) {
            ledger = new Ledger(new EntryDao(), new CategoryDao(getContext()));
        }

        return ledger;
    }
}
