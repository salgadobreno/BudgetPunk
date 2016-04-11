package com.br.widgettest.core;

import org.joda.money.Money;

import java.util.Date;

/**
 * Created by Breno on 1/8/2016.
 */
public abstract class Entry {
    public enum EntryType {
        DAILY,
        FIXED,
        BOUGHT
    }

    private String name;
    private Double value;
    private Money money;
    private Date startDate;
    private Date endDate;
    private Category category;
    private EntryType entryType;

    public Entry(String name, Double value, Date startDate, Date endDate, Category category, EntryType entryType) {
        this.name = name;
        this.value = value;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.entryType = entryType;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
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
