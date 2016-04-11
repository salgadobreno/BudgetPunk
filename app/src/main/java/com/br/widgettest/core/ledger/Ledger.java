package com.br.widgettest.core.ledger;

import android.content.Context;

import com.br.widgettest.core.Category;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.FixedEntry;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;
import com.br.widgettest.core.exceptions.NotImplementedException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Breno on 1/8/2016.
 */
public class Ledger implements ILedger {
    private CategoryDao categoryDao;
    private EntryDao entryDao;

    private final DailyEntries dailyEntries;
    private final FixedEntries fixedEntries;
    private final BuyEntries buyEntries;
//    private static SaveEntries saveEntries = new SaveEntries();

    public Ledger(Context context) {
        categoryDao = new CategoryDao(context);
        entryDao = new EntryDao(context);

        //init lists and maps
        dailyEntries = new DailyEntries();
        fixedEntries = new FixedEntries();
        buyEntries   = new BuyEntries();

        //init categories
        if (categoryDao.list().isEmpty()) {
            categoryDao.store(Category.getCategories());
        }

        //init entries
        List<Entry> entries = entryDao.list();
        if (entries.isEmpty()) {
//            Calendar yesterday = Calendar.getInstance();
//            yesterday.roll(Calendar.DAY_OF_MONTH, -1);
//            add(new DailyEntry(-77.11, Category.TRANSPORT, yesterday.getTime()));
//            add(new DailyEntry(-88.88, Category.TRANSPORT, yesterday.getTime()));
//
//            add(new DailyEntry(-55.55, Category.CLOTHES));
//            add(new DailyEntry(-66.66, Category.FOOD));
//            add(new DailyEntry(-11.11, Category.TRANSPORT));


//            add(new FixedEntry("Salario", 4200d, Category.NULL));
//            add(new FixedEntry("VR", 450d, Category.NULL));
//
//            add(new FixedEntry("Aluguel", -1300d, Category.HOUSE));
//            add(new FixedEntry("Internet", -90d, Category.NULL));
//            add(new FixedEntry("Telefone", -90d, Category.NULL));
//            add(new FixedEntry("Gasolina", -200d, Category.TRANSPORT));
//            add(new FixedEntry("Luz", -60d, Category.NULL));
//            add(new FixedEntry("Economia", -500d, Category.NULL));

//            persist();
        } else {
//                TODO: tosco
            for (Entry e : entries) {
                add(e, false);
            }
            //save
            persist();
        }
    }

    @Override
    public void add(Entry entry) {
        add(entry, true);
    }

    @Override
    public void rm(Entry entry) {
        throw new NotImplementedException();
    }

    @Override
    public double calcDailyAvailable(Date date) {
        return fixedEntries.getDailyValue() - buyEntries.getDailyBuysModifier(date);
    }

    @Override
    public double calcMonthModifier(Date date) {
        return buyEntries.getDailyBuysModifier(date);
    }

    @Override
    public List<Entry> getEntries(Entry.EntryType entryType) {
        switch (entryType) {
            case DAILY: return dailyEntries;
            case FIXED: return fixedEntries;
            case BOUGHT: return buyEntries;
            default: throw new IllegalArgumentException(String.format("I don't know what %s is", entryType));
        }
    }

    private void add(Entry entry, boolean persist) {
        Entry.EntryType entryType = entry.getEntryType();

        switch (entryType) {
            case DAILY: dailyEntries.add(entry); break;
            case FIXED: fixedEntries.add((FixedEntry) entry); break;
            case BOUGHT: buyEntries.add(entry); break;
            default: throw new IllegalArgumentException(String.format("I don't know what %s is", entryType));
        }

        if (persist) persist();
    }

    private void persist() {
        List<Entry> entries = new ArrayList<>();
        entries.addAll(dailyEntries);
        entries.addAll(fixedEntries);
        entryDao.store(entries);
    }
}
