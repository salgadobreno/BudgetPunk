package br.com.budgetpunk.bpcorem;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.junit.Test;

import static org.junit.Assert.*;

public class StretchEntryTest {

    StretchEntry stretchEntry = new StretchEntry("teste", -200d, Instant.parse("2016-01-01").toDate(), Instant.parse("2016-02-01").toDate());

    @Test
    public void testGetValue() {
        assertEquals(Money.of(Entry.CU, -200d), stretchEntry.value());
    }

    @Test
    public void testVd() {
        assertEquals(Money.of(CurrencyUnit.USD, -6.45d), stretchEntry.vd());
    }

    @Test
    public void getTotalParcels_1Month_is1() {
        assertEquals(1, stretchEntry.installments().total());
    }

    @Test
    public void getTotalParcels_2Months_is2() {
        StretchEntry buyEntry = new StretchEntry("teste", -200d, Instant.parse("2016-01-01").toDate(), Instant.parse("2016-03-01").toDate());
        assertEquals(2, buyEntry.installments().total());
    }

    @Test
    public void getTotalParcels_3Months_is3() {
        StretchEntry buyEntry = new StretchEntry("teste", -200d, Instant.parse("2016-01-01").toDate(), Instant.parse("2016-04-01").toDate());
        assertEquals(3, buyEntry.installments().total());
    }

    @Test
    public void getCurrentParcel_inFirstMonth_is1() {
        DateTime today = new DateTime();
        StretchEntry buyEntry = new StretchEntry("teste", -200d, today.dayOfMonth().withMinimumValue().toDate(), today.plusMonths(2).dayOfMonth().withMinimumValue().toDate());
        assertEquals(1, buyEntry.installments().current());
    }

    @Test
    public void getCurrentParcel_inSecondMonth_is2() {
        DateTime today = new DateTime();
        StretchEntry buyEntry = new StretchEntry("teste", -200d, today.minusMonths(1).dayOfMonth().withMinimumValue().toDate(), today.plusMonths(2).dayOfMonth().withMinimumValue().toDate());
        assertEquals(2, buyEntry.installments().current());
    }

    @Test
    public void getCurrentParcel_inThirdMonth_is3() {
        DateTime today = new DateTime();
        StretchEntry buyEntry = new StretchEntry("teste", -200d, today.dayOfMonth().withMinimumValue().minusMonths(2).toDate(), today.plusMonths(2).dayOfMonth().withMinimumValue().toDate());
        assertEquals(3, buyEntry.installments().current());
    }

    @Test
    public void getCurrentParcel_finished3MonthsAgo_remains3() {
        DateTime today = new DateTime();
        StretchEntry buyEntry = new StretchEntry("teste", -200d, today.dayOfMonth().withMinimumValue().minusMonths(6).toDate(), today.dayOfMonth().withMinimumValue().minusMonths(3).toDate());
        assertEquals(3, buyEntry.installments().current());
    }

    @Test
    public void testGetPeriod1Month() {
        assertEquals(1, stretchEntry.period().toPeriod().getMonths());
//        assertEquals("jan/2016", stretchEntry.period());
//        TODO: if this format is desired, should be moved to a printer of formatter
    }

    @Test
    public void testGetPeriod2Months() {
        StretchEntry buyEntry = new StretchEntry("teste", -200d, Instant.parse("2016-01-01").toDate(), Instant.parse("2016-03-01").toDate());
//        assertEquals("jan/2016 - fev/2016", buyEntry.period());
//        TODO: if this format is desired, should be moved to a printer of formatter
    }

    @Test
    public void testGetPeriod2MonthsFromParcela() {
        StretchEntry stretchEntry = StretchEntry.criarPorParcela("teste", 1, 2, 30, Instant.parse("2016-01-01").toDate());
        assertEquals(2, stretchEntry.period().toPeriod().getMonths());
//        assertEquals("jan/2016 - fev/2016", buyEntry.period());

        StretchEntry stretchEntry1 = StretchEntry.criarPorParcela("teste", 1, 2, 30, Instant.parse("2017-09-01").toDate());
        assertEquals(2, stretchEntry1.period().toPeriod().getMonths());
//        assertEquals("set/2017 - out/2017", stretchEntry1.period());
//        TODO: if this format is desired, should be moved to a printer of formatter
    }

