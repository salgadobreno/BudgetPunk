package com.br.widgettest.core.dao;

import com.br.widgettest.core.Entry;
import com.br.widgettest.core.entity.BuyEntryEntity;
import com.br.widgettest.core.entity.DailyEntryEntity;
import com.br.widgettest.core.entity.FixedEntryEntity;
import com.br.widgettest.core.entity.util.EntryEntityList;
import com.br.widgettest.core.exceptions.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Breno on 2/9/2016.
 */
public class EntryDao implements Dao<Entry> {

    @Override
    public Entry find(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Entry> list() {
        List<Entry> dailyEntries = new ArrayList<>(new EntryEntityList<Entry>(DailyEntryEntity.listAll(DailyEntryEntity.class)).get());
        List<Entry> fixedEntries = new ArrayList<>(new EntryEntityList<Entry>(FixedEntryEntity.listAll(FixedEntryEntity.class)).get());
        List<Entry> buyEntries   = new ArrayList<>(new EntryEntityList<Entry>(BuyEntryEntity.listAll(BuyEntryEntity.class)).get());

        List<Entry> all = new ArrayList<>();
        all.addAll(dailyEntries);
        all.addAll(fixedEntries);
        all.addAll(buyEntries);
        return all;
    }

    @Override
    public void save(Entry entry) {
        Entry.EntryType entryType = entry.getEntryType();

        switch (entryType) {
            case DAILY:
                DailyEntryEntity.save(entry.toEntity());
                break;
            case FIXED:
                FixedEntryEntity.save(entry.toEntity());
                break;
            case BOUGHT:
                BuyEntryEntity.save(entry.toEntity());
                break;
        }
    }

    @Override
    public void remove(Entry entry) {
        Entry.EntryType entryType = entry.getEntryType();

        switch (entryType) {
            case DAILY:
                DailyEntryEntity.delete(entry.toEntity());
                break;
            case FIXED:
                FixedEntryEntity.delete(entry.toEntity());
                break;
            case BOUGHT:
                BuyEntryEntity.delete(entry.toEntity());
                break;
        }
    }

    @Override
    public void store(List<Entry> entries) {
        throw new NotImplementedException();
    }
}
