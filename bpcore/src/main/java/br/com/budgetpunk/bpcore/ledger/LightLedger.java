//package br.com.budgetpunk.bpcore.ledger;
//
//import br.com.budgetpunk.bpcore.Entry;
//import br.com.budgetpunk.bpcore.ILedger;
//import com.br.widgettest.core.dao.EntryDao;
//
//import org.joda.money.Money;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by Breno on 9/25/2017.
// */
//public class LightLedger implements ILedger {
//    private final EntryDao entryDao;
//
//    public LightLedger(EntryDao entryDao) {
//        this.entryDao = entryDao;
//    }
//
//    @Override
//    public Money calcAvailableFromFixed() {
//        return null;
//    }
//
//    @Override
//    public Money calcDailyAvailable(Date date) {
//        return null;
//    }
//
//    @Override
//    public Money calcMonthModifier(Date date) {
//        return null;
//    }
//
//    @Override
//    public List<? extends Entry> getEntries(Entry.EntryType entryType) {
//        return null;
//    }
//
//    @Override
//    public void add(Entry entry) {
//        entryDao.save(entry);
//    }
//
//    @Override
//    public void rm(Entry entry) {
//
//    }
//}
