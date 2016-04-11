package com.br.widgettest.core.ledger;

import com.br.widgettest.core.Entry;

import java.util.ArrayList;

/**
 * Created by Breno on 1/17/2016.
 */
class FixedEntries extends ArrayList<Entry> {
    public Double getDailyValue() {
        Double value = 0d;

        for (Entry e : this) {
            value += e.getValue();
        }

        return value;
    }
}
