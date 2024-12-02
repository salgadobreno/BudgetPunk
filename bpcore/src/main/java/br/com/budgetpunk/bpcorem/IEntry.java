package br.com.budgetpunk.bpcorem;

import org.joda.money.Money;

import java.util.Date;

public interface IEntry extends Entity {

    enum EntryType {
        DAILY,
        BUDGET,
        STRETCH
    }

    String name();

    Money value();

    Date startDate();

    Date endDate();

    EntryType entryType();

}
