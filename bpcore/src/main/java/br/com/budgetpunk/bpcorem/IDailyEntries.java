package br.com.budgetpunk.bpcorem;

import org.joda.money.Money;

public interface IDailyEntries extends IRepository<IDailyEntry> {

    Money balance();

}
