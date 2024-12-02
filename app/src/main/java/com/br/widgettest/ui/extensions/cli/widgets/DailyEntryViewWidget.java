package com.br.widgettest.ui.extensions.cli.widgets;

import android.content.Context;

import com.br.widgettest.core.DailyEntry;
import com.br.widgettest.ui.extensions.ColorCodedCurrencyText;
import com.br.widgettest.ui.extensions.CurrencyFormattedText;

import java.text.SimpleDateFormat;

public class DailyEntryViewWidget extends SplitTextViewWidget {

    public DailyEntryViewWidget(Context context, DailyEntry dailyEntry) {
        super(context,
                new SimpleDateFormat("dd/MM").format(dailyEntry.getStartDate()),
                new ColorCodedCurrencyText(new CurrencyFormattedText(dailyEntry.getValue()))
        );
    }

}
