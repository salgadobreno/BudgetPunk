package com.br.widgettest.core;

import junit.framework.TestCase;

import org.joda.time.Instant;

import java.math.BigDecimal;

/**
 * Created by Breno on 4/10/2016.
 */
public class BuyEntryTest extends TestCase {

    BuyEntry buyEntry = new BuyEntry("teste", -200d, Instant.parse("2010-02-01").toDate(), Instant.parse("2010-01-01").toDate(), null);

    public void testGetValue() throws Exception {
        assertEquals(-200d, buyEntry.getValue());
    }

    public void testGetModifier() throws Exception {
        assertEquals(-6.45d, buyEntry.getModifier());
    }

    public void testGetPeriod() throws Exception {
        assertEquals("jan/2010 - fev/2010", buyEntry.getPeriod());
    }

    public void testMonthsAreTreatedAsFull() throws Exception {

    }

}