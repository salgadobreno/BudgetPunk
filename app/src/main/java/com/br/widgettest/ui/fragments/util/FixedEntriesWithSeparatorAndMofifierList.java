package com.br.widgettest.ui.fragments.util;

import com.br.widgettest.core.Entry;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.ledger.decorators.EntryByDateMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Breno on 4/8/2016.
 */
public class FixedEntriesWithSeparatorAndMofifierList extends ArrayList<Object> {
    public enum EntryViewType {
        DATE_SEPARATOR(0), ENTRY(1), MODIFIER_SUMMARY(2);

        private int i;

        EntryViewType(int i) {
            this.i = i;
        }

        public int getI() {
            return i;
        }
    }

    public FixedEntriesWithSeparatorAndMofifierList(EntryByDateMap map, ILedger ledger) {

        List<Date> inverseSortedDates = new ArrayList<>(map.keySet());
        Collections.sort(inverseSortedDates);

        for (Date date : inverseSortedDates) {
            add(date);
            List<Entry> entries = map.get(date);
            for (Entry e : entries) {
                add(e);
            }
            Double modifier = ledger.calcMonthModifier(date);

            add(modifier);
        }
    }

    public EntryViewType getEntryViewType(int position) {
        Object o = get(position);
        EntryViewType r;
        if (o instanceof Date) r = EntryViewType.DATE_SEPARATOR;
        else if (o instanceof Entry) r = EntryViewType.ENTRY;
        else if (o instanceof Double) r = EntryViewType.MODIFIER_SUMMARY;
        else throw new RuntimeException("I don't know what Object: " + o + " is.");

        return r;
    }

}
