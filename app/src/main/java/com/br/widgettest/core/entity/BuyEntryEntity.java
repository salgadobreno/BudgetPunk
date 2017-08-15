package com.br.widgettest.core.entity;

import com.br.widgettest.core.Entry;
import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Breno on 8/21/2016.
 */
public class BuyEntryEntity extends SugarRecord implements EntryEntity {
    private String name;
    private double value;
    private Date startDate;
    private Date endDate;
    private int categoryId;
    private Entry.EntryType entryType;

    public BuyEntryEntity() {
    }

    public BuyEntryEntity(String name, double value, Date startDate, Date endDate, int categoryId, Entry.EntryType entryType) {
        this.name = name;
        this.value = value;
        this.startDate = startDate;
        this.endDate = endDate;
        this.categoryId = categoryId;
        this.entryType = entryType;
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public int getCategoryId() {
        return categoryId;
    }

    @Override
    public Entry.EntryType getEntryType() {
        return entryType;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void setEntryType(Entry.EntryType entryType) {
        this.entryType = entryType;
    }
}
