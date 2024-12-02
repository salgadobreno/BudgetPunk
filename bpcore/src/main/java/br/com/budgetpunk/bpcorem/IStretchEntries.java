package br.com.budgetpunk.bpcorem;

import org.joda.money.Money;

import java.util.List;

public interface IStretchEntries extends IRepository<IStretchEntry> {

    List<IStretchEntry> activeThisMonth();

    Money getMonthlyModifier();

}
