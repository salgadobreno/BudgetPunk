package com.br.widgettest.core.ledger;

import com.br.widgettest.core.Entry;
import com.br.widgettest.core.FixedEntry;

import org.joda.money.Money;

import java.util.ArrayList;

/**
 * Created by Breno on 1/17/2016.
 */
class FixedEntries extends ArrayList<FixedEntry> {
    public Money getDailyValue() {
        Money value = Money.zero(Entry.CU);

        for (FixedEntry e : this) {
            value = value.plus(e.getMonthlyValue());
        }

        return value;
    }
}
