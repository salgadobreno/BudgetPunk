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

    public void testGetPeriod1Month() throws Exception {
        assertEquals("jan/2016", buyEntry.getPeriod());
    }

    public void testGetPeriod2Months() throws Exception {
        BuyEntry buyEntry = new BuyEntry("teste", -200d, Instant.parse("2016-01-01").toDate(), Instant.parse("2016-03-01").toDate(), null);
        assertEquals("jan/2016 - fev/2016", buyEntry.getPeriod());
    }

    public void testGetPeriod2MonthsFromParcela() throws Exception {
        BuyEntry buyEntry = BuyEntry.criarPorParcela("teste", 1, 2, 30, Instant.parse("2016-01-01").toDate(), null);
        assertEquals("jan/2016 - fev/2016", buyEntry.getPeriod());

        BuyEntry buyEntry2 = BuyEntry.criarPorParcela("teste", 1, 2, 30, Instant.parse("2017-09-01").toDate(), null);
        assertEquals("set/2017 - out/2017", buyEntry2.getPeriod());
    }

    public void testGetPeriod3Months() throws Exception {
        BuyEntry buyEntry = new BuyEntry("teste", -200d, Instant.parse("2016-01-01").toDate(), Instant.parse("2016-04-01").toDate(), null);
        assertEquals("jan/2016 - mar/2016", buyEntry.getPeriod());
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

    public void testCreateFromParcelaNumAndParcelaValue() throws Exception {
        int parcela = 2;
        int totalParcelas = 3;
        double valorParcela = -100.00;

        // refDate 2016-02-1
        // startDate é 2016-01-01
        // endDate é 2016-03-31 + 1 = 2016-04-01
        // value é -300.00
        // modifier é (31+29+31)/-300 = 3.30
        BuyEntry buyEntry = BuyEntry.criarPorParcela("", parcela, totalParcelas, valorParcela, Instant.parse("2016-02-01").toDate(), Category.NULL);

        assertEquals(Instant.parse("2016-01-01").toDate(), buyEntry.getStartDate());
        assertEquals(Instant.parse("2016-04-01").toDate(), buyEntry.getEndDate());
        assertEquals(-300.00, buyEntry.getValue().getAmount().doubleValue());
        assertEquals(-3.30, buyEntry.getModifier().getAmount().doubleValue());

        // mesmo valor na parcela 1, refDate 2016-01-01
        // startDate é 2016-01-01
        // endDate é 2016-03-31 + 1 = 2016-04-01
        // value é -300.00
        // modifier é 3.30
        BuyEntry buyEntry2 = BuyEntry.criarPorParcela("", 1, totalParcelas, valorParcela, Instant.parse("2016-01-01").toDate(), Category.NULL);

        assertEquals(Instant.parse("2016-01-01").toDate(), buyEntry2.getStartDate());
        assertEquals(Instant.parse("2016-04-01").toDate(), buyEntry2.getEndDate());
        assertEquals(-300.00, buyEntry2.getValue().getAmount().doubleValue());
        assertEquals(-3.30, buyEntry2.getModifier().getAmount().doubleValue());

        // 5 parcelas
        // 4 parcela atual
        // valor 500
        // modifier -100.00
        // refDate 2016-04-05
        // startDate 2016-01-1
        // endDate 2016-07-01
        BuyEntry buyEntry3 = BuyEntry.criarPorParcela("", 4, 5, -100.00, Instant.parse("2016-04-01").toDate(), Category.NULL);

        assertEquals(Instant.parse("2016-01-01").toDate(), buyEntry3.getStartDate());
        assertEquals(Instant.parse("2016-06-01").toDate(), buyEntry3.getEndDate());
        assertEquals(-500.00, buyEntry3.getValue().getAmount().doubleValue());
        assertEquals(-3.29, buyEntry3.getModifier().getAmount().doubleValue());
    }
}