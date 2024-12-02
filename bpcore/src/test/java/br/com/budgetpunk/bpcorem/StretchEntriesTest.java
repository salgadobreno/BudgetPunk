package br.com.budgetpunk.bpcorem;

import org.joda.money.Money;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StretchEntriesTest {
    IStretchEntries stretchEntries;
    Date mockCurrentDate = new Date();
    ITimeProvider mockTimeProvider = new ITimeProvider() {
        @Override
        public Date now() {
            return mockCurrentDate;
        }
    };

    @Before
    public void setUp() throws Exception {
        List<IStretchEntry> list = new ArrayList<>();
        list.add(new StretchEntry("compra",
                300D,
                new LocalDate(2019, 1, 1).toDate(),
                new LocalDate(2019, 4, 1).toDate()
        )); //90 days
        list.add(new StretchEntry("compra 2",
                120D,
                new LocalDate(2019, 2, 1).toDate(),
                new LocalDate(2019, 5, 1).toDate()
        )); //89 days
        list.add(new StretchEntry("compra 3",
                150D,
                new LocalDate(2018, 1, 1).toDate(),
                new LocalDate(2018, 3, 1).toDate()
        )); //59 days
        stretchEntries = new StretchEntries(list, mockTimeProvider);
    }

    @Test
    public void activeThisMonth() {
        mockCurrentDate.setTime(new LocalDate(2019, 1, 1).toDate().getTime());
        assertEquals(1, stretchEntries.activeThisMonth().size());

        mockCurrentDate.setTime(new LocalDate(2019, 2, 1).toDate().getTime());
        assertEquals(2, stretchEntries.activeThisMonth().size());

        mockCurrentDate.setTime(new LocalDate(2019, 12, 1).toDate().getTime());
        assertEquals(0, stretchEntries.activeThisMonth().size());

        mockCurrentDate.setTime(new LocalDate(2018, 2, 1).toDate().getTime());
        assertEquals(1, stretchEntries.activeThisMonth().size());
    }

    @Test
    public void getMonthlyModifier() {
//      1- 300D, 90 days, (2019, 1, 1), (2019, 4, 1), 3.33
//      2- 120D, 89 days, (2019, 2, 1), (2019, 5, 1), 1.35
//      3- 150D, 59 days, (2018, 1, 1), (2018, 3, 1), 2.54
        mockCurrentDate.setTime(new LocalDate(2019, 1, 1).toDate().getTime());
        // 1
        assertEquals(3.33, stretchEntries.getMonthlyModifier().getAmount().doubleValue(), 0.0);

        mockCurrentDate.setTime(new LocalDate(2019, 4, 1).toDate().getTime());
        // 2
        assertEquals(1.35, stretchEntries.getMonthlyModifier().getAmount().doubleValue(), 0.0);

        mockCurrentDate.setTime(new LocalDate(2019, 2, 1).toDate().getTime());
        // 1,2
        assertEquals(4.68, stretchEntries.getMonthlyModifier().getAmount().doubleValue(), 0.0);

        mockCurrentDate.setTime(new LocalDate(2019, 12, 1).toDate().getTime());
        //
        assertEquals(0, stretchEntries.getMonthlyModifier().getAmount().doubleValue(), 0.0);

        mockCurrentDate.setTime(new LocalDate(2018, 2, 1).toDate().getTime());
        // 3
        assertEquals(2.54, stretchEntries.getMonthlyModifier().getAmount().doubleValue(), 0.0);
    }

    //Regression olde test
    @Test
    public void testMonthModifierDoesntSpillToNextMonth() {
        //04-01 ate 05-01: 31 dias
        //modifier de 2016-05 "não deve" ter modifier do mês anterior
        StretchEntry stretchEntry = new StretchEntry("entry vaza", 30d, Instant.parse("2016-04-01").toDate(), Instant.parse("2016-05-01").toDate());
        StretchEntry stretchEntry1 = new StretchEntry("entry vaza", 62d, Instant.parse("2016-05-01").toDate(), Instant.parse("2016-06-01").toDate());
        List<IStretchEntry> entries = new ArrayList<>();
        entries.add(stretchEntry);
        entries.add(stretchEntry1);

        StretchEntries stretchEntries = new StretchEntries(entries, mockTimeProvider);

        mockCurrentDate.setTime(new LocalDate(2016, 4, 1).toDate().getTime());
        assertEquals(Money.of(Entry.CU, 1), stretchEntries.getMonthlyModifier());
        mockCurrentDate.setTime(new LocalDate(2016, 4, 30).toDate().getTime());
        assertEquals(Money.of(Entry.CU, 1), stretchEntries.getMonthlyModifier());
        mockCurrentDate.setTime(new LocalDate(2016, 5, 2).toDate().getTime());
        assertEquals(Money.of(Entry.CU, 2), stretchEntries.getMonthlyModifier());
        mockCurrentDate.setTime(new LocalDate(2016, 5, 1).toDate().getTime());
        assertEquals(Money.of(Entry.CU, 2), stretchEntries.getMonthlyModifier());
    }

}