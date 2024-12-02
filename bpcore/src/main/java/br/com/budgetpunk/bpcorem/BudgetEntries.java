package br.com.budgetpunk.bpcorem;

import org.joda.money.Money;

import java.util.List;

public class BudgetEntries extends InMemoryRepository<IBudgetEntry> implements IBudgetEntries {
    public BudgetEntries(List<IBudgetEntry> list) {
        super(list);
    }

    @Override
    public Money dailyAvailable() {
        Money r = Money.zero(Entry.CU);
        for (IBudgetEntry budgetEntry : all()) {
            r = r.plus(budgetEntry.vd());
        }
        return r;
    }
}
