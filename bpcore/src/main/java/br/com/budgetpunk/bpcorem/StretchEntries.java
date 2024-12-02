package br.com.budgetpunk.bpcorem;

import org.joda.money.Money;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StretchEntries extends InMemoryRepository<IStretchEntry> implements IStretchEntries {
    private final ITimeProvider timeProvider;

    public StretchEntries(List<IStretchEntry> list, ITimeProvider timeProvider) {
        super(list);
        this.timeProvider = timeProvider;
    }

    @Override
    public List<IStretchEntry> activeThisMonth() {
        Date now = timeProvider.now();
        List<IStretchEntry> filtered = new ArrayList<>();

        for (IStretchEntry stretchEntry : all()) {
            if (now.compareTo(stretchEntry.startDate()) >= 0 && now.compareTo(stretchEntry.endDate()) < 0 ) {
                filtered.add(stretchEntry);
            }
        }

        return filtered;
    }

    @Override
    public Money getMonthlyModifier() {
        Money r = Money.zero(Entry.CU);
        for (IStretchEntry stretchEntry : activeThisMonth()) {
            r = r.plus(stretchEntry.vd());
        }
        return r;
    }
}
