package com.br.widgettest.core;

import java.util.Date;

/**
 * Created by Breno on 1/17/2016.
 */
public class FixedEntry extends Entry {
    private static final EntryType ENTRY_TYPE = EntryType.FIXED;

    public FixedEntry(String name, Double value, Category category) {
        super(name, value, new Date(), null, category, ENTRY_TYPE);
    }

    @Override
    public Double getValue() {
        return super.getValue() / 30;
    }

    public FixedEntry(String name, Double value, Date startDate, Date endDate, Category category) {
        super(name, value, startDate, endDate, category, ENTRY_TYPE);
    }
}
