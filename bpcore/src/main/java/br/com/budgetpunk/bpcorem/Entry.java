package br.com.budgetpunk.bpcorem;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.RoundingMode;
import java.util.Date;

/**
 * Created by Breno on 1/8/2016.
 */
public abstract class Entry implements IEntry {

    public static final CurrencyUnit CU = CurrencyUnit.USD;

    private Long id;
    private String name;
    private Money value;
    private Date startDate;
    private Date endDate;
    private EntryType entryType;

    public Entry(String name, Double value, Date startDate, Date endDate, EntryType entryType) {
        this(null, name, value, startDate, endDate, entryType);
    }

    public Entry(Long id, String name, Double value, Date startDate, Date endDate, EntryType entryType) {
        this.id = id;
        this.name = name;
        this.value = Money.of(CU, value, RoundingMode.HALF_EVEN);
        this.startDate = startDate;
        this.endDate = endDate;
        this.entryType = entryType;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Money value() {
        return value;
    }

    @Override
    public Date startDate() {
        return startDate;
    }

    @Override
    public Date endDate() {
        return endDate;
    }

    @Override
    public EntryType entryType() {
        return entryType;
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public void id(Long id) {
        this.id = id;
    }
}
