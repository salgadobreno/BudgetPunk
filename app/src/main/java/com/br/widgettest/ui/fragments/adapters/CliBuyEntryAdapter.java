package com.br.widgettest.ui.fragments.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.br.widgettest.AddEntryActivity;
import com.br.widgettest.R;
import com.br.widgettest.core.BuyEntry;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.core.ledger.Ledger;
import com.br.widgettest.ui.extensions.cli.widgets.BentryDateSeparatorViewWidget;
import com.br.widgettest.ui.extensions.cli.widgets.BuyEntryViewWidget;
import com.br.widgettest.ui.extensions.cli.widgets.ModifierEntryViewWidget;
import com.br.widgettest.ui.fragments.util.BuyEntriesWithSeparatorAndMofifierList;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CliBuyEntryAdapter extends ArrayAdapter<Object> implements BuyEntryUI {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
    private BuyEntriesWithSeparatorAndMofifierList buyEntriesWithSeparatorAndMofifierList;
    private ILedger ledger;

    public CliBuyEntryAdapter(Context context, BuyEntriesWithSeparatorAndMofifierList buyEntriesWithSeparatorAndMofifierList) {
        super(context, R.layout.buy_entry_row, buyEntriesWithSeparatorAndMofifierList);
        this.buyEntriesWithSeparatorAndMofifierList = buyEntriesWithSeparatorAndMofifierList;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return buyEntriesWithSeparatorAndMofifierList.getEntryViewType(position).getI();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BuyEntriesWithSeparatorAndMofifierList.EntryViewType type = BuyEntriesWithSeparatorAndMofifierList.EntryViewType.values()[getItemViewType(position)];

        switch (type) {
            case DATE_SEPARATOR:
                final Date date = (Date) buyEntriesWithSeparatorAndMofifierList.get(position);

//                return new CliTitle(getContext(), simpleDateFormat.format(date));
                return new BentryDateSeparatorViewWidget(getContext(), date).getView();
            case ENTRY:
                final BuyEntry entry = (BuyEntry) getItem(position);

//                LinearLayout ll = new LinearLayout(getContext());
//                ll.setOrientation(LinearLayout.VERTICAL);
//
//                ll.addView(new CliFinancialEntryView(getContext(), entry.getName(), entry.getValue().toString()));
//                ll.addView(new CliFinancialEntryView(getContext(), "", entry.getPeriod()));
//                ll.addView(new CliFinancialEntryView(getContext(), "", entry.getModifier().toString()));
//
//                return ll;
                return new BuyEntryViewWidget(getContext(), entry).getView();
            case MODIFIER_SUMMARY:
                Double modifier = (Double) buyEntriesWithSeparatorAndMofifierList.get(position);

//                return new CliTitle(getContext(), String.format("MODIFIER: %s", modifier));
                return new ModifierEntryViewWidget(getContext(), modifier).getView();
            default:
                throw new IllegalArgumentException(String.format("I don't know what %s is", type));
        }
    }

    public void setBuyEntriesWithSeparatorAndMofifierList(BuyEntriesWithSeparatorAndMofifierList buyEntriesWithSeparatorAndMofifierList) {
        this.buyEntriesWithSeparatorAndMofifierList = buyEntriesWithSeparatorAndMofifierList;
    }

    @Override
    public boolean delete(Entry entry) {
        buyEntriesWithSeparatorAndMofifierList.remove(entry);

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
    public boolean toggleActive(Entry entry) {
        return false;
    }

    private ILedger getLedger() {
        if (ledger == null) {
            ledger = new Ledger(new EntryDao(), new CategoryDao(getContext()));
        }
        return ledger;
    }
}
