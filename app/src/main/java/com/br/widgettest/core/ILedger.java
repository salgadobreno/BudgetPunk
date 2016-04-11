package com.br.widgettest.core;

import java.util.Date;
import java.util.List;

/**
 * Created by Breno on 4/7/2016.
 */
public interface ILedger {

    double calcDailyAvailable(Date date);

    double calcMonthModifier(Date date);

    List<Entry> getEntries(Entry.EntryType entryType);

    void add(Entry entry);

    void rm(Entry entry);

}
