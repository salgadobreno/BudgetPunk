package com.br.widgettest.core.ledger;

import com.br.widgettest.core.Entry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Breno on 1/28/2016.
 */
class BuyEntries extends ArrayList<Entry> {
    public BuyEntries() {}

    public BuyEntries(Collection<? extends Entry> collection) {
        super(collection);
    }

    public Double getDailyBuysModifier(Date date) {
        DateFilteredEntries dateFilteredEntries = new DateFilteredEntries(this, date);
        Double value = 0d;

        for (Entry e : dateFilteredEntries) {
            value += e.getValue();
        }

        return value;
    }
}

class DateFilteredEntries extends ArrayList<Entry> {
    public DateFilteredEntries(Collection<? extends Entry> collection, Date date) {
        for (Entry e : collection) {
            if (date.compareTo(e.getStartDate()) >= 0 && date.compareTo(e.getEndDate()) <= 0 ) {
                add(e);
            }
        }
    }
}
