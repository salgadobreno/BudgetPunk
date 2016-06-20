package com.br.widgettest.core;

import com.aop.annotations.Trace;

import org.joda.money.Money;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Days;
import org.joda.time.Instant;
import org.joda.time.LocalDateTime;

import java.math.RoundingMode;
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
//        Date startDateBegginningOfDay = LocalDateTime.fromDateFields(startDate).withMillisOfDay(0).toDate();
//        Date endDateEndOfPreviousDay = LocalDateTime.fromDateFields(endDate).

        if (new Instant(startDate.getTime()).isAfter(new Instant(endDate.getTime()))) {
            throw new IllegalArgumentException("StartDate is after EndDate");
        }
        // startDate and endDate should be on first days of months
        if (new Instant(startDate.getTime()).get(DateTimeFieldType.dayOfMonth()) != 1 ||
            new Instant(endDate.getTime()).get(DateTimeFieldType.dayOfMonth()) != 1) {
            throw new IllegalArgumentException("Only full months are allowed for BuyEntries");
        }
    }

    @Override
    public Money getValue() {
        return super.getValue();
    }

    public String getPeriod() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");

        return sdf.format(getStartDate()) + " - " + sdf.format(getEndDate());
    }

    public Money getModifier() {
        return getValue().dividedBy(getPeriodInDays(), RoundingMode.HALF_EVEN);
    }

    public int getPeriodInDays(){
        long milliseconds = getEndDate().getTime() - getStartDate().getTime();

        return (int) TimeUnit.MILLISECONDS.toDays(milliseconds);
    }
}
