//package br.com.budgetpunk.bpcore.ledger;
//
//import br.com.budgetpunk.bpcore.Entry;
//import br.com.budgetpunk.bpcore.FixedEntry;
//
//import org.joda.money.Money;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
///**
// * Created by Breno on 1/17/2016.
// */
//class FixedEntries extends ArrayList<FixedEntry> {
//
//    public FixedEntries(Collection<FixedEntry> collection) {
//        super(collection);
//    }
//
//    public FixedEntries() {}
//
//    public Money getDailyValue() {
//        Money value = Money.zero(Entry.CU);
//
//        for (FixedEntry e : this) {
//            value = value.plus(e.getMonthlyValue());
//        }
//
//        return value;
//    }
//}
