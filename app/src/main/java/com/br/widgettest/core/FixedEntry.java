package com.br.widgettest.core;

import org.joda.money.Money;

import java.math.RoundingMode;
import java.util.Date;

/**
 * Created by Breno on 1/17/2016.
 */
public class FixedEntry extends Entry {
    private static final EntryType ENTRY_TYPE = EntryType.FIXED;

    public FixedEntry(String name, Double value, Category category) {
        super(name, value, new Date(), null, category, ENTRY_TYPE);
    }

    public Money getMonthlyValue() {
        return getValue().dividedBy(30, RoundingMode.HALF_EVEN);
    }

    public FixedEntry(String name, Double value, Date startDate, Date endDate, Category category) {
        super(name, value, startDate, endDate, category, ENTRY_TYPE);
    }
}
