package com.br.widgettest.ui.extensions.cli.widgets;

import android.content.Context;

import com.br.widgettest.ui.extensions.ColorCodedCurrencyText;
import com.br.widgettest.ui.extensions.CurrencyFormattedText;

public class FixedEntrySumViewWidget extends CliBaseAggregateWidget {

    public FixedEntrySumViewWidget(Context context, double total) {
        super(context);

        getWidgets().add(new SplitTextViewWidget(context, " ", "-----------")); //TODO
        getWidgets().add(new SplitTextViewWidget(context, "TOTAL:", new ColorCodedCurrencyText(new CurrencyFormattedText(total, true))));
    }
}
