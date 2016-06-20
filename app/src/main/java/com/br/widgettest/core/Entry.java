package com.br.widgettest.core;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.io.Serializable;
import java.math.RoundingMode;
import java.util.Date;

/**
 * Created by Breno on 1/8/2016.
 */
public abstract class Entry implements Serializable { //TODO: keep?
    public enum EntryType {
        DAILY,
        FIXED,
        BOUGHT
    }

    public static final CurrencyUnit CU = CurrencyUnit.USD;

    private String name;
    private Money value;
    private Date startDate;
    private Date endDate;
    private Category category;
    private EntryType entryType;

    public Entry(String name, Double value, Date startDate, Date endDate, Category category, EntryType entryType) {
        this.name = name;
        this.value = Money.of(CU, value, RoundingMode.HALF_EVEN);
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.entryType = entryType;
    }

    public String getName() {
        return name;
    }

    public Money getValue() {
        return value;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Category getCategory() {
        return category;
    }

    public EntryType getEntryType() {
        return entryType;
    }
}
