//package br.com.budgetpunk.bpcore;
//
//import br.com.budgetpunk.bpcore.entity.EntryEntity;
//import com.br.widgettest.core.entity.BuyEntryEntity;
//import com.br.widgettest.core.entity.DailyEntryEntity;
//import com.br.widgettest.core.entity.FixedEntryEntity;
//
//import org.joda.money.CurrencyUnit;
//import org.joda.money.Money;
//
//import java.math.RoundingMode;
//import com.br.widgettest.core.Entry;
//import java.util.Date;
//
///**
// * Created by Breno on 1/8/2016.
// */
//public abstract class Entry {
//
//    public enum EntryType {
//        DAILY,
//        FIXED,
//        BOUGHT
//    }
//
//    public static final CurrencyUnit CU = CurrencyUnit.USD;
//
//    private String name;
//    private Money value;
//    private Date startDate;
//    private Date endDate;
//    private Category category;
//    private EntryType entryType;
//    private EntryEntity origin;
//
//    public Entry(String name, Double value, Date startDate, Date endDate, Category category, EntryType entryType) {
//        this.name = name;
//        this.value = Money.of(CU, value, RoundingMode.HALF_EVEN);
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.category = category;
//        this.entryType = entryType;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Money getValue() {
//        return value;
//    }
//
//    public Date getStartDate() {
//        return startDate;
//    }
//
//    public Date getEndDate() {
//        return endDate;
//    }
//
//    public Category getCategory() {
//        return category;
//    }
//
//    public EntryType getEntryType() {
//        return entryType;
//    }
//
//    public void setOrigin(EntryEntity origin) {
//        this.origin = origin;
//    }
//
//    public EntryEntity toEntity() {
//        if (origin != null) {
//            fillEntityFields(origin);
//            return origin;
//        } else {
//            EntryEntity entryEntity;
//            switch (entryType) {
//                case DAILY:
//                    entryEntity = new DailyEntryEntity();
//                    break;
//                case FIXED:
//                    entryEntity = new FixedEntryEntity();
//                    break;
//                case BOUGHT:
//                    entryEntity = new BuyEntryEntity();
//                    break;
//                default: throw new IllegalArgumentException();
//            }
//            fillEntityFields(entryEntity);
//            setOrigin(entryEntity);
//            return entryEntity;
//        }
//    }
//
//    private void fillEntityFields(EntryEntity entryEntity) {
//        entryEntity.setName(getName());
//        entryEntity.setValue(getValue().getAmount().doubleValue());
//        entryEntity.setStartDate(getStartDate());
//        entryEntity.setEndDate(getEndDate());
//        entryEntity.setEntryType(getEntryType());
//        entryEntity.setCategoryId(Category.getCategories().indexOf(category));
//    }
//
//    //TODO: encapsulate modifiable?
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setValue(Money value) {
//        this.value = value;
//    }
//
//    public void setStartDate(Date startDate) {
//        this.startDate = startDate;
//    }
//
//    public void setEndDate(Date endDate) {
//        this.endDate = endDate;
//    }
//
//    public void setCategory(Category category) {
//        this.category = category;
//    }
//
//    public void setEntryType(EntryType entryType) {
//        this.entryType = entryType;
//    }
//}
