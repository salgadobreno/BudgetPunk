package com.br.widgettest.core.ledger;

import com.br.widgettest.core.Entry;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Breno on 1/17/2016.
 */
class DailyEntries extends ArrayList<Entry> {

    public DailyEntries(Collection<? extends Entry> collection) {
        super(collection);
    }

    public DailyEntries() {
    }

    public Double getDailySpent() {
        Double value = 0d;

        for (Entry e : this) {
            value += e.getValue();
        }

        return value;
    }
}
