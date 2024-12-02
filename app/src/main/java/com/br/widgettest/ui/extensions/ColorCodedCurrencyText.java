package com.br.widgettest.ui.extensions;

public class ColorCodedCurrencyText extends ColoredText {

    public ColorCodedCurrencyText(CurrencyFormattedText currencyFormattedText) {
        super(
                currencyFormattedText,
                currencyFormattedText.getValue() >= 0 ? ColorCode.Positive : ColorCode.Negative
        );
    }
}
