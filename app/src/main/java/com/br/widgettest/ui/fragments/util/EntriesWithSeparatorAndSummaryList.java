package com.br.widgettest.ui.fragments.util;

import com.br.widgettest.core.Entry;
import com.br.widgettest.core.ledger.decorators.EntryByDateMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Breno on 1/25/2016.
 */
public class EntriesWithSeparatorAndSummaryList extends ArrayList<Object> {

    public enum EntryViewType {
        DATE_SEPARATOR(0), ENTRY(1), SUMMARY(2);

        private int i;

        EntryViewType(int i) {
            this.i = i;
        }

        public int getI() {
            return i;
        }
    }

    public EntriesWithSeparatorAndSummaryList(EntryByDateMap map, double dailyAvailable) {
        Double balance = 0d;

        List<Date> inverseSortedDates = new ArrayList<>(map.keySet());
        Collections.sort(inverseSortedDates);

        for (Date date : inverseSortedDates) {
            add(date);
            List<Entry> entries = map.get(date);
            for (Entry e : entries) {
                add(e);
            }
            Double[] doubles = new Double[2]; // total, balance
            doubles[0] = calcEntryListTotal(entries);

            balance += dailyAvailable + calcEntryListTotal(entries);
            doubles[1] = balance;

            add(doubles);
        }
    }

    public EntryViewType getEntryViewType(int position) {
        Object o = get(position);
        EntryViewType r;
        if (o instanceof Date) r = EntryViewType.DATE_SEPARATOR;
        else if (o instanceof Entry) r = EntryViewType.ENTRY;
        else if (o instanceof Double[]) r = EntryViewType.SUMMARY;
        else throw new RuntimeException("I don't know what Object: " + o + " is.");

        return r;
    }

    private double calcEntryListTotal(List<Entry> entries) {
        Double value = 0d;

        for (Entry e : entries) {
            value += e.getValue();
        }

        return value;
    }
}
