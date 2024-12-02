package br.com.budgetpunk.bpcorem;

import org.joda.money.Money;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BudgetEntryTest {

    BudgetEntry budgetEntry = new BudgetEntry("teste", 500d);

    @Test
    public void testValue() {
        assertEquals(Money.of(Entry.CU, 500d), budgetEntry.value());
    }

    @Test
    public void testVd() {
        assertEquals(Money.of(Entry.CU, 16.67d), budgetEntry.vd());
    }

}