package com.br.widgettest.ui.extensions.cli.widgets;

import android.content.Context;

import com.br.widgettest.ui.extensions.ColoredText;
import com.br.widgettest.ui.extensions.CurrencyFormattedText;

public class ModifierEntryViewWidget extends CliBaseAggregateWidget {

    public ModifierEntryViewWidget(Context context, double v) {
        super(context);

        getWidgets().add(new DashedTextViewWidget(context));
        getWidgets().add(
            new SplitTextViewWidget(
                context,
                "PREV MOD:",
                new ColoredText(
                    new CurrencyFormattedText(v, true),
                    v >= 0 ? ColoredText.ColorCode.Positive : ColoredText.ColorCode.Negative
                )
            )
        );
    }
}
