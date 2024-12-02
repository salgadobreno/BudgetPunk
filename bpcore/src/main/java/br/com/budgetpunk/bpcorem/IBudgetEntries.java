package br.com.budgetpunk.bpcorem;

import org.joda.money.Money;

public interface IBudgetEntries extends IRepository<IBudgetEntry> {

    Money dailyAvailable();
    //TODO: consider using a decorator to declare Daily vs Monthly values instead of different interfaces,
    //      then all/most Money values could be clearly speaking the same value granularity

}
