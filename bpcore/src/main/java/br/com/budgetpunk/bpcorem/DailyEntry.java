package br.com.budgetpunk.bpcorem;

import java.util.Date;

/**
 * Created by Breno on 1/17/2016.
 */
public class DailyEntry extends Entry implements IDailyEntry {
    private static final EntryType ENTRY_TYPE = EntryType.DAILY;

    public DailyEntry(Double value) {
        super(null, value, new Date(), null, ENTRY_TYPE);
    }

    public DailyEntry(Double value, Date startDate) {
        super(null, value, startDate, null, ENTRY_TYPE);
    }

    public DailyEntry(String name, Double value, Date startDate, Date endDate) {
        super(name, value, startDate, endDate, ENTRY_TYPE);
    }
}
