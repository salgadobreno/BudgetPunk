package com.br.widgettest.core.ledger.decorators;

import com.br.widgettest.core.Entry;

import java.text.SimpleDateFormat;
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

/**
 * Data que retorna 0 na comparação quando a outra data pertence ao mesmo dia/mês/ano
 */
class DailyDate extends Date {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public DailyDate(long milliseconds) {
        super(milliseconds);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;

        return object instanceof Date && this.hashCode() == object.hashCode();

    }

    @Override
    public int hashCode() {
        return sdf.format(this).hashCode();
    }
}

/**
 * Data que retorna 0 na comparação quando a outra data pertence ao mesmo mês/ano
 */
class MonthlyDate extends Date {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

    public MonthlyDate(long milliseconds) {
        super(milliseconds);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;

        return object instanceof Date && this.hashCode() == object.hashCode();

    }

    @Override
    public int hashCode() {
        return sdf.format(this).hashCode();
    }
}
