package com.br.widgettest.ui.fragments.util;

import com.br.widgettest.core.Entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntryByPositiveMap extends HashMap<EntryByPositiveMap.Key, List<Entry>> {
    public enum Key {
        IN, OUT
    }

    public EntryByPositiveMap(List<? extends com.br.widgettest.core.Entry> entries) {
        List<com.br.widgettest.core.Entry> inEntries = new ArrayList<>();
        List<com.br.widgettest.core.Entry> outEntries = new ArrayList<>();

        put(Key.IN, inEntries);
        put(Key.OUT, outEntries);
        for (com.br.widgettest.core.Entry entry : entries) {
            if (entry.getValue().isPositiveOrZero()) {
                inEntries.add(entry);
            } else {
                outEntries.add(entry);
            }
        }
    }
}
