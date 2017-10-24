package com.br.widgettest.ui.fragments.util;

import com.br.widgettest.core.Category;
import com.br.widgettest.core.DailyEntry;
import com.br.widgettest.core.Entry;

import junit.framework.TestCase;

import org.joda.time.LocalDate;
import org.w3c.dom.EntityReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by Breno on 6/22/2016.
 */
public class EntriesWithSeparatorAndSummaryListTest extends TestCase {
    private final double dailyAvailable = 50d;
    Entry[] entries;
    EntriesWithSeparatorAndSummaryList list;

    @Override
    public void setUp() throws Exception {
        //User missed 23 and 25
        entries = new Entry[] {
                new DailyEntry(20d, Category.BILLS, new LocalDate(2016, 6, 22).toDate()),
                new DailyEntry(20d, Category.BILLS, new LocalDate(2016, 6, 22).toDate()),
                new DailyEntry(20d, Category.BILLS, new LocalDate(2016, 6, 24).toDate()),
                new DailyEntry(20d, Category.BILLS, new LocalDate(2016, 6, 26).toDate()),
        };
        //23 and 25 should be in list
        //list should:
        //0  - 2016-06-22
        //1  - Daily IN
        //2  - Entry
        //3  - Entry
        //4  - Summary
        //5  - 2016-06-23
        //6  - Daily IN
        //7  - Daily OUT
        //8  - Summary
        //9  - 2016-06-24
        //10 - Daily IN
        //11 - Entry
        //12 - Summary
        //13 - 2016-06-25
        //14 - Daily IN
        //15 - Daily OUT
        //16 - Summary
        //17 - 2016-06-26
        //18 - Daily IN
        //19 - Entry
        //20 - Summary
        list = new EntriesWithSeparatorAndSummaryList(Arrays.asList(entries), dailyAvailable);
    }

    public void testInBetweenDaysShouldBeInList() throws Exception {
        assertEquals(EntriesWithSeparatorAndSummaryList.EntryViewType.DATE_SEPARATOR, list.getEntryViewType(0));
        assertEquals(EntriesWithSeparatorAndSummaryList.EntryViewType.DATE_SEPARATOR, list.getEntryViewType(5));
        assertEquals(EntriesWithSeparatorAndSummaryList.EntryViewType.DATE_SEPARATOR, list.getEntryViewType(9));
        assertEquals(EntriesWithSeparatorAndSummaryList.EntryViewType.DATE_SEPARATOR, list.getEntryViewType(13));
        assertEquals(EntriesWithSeparatorAndSummaryList.EntryViewType.DATE_SEPARATOR, list.getEntryViewType(17));
        assertEquals(list.get(0), new DailyDate(new LocalDate(2016, 6, 22).toDate().getTime()));
        assertEquals(list.get(5), new DailyDate(new LocalDate(2016, 6, 23).toDate().getTime()));
        assertEquals(list.get(9), new DailyDate(new LocalDate(2016, 6, 24).toDate().getTime()));
        assertEquals(list.get(13), new DailyDate(new LocalDate(2016, 6, 25).toDate().getTime()));
        assertEquals(list.get(17), new DailyDate(new LocalDate(2016, 6, 26).toDate().getTime()));
    }

    public void testDailyInTransactionsInList() throws Exception {
        //1  - Daily IN
        //6  - Daily IN
        //10 - Daily IN
        //14 - Daily IN
        //18 - Daily IN
//        assertEquals(list.getEntryViewType(2), EntriesWithSeparatorAndSummaryList.EntryViewType.DAILY_IN);
//        assertEquals(list.getEntryViewType(7), EntriesWithSeparatorAndSummaryList.EntryViewType.DAILY_IN);
//        assertEquals(list.getEntryViewType(11), EntriesWithSeparatorAndSummaryList.EntryViewType.DAILY_IN);
//        assertEquals(list.getEntryViewType(15), EntriesWithSeparatorAndSummaryList.EntryViewType.DAILY_IN);
//        assertEquals(list.getEntryViewType(19), EntriesWithSeparatorAndSummaryList.EntryViewType.DAILY_IN);
        assertEquals(EntriesWithSeparatorAndSummaryList.EntryViewType.ENTRY, list.getEntryViewType(1));
        assertEquals(EntriesWithSeparatorAndSummaryList.EntryViewType.ENTRY, list.getEntryViewType(6));
        assertEquals(EntriesWithSeparatorAndSummaryList.EntryViewType.ENTRY, list.getEntryViewType(10));
        assertEquals(EntriesWithSeparatorAndSummaryList.EntryViewType.ENTRY, list.getEntryViewType(14));
        assertEquals(EntriesWithSeparatorAndSummaryList.EntryViewType.ENTRY, list.getEntryViewType(18));
        assertEquals(dailyAvailable, ((Entry)list.get(18)).getValue().getAmount().doubleValue());
    }

    public void testDailyOutTransactionsInList() throws Exception {
        //7  - Daily OUT
        //15 - Daily OUT
//        assertEquals(list.getEntryViewType(8), EntriesWithSeparatorAndSummaryList.EntryViewType.DAILY_OUT);
//        assertEquals(list.getEntryViewType(16), EntriesWithSeparatorAndSummaryList.EntryViewType.DAILY_OUT);
        assertEquals(EntriesWithSeparatorAndSummaryList.EntryViewType.ENTRY, list.getEntryViewType(7));
        assertEquals(EntriesWithSeparatorAndSummaryList.EntryViewType.ENTRY, list.getEntryViewType(15));
        assertEquals((dailyAvailable) * -1, ((Entry)list.get(15)).getValue().getAmount().doubleValue());
    }

    public void testDaysWithNoEntriesShouldAssumeDailyValue() throws Exception {
        // otherwise a missed day makes the lazy guy richer.
        // Default is to assume he missed and suppose the daily available was spent so at least
        // it's close
        // TODO: make this a config option?
        // TODO: this shit should go elsewhere
        //5  - 2016-06-23
        //6  - Daily IN
        //7  - Daily OUT
        //8  - Summary
        assertEquals(EntriesWithSeparatorAndSummaryList.EntryViewType.SUMMARY, list.getEntryViewType(8));
        //balance remains same as day before
        double dayBeforeBalance = ((Double[])list.get(4))[1];
        assertArrayEquals(new Double[]{0d, dayBeforeBalance}, (Double[]) list.get(8));
    }

    public void testDoesNotAddDailyOutForToday() throws Exception {
        Entry entry = new DailyEntry(10d, null, LocalDate.now().minusDays(2).toDate());
        List<Entry> entries = new ArrayList<>();
        entries.add(entry);
        list = new EntriesWithSeparatorAndSummaryList(entries, 50d);

        //0  - Date
        //1  - Daily IN
        //2  - Entry
        //3  - Summary
        //4  - Date
        //5  - Daily IN
        //6  - Daily OUT
        //7  - Summary
        //8  - Today
        //9  - Daily IN
        //10 - Summary
        assertEquals(-50d, ((Entry) list.get(6)).getValue().getAmount().doubleValue());
        assertEquals(50d, ((Entry) list.get(9)).getValue().getAmount().doubleValue());
        assertEquals(11, list.size());
    }
}