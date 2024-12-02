package br.com.budgetpunk.bpcorem;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DailyEntriesTest {
    IDailyEntries dailyEntries;

    @Before
    public void setUp() {
        List<IDailyEntry> entries = new ArrayList<>();
        entries.add(new DailyEntry(-200d));
        entries.add(new DailyEntry(100d));
        entries.add(new DailyEntry(50d));

        dailyEntries = new DailyEntries(entries);
    }

    @Test
    public void testGetBalance() {
        assertEquals(-50d, dailyEntries.balance().getAmount().doubleValue(), 0.0);
    }

    //    @Test
//    public void testCalcDailyAvailable() throws Exception {
//        assertEquals(Money.of(Entry.CU, -13.12d), ledger.calcDailyAvailable(new LocalDate(2016, 1, 2).toDate()));
//    }
//
//    @Test
//    public void testCalcMonthModifier() throws Exception {
//        assertEquals(Money.of(Entry.CU, -6.45d), ledger.calcMonthModifier(new LocalDate(2016, 1, 2).toDate()));
//    }
//
//    @Test
//    public void testCalcAvailableFromFixed() throws Exception {
//        assertEquals(Money.of(Entry.CU, -6.67d), ledger.calcAvailableFromFixed());
//    }
//
//    @Test
//    public void testMonthModifierDoesntSpillToNextMonth() {
//        //04-01 ate 05-01: 31 dias
//        //modifier de 2016-05 "não deve" ter modifier do mês anterior
//        BuyEntry buyEntry = new BuyEntry("entry vaza", 30d, Instant.parse("2016-04-01").toDate(), Instant.parse("2016-05-01").toDate(), null);
//        BuyEntry buyEntry2 = new BuyEntry("entry vaza", 62d, Instant.parse("2016-05-01").toDate(), Instant.parse("2016-06-01").toDate(), null);
//        List<Entry> entries = new ArrayList<>();
//        entries.add(buyEntry);
//        entries.add(buyEntry2);
//        when(mockEntryDao.list()).thenReturn(entries);
//        ledger = new Ledger(mockEntryDao, mockCategoryDao);
//
//        assertEquals(Money.of(Entry.CU, 1), ledger.calcMonthModifier(Instant.parse("2016-04-01").toDate()));
//        assertEquals(Money.of(Entry.CU, 1), ledger.calcMonthModifier(Instant.parse("2016-04-30").toDate()));
//        assertEquals(Money.of(Entry.CU, 2), ledger.calcMonthModifier(Instant.parse("2016-05-02").toDate()));
//        assertEquals(Money.of(Entry.CU, 2), ledger.calcMonthModifier(Instant.parse("2016-05-01").toDate()));
//    }
//
//    @Test
//    public void testTurnedOffBuysDontAffectModifier() {
////        fail();
//        // TODO: 9/25/2017
//    }
//
//    @Test
//    public void testRemove() throws Exception {
//        DailyEntry dailyEntry = new DailyEntry(20d, Category.BILLS);
//
//        ledger.add(dailyEntry);
//        assertTrue(ledger.getEntries(Entry.EntryType.DAILY).contains(dailyEntry));
//        ledger.rm(dailyEntry);
//        assertFalse(ledger.getEntries(Entry.EntryType.DAILY).contains(dailyEntry));
//
//        verify(mockEntryDao, atLeastOnce()).remove(dailyEntry);
//    }
}
