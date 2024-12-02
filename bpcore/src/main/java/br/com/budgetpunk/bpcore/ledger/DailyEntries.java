//package br.com.budgetpunk.bpcore.ledger;
//
//import br.com.budgetpunk.bpcore.DailyEntry;
//import br.com.budgetpunk.bpcore.Entry;
//
//import org.joda.money.Money;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
///**
// * Created by Breno on 1/17/2016.
// */
//class DailyEntries extends ArrayList<DailyEntry> {
//
//    public DailyEntries(Collection<DailyEntry> collection) {
//        super(collection);
//    }
//
//    public DailyEntries() {
//    }
//
//    public Money getDailySpent() {
//        Money value = Money.zero(Entry.CU);
//
//        for (Entry e : this) {
//            value = value.plus(e.getValue());
//        }
//
//        return value;
//    }
//}
