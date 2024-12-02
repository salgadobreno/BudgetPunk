//package br.com.budgetpunk.bpcore.ledger;
//
//import br.com.budgetpunk.bpcore.BuyEntry;
//import br.com.budgetpunk.bpcore.Entry;
//
//import org.joda.money.Money;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;
//
///**
// * Created by Breno on 1/28/2016.
// */
//class BuyEntries extends ArrayList<BuyEntry> {
//    public BuyEntries() {}
//
//    public BuyEntries(Collection<BuyEntry> collection) {
//        super(collection);
//    }
//
//    public Money getDailyBuysModifier(Date date) {
//        DateFilteredEntries dateFilteredEntries = new DateFilteredEntries(this, date);
//        Money value = Money.zero(Entry.CU);
//
//        for (BuyEntry e : dateFilteredEntries) {
//            value = value.plus(e.getModifier());
//        }
//
//        return value;
//    }
//}
//
//class DateFilteredEntries extends ArrayList<BuyEntry> {
//    public DateFilteredEntries(Collection<BuyEntry> collection, Date date) {
//        for (BuyEntry e : collection) {
//            if (date.compareTo(e.getStartDate()) >= 0 && date.compareTo(e.getEndDate()) < 0 ) {
//                add(e);
//            }
//        }
//    }
//}
