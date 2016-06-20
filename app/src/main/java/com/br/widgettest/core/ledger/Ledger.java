package com.br.widgettest.core.ledger;

import android.util.Log;

import com.br.widgettest.core.BuyEntry;
import com.br.widgettest.core.Category;
import com.br.widgettest.core.DailyEntry;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.FixedEntry;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;

import org.joda.money.Money;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Breno on 1/8/2016.
 */
public class Ledger implements ILedger {
    private static final String TAG = "LEDGER";

    private CategoryDao categoryDao;
    private EntryDao entryDao;

    private final DailyEntries dailyEntries;
    private final FixedEntries fixedEntries;
    private final BuyEntries buyEntries;

    public Ledger(EntryDao entryDao, CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
        this.entryDao = entryDao;

        //init lists and maps
        dailyEntries = new DailyEntries();
        fixedEntries = new FixedEntries();
        buyEntries   = new BuyEntries();

        //init categories
        if (categoryDao.list().isEmpty()) {
            Log.d(TAG, "Ledger: categoryDao list empty, seeding.");
            categoryDao.store(Category.getCategories());
        }

        //init entries
        List<Entry> entries = entryDao.list();
        if (!entries.isEmpty()) {
            //TODO: tosco
            for (Entry e : entries) {
                add(e, false);
            }
            // save
//            persist();
        }
    }

    @Override
    public void rm(Entry entry) {
        switch (entry.getEntryType()) {
            case BOUGHT:
                buyEntries.remove(entry);
                break;
            case DAILY:
                dailyEntries.remove(entry);
                break;
            case FIXED:
                fixedEntries.remove(entry);
                break;
        }
        entryDao.remove(entry);
        persist();
    }

    @Override
    public Money calcAvailableFromFixed() {
        return fixedEntries.getDailyValue();
    }

    @Override
    public Money calcDailyAvailable(Date date) {
        return fixedEntries.getDailyValue().plus(buyEntries.getDailyBuysModifier(date));
    }

    @Override
    public Money calcMonthModifier(Date date) {
        return buyEntries.getDailyBuysModifier(date);
    }

    @Override
    public List<? extends Entry> getEntries(Entry.EntryType entryType) {
        switch (entryType) {
            case DAILY: return dailyEntries;
            case FIXED: return fixedEntries;
            case BOUGHT: return buyEntries;
            default: throw new IllegalArgumentException(String.format("I don't know what %s is", entryType));
        }
    }

    @Override
    public void add(Entry entry) {
        Log.d(TAG, "add");
        add(entry, true);
    }

    private void add(Entry entry, boolean persist) {
        Entry.EntryType entryType = entry.getEntryType();

        switch (entryType) {
            case DAILY: dailyEntries.add((DailyEntry) entry); break;
            case FIXED: fixedEntries.add((FixedEntry) entry); break;
            case BOUGHT: buyEntries.add((BuyEntry) entry); break;
            default: throw new IllegalArgumentException(String.format("I don't know what %s is", entryType));
        }

        if (persist) persist();
    }

    private void persist() {
        List<Entry> entries = new ArrayList<>();
        entries.addAll(dailyEntries);
        entries.addAll(fixedEntries);
        entries.addAll(buyEntries);
        entryDao.store(entries);
        Log.d(TAG, "persist");
    }
}
