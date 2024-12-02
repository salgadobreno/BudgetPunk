package com.br.widgettest.ui.extensions.cli;

import android.content.Context;
import android.widget.LinearLayout;

public class CliSummary extends LinearLayout {
    private final CliSeparator topSeparator;
    private final CliSeparator bottomSeparator;
    private final CliFinancialEntryView total;

    public CliSummary(Context context, String leftStr, String rightStr) {
        super(context);
        setOrientation(VERTICAL);
        topSeparator = new CliSeparator(context);
        bottomSeparator = new CliSeparator(context);
        total = new CliFinancialEntryView(context, leftStr, rightStr);

        addView(topSeparator);
        addView(total);
        addView(bottomSeparator);
    }

    public CliSummary(Context context, String summary) {
        this(context, "SUMMARY:", summary);
    }
}
