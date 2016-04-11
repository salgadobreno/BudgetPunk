package com.br.widgettest.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Breno on 1/28/2016.
 */
public class BuyEntry extends Entry {
    private static final EntryType ENTRY_TYPE = EntryType.BOUGHT;

    public BuyEntry(String name, Double value, Date startDate, Date endDate, Category category) {
        super(name, value, startDate, endDate, category, ENTRY_TYPE);
    }

    @Override
    public Double getValue() {
        return super.getValue();
    }

    public String getPeriod() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");

        return sdf.format(getStartDate()) + " - " + sdf.format(getEndDate());
    }

    public Double getModifier() {
        long milliseconds = getEndDate().getTime() - getStartDate().getTime();
        long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
        return getValue() / days;
    }

}
