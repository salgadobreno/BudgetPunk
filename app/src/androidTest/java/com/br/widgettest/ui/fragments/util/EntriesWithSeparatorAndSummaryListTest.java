package com.br.widgettest.ui.fragments.util;

import com.br.widgettest.core.Category;
import com.br.widgettest.core.DailyEntry;
import com.br.widgettest.core.Entry;

import junit.framework.TestCase;

import org.joda.time.LocalDate;

import java.util.Arrays;

/**
 * Created by Breno on 6/22/2016.
 */
public class EntriesWithSeparatorAndSummaryListTest extends TestCase {
    private final double dailyAvailable = 50d;
    Entry[] entries;
    EntriesWithSeparatorAndSummaryList list;

    @Override
    public void setUp() throws Exception {
        entries = new Entry[] {
                new DailyEntry(20d, Category.BILLS, new LocalDate(2016, 6, 22).toDate()),
                new DailyEntry(20d, Category.BILLS, new LocalDate(2016, 6, 22).toDate()),
                new DailyEntry(20d, Category.BILLS, new LocalDate(2016, 6, 24).toDate()),
                new DailyEntry(20d, Category.BILLS, new LocalDate(2016, 6, 26).toDate()),
        };
        //23 and 25 should be in list
        //list should:
        //0 - 2016-06-22
        //1 - entry
        //2 - entry
        //3 - summary
        //4 - 2016-06-23
        //5 - summary
        //6 - 2016-06-24
        //7 - entry
        //8 - summary
        //9 - 2016-06-25
        //10 - summary
        //11 - 2016-06-26
        //12 - entry
        //13 - summary
        list = new EntriesWithSeparatorAndSummaryList(Arrays.asList(entries), dailyAvailable);
    }

    public void testInBetweenDaysShouldBeInList() throws Exception {
//        assertTrue(list.size() == 14);
        assertEquals(list.getEntryViewType(0), EntriesWithSeparatorAndSummaryList.EntryViewType.DATE_SEPARATOR);
        assertEquals(list.getEntryViewType(4), EntriesWithSeparatorAndSummaryList.EntryViewType.DATE_SEPARATOR);
        assertEquals(list.getEntryViewType(6), EntriesWithSeparatorAndSummaryList.EntryViewType.DATE_SEPARATOR);
        assertEquals(list.getEntryViewType(9), EntriesWithSeparatorAndSummaryList.EntryViewType.DATE_SEPARATOR);
        assertEquals(list.getEntryViewType(11), EntriesWithSeparatorAndSummaryList.EntryViewType.DATE_SEPARATOR);
        assertTrue(list.get(0).equals(new DailyDate(new LocalDate(2016, 6, 22).toDate().getTime())));
        assertTrue(list.get(4).equals(new DailyDate(new LocalDate(2016, 6, 23).toDate().getTime())));
        assertTrue(list.get(6).equals(new DailyDate(new LocalDate(2016, 6, 24).toDate().getTime())));
        assertTrue(list.get(9).equals(new DailyDate(new LocalDate(2016, 6, 25).toDate().getTime())));
        assertTrue(list.get(11).equals(new DailyDate(new LocalDate(2016, 6, 26).toDate().getTime())));
    }

    public void testDaysWithNoEntriesShouldAssumeDailyValue() throws Exception {
        // otherwise a missed day makes the lazy guy richer.
        // Default is to assume he missed and suppose the daily available was spent so at least
        // it's close
        //3 - summary
        //4 - 2016-06-23
        //5 - summary
        assertEquals(EntriesWithSeparatorAndSummaryList.EntryViewType.SUMMARY, list.getEntryViewType(5));
        //balance remains same as day before
        double dayBeforeBalance = ((Double[])list.get(3))[1];
        org.junit.Assert.assertArrayEquals(new Double[]{-dailyAvailable, dayBeforeBalance}, (Double[]) list.get(5));
    }
}