package com.br.widgettest.core.entity.util;

import com.br.widgettest.core.Entry;
import com.br.widgettest.core.entity.EntryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Breno on 8/24/2016.
 */
public class EntryEntityList<T extends Entry> {
    private List<T> list = new ArrayList<>();

    public EntryEntityList(List<? extends EntryEntity> list) {
        for (EntryEntity item : list) {
            this.list.add((T) new EntryEntityToEntry(item).get());
        }
    }

    public List<T> get() {
        return list;
    }
}
