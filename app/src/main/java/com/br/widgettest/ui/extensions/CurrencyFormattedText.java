package com.br.widgettest.ui.extensions;

import org.joda.money.Money;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Breno on 1/23/2016.
 */
public class CurrencyFormattedText implements CharSequence {
    private String formatted;
    private final Double value;

    public CurrencyFormattedText(Money value) {
        this(value.getAmount().doubleValue());
    }

    public CurrencyFormattedText(Double value) {
        this(value, false);
    }


    public CurrencyFormattedText(Double value, boolean includeByDay) {
        this.value = value;
        this.formatted = NumberFormat.getCurrencyInstance().format(value);
        if(includeByDay) this.formatted += "/d";
    }

    @Override
    public int length() {
        return formatted.length();
    }

    @Override
    public char charAt(int index) {
        return formatted.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return formatted.subSequence(start, end);
    }

    @Override
    public String toString() {
        return formatted;
    }

    public Double getValue() {
        return value;
    }
}
