package com.br.widgettest.ui.fragments.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Data que retorna 0 na comparação quando a outra data pertence ao mesmo mês/ano
 */
public class MonthlyDate extends Date {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

    public MonthlyDate(long milliseconds) {
        super(milliseconds);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;

        return object instanceof Date && this.hashCode() == object.hashCode();

    }

    @Override
    public int hashCode() {
        return sdf.format(this).hashCode();
    }
}
