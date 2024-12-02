package com.br.widgettest.ui.extensions.cli.widgets;

import android.content.Context;

import com.br.widgettest.core.FixedEntry;
import com.br.widgettest.ui.extensions.ColorCodedCurrencyText;
import com.br.widgettest.ui.extensions.CurrencyFormattedText;

public class FixedEntryViewWidget extends CliBaseAggregateWidget {

    public FixedEntryViewWidget(Context context, FixedEntry fixedEntry) {
        super(context);

        getWidgets().add(new SplitTextViewWidget(context, fixedEntry.getName(), new ColorCodedCurrencyText(new CurrencyFormattedText(fixedEntry.getValue()))));
        getWidgets().add(new SplitTextViewWidget(context, " ", new ColorCodedCurrencyText(new CurrencyFormattedText(fixedEntry.getMonthlyValue().getAmount().doubleValue(), true))));
    }

}