    @Test
    public void testGetPeriod3Months() {
        StretchEntry stretchEntry = new StretchEntry("teste", -200d, Instant.parse("2016-01-01").toDate(), Instant.parse("2016-04-01").toDate());
        assertEquals(3, stretchEntry.period().toPeriod().getMonths());
//        assertEquals("jan/2016 - mar/2016", buyEntry.period()); //TODO: printer? formatter?
//        TODO: if this format is desired, should be moved to a printer of formatter
    }

    @Test
    public void testStartMustBeGreaterThanEnd() {
        try {
            StretchEntry stretchEntry = new StretchEntry("", -200d, Instant.parse("2010-01-01").toDate(), Instant.parse("2009-02-01").toDate());
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testEndDateIsExclusive() {
        //2016-05-01 - 2016-06-01 -> 32 dias, mas como end é exclusivo deve ser 31
        StretchEntry stretchEntry = new StretchEntry("", -200d, Instant.parse("2016-05-01").toDate(), Instant.parse("2016-06-01").toDate());

        assertEquals(31, stretchEntry.period().toDuration().getStandardDays());
    }

    @Test
    public void testOnlyAllowsFullMonths() {
        try {
            StretchEntry stretchEntry = new StretchEntry("teste", -200d, Instant.parse("2010-01-01").toDate(), Instant.parse("2010-02-05").toDate());
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testCreateFromParcelaNumAndParcelaValue() {
        int parcela = 2;
        int totalParcelas = 3;
        double valorParcela = -100.00;

        // refDate 2016-02-1
        // startDate é 2016-01-01
        // endDate é 2016-03-31 + 1 = 2016-04-01
        // value é -300.00
        // modifier é (31+29+31)/-300 = 3.30
        StretchEntry stretchEntry = StretchEntry.criarPorParcela("", parcela, totalParcelas, valorParcela, Instant.parse("2016-02-01").toDate());

        assertEquals(Instant.parse("2016-01-01").toDate(), stretchEntry.startDate());
        assertEquals(Instant.parse("2016-04-01").toDate(), stretchEntry.endDate());
        assertEquals(-300.00, stretchEntry.value().getAmount().doubleValue(), 1f);
        assertEquals(-3.30, stretchEntry.vd().getAmount().doubleValue(), 1f);

        // mesmo valor na parcela 1, refDate 2016-01-01
        // startDate é 2016-01-01
        // endDate é 2016-03-31 + 1 = 2016-04-01
        // value é -300.00
        // modifier é 3.30
        StretchEntry stretchEntry1 = StretchEntry.criarPorParcela("", 1, totalParcelas, valorParcela, Instant.parse("2016-01-01").toDate());

        assertEquals(Instant.parse("2016-01-01").toDate(), stretchEntry1.startDate());
        assertEquals(Instant.parse("2016-04-01").toDate(), stretchEntry1.endDate());
        assertEquals(-300.00, stretchEntry1.value().getAmount().doubleValue(), 1f);
        assertEquals(-3.30, stretchEntry1.vd().getAmount().doubleValue(), 1f);

        // 5 parcelas
        // 4 parcela atual
        // valor 500
        // modifier -100.00
        // refDate 2016-04-05
        // startDate 2016-01-1
        // endDate 2016-07-01
        StretchEntry stretchEntry2 = StretchEntry.criarPorParcela("", 4, 5, -100.00, Instant.parse("2016-04-01").toDate());

        assertEquals(Instant.parse("2016-01-01").toDate(), stretchEntry2.startDate());
        assertEquals(Instant.parse("2016-06-01").toDate(), stretchEntry2.endDate());
        assertEquals(-500.00, stretchEntry2.value().getAmount().doubleValue(), 1f);
        assertEquals(-3.29, stretchEntry2.vd().getAmount().doubleValue(), 1f);
    }

}