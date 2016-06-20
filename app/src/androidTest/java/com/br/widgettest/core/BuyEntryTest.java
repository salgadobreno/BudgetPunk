package com.br.widgettest.core;

import junit.framework.TestCase;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.Instant;
import org.junit.Test;

/**
 * Created by Breno on 4/10/2016.
 */
public class BuyEntryTest extends TestCase {

    BuyEntry buyEntry = new BuyEntry("teste", -200d, Instant.parse("2016-01-01").toDate(), Instant.parse("2016-02-01").toDate(), null);

    public void testGetValue() throws Exception {
        assertEquals(Money.of(Entry.CU, -200d), buyEntry.getValue());
    }

    public void testGetModifier() throws Exception {
        assertEquals(Money.of(CurrencyUnit.USD, -6.45d), buyEntry.getModifier());
    }

    public void testGetPeriod() throws Exception {
        assertEquals("jan/2010 - fev/2010", buyEntry.getPeriod());
    }

    public void testStartMustBeGreaterThanEnd() throws Exception {
        try {
            BuyEntry buyEntry = new BuyEntry("", -200d, Instant.parse("2010-01-01").toDate(), Instant.parse("2009-02-01").toDate(), null);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    public void testEndDateIsExclusive() throws Exception {
        //2016-05-01 - 2016-06-01 -> 32 dias, mas como end é exclusivo deve ser 31
        BuyEntry buyEntry = new BuyEntry("", -200d, Instant.parse("2016-05-01").toDate(), Instant.parse("2016-06-01").toDate(), null);

        assertEquals(31, buyEntry.getPeriodInDays());
    }

    public void testOnlyAllowsFullMonths() throws Exception {
        try {
            BuyEntry buyEntry = new BuyEntry("teste", -200d, Instant.parse("2010-01-01").toDate(), Instant.parse("2010-02-05").toDate(), null);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }
}