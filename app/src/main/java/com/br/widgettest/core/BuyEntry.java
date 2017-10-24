package com.br.widgettest.core;

import com.orm.SugarRecord;

import org.joda.money.Money;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.Months;

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

    public static BuyEntry criarPorParcela(String name, int parcela, int totalParcelas, double valorParcela, Date referenceDate, Category category) {
        //TODO: mudar pra fabrica/forma descritiva de criação?
        double totalValue = totalParcelas * valorParcela;
        int monthsBack = parcela - 1;
        int monthsForward = totalParcelas - parcela;
        Date startDate = new LocalDate(referenceDate.getTime()).minusMonths(monthsBack).withDayOfMonth(1).toDate();
        Date endDate = new LocalDate(referenceDate.getTime()).plusMonths(monthsForward + 1).withDayOfMonth(1).toDate();

        return new BuyEntry(name, totalValue, startDate, endDate, category);
    }

    @Override
    public Money getValue() {
        return super.getValue();
    }

    public String getPeriod() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
        Instant start = new Instant(getStartDate().getTime());
        Instant end = new Instant(getEndDate().getTime());

        if (start.get(DateTimeFieldType.year()) == end.get(DateTimeFieldType.year())) {
            if (start.get(DateTimeFieldType.monthOfYear()) - end.get(DateTimeFieldType.monthOfYear()) == -1) {
                return sdf.format(getStartDate());
            } else {
                return sdf.format(getStartDate()) + " - " + sdf.format(new LocalDate(getEndDate().getTime()).minusDays(1).toDate());
            }
        } else {
            return sdf.format(getStartDate()) + " - " + sdf.format(new LocalDate(getEndDate().getTime()).minusDays(1).toDate());
        }
    }

    public Money getModifier() {
        return getValue().dividedBy(getPeriodInDays(), RoundingMode.HALF_EVEN);
    }

    public int getPeriodInDays(){
        long milliseconds = getEndDate().getTime() - getStartDate().getTime();

        return (int) TimeUnit.MILLISECONDS.toDays(milliseconds);
    }
}
