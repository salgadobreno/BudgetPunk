package com.br.widgettest.ui.fragments.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.br.widgettest.AddEntryActivity;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.FixedEntry;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.core.exceptions.NotImplementedException;
import com.br.widgettest.core.ledger.Ledger;
import com.br.widgettest.ui.extensions.cli.widgets.FixedEntrySumViewWidget;
import com.br.widgettest.ui.extensions.cli.widgets.FixedEntrySummaryViewWidget;
import com.br.widgettest.ui.extensions.cli.widgets.FixedEntryViewWidget;
import com.br.widgettest.ui.extensions.cli.widgets.TitleViewWidget;
import com.br.widgettest.ui.fragments.util.EntryByPositiveMap;
import com.br.widgettest.ui.fragments.util.SumEntriesValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CliFixedEntryAdapter extends BaseAdapter implements FixedEntryUI {
//public class CliFixedEntryAdapter extends BaseAdapter {
    private Context context;

    private FixedEntriesWithSeparatorAndSummary fixedEntriesWithSeparatorAndSummary;
    private ILedger ledger;

    public CliFixedEntryAdapter(Context context, FixedEntriesWithSeparatorAndSummary fixedEntriesWithSeparatorAndSummary) {
        this.context = context;
        this.fixedEntriesWithSeparatorAndSummary = fixedEntriesWithSeparatorAndSummary;
    }

    @Override
    public int getCount() {
        return fixedEntriesWithSeparatorAndSummary.size();
    }

    @Override
    public Object getItem(int position) {
        return fixedEntriesWithSeparatorAndSummary.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return fixedEntriesWithSeparatorAndSummary.getEntryViewType(position).getI();
    }

    @Override
    public int getViewTypeCount() {
        return FixedEntriesWithSeparatorAndSummary.FixedEntryViewType.values().length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FixedEntriesWithSeparatorAndSummary.FixedEntryViewType type = FixedEntriesWithSeparatorAndSummary.FixedEntryViewType.values()[getItemViewType(position)];
        final Object item = getItem(position);

        switch (type) {
            case IN_OUT_TITLE:
//                return new CliTitle(context, item.toString());
                  return new TitleViewWidget(context, item.toString()).getView();
            case ENTRY:
//                return new CliFixedEntryView(context, (FixedEntry) item);
                return new FixedEntryViewWidget(context, (FixedEntry) item).getView();
            case TOTAL:
//                return new CliSummary(context, "TOTAL:",  Money.of(Entry.CU,
//                        ((FixedEntriesWithSeparatorAndSummary.Total)item).doubleValue())
//                        .toString()
//                );
                return new FixedEntrySumViewWidget(context, ((FixedEntriesWithSeparatorAndSummary.Total) item).doubleValue()/30).getView();//TODO
            case SUMMARY:
//                return new CliSummary(context, Money.of(Entry.CU,
//                        ((FixedEntriesWithSeparatorAndSummary.Summary)item).doubleValue())
//                        .toString()
//                );
                return new FixedEntrySummaryViewWidget(context, ((FixedEntriesWithSeparatorAndSummary.Summary)item).doubleValue(), getLedger().calcMonthModifier(new Date()).getAmount().doubleValue()).getView();
            default:
                throw new IllegalArgumentException(String.format("I don't know what %s is", type));
        }
    }

    @Override
    public boolean delete(Entry entry) {
        fixedEntriesWithSeparatorAndSummary.remove(entry);

        getLedger().rm(entry);
        notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean edit(Entry entry) {
        Intent intent = new Intent(context, AddEntryActivity.class);
        intent.putExtra("id", entry.toEntity().getId());
        intent.putExtra("entryType", entry.getEntryType().name());
        context.startActivity(intent);
        return true;
    }

    private ILedger getLedger() {
//        TODO: factory?
        if (ledger == null) {
            ledger = new Ledger(new EntryDao(), new CategoryDao(context));
        }
        return ledger;
    }

    public static class FixedEntriesWithSeparatorAndSummary extends ArrayList<Object> {
        public enum FixedEntryViewType {
            IN_OUT_TITLE(0), ENTRY(1), TOTAL(2), SUMMARY(3);

            private int i;

            FixedEntryViewType(int i) {
                this.i = i;
            }

            public int getI() {
                return i;
            }
        }

        public FixedEntriesWithSeparatorAndSummary(EntryByPositiveMap map, ILedger ledger) {
            List<Entry> inEntries = map.get(EntryByPositiveMap.Key.IN);
            List<Entry> outEntries = map.get(EntryByPositiveMap.Key.OUT);

            add("IN");
            addAll(inEntries);
            add(new Total(new SumEntriesValue(inEntries).get()));
            add("OUT");
            addAll(outEntries);
            add(new Total(new SumEntriesValue(outEntries).get()));
            add(new Summary(ledger.calcAvailableFromFixed().getAmount().doubleValue()));
        }

        public FixedEntryViewType getEntryViewType(int position) {
            Object o = get(position);
            FixedEntryViewType r;
            if (o instanceof String) r = FixedEntryViewType.IN_OUT_TITLE;
            else if (o instanceof Entry) r = FixedEntryViewType.ENTRY;
            else if (o instanceof Total) r = FixedEntryViewType.TOTAL;
            else if (o instanceof Summary) r = FixedEntryViewType.SUMMARY;
            else throw new RuntimeException("I don't know what Object: " + o + " is.");

            return r;
        }

        public static class Total extends Number {
            private final Double value;

            public Total(Double value) {
                this.value = value;
            }

            @Override
            public String toString() {
                return super.toString();
            }

            @Override
            public double doubleValue() {
                return value;
            }

            @Override
            public float floatValue() {
                throw new NotImplementedException();
            }

            @Override
            public int intValue() {
                throw new NotImplementedException();
            }

            @Override
            public long longValue() {
                throw new NotImplementedException();
            }
        }

        public static class Summary extends Number {
            private final Double value;

            public Summary(Double value) {
                this.value = value;
            }

            @Override
            public double doubleValue() {
                return value;
            }

            @Override
            public float floatValue() {
                throw new NotImplementedException();
            }

            @Override
            public int intValue() {
                throw new NotImplementedException();
            }

            @Override
            public long longValue() {
                throw new NotImplementedException();
            }
        }
    }
}
