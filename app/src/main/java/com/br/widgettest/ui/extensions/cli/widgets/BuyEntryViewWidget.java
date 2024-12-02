package com.br.widgettest.ui.extensions.cli.widgets;

import android.content.Context;

import com.br.widgettest.core.BuyEntry;
import com.br.widgettest.ui.extensions.ColoredText;
import com.br.widgettest.ui.extensions.CurrencyFormattedText;

public class BuyEntryViewWidget extends CliBaseAggregateWidget {
    static final String SYM_DONE = "█";
    static final String SYM_TODO = "▒";
    static final String SYM_SEPARATOR = "|";

    public BuyEntryViewWidget(Context context, BuyEntry buyEntry) { //TODO: move to BuyEntry
        super(context);

        getWidgets().add(
            new SplitTextViewWidget(
                context,
                buyEntry.getName(),
                new ColoredText(
                    new CurrencyFormattedText(buyEntry.getValue()) + String.format("/%sm", buyEntry.getTotalParcels()),
                    buyEntry.doubleValue() >= 0 ? ColoredText.ColorCode.Positive : ColoredText.ColorCode.Negative
                )
            )
        );
        getWidgets().add(
            new SplitTextViewWidget(
                context,
                " ",
                new ColoredText(
                    new CurrencyFormattedText(buyEntry.doubleValue(), true),
                    buyEntry.doubleValue() >= 0 ? ColoredText.ColorCode.Positive : ColoredText.ColorCode.Negative //TODO: getModifier double?
                )
            )
        );
        String display = "";
        for (int i = 0; i < buyEntry.getTotalParcels(); i++) {
            if (i < buyEntry.getCurrentParcel()) {
                display += SYM_DONE;
            } else {
                display += SYM_TODO;
            }
            display += SYM_SEPARATOR;
        }
        getWidgets().add(new CliTextViewWidget(context, String.format("%s/%s %s", buyEntry.getCurrentParcel(), buyEntry.getTotalParcels(), display)));

    }
}
