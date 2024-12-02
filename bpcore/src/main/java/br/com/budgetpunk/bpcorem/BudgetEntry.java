package br.com.budgetpunk.bpcorem;

import org.joda.money.Money;

import java.math.RoundingMode;
import java.util.Date;

/**
 * Created by Breno on 1/17/2016.
 */
public class BudgetEntry extends Entry implements IBudgetEntry {
    private static final EntryType ENTRY_TYPE = EntryType.BUDGET;

    public BudgetEntry(String name, Double value) {
        super(name, value, new Date(), null, ENTRY_TYPE);
    }

    public BudgetEntry(String name, Double value, Date startDate, Date endDate) {
        super(name, value, startDate, endDate, ENTRY_TYPE);
    }

    @Override
    public Money vd() {
        return value().dividedBy(30, RoundingMode.HALF_EVEN);
    }
}
