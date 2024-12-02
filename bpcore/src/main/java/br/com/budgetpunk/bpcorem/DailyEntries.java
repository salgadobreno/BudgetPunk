package br.com.budgetpunk.bpcorem;

import org.joda.money.Money;

import java.util.List;

public class DailyEntries extends InMemoryRepository<IDailyEntry> implements IDailyEntries {
    public DailyEntries(List<IDailyEntry> list) {
        super(list);
    }

    @Override
    public Money balance() {
        Money r = Money.zero(Entry.CU);
        for (IDailyEntry dailyEntry : all()) {
            r = r.plus(dailyEntry.value());
        }
        return r;
    }
}
