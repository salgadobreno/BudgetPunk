package com.br.widgettest.ui.fragments.util;

import android.util.Log;

import com.br.widgettest.core.Entry;
import com.br.widgettest.core.ledger.decorators.EntryByDateMap;

import org.joda.money.Money;
import org.joda.time.Days;
import org.joda.time.Instant;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Breno on 1/25/2016.
 */
public class EntriesWithSeparatorAndSummaryList extends ArrayList<Object> {
    private double dailyAvailable;
    private List<Entry> entries;

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

    public EntriesWithSeparatorAndSummaryList(List<Entry> entries, double dailyAvailable) {
        this.dailyAvailable = dailyAvailable;
        this.entries = entries;

        makeList();
    }

    private void makeList() {
        clear();

        if (entries.isEmpty()) return;

        EntryByDateMap map = new EntryByDateMap(entries, EntryByDateMap.Granularity.DAILY); //TODO: WARNING

        Double balance = 0d;

        List<Date> inverseSortedDates = new ArrayList<>(map.keySet());
        Collections.sort(inverseSortedDates);

        // save missing between dates
        Date firstDate = inverseSortedDates.get(0);
        Date lastDate = inverseSortedDates.get(inverseSortedDates.size() - 1);
        int days = Days.daysBetween(new Instant(firstDate), new Instant(lastDate)).getDays();

        DailyDate control = new DailyDate(firstDate.getTime());
        for (int i = 0; i < days; i++) {
            if (!inverseSortedDates.contains(control)) inverseSortedDates.add(control);
            control = new DailyDate(new LocalDate(control).plusDays(1).toDate().getTime());
        }
        Collections.sort(inverseSortedDates);

        for (Date date : inverseSortedDates) {
            add(date);
            List<Entry> entries = map.get(date);
            if (entries == null) entries = Collections.emptyList(); // fuck null

            for (Entry e : entries) {
                add(e);
            }
            Double[] doubles = new Double[2]; // total, balance
            doubles[0] = entries.isEmpty() ? -dailyAvailable : calcEntryListTotal(entries).getAmount().doubleValue(); // empty day uses daily value

            balance += dailyAvailable + doubles[0];//calcEntryListTotal(entries).getAmount().doubleValue();
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

    @Override
    public boolean remove(Object object) {
        Log.d("EntriesWithSeparator", "remove() called with: " + "object = [" + object + "]" + " " + ((Entry) object).getValue());
        boolean ret = super.remove(object);
        if (ret) entries.remove(object);
        makeList();

        return ret;
    }

    private Money calcEntryListTotal(List<Entry> entries) {
        Money value = Money.zero(Entry.CU);

        for (Entry e : entries) {
            value = value.plus(e.getValue());
        }

        return value;
    }
}
