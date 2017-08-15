package com.br.widgettest.core.entity;

import com.br.widgettest.core.Entry;

import java.util.Date;

/**
 * Created by Breno on 8/24/2016.
 */
public interface EntryEntity {
    Long getId();
    String getName();
    void setName(String name);
    double getValue();
    void setValue(double value);
    Date getStartDate();
    void setStartDate(Date date);
    Date getEndDate();
    void setEndDate(Date date);
    int getCategoryId();
    void setCategoryId(int id);
    Entry.EntryType getEntryType();
    void setEntryType(Entry.EntryType entryType);
}
