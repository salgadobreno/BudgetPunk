package com.br.widgettest.core.entity.util;

import com.br.widgettest.core.BuyEntry;
import com.br.widgettest.core.Category;
import com.br.widgettest.core.DailyEntry;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.FixedEntry;
import com.br.widgettest.core.entity.EntryEntity;

/**
 * Created by Breno on 8/24/2016.
 */
public class EntryEntityToEntry {
    private Entry entry;

    public EntryEntityToEntry(EntryEntity entity) {
        Category category = Category.getCategories().get(entity.getCategoryId());

        switch (entity.getEntryType()) {
            case DAILY:
                this.entry = new DailyEntry(entity.getValue(), category, entity.getStartDate()); //TODO: Category.find_by_id
                entry.setOrigin(entity);
                break;
            case FIXED:
                this.entry = new FixedEntry(entity.getName(), entity.getValue(), entity.getStartDate(), entity.getEndDate(), category); //TODO: Category.find_by_id
                entry.setOrigin(entity);
                break;
            case BOUGHT:
                this.entry = new BuyEntry(entity.getName(), entity.getValue(), entity.getStartDate(), entity.getEndDate(), category); //TODO: Category.find_by_id
                entry.setOrigin(entity);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public Entry get() {
        return entry;
    }
}
