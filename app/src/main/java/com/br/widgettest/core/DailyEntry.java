package com.br.widgettest.core;

import java.util.Date;

/**
 * Created by Breno on 1/17/2016.
 */
public class DailyEntry extends Entry {
    private static final EntryType ENTRY_TYPE = EntryType.DAILY;

    public DailyEntry(Double value, Category category) {
        super(null, value, new Date(), null, category, ENTRY_TYPE);
    }

    public DailyEntry(Double value, Category category, Date startDate) {
        super(null, value, startDate, null, category, ENTRY_TYPE);
    }

    public DailyEntry(String name, Double value, Date startDate, Date endDate, Category category) {
        super(name, value, startDate, endDate, category, ENTRY_TYPE);
    }
}
