package com.br.widgettest.ui.extensions.cli.widgets;

import android.content.Context;

import com.br.widgettest.ui.extensions.ColorCodedCurrencyText;
import com.br.widgettest.ui.extensions.CurrencyFormattedText;

public class SummaryWithBalanceViewWidget extends CliBaseAggregateWidget {

    public SummaryWithBalanceViewWidget(Context context, double total, double previous) {
        super(context);

        SplitTextViewWidget upperSeparator = new SplitTextViewWidget(context, " ", "------------");
        SplitTextViewWidget total1 = new SplitTextViewWidget(context, "TOTAL:", new ColorCodedCurrencyText(new CurrencyFormattedText(total, false)));
        SplitTextViewWidget prevBalance = new SplitTextViewWidget(context, "PREV BALANCE:", new ColorCodedCurrencyText(new CurrencyFormattedText(previous, false)));
        SplitTextViewWidget separator = new SplitTextViewWidget(context, " ", "------------");
        SplitTextViewWidget finalBalance = new SplitTextViewWidget(context, "BALANCE:", new ColorCodedCurrencyText(new CurrencyFormattedText(total + previous, false)));

        getWidgets().add(upperSeparator);
        getWidgets().add(total1);
        getWidgets().add(prevBalance);
        getWidgets().add(separator);
        getWidgets().add(finalBalance);
    }

}
