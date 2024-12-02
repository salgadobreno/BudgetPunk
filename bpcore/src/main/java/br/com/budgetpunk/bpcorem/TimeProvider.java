package br.com.budgetpunk.bpcorem;

import org.joda.time.DateTime;

import java.util.Date;

public class TimeProvider implements ITimeProvider {

    @Override
    public Date now() {
        return DateTime.now().toDate();
    }
}
