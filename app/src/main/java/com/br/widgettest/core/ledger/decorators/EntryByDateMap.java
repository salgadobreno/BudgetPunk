package com.br.widgettest.core.ledger.decorators;

import com.br.widgettest.core.Entry;
import com.br.widgettest.ui.fragments.util.DailyDate;
import com.br.widgettest.ui.fragments.util.MonthlyDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Entries mapeadas por startDate com granulação pelo Granularity
 *
 * Created by Breno on 1/21/2016.
 */
public class EntryByDateMap extends HashMap<Date, List<Entry>> {

    public enum Granularity {
        DAILY,
        MONTHLY
    }

    public EntryByDateMap(List<com.br.widgettest.core.Entry> entries, Granularity granularity) {
        for (com.br.widgettest.core.Entry entry : entries) {
            Date date;
            switch (granularity) {
                case DAILY:
                    date = new DailyDate(entry.getStartDate().getTime());
                    break;
                case MONTHLY:
                    date = new MonthlyDate(entry.getStartDate().getTime());
                    break;
                default: throw new IllegalArgumentException(String.format("Dunno what %s is", granularity));
            }

            if (get(date) == null) {
                put(date, new ArrayList<com.br.widgettest.core.Entry>());
            }
            get(date).add(entry);
        }
    }
}

