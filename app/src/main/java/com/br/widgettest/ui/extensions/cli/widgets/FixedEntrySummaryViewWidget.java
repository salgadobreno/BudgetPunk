package com.br.widgettest.ui.extensions.cli.widgets;

import android.content.Context;

import com.br.widgettest.ui.extensions.ColorCodedCurrencyText;
import com.br.widgettest.ui.extensions.CurrencyFormattedText;

public class FixedEntrySummaryViewWidget extends CliBaseAggregateWidget {

    public FixedEntrySummaryViewWidget(Context context, double fixedVd, double modifier) {
        super(context);

        getWidgets().add(new DashedTextViewWidget(context));
        getWidgets().add(new CliTextViewWidget(context, "V/D:"));
        getWidgets().add(new SplitTextViewWidget(context, "- Fixed V/D:", new ColorCodedCurrencyText(new CurrencyFormattedText(fixedVd, true))));
        getWidgets().add(new SplitTextViewWidget(context, "- MODIFIER:", new ColorCodedCurrencyText(new CurrencyFormattedText(modifier, true))));
        getWidgets().add(new SplitTextViewWidget(context, " ", "----------")); //TODO
        getWidgets().add(new SplitTextViewWidget(context, " ", new ColorCodedCurrencyText(new CurrencyFormattedText(fixedVd + modifier, true))));
    }

}
