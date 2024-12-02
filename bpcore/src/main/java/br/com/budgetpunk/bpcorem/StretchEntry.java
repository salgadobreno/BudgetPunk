package br.com.budgetpunk.bpcorem;

import org.joda.money.Money;
import org.joda.time.*;

import java.math.RoundingMode;
import java.util.Date;

/**
 * Created by Breno on 1/28/2016.
 */
public class StretchEntry extends Entry implements IStretchEntry {
    private static final IEntry.EntryType ENTRY_TYPE = IEntry.EntryType.STRETCH;

    public StretchEntry(String name, Double value, Date startDate, Date endDate) {
        super(name, value, startDate, endDate, ENTRY_TYPE);

        if (new Instant(startDate.getTime()).isAfter(new Instant(endDate.getTime()))) {
            throw new IllegalArgumentException("StartDate is after EndDate");
        }
        // startDate and endDate should be on first days of months
        if (new Instant(startDate.getTime()).get(DateTimeFieldType.dayOfMonth()) != 1 ||
            new Instant(endDate.getTime()).get(DateTimeFieldType.dayOfMonth()) != 1) {
            throw new IllegalArgumentException("Only full months are allowed for BuyEntries");
        }
    }

    public static StretchEntry criarPorParcela(String name, int parcela, int totalParcelas, double valorParcela, Date referenceDate) {
        //TODO: mudar pra fabrica/forma descritiva de criação?
        double totalValue = totalParcelas * valorParcela;
        int monthsBack = parcela - 1;
        int monthsForward = totalParcelas - parcela;
        Date startDate = new LocalDate(referenceDate.getTime()).minusMonths(monthsBack).withDayOfMonth(1).toDate();
        Date endDate = new LocalDate(referenceDate.getTime()).plusMonths(monthsForward + 1).withDayOfMonth(1).toDate();

        return new StretchEntry(name, totalValue, startDate, endDate);
    }

    @Override
    public Interval period() {
        return new Interval(startDate().getTime(), endDate().getTime());
//        SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
//        Instant start = new Instant(getStartDate().getTime());
//        Instant end = new Instant(getEndDate().getTime());
//
//        if (start.get(DateTimeFieldType.year()) == end.get(DateTimeFieldType.year())) {
//            if (start.get(DateTimeFieldType.monthOfYear()) - end.get(DateTimeFieldType.monthOfYear()) == -1) {
//                return sdf.format(getStartDate());
//            } else {
//                return sdf.format(getStartDate()) + " - " + sdf.format(new LocalDate(getEndDate().getTime()).minusDays(1).toDate());
//            }
//        } else {
//            return sdf.format(getStartDate()) + " - " + sdf.format(new LocalDate(getEndDate().getTime()).minusDays(1).toDate());
//        }
    }

    @Override
    public Money vd() {
        return value().dividedBy(period().toDuration().getStandardDays(), RoundingMode.HALF_EVEN);
    }

    public Installments installments() {
        return new Installments() {
            @Override
            public int current() {
                if (Instant.now().isAfter(new Instant(endDate()))) return total();
                return new DateTime().monthOfYear().getDifference(new DateTime(startDate())) + 1;
            }

            @Override
            public int total() {
                return period().toPeriod().getMonths();
            }
        };
    }

}
