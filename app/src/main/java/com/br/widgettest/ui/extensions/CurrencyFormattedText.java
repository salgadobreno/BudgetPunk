package com.br.widgettest.ui.extensions;

import java.text.NumberFormat;

/**
 * Created by Breno on 1/23/2016.
 */
public class CurrencyFormattedText implements CharSequence {
    private String formatted;

    public CurrencyFormattedText(Double formatted) {
        this.formatted = NumberFormat.getCurrencyInstance().format(formatted);
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
}
