package com.br.widgettest.core.ledger;

import com.br.widgettest.ApplicationTest;
import com.br.widgettest.core.BuyEntry;
import com.br.widgettest.core.Category;
import com.br.widgettest.core.DailyEntry;
import com.br.widgettest.core.Entry;
import com.br.widgettest.core.FixedEntry;
import com.br.widgettest.core.ILedger;
import com.br.widgettest.core.dao.CategoryDao;
import com.br.widgettest.core.dao.EntryDao;

import org.joda.money.Money;
import org.joda.time.Days;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by Breno on 4/16/2016.
 */
public class LedgerTest extends ApplicationTest {
    ILedger ledger;
    EntryDao mockEntryDao;
    CategoryDao mockCategoryDao;

    @Override
    public void setUp() throws Exception {
        System.setProperty("dexmaker.dexcache", getContext().getCacheDir().getPath());

        List<Entry> entries = new ArrayList<>();
        entries.add(new FixedEntry("teste", -200d, null));
        entries.add(new BuyEntry("compra", -200d,
                new LocalDate(2016, 1, 1).toDate(),
                new LocalDate(2016, 2, 1).toDate(),
                null));

        mockEntryDao = mock(EntryDao.class);
        when(mockEntryDao.list()).thenReturn(entries);

        mockCategoryDao = mock(CategoryDao.class);

        ledger = new Ledger(mockEntryDao, mockCategoryDao);
    }

    public void testCalcDailyAvailable() throws Exception {
        assertEquals(Money.of(Entry.CU, -13.12d), ledger.calcDailyAvailable(new LocalDate(2016, 1, 2).toDate()));
    }

    public void testCalcMonthModifier() throws Exception {
        assertEquals(Money.of(Entry.CU, -6.45d), ledger.calcMonthModifier(new LocalDate(2016, 1, 2).toDate()));
    }

    public void testCalcAvailableFromFixed() throws Exception {
        assertEquals(Money.of(Entry.CU, -6.67d), ledger.calcAvailableFromFixed());
    }

    public void testMonthModifierDoesntSpillToNextMonth() {
        //04-01 ate 05-01: 31 dias
        //modifier de 2016-05 "não deve" ter modifier do mês anterior
        BuyEntry buyEntry = new BuyEntry("entry vaza", 30d, Instant.parse("2016-04-01").toDate(), Instant.parse("2016-05-01").toDate(), null);
        BuyEntry buyEntry2 = new BuyEntry("entry vaza", 62d, Instant.parse("2016-05-01").toDate(), Instant.parse("2016-06-01").toDate(), null);
        List<Entry> entries = new ArrayList<>();
        entries.add(buyEntry);
        entries.add(buyEntry2);
        when(mockEntryDao.list()).thenReturn(entries);
        ledger = new Ledger(mockEntryDao, mockCategoryDao);

        assertEquals(Money.of(Entry.CU, 1), ledger.calcMonthModifier(Instant.parse("2016-04-01").toDate()));
        assertEquals(Money.of(Entry.CU, 1), ledger.calcMonthModifier(Instant.parse("2016-04-30").toDate()));
        assertEquals(Money.of(Entry.CU, 2), ledger.calcMonthModifier(Instant.parse("2016-05-02").toDate()));
        assertEquals(Money.of(Entry.CU, 2), ledger.calcMonthModifier(Instant.parse("2016-05-01").toDate()));
    }

    public void testRemove() throws Exception {
        DailyEntry dailyEntry = new DailyEntry(20d, Category.BILLS);

        ledger.add(dailyEntry);
        assertTrue(ledger.getEntries(Entry.EntryType.DAILY).contains(dailyEntry));
        ledger.rm(dailyEntry);
        assertFalse(ledger.getEntries(Entry.EntryType.DAILY).contains(dailyEntry));

        verify(mockEntryDao, atLeastOnce()).remove(dailyEntry);
    }
}