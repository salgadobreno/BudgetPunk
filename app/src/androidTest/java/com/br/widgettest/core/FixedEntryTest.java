package com.br.widgettest.core;

import junit.framework.TestCase;

import org.joda.money.Money;

/**
 * Created by Breno on 4/16/2016.
 */
public class FixedEntryTest extends TestCase {

    FixedEntry fixedEntry = new FixedEntry("teste", 500d, null);

    public void testValue() throws Exception {
        assertEquals(Money.of(Entry.CU, 500d), fixedEntry.getValue());
    }

    public void testMonthlyValue() throws Exception {
        assertEquals(Money.of(Entry.CU, 16.67d), fixedEntry.getMonthlyValue());
    }
}