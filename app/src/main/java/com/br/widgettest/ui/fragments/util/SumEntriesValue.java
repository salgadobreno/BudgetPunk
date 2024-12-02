package com.br.widgettest.ui.fragments.util;

import com.br.widgettest.core.Entry;

import java.util.List;

public class SumEntriesValue {
    private final List<Entry> entries;

    public SumEntriesValue(List<Entry> entries) {
        this.entries = entries;
    }

    public double get() {
        double val = 0;

        for (Entry entry : entries) {
            val += entry.getValue().getAmount().doubleValue();
        }

        return val;
    }
}
