package br.com.budgetpunk.bpcorem;

import org.joda.money.Money;
import org.joda.time.Interval;

public interface IStretchEntry extends IEntry {
    Money vd();

    Interval period();

    Installments installments();

    interface Installments {
        int current();

        int total();
    }

}
